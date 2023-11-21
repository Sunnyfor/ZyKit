package com.sunny.kit.utils.application.common

import java.io.File

interface ZyFileUtil {

    /**
     * 获取私有目录文件路径
     */
    fun getExternalFilesDir(): String

    /**
     * 根据文件名获取文件
     */
    fun getExternalFile(name: String, isMake: Boolean = false): File

    /**
     * 获取目录缓存大小
     */
    fun getExternalFileSize(): Long

    /**
     * 转换文件大小
     *
     * @param fileSize 文件长度
     * @return 转换后的文件大小
     */
    fun formatFileSize(fileSize: Long): String


    /**
     * 获取指定文件夹内所有文件大小的和
     * @param file 文件
     * @return 文件大小
     */
    fun getFileSize(file: File): Long


    /**
     * 删除文件
     * @param filePath 文件路径
     */
    fun deleteFile(filePath: String)

    /**
     * 删除文件
     * @param file 文件
     */
    fun deleteFile(file: File)
}