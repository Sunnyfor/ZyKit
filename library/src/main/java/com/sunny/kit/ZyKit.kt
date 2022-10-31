package com.sunny.kit

import android.app.Application

/**
 * Desc 工具类初始化
 * Author ZY
 * Date 2022/10/27
 */
object ZyKit {

    private lateinit var instance: Application

    fun init(application: Application) {
        instance = application
    }

    fun getContext() = instance


    /**
     * provider权限
     */
    var authorities = "com.sunny.zy.provider"

}