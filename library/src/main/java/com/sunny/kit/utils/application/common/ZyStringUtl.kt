package com.sunny.kit.utils.application.common

interface ZyStringUtl {
    /**
     * 判断字符串是否符合数字规则
     */
    fun isNumeric(str: String): Boolean

    /**
     * 根据长度生成随机字符串
     */
    fun getRandomString(len:Int):String
}