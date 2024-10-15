package com.sunny.kit.utils.domain.http.request

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.http.ZyHttpConfig
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

/**
 * Desc 生成网络请求
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/4/29
 */
internal object ZyRequestFactory {

    val formParamsTag = Map::class.java
    val jsonParamsTag = JSONObject::class.java
    private fun getUrlSb(url: String) = StringBuilder().apply {
        if (!url.contains("://")) {
            append(ZyHttpConfig.HOST)
            append("/")
        }
        append(url)
    }


    /**
     * GET请求生成
     */
    fun getRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val paramsStr = paramsToString(params)
        if (paramsStr.isNotEmpty()) {
            urlSb.append("?").append(paramsStr)
        }
        return Request.Builder().url(urlSb.toString()).tag(formParamsTag, params).build()
    }

    /**
     * HEAD请求生成
     */
    fun headRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val paramsStr = paramsToString(params)
        if (paramsStr.isNotEmpty()) {
            urlSb.append("?").append(paramsStr)
        }
        return Request.Builder().url(urlSb.toString()).head().tag(formParamsTag, params).build()
    }


    /**
     * POST-JSON请求生成
     */
    fun postJsonRequest(url: String, json: String): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToJsonBody(json)
        return Request.Builder().url(urlSb.toString()).post(body).tag(jsonParamsTag, JSONObject(json)).build()
    }


    /**
     * POST-FORM请求生成
     */
    fun postFormRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val formBody = paramsToFormBody(params)
        return Request.Builder().url(urlSb.toString()).post(formBody).tag(formParamsTag, params).build()
    }


    /**
     * PUT-FORM请求生成
     */
    fun putFormRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val formBody = paramsToFormBody(params)
        return Request.Builder().url(urlSb.toString()).put(formBody).tag(formParamsTag, params).build()
    }

    /**
     * PUT-JSON请求生成
     */
    fun putJsonRequest(url: String, json: String): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToJsonBody(json)
        return Request.Builder().url(urlSb.toString()).put(body).tag(jsonParamsTag, JSONObject(json)).build()
    }


    /**
     * PATCH-Form请求生成
     */
    fun patchFormRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToFormBody(params)
        return Request.Builder().url(urlSb.toString()).patch(body).tag(formParamsTag, params).build()
    }

    /**
     * PATCH-Json请求生成
     */
    fun patchJsonRequest(url: String, json: String): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToJsonBody(json)
        return Request.Builder().url(urlSb.toString()).patch(body).tag(jsonParamsTag, JSONObject(json)).build()
    }


    /**
     * DELETE-Form请求生成
     */
    fun deleteFormRequest(url: String, params: Map<String, String>): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToFormBody(params)
        return Request.Builder().url(urlSb.toString()).delete(body).tag(formParamsTag, params).build()
    }

    /**
     *  DELETE-Json请求
     */
    fun deleteJsonRequest(url: String, json: String): Request {
        val urlSb = getUrlSb(url)
        val body = paramsToJsonBody(json)
        return Request.Builder().url(urlSb.toString()).delete(body).tag(jsonParamsTag, JSONObject(json)).build()
    }

    /**
     * FORM形式上传文件
     */
    fun formUploadRequest(url: String, params: Map<String, Any>): Request {
        val urlSb = getUrlSb(url)
        val body = MultipartBody.Builder()
        body.setType(MultipartBody.FORM)
        params.entries.forEach { entry ->
            val key = entry.key
            val value = entry.value
            if (value is List<*>) {
                value.forEach {
                    addFormDataPart(body, key, it)
                }
            } else {
                addFormDataPart(body, key, value)
            }
        }

        return Request.Builder().url(urlSb.toString()).post(body.build()).tag(formParamsTag, params).build()
    }

    /**
     * 将参数转换为字符串
     */
    private fun paramsToString(params: Map<String, Any>): String {
        val paramsSb = StringBuilder()
        params.entries.forEachIndexed { index, entry ->
            paramsSb.append(entry.key)
                .append("=")
                .append(entry.value)
            if (index < params.size - 1) {
                paramsSb.append("&")
            }
        }
        return paramsSb.toString()
    }

    /**
     * 将参数转换为FormBody
     */
    private fun paramsToFormBody(params: Map<String, Any>): RequestBody {
        val body = FormBody.Builder()
        if (params.isNotEmpty()) {
            params.entries.forEach {
                body.add(it.key, it.value.toString())
            }
        }
        return body.build()
    }


    /**
     * 将参数转换为FormBody
     */
    private fun paramsToJsonBody(json:String): RequestBody {
        return json.toRequestBody("application/json; charset=utf-8".toMediaType())
    }

    /**
     * formBody添加数据
     */
    private fun addFormDataPart(body: MultipartBody.Builder, key: String, value: Any?) {
        val contentType = "multipart/form-data".toMediaType()

        if (value is String) {
            val file = File(value)
            body.addFormDataPart(
                key,
                file.name,
                file.asRequestBody(contentType)
            )
            return
        }

        if (value is File) {
            body.addFormDataPart(
                key,
                value.name,
                value.asRequestBody(contentType)
            )
            return
        }

        if (value is Uri) {
            val fileName = DocumentFile.fromSingleUri(ZyKit.getContext(), value)?.name
            ZyKit.getContext().contentResolver.openInputStream(value)?.use { stream ->
                body.addFormDataPart(
                    key,
                    fileName,
                    stream.readBytes().toRequestBody(contentType)
                )
            }
            return
        }
    }
}