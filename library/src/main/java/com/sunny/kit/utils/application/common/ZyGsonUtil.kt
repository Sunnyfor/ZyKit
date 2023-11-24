package com.sunny.kit.utils.application.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface ZyGsonUtil {

    fun getGsonBuilder(): GsonBuilder

    fun getGson(): Gson
}