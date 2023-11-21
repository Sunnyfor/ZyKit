package com.sunny.kit.utils.application.common

import java.util.Locale

interface ZyMoneyUtil {

    /**
     * 格式化金额
     * @param money 要格式化的金额
     * @return 格式化后的金额
     */
    fun formatMoney(money: Double, locale: Locale = Locale.CHINA): String

    /**
     * 根据数字的小数点位数进行格式化
     * @param number 要格式化的数字
     * @param len 小数点位数
     * @return 格式化后的金额
     */
    fun formatNumber(number: Double, len: Int = 2): String

    /**
     * 加法操作
     */
    fun add(number1: Double,number2: Double):Double

    /**
     * 减法操作
     */
    fun subtract(number1: Double,number2: Double):Double

    /**
     * 乘法操作
     */
    fun multiply(number1: Double,number2: Double):Double

    /**
     * 除法操作
     */
    fun divide(number1: Double,number2: Double):Double
}