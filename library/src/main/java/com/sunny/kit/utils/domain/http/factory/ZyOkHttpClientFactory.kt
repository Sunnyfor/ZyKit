package com.sunny.kit.utils.domain.http.factory

import com.sunny.kit.utils.application.glide.ZyAppGlideModule
import com.sunny.kit.utils.application.http.ZyHttpConfig
import com.sunny.kit.utils.domain.http.interceptor.ResponseProgressInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Desc 创建OkHttpClient工厂类
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/8/24
 */
internal object ZyOkHttpClientFactory {

    /**
     * 创建新的Client对象
     */
    fun getOkHttpClient(): OkHttpClient {
        val okHttpClient = getBuild().build()
        ZyAppGlideModule.okHttpClient = okHttpClient
        return okHttpClient
    }


    /**
     * 获取OkHttpClient.Builder对象
     */
    private fun getBuild(): OkHttpClient.Builder {
        val build = OkHttpClient.Builder()
            .addInterceptor(ZyHttpConfig.headerInterceptor)
            .addNetworkInterceptor(ZyHttpConfig.logInterceptor)
            .addNetworkInterceptor(ResponseProgressInterceptor())
            .sslSocketFactory(
                ZySSLSocketFactory.createSSLSocketFactory(),
                ZySSLSocketFactory.getTrustManager()
            )
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(ZyHttpConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS) //连接超时时间
            .readTimeout(ZyHttpConfig.READ_TIME_OUT, TimeUnit.MILLISECONDS) //读取超时时间
            .callTimeout(ZyHttpConfig.CALL_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(ZyHttpConfig.WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            .cookieJar(ZyHttpConfig.cookie)

        ZyHttpConfig.okhttpBuild(build)
        return build
    }
}
