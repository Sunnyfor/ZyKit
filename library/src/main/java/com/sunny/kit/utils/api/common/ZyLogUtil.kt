package com.sunny.kit.utils.api.common

interface ZyLogUtil {

    fun v(title: String, message: String = "", isShowSource: Boolean = false)

    fun d(title: String, message: String? = "", isShowSource: Boolean = false)

    fun i(title: String, message: String = "", isShowSource: Boolean = false)

    fun w(title: String, message: String = "", isShowSource: Boolean = false)

    fun e(title: String, message: String? = "", isShowSource: Boolean = false)
}