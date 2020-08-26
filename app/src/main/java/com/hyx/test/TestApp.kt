package com.hyx.test

import android.app.Application
import com.hyx.test.util.CrashHandler
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.model.HttpHeaders
import okhttp3.OkHttpClient.Builder
import java.util.concurrent.TimeUnit
import java.util.logging.Level

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initOkGo()

        //全局异常捕获初始化
        CrashHandler.instance?.init(this)
    }

    /**
     * 初始化OkGo
     */
    private fun initOkGo() {
        var builder = Builder()
        var loggingInterceptor =
            HttpLoggingInterceptor("OkGo")

        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO)
        builder.addInterceptor(loggingInterceptor)

        //全局的读取超时时间  基于前面的通道建立完成后，客户端终于可以向服务端发送数据了
        builder.readTimeout(
            OkGo.DEFAULT_MILLISECONDS,
            TimeUnit.MILLISECONDS
        )
        //全局的写入超时时间  服务器发回消息
        builder.writeTimeout(
            OkGo.DEFAULT_MILLISECONDS,
            TimeUnit.MILLISECONDS
        )
        //全局的连接超时时间  http建立通道的时间
        builder.connectTimeout(
            OkGo.DEFAULT_MILLISECONDS,
            TimeUnit.MILLISECONDS
        )

        //  === 请求头 和 参数的 设置 ===
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        var headers = HttpHeaders()
        headers.put("Content-Type", "application/json;charset=UTF-8")    //header不支持中文，不允许有特殊字符
//        HttpParams params = new HttpParams();

        OkGo.getInstance().init(this)                       //必须调用初始化
            .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
            .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
            .setRetryCount(3)                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
            .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);

    }
}