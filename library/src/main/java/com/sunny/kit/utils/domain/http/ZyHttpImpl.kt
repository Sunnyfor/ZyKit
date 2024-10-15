package com.sunny.kit.utils.domain.http

import android.net.Uri
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.http.ZyHttp
import com.sunny.kit.utils.application.http.ZyHttpConfig
import com.sunny.kit.utils.application.http.bean.BaseHttpResultBean
import com.sunny.kit.utils.application.http.bean.DownLoadResultBean
import com.sunny.kit.utils.application.http.bean.HttpResultBean
import com.sunny.kit.utils.application.http.bean.WebSocketResultBean
import com.sunny.kit.utils.domain.http.execute.DefaultHttpExecute
import com.sunny.kit.utils.domain.http.request.ZyRequestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request

internal class ZyHttpImpl : ZyHttp {


    val httpExecute by lazy { DefaultHttpExecute() }


    /**
     * get请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 包含解析结果的实体bean
     */
    override suspend fun <T : BaseHttpResultBean> get(url: String, params: Map<String, String>, httpResultBean: T) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.getRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }


    override suspend fun <T : BaseHttpResultBean> head(
        url: String,
        params: Map<String, String>,
        httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.headRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }

    /**
     * postForm请求
     * @param url URL服务器地址
     * @param params 传递的数据map（key,value)
     * @param httpResultBean 包含解析结果的实体bean
     */
    override suspend fun <T : BaseHttpResultBean> post(
        url: String,
        params: Map<String, String>,
        httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.postFormRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }


    override suspend fun <T : BaseHttpResultBean> postJson(url: String, json: String, httpResultBean: T) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.postJsonRequest(url, json)
            execution(request, httpResultBean, this)
        }
    }


    override suspend fun <T : BaseHttpResultBean> patch(
        url: String,
        params: Map<String, String>,
        httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.patchFormRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }

    override suspend fun <T : BaseHttpResultBean> patchJson(url: String, json: String, httpResultBean: T) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.patchJsonRequest(url, json)
            execution(request, httpResultBean, this)
        }
    }


    override suspend fun <T : BaseHttpResultBean> put(
        url: String, params: Map<String, String>, httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.putFormRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }

    override suspend fun <T : BaseHttpResultBean> putJson(url: String, json: String, httpResultBean: T) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.putJsonRequest(url, json)
            execution(request, httpResultBean, this)
        }
    }


    override suspend fun <T : BaseHttpResultBean> delete(
        url: String, params: Map<String, String>, httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.deleteFormRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }

    override suspend fun <T : BaseHttpResultBean> deleteJson(url: String, json: String, httpResultBean: T) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.deleteJsonRequest(url, json)
            execution(request, httpResultBean, this)
        }

    }

    override suspend fun <T : BaseHttpResultBean> formUpload(
        url: String,
        params: Map<String, Any>,
        httpResultBean: T
    ) {
        return withContext(Dispatchers.IO) {
            //创建okHttp请求
            val request = ZyRequestFactory.formUploadRequest(url, params)
            execution(request, httpResultBean, this)
        }
    }

    override suspend fun webSocket(
        url: String,
        params: Map<String, String>,
        webSocketResultBean: WebSocketResultBean
    ) {

        return withContext(Dispatchers.IO) {
            try {
                val request = ZyRequestFactory.getRequest(url, params)
                execution(request, webSocketResultBean, this)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


    /**
     * 执行网络请求并处理结果
     * @param request OkHttp请求对象
     * @param httpResultBean 包含解析结果的实体bean
     */
    private suspend fun <T : BaseHttpResultBean> execution(
        request: Request,
        httpResultBean: T,
        scope: CoroutineScope
    ) {
        try {
            httpResultBean.scope = scope
            //请求URL赋值
            httpResultBean.url = Uri.decode(request.url.toString())
            val newRequest = request.newBuilder().tag(BaseHttpResultBean::class.java, httpResultBean).build()
            //执行异步网络请求
            if (httpResultBean is DownLoadResultBean) {
                httpExecute.executeDownload(newRequest, httpResultBean)
                return
            }
            if (httpResultBean is HttpResultBean<*>) {
                httpExecute.executeHttp(request, httpResultBean)
                return
            }

            if (httpResultBean is WebSocketResultBean){
                httpExecute.executeWebSocket(newRequest,httpResultBean)
            }
        } catch (e: Exception) {
            //出现异常获取异常信息
            httpResultBean.exception = e
            httpResultBean.message = e.message ?: ""
            ZyKit.log.e("发生异常->:$httpResultBean")
        }

        withContext(Dispatchers.Main) {
            ZyHttpConfig.httpResultCallback.invoke(httpResultBean)
        }
    }
}