package com.sunny.kit.utils.api.common

import android.util.ArrayMap
import com.sunny.kit.utils.api.ZyKit

open class DataStore {

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

    open fun clear(): DataStore {
        mMap.clear()
        return this
    }
}