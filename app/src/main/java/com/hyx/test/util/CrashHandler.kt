package com.hyx.test.util

import android.R.drawable
import android.app.AlertDialog.Builder
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import com.hyx.test.R.string
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.Thread.UncaughtExceptionHandler
import java.lang.reflect.Field
import java.util.*

/**
 * 全局异常捕获并弹窗提示
 */
class CrashHandler private constructor() : UncaughtExceptionHandler {
    //系统默认的UncaughtException处理类
    private var mDefaultHandler: UncaughtExceptionHandler? = null
    //程序的Context对象
    private var mContext: Context? = null
    //用来存储设备信息和异常信息
    private val mInfos: MutableMap<String, String> = HashMap()
    private var mCrashMessage: String? = null
    /**
     * 初始化
     *
     * @param context
     */
    fun init(context: Context?) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 异常捕获
     */
    override fun uncaughtException(thread: Thread,ex: Throwable) {
        val handleException = handleException(ex)
        if (!handleException && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            //跳出错误提示对话框
            Builder(mContext)
                .setTitle(mContext!!.getString(string.error_tip))
                .setMessage(mCrashMessage)
                .setIcon(drawable.ic_dialog_info)
                .setPositiveButton(mContext!!.getString(string.known)) { dialog, i -> dialog.dismiss() }
                .show()
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //收集设备参数信息
        collectDeviceInfo(mContext)

        //保存日志文件
        mCrashMessage = saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context?) {
        try {
            val pm: PackageManager = ctx!!.packageManager
            val pi: PackageInfo? = pm.getPackageInfo(ctx.packageName,PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName: String = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                mInfos["versionName"] = versionName
                mInfos["versionCode"] = versionCode
            }
        } catch (e: NameNotFoundException) {
        }
        val fields: Array<Field> = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                mInfos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 如果有需求可以将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String {
        val sb = StringBuffer()
        for ((key, value) in mInfos) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        return sb.toString()
    }

    companion object {
        //CrashHandler实例
        private var INSTANCE: CrashHandler? = null

        /**
         * 获取CrashHandler实例 ,单例模式
         */
        val instance: CrashHandler?
            get() {
                if (INSTANCE == null) INSTANCE = CrashHandler()
                return INSTANCE
            }
    }
}