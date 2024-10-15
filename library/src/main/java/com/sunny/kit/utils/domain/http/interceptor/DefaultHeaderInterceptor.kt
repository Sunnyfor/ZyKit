package com.sunny.kit.utils.domain.http.interceptor

import com.sunny.kit.utils.application.http.interceptor.IHeaderInterceptor
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Desc Header拦截器
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/08/24
 */
internal class DefaultHeaderInterceptor : IHeaderInterceptor {

    private var headerMap = hashMapOf<String, Any>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorised = originalRequest.newBuilder()
        //全局头信息
        headerMap.forEach {
            authorised.header(it.key, it.value.toString())
        }
        return chain.proceed(authorised.build())
    }

    /**
     * 设置网络请求头信息
     */
    override fun setHttpHeader(headerMap: HashMap<String, *>) {
        this.headerMap.clear()
        this.headerMap.putAll(headerMap)
    }

    /**
     * 获取网络请求头信息
     */
    override fun getHttpHeader(): HashMap<String, *> {
        return headerMap
    }
}