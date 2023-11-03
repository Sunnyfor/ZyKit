package com.sunny.kit.utils.api.common

interface ZyEncryptionUtil {

    fun md5(message: String): String

    fun sha1(strSrc: String): String

    fun bytes2Hex(byteArray: ByteArray): String
}