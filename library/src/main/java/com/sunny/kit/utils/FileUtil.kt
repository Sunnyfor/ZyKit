package com.sunny.kit.utils

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.sunny.kit.ZyKit
import java.io.File
import java.text.DecimalFormat

/**
 * 文件操作相关工具类
 */
object FileUtil {

    /**
     * 获取换成你文件路径
     */
    fun getExternalDir(pathName: String = "temp"): String {
        val temp = ZyKit.getContext().getExternalFilesDir(pathName)
        var path = ""
        temp?.let {
            if (!it.exists()) {
                it.mkdir()
            }
            path = it.path
        }
        return path
    }


    /**
     * 根据文件名获取文件
     */
    fun getFile(name: String): File {
        return File(getExternalDir(), name)
    }


    fun insertImage(name: String): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        return ZyKit.getContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    /**
     * 获取目录缓存大小
     */
    fun getCacheSize(): Long {
        var size = 0L
        size += getFileSize(File(getExternalDir()))
        return size
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    fun formatFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        return when {
            fileS < 1024 -> df.format(fileS.toDouble()) + "B"
            fileS < 1048576 -> df.format(fileS.toDouble() / 1024) + "KB"
            fileS < 1073741824 -> df.format(fileS.toDouble() / 1048576) + "MB"
            else -> df.format(fileS.toDouble() / 1073741824) + "GB"
        }
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    fun getFileSize(file: File): Long {
        var size: Long = 0
        try {
            if (file.isDirectory) {
                file.listFiles()?.let {
                    for (itemFile in it) {
                        size += getFileSize(itemFile)
                    }
                }
            } else {
                size = file.length()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }


    /**
     * 删除文件夹
     */
    fun deleteFile(filePath: String) {
        val file = File(filePath)
        deleteFile(file)
    }

    fun deleteFile(file: File) {
        if (file.isDirectory) {
            val files = file.listFiles()
            files?.forEach {
                deleteFile(it.absolutePath)
            }
        } else {
            file.delete()
        }
    }


    /**
     * 通过URI获取文件路径
     */
    private fun getFilePathFromUri(uri: Uri): String {
        var filePath: String? = null
        val docId = if (DocumentsContract.isDocumentUri(ZyKit.getContext(), uri)) {
            DocumentsContract.getDocumentId(uri)
        } else {
            DocumentsContract.getTreeDocumentId(uri)
        }

        if (docId.isEmpty()) {
            ToastUtil.show("选择失败，当前设备不支持！")
            return ""
        }

        when (uri.authority) {
            "com.android.externalstorage.documents" -> {
                val split = docId.split(":")
                if (split.size > 1) {
                    val storageType = split[0]
                    filePath = if ("primary".equals(storageType, true)) {
                        "${Environment.getExternalStorageDirectory()}/" + split[1]
                    } else {
                        "${System.getenv("ANDROID_STORAGE")}/${split[0]}/${split[1]}"
                    }
                }
            }

            "com.android.providers.downloads.documents" -> {
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
                } else if ("content".equals(uri.scheme, true)) {
                    filePath = uri.path
                }
            }
        }
        return filePath ?: ""
    }


    fun queryFilePath(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        val cursor = ZyKit.getContext().contentResolver.query(uri, projection, selection, selectionArgs, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            if (it.moveToFirst()) {
                return it.getString(columnIndex)
            }
        }
        return null
    }


}