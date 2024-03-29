package com.sunny.kit.utils.domain.common

import android.util.Log
import com.sunny.kit.utils.application.common.ZyLogUtil

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
    private var lineLength = 160

    private var topBorder = "┌"
    private var bottomBorder = "└"
    private var middleLine = "├"
    private var verticalLine = "│ "
    private var horizontalLine = "─"

    private var titleStart = "【"

    private var titleEnd = "】"


    init {
        setLineLength(lineLength)
    }

    fun setLineLength(length: Int) {
        lineLength = length
        val sb = StringBuilder()
        for (i in 0 until lineLength) {
            sb.append(horizontalLine)
        }
        horizontalLine = sb.toString()
    }


    override fun v(title: String, message: String, isShowSource: Boolean) {
        print(verbose, title, message, isShowSource)
    }

    override fun v(message: String, isShowSource: Boolean) {
        v("", message, isShowSource)
    }

    override fun d(title: String, message: String, isShowSource: Boolean) {
        print(debug, title, message, isShowSource)
    }

    override fun d(message: String, isShowSource: Boolean) {
        d("", message, isShowSource)
    }

    override fun i(title: String, message: String, isShowSource: Boolean) {
        print(info, title, message, isShowSource)
    }

    override fun i(message: String, isShowSource: Boolean) {
        i("", message, isShowSource)
    }

    override fun w(title: String, message: String, isShowSource: Boolean) {
        print(warn, title, message, isShowSource)
    }

    override fun w(message: String, isShowSource: Boolean) {
        w("", message, isShowSource)
    }

    override fun e(title: String, message: String, isShowSource: Boolean) {
        print(error, title, message, isShowSource)
    }

    override fun e(message: String, isShowSource: Boolean) {
        e("", message, isShowSource)
    }


    private fun printTitle(logType: Int, title: String, isShowSource: Boolean) {
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


    private fun printBorder(logType: Int, borderType: String) {
        val sb = StringBuilder()
        sb.append(borderType)
        sb.append(horizontalLine)
        log(logType, sb.toString())
    }


    private fun printContent(logType: Int, content: String?) {
        if (content == null) {
            return
        }
        var msgLength = 0
        val msgSb = StringBuilder(verticalLine)
        content.forEach {
            if (it.code == 10) {
                log(logType, msgSb.toString())
                msgSb.setLength(0)
                msgSb.append(verticalLine)
                msgLength = 0
            } else {
                val charLength = if (it.code in 0x4E00..0x9FFF) 2 else 1
                if (msgLength + charLength > lineLength) {
                    log(logType, msgSb.toString())
                    msgSb.setLength(0)
                    msgSb.append(verticalLine)
                    msgLength = 0
                } else {
                    msgSb.append(it)
                    msgLength += charLength
                }
            }
        }
        if (msgSb.length > 2) {
            log(logType, msgSb.toString())
        }
    }


    private fun print(logType: Int, title: String, message: String, isShowSource: Boolean) {
        printBorder(logType, topBorder)
        if (title.isNotEmpty()) {
            printTitle(logType, title, isShowSource)
            printBorder(logType, middleLine)
        } else {
            if (isShowSource) {
                log(logType, verticalLine + getSourceStr(0))
            }
        }
        printContent(logType, message)
        printBorder(logType, bottomBorder)
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
