package com.sunny.kit.utils.impl.common

import com.sunny.kit.utils.api.common.ZyMoneyUtil
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

internal class ZyMoneyUtilImpl : ZyMoneyUtil {


    override fun formatMoney(money: Double, locale: Locale): String {
        return NumberFormat.getCurrencyInstance(locale).format(money)
    }

    override fun formatNumber(number: Double, len: Int): String {
        val bigDecimal = BigDecimal(number)
        return bigDecimal.setScale(len, RoundingMode.HALF_UP).toString()
    }

    override fun add(number1: Double, number2: Double):Double {
        return BigDecimal(number1).plus(BigDecimal(number2)).toDouble()
    }

    override fun subtract(number1: Double, number2: Double):Double {
        return BigDecimal(number1).minus(BigDecimal(number2)).toDouble()
    }

    override fun multiply(number1: Double, number2: Double):Double {
        return  BigDecimal(number1).times(BigDecimal(number2)).toDouble()
    }

    override fun divide(number1: Double, number2: Double):Double {
       return BigDecimal(number1).div(BigDecimal(number2)).toDouble()
    }


}