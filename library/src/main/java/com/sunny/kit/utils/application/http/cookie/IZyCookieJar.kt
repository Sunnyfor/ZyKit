package com.sunny.kit.utils.application.http.cookie

import okhttp3.Cookie
import okhttp3.CookieJar

interface IZyCookieJar: CookieJar {

    /**
     * 设置cookie
     */
    fun setCookie(url: String, cookList: List<Cookie>)

    /**
     * 清除cookie
     */
    fun clearCookie()
}