package com.sunny.kit.utils.application.common

interface ZyLogUtil {

    var isDebug: Boolean

    fun v(title: String, message: String, isShowSource: Boolean = false)
    fun v(message: String, isShowSource: Boolean = false)

    fun d(title: String, message: String, isShowSource: Boolean = false)
    fun d(message: String, isShowSource: Boolean = false)

    fun i(title: String, message: String, isShowSource: Boolean = false)
    fun i(message: String, isShowSource: Boolean = false)

    fun w(title: String, message: String, isShowSource: Boolean = false)
    fun w(message: String, isShowSource: Boolean = false)

    fun e(title: String, message: String, isShowSource: Boolean = false)
    fun e(message: String, isShowSource: Boolean = false)
}