package com.sunny.kit.utils.application.http

import com.sunny.kit.utils.application.http.bean.BaseHttpResultBean
import com.sunny.kit.utils.application.http.bean.WebSocketResultBean

interface ZyHttp {

    /**
     * get 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param T 请求结果
     */
    suspend fun <T : BaseHttpResultBean> get(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * head 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> head(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * post 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> post(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * post 请求
     * @param url URL服务器地址
     * @param json JSON字符串
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> postJson(url: String, json: String, httpResultBean: T)

    /**
     * patch 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> patch(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * patch 请求
     * @param url URL服务器地址
     * @param json JSON字符串
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> patchJson(url: String, json: String, httpResultBean: T)

    /**
     * put 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> put(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * put 请求
     * @param url URL服务器地址
     * @param json JSON字符串
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> putJson(url: String, json: String, httpResultBean: T)

    /**
     * delete 请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> delete(url: String, params: Map<String, String> = hashMapOf(), httpResultBean: T)

    /**
     * delete 请求
     * @param url URL服务器地址
     * @param json JSON字符串
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> deleteJson(url: String, json: String, httpResultBean: T)

    /**
     * post 上传文件
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 请求结果
     */
    suspend fun <T : BaseHttpResultBean> formUpload(url: String, params: Map<String, Any>, httpResultBean: T)

    /**
     * websocket 连接
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param webSocketResultBean 连接结果
     */
    suspend fun webSocket(url: String, params: Map<String, String> = hashMapOf(), webSocketResultBean: WebSocketResultBean)
}