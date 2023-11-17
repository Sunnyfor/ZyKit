package com.sunny.kit.utils.impl.common

import com.sunny.kit.utils.api.common.ZyStringUtl

internal class ZyStringUtlImpl:ZyStringUtl {
    /**
     * 判断字符串是否符合数字规则
     */
    override fun isNumeric(str: String): Boolean {
        return str.isNotEmpty() && str.matches(Regex("\\d+(\\.\\d+)?"))
    }

    /**
     * 根据长度生成随机字符串
     */
    override fun getRandomString(len: Int): String {
        val chars = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )
        chars.shuffle()
        return buildString(len) {
            for (i in indices)
                append(chars.random())
        }
    }


}