package com.sunny.kit.utils.domain.http.cookie

import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.http.cookie.IZyCookieJar
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * Desc Cookie持久化存储
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/6/12
 */
internal class ZyCookieJar : IZyCookieJar {

    private val remoteCookieMap = HashMap<String, List<Cookie>>()

    private val localCookieMap = HashMap<String, List<Cookie>>()

    private val fileName = "zy_http_cookie"

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        remoteCookieMap[url.host] = cookies
        ZyKit.sp(fileName).set(url.host, cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val resultList = arrayListOf<Cookie>()
        var remoteCookieList = remoteCookieMap[url.host]
        if (remoteCookieList == null) {
            remoteCookieList = ZyKit.sp(fileName).getObject<List<Cookie>>(url.host) ?: listOf()
        }
        remoteCookieMap[url.host] = remoteCookieList
        resultList.addAll(remoteCookieList)

        localCookieMap[url.host]?.let {
            resultList.addAll(it)
        }
        return resultList
    }

    override fun setCookie(url: String, cookList: List<Cookie>) {
        try {
            val httpUrl = url.toHttpUrl()
            if (httpUrl.host.isNotEmpty()) {
                localCookieMap[httpUrl.host] = cookList
            }
        } catch (e: IllegalArgumentException) {
            ZyKit.log.e("无效的 URL 地址：$url")
        }
    }

    /**
     * 清除Cookie
     */
    override fun clearCookie() {
        remoteCookieMap.clear()
        localCookieMap.clear()
        ZyKit.sp(fileName).clear()
    }
}