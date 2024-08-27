package com.sunny.kit.utils.application.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class ZyCacheViewModel : ViewModel() {

    val dataStore = ZyDataStore()

    private val storeMapLiveData by lazy {
        MutableLiveData(dataStore)
    }


    fun observe(owner: LifecycleOwner, observer: Observer<ZyDataStore>) {
        storeMapLiveData.observe(owner, observer)
    }

    fun setData(key: String, value: Any) {
        dataStore.set(key, value)
        storeMapLiveData.value = dataStore
    }

    inline fun <reified T> getData(key: String, isDelete: Boolean = false): T? {
        val result = dataStore.get<T>(key)
        if (isDelete) {
            dataStore.remove(key)
        }
        return result
    }

    fun removeData(key: String) {
        dataStore.remove(key)
        storeMapLiveData.value = dataStore
    }


    fun removeAllData() {
        dataStore.clear()
        storeMapLiveData.value = dataStore
    }
}