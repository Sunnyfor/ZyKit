package com.sunny.kit.utils.domain.common

import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyFileUtil
import java.io.File
import java.text.DecimalFormat

/**
 * 文件操作相关工具类
 */

internal class ZyFileUtilImpl : ZyFileUtil {

    /**
     * 获取私有目录文件路径
     */
    override fun getExternalFilesDir(rootPath: String): String {
        val path = rootPath.ifEmpty { "temp" }
        val rootFile = ZyKit.getContext().getExternalFilesDir(path)
        return rootFile?.apply {
            if (!exists()) {
                mkdir()
            }
        }?.path ?: ""
    }


    /**
     * 根据文件名获取文件
     */
    override fun getExternalFile(name: String, isMake: Boolean): File {
        return File(getExternalFilesDir(), name).apply {
            if (isMake) createNewFile()
        }
    }


    /**
     * 获取目录缓存大小
     */
    override fun getExternalFileSize(): Long {
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
    override fun formatFileSize(fileSize: Long): String {
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
    override fun getFileSize(file: File): Long {
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
    override fun deleteFile(filePath: String) {
        val file = File(filePath)
        deleteFile(file)
    }

    /**
     * 删除文件
     * @param file 文件
     */
    override fun deleteFile(file: File) {
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