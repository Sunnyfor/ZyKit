package com.sunny.kit.utils.application.common

import android.util.ArrayMap
import com.sunny.kit.utils.application.ZyKit

open class ZyDataStore {
    private val storeMap = ArrayMap<String, Any>()

    fun get() = storeMap

    fun set(key: String, value: Any): ZyDataStore {
        storeMap[key] = value
        return this
    }


    inline fun <reified T> get(key: String): T? {
        val value = get()[key]
        if (value is T) {
            return value
        }
        ZyKit.log.i("${value?.javaClass?.name} 不能被转换为 ${T::class.java.name}")
        return null
    }

    fun remove(key: String): ZyDataStore {
        storeMap.remove(key)
        return this
    }

    open fun clear(): ZyDataStore {
        storeMap.clear()
        return this
    }

    fun formatJson(): String {
        return ZyKit.gson.toJson(storeMap)
    }
}