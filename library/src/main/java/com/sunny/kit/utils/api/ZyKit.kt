package com.sunny.kit.utils.api

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.sunny.kit.utils.impl.ZyPhoneUtilImpl
import com.sunny.kit.utils.impl.ZyDensityUtilImpl
import com.sunny.kit.utils.impl.ZyEncryptionUtilImpl
import com.sunny.kit.utils.impl.ZyFileUtilImpl
import com.sunny.kit.utils.impl.glide.ZyGlideUtilImpl
import com.sunny.kit.utils.api.common.ZyCacheUtil
import com.sunny.kit.utils.api.common.ZyDensityUtil
import com.sunny.kit.utils.api.common.ZyEncryptionUtil
import com.sunny.kit.utils.api.common.ZyFileUtil
import com.sunny.kit.utils.api.glide.ZyGlideUtil
import com.sunny.kit.utils.api.common.ZyPhoneUtil

/**
 * Desc 工具类初始化
 * Author ZY
 * Date 2022/10/27
 */
object ZyKit {

    private lateinit var instance: Application

    val cache: ZyCacheUtil by lazy {
        ViewModelProvider.AndroidViewModelFactory(instance).create(ZyCacheUtil::class.java)
    }

    val encrypt: ZyEncryptionUtil by lazy {
        ZyEncryptionUtilImpl()
    }

    val density: ZyDensityUtil by lazy {
        ZyDensityUtilImpl()
    }

    val file: ZyFileUtil by lazy {
        ZyFileUtilImpl()
    }

    val glide: ZyGlideUtil by lazy {
        ZyGlideUtilImpl()
    }

    val phone: ZyPhoneUtil by lazy {
        ZyPhoneUtilImpl()
    }

    fun init(application: Application) {
        instance = application
    }

    val authorities: String
        get() {
            return "${instance.packageName}.provider"
        }


    fun getContext() = instance

}