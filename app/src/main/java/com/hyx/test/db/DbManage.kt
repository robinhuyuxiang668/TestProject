package com.hyx.test.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.hyx.test.entity.TestEntity
import com.hyx.test.util.Constants
import java.util.*

class DbManage private constructor(ctx: Context) {
    /**
     * 插入数据
     */
    fun insert(entity: TestEntity) {
        val db: SQLiteDatabase = mDbHelper!!.writableDatabase
        val cv = ContentValues()
        cv.put(DbHelper.COLUMN_TIME, entity.time)
        cv.put(DbHelper.COLUMN_MESSAGE, entity.message)
        cv.put(DbHelper.COLUMN_DOCUMEENTATION_URL, entity.documentation_url)
        db.insert(DbHelper.TABLE_TEAM, null, cv)
        db.close()
    }

    /**
     * 获取最后一次的数据
     */
    val lastData: TestEntity?get() {
            val db: SQLiteDatabase = mDbHelper!!.readableDatabase
            val cursor: Cursor = db.rawQuery("select * from " + DbHelper.TABLE_TEAM + " ORDER BY " + DbHelper.COLUMN_TIME + " desc limit 1",
                null)
            var entity: TestEntity? = null
            if (cursor.moveToNext()) {
                val time = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_TIME))
                val message: String = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_MESSAGE))
                val documentation_url: String = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_DOCUMEENTATION_URL))
                entity = TestEntity(time, message, documentation_url)
            }
            cursor.close()
            db.close()
            return entity
        }

    /**
     * 分页获取数据
     */
    fun getDatasByPage(page: Int): List<TestEntity> {
        val db: SQLiteDatabase = mDbHelper!!.readableDatabase
        val count = page * Constants.PER_PAGE_COUNT
        val sql = "select * from " + DbHelper.TABLE_TEAM + " ORDER BY " + DbHelper.COLUMN_TIME + " desc limit 10 offset " + count
        val cursor: Cursor = db.rawQuery(sql, null)
        val list: MutableList<TestEntity> = ArrayList()
        while (cursor.moveToNext()) {
            val time = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_TIME))
            val message: String = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_MESSAGE))
            val documentation_url: String = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_DOCUMEENTATION_URL))
            list.add(TestEntity(time, message, documentation_url))
        }
        cursor.close()
        db.close()
        return list
    }

    companion object {
        private var mDbHelper: DbHelper? = null
        private var mInstance: DbManage? = null
        fun getInstance(ctx: Context): DbManage? {
            if (mInstance == null) {
                synchronized( DbManage::class.java) {
                    if (mInstance == null) {
                        mInstance = DbManage(ctx)
                    }
                }
            }
            return mInstance
        }
    }

    init {
        mDbHelper = DbHelper(ctx)
    }
}