package com.sunny.kit.utils.application.common

interface ZyLogUtil {

    var isDebug: Boolean

    /**
     * 设置每行打印长度
     */
    fun setLineLength(length: Int)

    /**
     * 设置每秒打印行数
     */
    fun setLinesPerSecond(value: Int)

    /**
     * 设置日志标签
     */
    fun setTag(tag: String)

    fun v(message: String, isShowStackTrace: Boolean = false)
    fun v(title: String, message: String, isShowStackTrace: Boolean = false)

    fun d(message: String, isShowStackTrace: Boolean = false)
    fun d(title: String, message: String, isShowStackTrace: Boolean = false)

    fun i(message: String, isShowStackTrace: Boolean = false)
    fun i(title: String, message: String, isShowStackTrace: Boolean = false)

    fun w(message: String, isShowStackTrace: Boolean = false)
    fun w(title: String, message: String, isShowStackTrace: Boolean = false)

    fun e(message: String, isShowStackTrace: Boolean = false)
    fun e(title: String, message: String, isShowStackTrace: Boolean = false)

    fun println(message: String)
    fun println(logType: Int, message: String)

}