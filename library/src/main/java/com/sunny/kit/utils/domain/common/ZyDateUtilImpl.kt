package com.sunny.kit.utils.domain.common

import com.sunny.kit.utils.application.common.ZyDateUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class ZyDateUtilImpl : ZyDateUtil {

    /**
     * 获取当前秒级时间戳
     */
    override fun getTimeStamp(): Long {
        return (System.currentTimeMillis() / 1000)
    }

    /**
     * 根据格式获取当前时间
     */
    override fun getCurrentTime(pattern: String): String {
        return formatTime(Date().time, pattern)
    }

    /**
     * 格式化时间
     */
    override fun formatTime(date: Long, pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(Date(date))
    }

    override fun formatTime(date: String, pattern: String): Long {
        return try {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            simpleDateFormat.parse(date)?.time ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

}