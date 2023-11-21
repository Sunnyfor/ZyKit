package com.sunny.kit.utils.application.common

import android.util.ArrayMap
import com.sunny.kit.utils.application.ZyKit

open class ZyDataStore {

    val mMap = ArrayMap<String, Any>()

    fun set(key: String, value: Any) {
        mMap[key] = value
    }


    inline fun <reified T> get(key: String): T? {
        val value = mMap[key]
        if (value is T) {
            return value
        }
        ZyKit.log.i("${value?.javaClass?.name} 不能被转换为 ${T::class.java.name}")
        return null
    }

    fun remove(key: String) {
        mMap.remove(key)
    }

    open fun clear(): ZyDataStore {
        mMap.clear()
        return this
    }
}