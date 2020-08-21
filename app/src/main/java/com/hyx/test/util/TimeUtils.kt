package com.hyx.test.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    const val TIME_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"
    fun getTimeStr(timeMillius: Long, format: String?): String {
        val sdf = SimpleDateFormat(format)
        return sdf.format(Date(timeMillius))
    }
}