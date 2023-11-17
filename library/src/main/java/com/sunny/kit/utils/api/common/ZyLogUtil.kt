package com.sunny.kit.utils.api.common

interface ZyLogUtil {

    var isDebug: Boolean

    fun v(message: String, title: String = "", isShowSource: Boolean = false)

    fun d(message: String, title: String = "", isShowSource: Boolean = false)

    fun i(message: String, title: String = "", isShowSource: Boolean = false)

    fun w(message: String, title: String = "", isShowSource: Boolean = false)

    fun e(message: String, title: String = "", isShowSource: Boolean = false)
}