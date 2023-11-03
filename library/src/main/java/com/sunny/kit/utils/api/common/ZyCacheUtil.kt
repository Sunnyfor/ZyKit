package com.sunny.kit.utils.api.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZyCacheUtil : ViewModel() {

    val storeMapLiveData by lazy {
        MutableLiveData(DataStore())
    }


    inline fun <reified T> getData(key: String, isDelete: Boolean = false): T? {
        storeMapLiveData.value?.let {
            val result = it.get<T>(key)
            if (isDelete) {
                it.remove(key)
            }
            return result
        }
        return null
    }

    fun setData(key: String, value: Any) {
        storeMapLiveData.value?.set(key, value)
    }


    fun removeData(key: String) {
        storeMapLiveData.value?.remove(key)
    }


    fun removeAllData() {
        storeMapLiveData.value?.clear()
    }
}