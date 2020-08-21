package com.hyx.test.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) :
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE_TEAM)
    }

    override fun onUpgrade(db: SQLiteDatabase,oldVersion: Int,newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TEAM")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "test_project.db"
        private const val DATABASE_VERSION = 1
        const val COLUMN_ID = "_id"
        const val TABLE_TEAM = "test_data"
        const val COLUMN_TIME = "TIME"
        const val COLUMN_CONTENT = "CONTENT"
        const val DATABASE_CREATE_TEAM = ("create table "
                + TABLE_TEAM + "(" + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_TIME + " INTEGER NOT NULL, "
                + COLUMN_CONTENT + " text);")
    }
}