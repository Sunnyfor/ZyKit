package com.sunny.kit.utils.application.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunny.kit.utils.application.ZyKit

@Suppress("MemberVisibilityCanBePrivate")
class ZySpUtil(fileName: String) {

    private val sharedPreferences: SharedPreferences by lazy {
        ZyKit.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    /**
     * 保存String信息
     */
    fun setString(key: String, content: String) =
        sharedPreferences.edit().putString(key, content).apply()

    /**
     * 获取String信息
     */
    fun getString(key: String, defValue: String = ""): String =
        sharedPreferences.getString(key, defValue) ?: defValue

    /**
     * 保存Boolean信息
     */
    fun setBoolean(key: String, flag: Boolean) =
        sharedPreferences.edit().putBoolean(key, flag).apply()

    /**
     * 获取Boolean信息
     */
    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defValue)

    /**
     * 保存Integer信息
     */
    fun setInteger(key: String, content: Int = 0) =
        sharedPreferences.edit().putInt(key, content).apply()

    /**
     * 获取Integer信息
     */
    fun getInteger(key: String, defValue: Int = 0): Int = sharedPreferences.getInt(key, defValue)

    /**
     * 保存Long信息
     */
    fun setLong(key: String, content: Long = 0) =
        sharedPreferences.edit().putLong(key, content).apply()

    /**
     * 获取Long信息
     */
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
     * 根据键名从SharedPreferences中获取泛型对象
     *
     * @param key 键名
     * @return 对象实例，如果键名不存在或解析失败则返回null
     */
    inline fun <reified T> getObject(key: String): T? {
        val json = getString(key)
        if (json.isEmpty()) {
            return null
        }
        return try {
            val type = object : TypeToken<T>() {}.type
            ZyKit.gson.fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 删除指定键名的数据
     */
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }


    /**
     * 清空SharedPreferences
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
