package com.sunny.kit.utils.application.common

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunny.kit.utils.application.ZyKit


class ZySpUtil private constructor() {

    private lateinit var fileName: String

    companion object {
        fun get(fileName: String = ""): ZySpUtil {
            return ZySpUtil().apply {
                if (fileName.isEmpty()) {
                    this.fileName = "sharedPreferences_info"
                } else {
                    this.fileName = fileName
                }
            }
        }
    }

    private val sharedPreferences: SharedPreferences by lazy {
        ZyKit.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    /**
     * 保存String信息
     */
    fun setString(key: String, content: String) =
        sharedPreferences.edit().putString(key, content).apply()

    fun getString(key: String, defValue: String = ""): String =
        sharedPreferences.getString(key, defValue) ?: defValue


    /**
     * 保存Boolean类型的信息
     */
    fun setBoolean(key: String, flag: Boolean) =
        sharedPreferences.edit().putBoolean(key, flag).apply()

    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defValue)


    /**
     * 保存Integer信息
     */
    fun setInteger(key: String, content: Int = 0) =
        sharedPreferences.edit().putInt(key, content).apply()

    fun getInteger(key: String, defValue: Int = 0): Int = sharedPreferences.getInt(key, defValue)

    /**
     * 保存Long信息
     */
    fun setLong(key: String, content: Long = 0) =
        sharedPreferences.edit().putLong(key, content).apply()

    fun getLong(key: String, defValue: Long = 0): Long = sharedPreferences.getLong(key, defValue)


    /**
     * 保存Float信息
     */
    fun setFloat(key: String, content: Float) =
        sharedPreferences.edit().putFloat(key, content).apply()

    fun getFloat(key: String, defValue: Float = 0f): Float = sharedPreferences.getFloat(key, defValue)


    /**
     * 保存Object信息
     */
    fun set(key: String, obj: Any) {
        when (obj) {
            is String -> setString(key, obj)
            is Boolean -> setBoolean(key, obj)
            is Long -> setLong(key, obj)
            is Int -> setInteger(key, obj)
            is Float -> setFloat(key, obj)
            else -> {
                val gSon = Gson()
                val json = gSon.toJson(obj)
                setString(key, json)
            }
        }
    }

    /**
     * 获取Object信息
     */
    inline fun <reified T> getObject(key: String): T? {
        val json = getString(key)

        if (TextUtils.isEmpty(json)) {
            return null
        }
        return try {
            val gSon = Gson()
            gSon.fromJson(json, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取List信息
     */
    inline fun <reified T> getList(key: String): List<T>? {
        val json = getString(key)
        if (TextUtils.isEmpty(json)) {
            return null
        }

        return try {
            val type = TypeToken.getParameterized(List::class.java, T::class.java)
            val gSon = Gson()
            gSon.fromJson(json, type) as List<T>?
        } catch (e: Exception) {
            null
        }
    }


    /**
     * 删除元素
     */
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }


    /**
     * 清空share文件
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
