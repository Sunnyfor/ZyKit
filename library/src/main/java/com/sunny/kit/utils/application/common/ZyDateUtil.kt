package com.sunny.kit.utils.application.common

interface ZyDateUtil {
    /**
     * 获取当前秒级时间戳
     */
    fun getTimeStamp(): Long

    /**
     * 根据格式获取当前时间
     */
    fun getCurrentTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String

    /**
     * 格式化时间
     * return 格式化后的时间
     */
    fun formatTime(date: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String

    /**
     * 格式化时间
     * return 时间戳
     */
    fun formatTime(date: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Long
}