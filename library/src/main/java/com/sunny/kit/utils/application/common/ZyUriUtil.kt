package com.sunny.kit.utils.application.common

import android.net.Uri

interface ZyUriUtil {

    /**
     * 通过URI获取文件路径
     */
    fun getFilePathFromUri(uri: Uri): String

    /**
     * 从文件得到URI
     */
    fun getUriFromPath(path: String): Uri?

}