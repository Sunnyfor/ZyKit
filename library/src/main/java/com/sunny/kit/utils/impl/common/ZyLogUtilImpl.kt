package com.sunny.kit.utils.impl.common

import android.util.Log
import com.sunny.kit.utils.api.common.ZyLogUtil

/**
 * Desc 封装使用Log日志代码
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021年6月30日
 */
internal class ZyLogUtilImpl : ZyLogUtil {

    /**
     * 是否打印LOG
     */
    override var isDebug = true

    /**
     * 设置Log标签名
     */
    private var logTag = "ZYLog"

    private val verbose = 0
    private val debug = 1
    private val info = 2
    private val warn = 3
    private val error = 4

    private var steIndex = 5
    private var lineSize = 160

    private var topBorder = "┌"
    private var bottomBorder = "└"
    private var middleLine = "├"
    private var verticalLine = "│ "
    private var horizontalLine = "─"

    private var titleStart = "【"

    private var titleEnd = "】"


    override fun v(message: String, title: String, isShowSource: Boolean) {
        print(verbose, title, message, isShowSource)
    }

    override fun d(message: String, title: String, isShowSource: Boolean) {
        print(debug, title, message, isShowSource)
    }

    override fun i(message: String, title: String, isShowSource: Boolean) {
        print(info, title, message, isShowSource)
    }

    override fun w(message: String, title: String, isShowSource: Boolean) {
        print(warn, title, message, isShowSource)
    }

    override fun e(message: String, title: String, isShowSource: Boolean) {
        print(error, title, message, isShowSource)
    }


    private fun generateTitle(logType: Int, title: String, isShowSource: Boolean) {
        val sb = StringBuilder(verticalLine)
        sb.append("$titleStart$title$titleEnd")
        if (isShowSource) {
            sb.append("  ")
            sb.append(getSourceStr(1))
        }
        log(logType, sb.toString())

    }


    private fun getSourceStr(complement: Int): String {
        val ste = Thread.currentThread().stackTrace
        if (ste.size >= steIndex + complement) {
            return ste[steIndex + complement].toString()
        }
        return ""
    }


    private fun generateBorder(logType: Int, borderType: String) {
        val sb = StringBuilder()
        sb.append(borderType)
        var isEnd = true
        while (isEnd) {
            sb.append(horizontalLine)
            val size = sb.length
            if (size == lineSize) {
                isEnd = false
            }
        }
        log(logType, sb.toString())
    }


    private fun generateLine(logType: Int, content: String?) {
        if (content == null) {
            return
        }
        val msgArray = content.toCharArray()
        val msgSb = StringBuilder()
        msgSb.append(verticalLine)
        var chineseCount = 0
        msgArray.forEachIndexed { index, c ->
            if (c != '\n') {
                msgSb.append(c)
                if (c.toString().matches(Regex("[^x00-xf]"))) {
                    chineseCount++
                }
            }
            val size = msgSb.length
            val total = lineSize - (chineseCount / 2)
            if (size >= total || c == '\n') {
                log(logType, msgSb.toString())
                chineseCount = 0
                msgSb.clear()
                msgSb.append(verticalLine)
            }

            if (index == msgArray.size - 1) {
                if (msgSb.isNotEmpty()) {
                    log(logType, msgSb.toString())
                }
                generateBorder(logType, bottomBorder)
            }

        }
    }


    private fun print(logType: Int, title: String, message: String, isShowSource: Boolean) {
        generateBorder(logType, topBorder)
        if (title.isNotEmpty()) {
            generateTitle(logType, title, isShowSource)
            generateBorder(logType, middleLine)
        } else {
            if (isShowSource) {
                log(logType, verticalLine + getSourceStr(0))
            }
        }
        generateLine(logType, message)
    }


    private fun log(logType: Int, content: String) {

        if (!isDebug) {
            return
        }

        when (logType) {
            verbose -> Log.v(logTag, content)
            debug -> Log.d(logTag, content)
            info -> Log.i(logTag, content)
            warn -> Log.w(logTag, content)
            error -> Log.e(logTag, content)
        }
    }
}
