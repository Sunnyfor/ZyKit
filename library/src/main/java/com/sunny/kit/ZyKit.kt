package com.sunny.kit

import android.app.Application

/**
 * Desc 工具类初始化
 * Author ZY
 * Date 2022/10/27
 */
object ZyKit {

    private lateinit var instance: Application

    /**
     * 两次点击事件间隔，单位毫秒
     */
    var clickInterval = 500L

    fun init(application: Application) {
        instance = application
    }

    fun getContext() = instance

}