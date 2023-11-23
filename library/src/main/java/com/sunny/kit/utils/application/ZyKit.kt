package com.sunny.kit.utils.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sunny.kit.utils.application.common.ZyCacheUtil
import com.sunny.kit.utils.application.common.ZyDateUtil
import com.sunny.kit.utils.application.common.ZyDensityUtil
import com.sunny.kit.utils.application.common.ZyEncryptionUtil
import com.sunny.kit.utils.application.common.ZyFileUtil
import com.sunny.kit.utils.application.common.ZyLogUtil
import com.sunny.kit.utils.application.common.ZyMoneyUtil
import com.sunny.kit.utils.application.common.ZyNetworkUtil
import com.sunny.kit.utils.application.common.ZyPhoneUtil
import com.sunny.kit.utils.application.common.ZySpUtil
import com.sunny.kit.utils.application.common.ZyStringUtl
import com.sunny.kit.utils.application.common.ZyToastUtil
import com.sunny.kit.utils.application.common.ZyUriUtil
import com.sunny.kit.utils.application.glide.ZyImageUtil
import com.sunny.kit.utils.domain.common.ZyDateUtilImpl
import com.sunny.kit.utils.domain.common.ZyDensityUtilImpl
import com.sunny.kit.utils.domain.common.ZyEncryptionUtilImpl
import com.sunny.kit.utils.domain.common.ZyFileUtilImpl
import com.sunny.kit.utils.domain.common.ZyLogUtilImpl
import com.sunny.kit.utils.domain.common.ZyMoneyUtilImpl
import com.sunny.kit.utils.domain.common.ZyNetworkUtilImpl
import com.sunny.kit.utils.domain.common.ZyPhoneUtilImpl
import com.sunny.kit.utils.domain.common.ZyStringUtlImpl
import com.sunny.kit.utils.domain.common.ZyToastUtilImpl
import com.sunny.kit.utils.domain.common.ZyUriUtilImpl
import com.sunny.kit.utils.domain.glide.ZyGlideImageUtilImpl

/**
 * Desc 工具类初始化
 * Author ZY
 * Date 2022/10/27
 */
@Suppress("unused")
object ZyKit {

    private lateinit var instance: Application


    /**
     * 缓存工具
     */
    val cache: ZyCacheUtil by lazy {
        ViewModelProvider.AndroidViewModelFactory(instance).create(ZyCacheUtil::class.java)
    }

    /**
     * SharedPreferences工具
     */
    val sp by lazy {
        ZySpUtil.get()
    }

    /**
     * SharedPreferences工具
     */
    fun sp(fineName: String): ZySpUtil {
        return ZySpUtil.get(fineName)
    }

    /**
     * 加密工具
     */
    val encrypt: ZyEncryptionUtil by lazy {
        ZyEncryptionUtilImpl()
    }

    /**
     * 屏幕密度工具
     */
    val density: ZyDensityUtil by lazy {
        ZyDensityUtilImpl()
    }

    /**
     * 文件工具
     */
    val file: ZyFileUtil by lazy {
        ZyFileUtilImpl()
    }

    /**
     * Glide工具
     */
    val image: ZyImageUtil by lazy {
        ZyGlideImageUtilImpl()
    }

    /**
     * 字符串工具
     */
    val string: ZyStringUtl by lazy {
        ZyStringUtlImpl()
    }

    /**
     * 电话工具
     */
    val phone: ZyPhoneUtil by lazy {
        ZyPhoneUtilImpl()
    }

    /**
     * 时间工具
     */
    val date: ZyDateUtil by lazy {
        ZyDateUtilImpl()
    }

    /**
     * 金额工具
     */
    val money: ZyMoneyUtil by lazy {
        ZyMoneyUtilImpl()
    }

    /**
     * 日志工具
     */
    val log: ZyLogUtil by lazy {
        ZyLogUtilImpl()
    }

    /**
     * 网络工具
     */
    val network: ZyNetworkUtil by lazy {
        ZyNetworkUtilImpl()
    }

    /**
     * Uri工具
     */
    val uri: ZyUriUtil by lazy {
        ZyUriUtilImpl()
    }

    /**
     * 吐司工具
     */
    val toast: ZyToastUtil by lazy {
        ZyToastUtilImpl()
    }

    /**
     * 初始化
     */
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