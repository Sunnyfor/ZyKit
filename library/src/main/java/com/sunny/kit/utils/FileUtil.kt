package com.sunny.kit.utils

import com.sunny.kit.ZyKit
import java.io.File
import java.text.DecimalFormat

/**
 * 文件操作相关工具类
 */
@Suppress("MemberVisibilityCanBePrivate")
object FileUtil {

    /**
     * 获取换成你文件路径
     */
    fun getExternalFilesDir(pathName: String = "temp"): String {
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
    fun getExternalFile(name: String): File {
        return File(getExternalFilesDir(), name)
    }


    /**
     * 获取目录缓存大小
     */
    fun getExternalFileSize(): Long {
        var size = 0L
        size += getFileSize(File(getExternalFilesDir()))
        return size
    }


    /**
     * 转换文件大小
     *
     * @param fileSize 文件长度
     * @return 转换后的文件大小
     */
    fun formatFileSize(fileSize: Long): String {
        val df = DecimalFormat("#.00")
        val wrongSize = "0B"
        if (fileSize == 0L) {
            return wrongSize
        }
        return when {
            fileSize < 1024 -> df.format(fileSize.toDouble()) + "B"
            fileSize < 1048576 -> df.format(fileSize.toDouble() / 1024) + "KB"
            fileSize < 1073741824 -> df.format(fileSize.toDouble() / 1048576) + "MB"
            else -> df.format(fileSize.toDouble() / 1073741824) + "GB"
        }
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     * @param file 文件
     * @return 文件大小
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
     * 删除文件
     * @param filePath 文件路径
     */
    fun deleteFile(filePath: String) {
        val file = File(filePath)
        deleteFile(file)
    }

    /**
     * 删除文件
     * @param file 文件
     */
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
}