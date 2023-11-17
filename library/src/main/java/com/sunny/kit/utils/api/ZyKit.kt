package com.sunny.kit.utils.api

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sunny.kit.utils.api.common.ZyCacheUtil
import com.sunny.kit.utils.api.common.ZyDateUtil
import com.sunny.kit.utils.api.common.ZyDensityUtil
import com.sunny.kit.utils.api.common.ZyEncryptionUtil
import com.sunny.kit.utils.api.common.ZyFileUtil
import com.sunny.kit.utils.api.common.ZyLogUtil
import com.sunny.kit.utils.api.common.ZyMoneyUtil
import com.sunny.kit.utils.api.common.ZyNetworkUtil
import com.sunny.kit.utils.api.common.ZyPhoneUtil
import com.sunny.kit.utils.api.common.ZyStringUtl
import com.sunny.kit.utils.api.glide.ZyGlideUtil
import com.sunny.kit.utils.impl.common.ZyDateUtilImpl
import com.sunny.kit.utils.impl.common.ZyDensityUtilImpl
import com.sunny.kit.utils.impl.common.ZyEncryptionUtilImpl
import com.sunny.kit.utils.impl.common.ZyFileUtilImpl
import com.sunny.kit.utils.impl.common.ZyLogUtilImpl
import com.sunny.kit.utils.impl.common.ZyMoneyUtilImpl
import com.sunny.kit.utils.impl.common.ZyNetworkUtilImpl
import com.sunny.kit.utils.impl.common.ZyPhoneUtilImpl
import com.sunny.kit.utils.impl.common.ZyStringUtlImpl
import com.sunny.kit.utils.impl.glide.ZyGlideUtilImpl

/**
 * Desc 工具类初始化
 * Author ZY
 * Date 2022/10/27
 */
@Suppress("unused")
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

    val string: ZyStringUtl by lazy {
        ZyStringUtlImpl()
    }

    val phone: ZyPhoneUtil by lazy {
        ZyPhoneUtilImpl()
    }

    val date: ZyDateUtil by lazy {
        ZyDateUtilImpl()
    }

    val money: ZyMoneyUtil by lazy {
        ZyMoneyUtilImpl()
    }

    val log: ZyLogUtil by lazy {
        ZyLogUtilImpl()
    }

    val network: ZyNetworkUtil by lazy {
        ZyNetworkUtilImpl()
    }


    fun init(application: Application) {
        instance = application
    }

    val authorities: String
        get() {
            return "${instance.packageName}.provider"
        }


    fun getContext(): Context {
        if (::instance.isInitialized) {
            return instance
        }
        throw IllegalStateException("请先调用ZyKit.init(application: Application)方法初始化")
    }

}