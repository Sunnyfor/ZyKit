package com.sunny.kit.utils.domain.http.execute

import android.net.Uri
import com.sunny.kit.utils.application.http.bean.BaseHttpResultBean
import com.sunny.kit.utils.application.http.bean.DownLoadResultBean
import com.sunny.kit.utils.application.http.bean.HttpResultBean
import com.sunny.kit.utils.application.http.bean.WebSocketResultBean
import com.sunny.kit.utils.domain.http.factory.ZyOkHttpClientFactory
import com.sunny.kit.utils.domain.http.parser.DefaultResponseParser
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString


/**
 * Desc 默认的Http执行器，可重写
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021/8/10
 */
internal class DefaultHttpExecute {

    private val okHttpClient by lazy { ZyOkHttpClientFactory.getOkHttpClient() }

    private val responseParser by lazy { DefaultResponseParser() }

    /**
     * 执行下载文件
     */
    fun executeDownload(request: Request, resultBean: DownLoadResultBean) {
        resultBean.reset()
        resultBean.call = okHttpClient.newCall(request)
        resultBean.call?.execute()?.let { response ->
            populateHttpResultBean(resultBean, response)
            if (response.isSuccessful) {
                response.body?.let { body ->
                    if (resultBean.fileName.isEmpty()) {
                        val contentDisposition = response.header("Content-Disposition")
                        if (contentDisposition != null) {
                            val matcher = Regex("filename=\"(.+?)\"").find(contentDisposition)
                            resultBean.fileName = Uri.decode(matcher?.groupValues?.get(1))
                        } else {
                            resultBean.fileName = resultBean.url.substringAfterLast("/").replace("?", "_")
                        }
                    }
                    val contentType = body.contentType()
                    if (contentType != null) {
                        resultBean.contentType = contentType.toString()
                    }
                    responseParser.parserDownloadResponse(body, resultBean)
                }
            }
        }
    }

    /**
     * 执行网络请求
     */
    fun <T> executeHttp(request: Request, resultBean: HttpResultBean<T>) {
        resultBean.call = okHttpClient.newCall(request)
        resultBean.call?.execute()?.let { response ->
            populateHttpResultBean(resultBean, response)
            if (response.isSuccessful) {
                response.body?.let {
                    resultBean.data = responseParser.parserHttpResponse(it, resultBean)
                }
            }
        }
    }

    fun executeWebSocket(request: Request, webSocketResultBean: WebSocketResultBean) {
        okHttpClient.newWebSocket(request, object : okhttp3.WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                populateHttpResultBean(webSocketResultBean, response)
                webSocketResultBean.initWebSocket(webSocket)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                webSocketResultBean.onMessageBytes.invoke(bytes)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocketResultBean.onMessageString.invoke(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                webSocketResultBean.initWebSocket(webSocket)
                webSocketResultBean.exception = t
                response?.let {
                    populateHttpResultBean(webSocketResultBean, it)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocketResultBean.initWebSocket(webSocket)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                webSocketResultBean.initWebSocket(webSocket)
            }
        })
    }


    /**
     * 填充HttpResultBean
     */
    private fun populateHttpResultBean(httpResultBean: BaseHttpResultBean, response: Response) {
        //获取请求是否成功
        httpResultBean.httpIsSuccess = response.isSuccessful
        //获取HTTP状态码
        httpResultBean.httpCode = response.code
        //获取Response回执信息
        httpResultBean.message = response.message
        //获取响应URL
        val resUrl = Uri.decode(response.request.url.toString())
        if (resUrl != httpResultBean.url) {
            httpResultBean.resUrl = resUrl
        }
    }
}