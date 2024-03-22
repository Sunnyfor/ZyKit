package com.sunny.kit.utils.domain.common

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyUriUtil
import java.io.File

class ZyUriUtilImpl : ZyUriUtil {

    /**
     * 获取文件ID
     */
    private fun getDocumentId(uri: Uri): String {
        return if (DocumentsContract.isDocumentUri(ZyKit.getContext(), uri)) {
            DocumentsContract.getDocumentId(uri)
        } else {
            DocumentsContract.getTreeDocumentId(uri)
        }
    }

    /**
     * 通过URI获取文件路径
     */
    private fun queryFilePath(
        uri: Uri,
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): String {
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        val cursor = ZyKit.getContext().contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            null
        )
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            if (it.moveToFirst()) {
                return it.getString(columnIndex)
            }
        }
        return ""
    }

    /**
     * 通过URI获取文件路径
     * */
    override fun getFilePathFromUri(uri: Uri): String {
        var filePath: String? = null

        when (uri.authority) {
            "com.android.externalstorage.documents" -> {
                val docId = getDocumentId(uri)
                val split = docId.split(":")
                if (split.size > 1) {
                    val storageType = split[0]
                    filePath = if ("primary".equals(storageType, true)) {
                        "${Environment.getExternalStorageDirectory()}/" + split[1]
                    } else {
                        val path = "${System.getenv("ANDROID_STORAGE")}/${split[0]}"
                        val file = File(path)
                        if (file.exists()) {
                            "${path}/${split[1]}"
                        } else {
                            "${System.getenv("ANDROID_STORAGE")}/sdcard/${split[1]}"
                        }
                    }
                }
            }

            "com.android.providers.downloads.documents" -> {
                val docId = getDocumentId(uri)
                filePath = if (docId.contains("raw")) {
                    val split = docId.split(":")
                    split[1]
                } else {
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        docId.toLong()
                    )
                    queryFilePath(contentUri)
                }
            }

            "com.android.providers.media.documents" -> {
                val docId = getDocumentId(uri)
                val split = docId.split(":")
                val type = split[0]
                val contentUri: Uri?

                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }

                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }

                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    else -> {
                        contentUri = MediaStore.Files.getContentUri("external")
                    }
                }
                val selection = MediaStore.Images.Media._ID + "=?"
                val selectionArgs = arrayOf(split[1])
                contentUri?.let {
                    filePath = queryFilePath(it, selection, selectionArgs)
                }
            }

            else -> {
                if ("content".equals(uri.scheme, true)) {
                    filePath = queryFilePath(uri)
                } else if ("file".equals(uri.scheme, true)) {
                    filePath = uri.path
                }
            }
        }
        return filePath ?: ""
    }


    /**
     * 从文件得到URI
     */
    override fun getUriFromPath(path: String): Uri? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    ZyKit.getContext(), ZyKit.authorities, File(path)
                )
            } else {
                Uri.fromFile(File(path))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}