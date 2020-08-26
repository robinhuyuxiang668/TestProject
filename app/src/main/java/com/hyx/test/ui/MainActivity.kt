package com.hyx.test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.hyx.test.R.*
import com.hyx.test.db.DbManage
import com.hyx.test.db.DbManage.Companion.getInstance
import com.hyx.test.entity.TestEntity
import com.hyx.test.util.Constants
import com.hyx.test.util.TimeUtils
import com.hyx.test.util.TimeUtils.getTimeStr
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private var tv_content: TextView? = null
    private var tv_show_history: TextView? = null
    private var mDisposable: Disposable? = null
    private var mDbManage: DbManage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        tv_content = findViewById<TextView?>(id.tv_content)
        tv_show_history = findViewById<TextView?>(id.tv_show_history)
        tv_show_history!!.setOnClickListener {
            startActivity(Intent(this@MainActivity,HistoryActivity::class.java))
        }
        mDbManage = getInstance(this)
        showLast()
        mDisposable = Observable.interval(5, 5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { requestData() }
    }

    /**
     * 显示最后一次的调用结果
     */
    private fun showLast() {
        val entity = mDbManage!!.lastData
        if (entity != null) {
            val time = getTimeStr(entity.time,TimeUtils.TIME_FORMAT_YYYYMMDDHHMMSS)
            tv_content!!.text = getString(string.main_content, time, "message="+entity.message+"\n\nurl="+entity.documentation_url)
        }
    }

    private fun requestData() {
        OkGo.post<String>(Constants.REQUEST_URL)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>) {
                    dealData(response.body())
                }

                override fun onError(response: Response<String?>?) {
                    super.onError(response)
                }
            })
    }

    private fun dealData(str: String) {
        val timeMillis = System.currentTimeMillis()
        val time = getTimeStr(timeMillis,TimeUtils.TIME_FORMAT_YYYYMMDDHHMMSS)

        var testEntity: TestEntity? = null
        var message = ""
        var documentation_url = ""
        try {
            val json = JSONObject(str)
            message = json.getString("message")
            documentation_url = json.getString("documentation_url")
            testEntity = TestEntity(timeMillis, message, documentation_url)
        } catch (e: Exception) {
        }

        if(testEntity != null){
            tv_content!!.text = getString(string.main_content, time, "message="+testEntity.message+"\n\nurl="+testEntity.documentation_url)
        }

        mDbManage!!.insert(TestEntity(timeMillis, message, documentation_url))
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable!!.dispose()
    }
}