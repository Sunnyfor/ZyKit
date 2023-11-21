package com.sunny.kit.utils.domain.common

import android.content.Intent
import android.net.Uri
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyPhoneUtil

/**
 * Desc 手机号校验工具类
 * Author ZY
 * Date 2022/9/16
 */
internal class ZyPhoneUtilImpl : ZyPhoneUtil {


    /**
     * 校验手机号格式
     */
    override fun isPhoneValid(phone: String): Boolean {
        return matchMobile(phone) || matchTelephone(phone)
    }

    /**
     * 隐藏手机号中间四位
     */
    override fun encryptPhone(phone: String, format: String): String {
        if (isPhoneValid(phone)) {
            return phone.substring(0, 3) + format + phone.substring(7, phone.length)
        }
        return ""
    }

    /**
     * 校验手机号格式
     */
    private fun matchMobile(phone: String?): Boolean {
        val reg = Regex("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}\$")
        return phone?.matches(reg) ?: false
    }

    /**
     * 校验座机号码：("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)
     */
    private fun matchTelephone(phone: String?): Boolean {
        val reg = Regex("^((\\d{3,4}-)|\\d{3,4}-)?\\d{7,8}\$")
        return phone?.matches(reg) ?: false
    }


    /**
     * 拨打电话
     */
    override fun call(phone: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            val uri = Uri.parse("tel:$phone")
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ZyKit.getContext().startActivity(intent)
        } catch (e: Exception) {
            throw e
        }
    }


}