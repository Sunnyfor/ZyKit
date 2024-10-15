package com.sunny.kit.utils.application.http.bean

import okhttp3.WebSocket
import okio.ByteString

/**
 * Desc Socket请求结果实体类
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021/4/13
 */
class WebSocketResultBean : BaseHttpResultBean() {

    private var webSocket: WebSocket? = null

    var onMessageBytes: (bytes: ByteString) -> Unit = {}

    var onMessageString: (text: String) -> Unit = {}

    fun initWebSocket(webSocket: WebSocket) {
        this.webSocket = webSocket
    }

    fun send(text: String) {
        webSocket?.send(text)
    }

    fun send(bytes: ByteString) {
        webSocket?.send(bytes)
    }

    fun close(code: Int = 10000, reason: String = "") {
        webSocket?.close(code, reason)
    }

    fun cancel() {
        webSocket?.cancel()
    }
}