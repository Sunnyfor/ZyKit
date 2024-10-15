package com.sunny.kit.utils.application.http.interceptor

import okhttp3.Interceptor

interface IHeaderInterceptor : Interceptor {
    /**
     * 设置请求头
     */
    fun setHttpHeader(headerMap: HashMap<String, *>)

    /**
     * 获取请求头
     */
    fun getHttpHeader(): HashMap<String, *>
}