package com.sunny.kit.utils.application.common

interface ZyPhoneUtil {
    /**
     * 手机号校验
     */
    fun isPhoneValid(phone: String): Boolean


    /**
     * 手机号脱敏
     */
    fun encryptPhone(phone: String,format:String ="****"): String

    /**
     * 拨打电话
     */
    fun call(phone: String)

}