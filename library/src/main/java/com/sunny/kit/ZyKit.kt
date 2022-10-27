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
     * 内存卡缓存路径
     */
    private var tempPath = ""

    fun init(application: Application) {
        instance = application
    }

    fun getContext() = instance


    /**
     * provider权限
     */
    var authorities = "com.sunny.zy.provider"


    /**
     * 获取缓存目录
     */
    fun getCatchPath(): String {
        if (tempPath.isEmpty()) {
            tempPath = getContext().getExternalFilesDir("temp")?.path ?: ""
        }
        return tempPath
    }

}