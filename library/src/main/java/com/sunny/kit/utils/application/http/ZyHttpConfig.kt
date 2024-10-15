package com.sunny.kit.utils.application.http

import com.sunny.kit.utils.application.http.bean.BaseHttpResultBean
import com.sunny.kit.utils.application.http.cookie.IZyCookieJar
import com.sunny.kit.utils.application.http.interceptor.IHeaderInterceptor
import com.sunny.kit.utils.domain.http.cookie.ZyCookieJar
import com.sunny.kit.utils.domain.http.interceptor.DefaultHeaderInterceptor
import com.sunny.kit.utils.domain.http.interceptor.DefaultLogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Desc 网络请求配置清单
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2017/10/12
 */
object ZyHttpConfig {

    var HOST: String = ""
        set(value) {
            var mValue = value.replace(" ", "")
            if (!mValue.contains("://")) {
                //默认Http协议
                mValue = "http://$value"
            }
            field = mValue
        }

    /**
     * 连接超时时间，单位毫秒
     */
    var CONNECT_TIME_OUT = 10 * 1000L

    /**
     * 读取超时时间，单位毫秒
     */
    var READ_TIME_OUT = 30 * 1000L


    /**
     * 写入超时时间，单位毫秒
     */
    var WRITE_TIME_OUT = 30 * 1000L


    /**
     * 设置完成调用的默认超时时间。值为0表示没有超时，否则值必须在1和Integer之间。
     */
    var CALL_TIME_OUT = 0L


    /**
     * 打印最大响应内容
     */
    var RESPONSE_BODY_MAX_LOG_SIZE = 2 * (1024 * 1024L)

    /**
     * Log拦截器
     */
    var logInterceptor: Interceptor = DefaultLogInterceptor()

    /**
     * 默认头拦截器
     */
    internal val headerInterceptor: IHeaderInterceptor by lazy {
        DefaultHeaderInterceptor()
    }

    /**
     * 设置头信息
     */
    fun setHttpHeader(headerMap: HashMap<String, *>) {
        headerInterceptor.setHttpHeader(headerMap)
    }

    /**
     * 数据结果解析器
     */
//    var iResponseParser: IResponseParser = DefaultResponseParser()


    /**
     * Cookie配置
     */
    val cookie: IZyCookieJar by lazy {
        ZyCookieJar()
    }

    /**
     * 网络请求全局回调
     */
    var httpResultCallback: ((resultBean: BaseHttpResultBean) -> Unit) = {}

    /**
     * okhttp构建回调
     */
    var okhttpBuild: ((okhttpBuilder: OkHttpClient.Builder) -> Unit) = {}
}