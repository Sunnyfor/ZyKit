package com.sunny.kit.utils.domain.common

import android.util.Log
import com.sunny.kit.utils.application.common.ZyLogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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

    private var linesPerSecond = 250
    private var currentCount = 0

    /**
     * 设置Log标签名
     */
    private var logTag = "ZYLog"

    private var lineLength = 160

    private var topBorder = "┌"
    private var bottomBorder = "└"
    private var middleLine = "├"
    private var verticalLine = "│ "
    private var horizontalLine = "─"

    private var titleStart = "【"

    private var titleEnd = "】"

    private val scope by lazy { CoroutineScope(IO) }

    private val mutex by lazy { Mutex() }

    private var blockTimeMillis = 0L

    init {
        setMaxLogLength(lineLength)
    }

    override fun setMaxLogLength(length: Int) {
        lineLength = length
        horizontalLine = horizontalLine.repeat(length)
    }

    override fun setLinesPerSecond(value: Int) {
        this.linesPerSecond = value
    }

    override fun setTag(tag: String) {
        logTag = tag
    }


    override fun v(title: String, message: String, isShowStackTrace: Boolean) {
        print(Log.VERBOSE, title, message, isShowStackTrace)
    }

    override fun v(message: String, isShowStackTrace: Boolean) {
        v("", message, isShowStackTrace)
    }

    override fun d(title: String, message: String, isShowStackTrace: Boolean) {
        print(Log.DEBUG, title, message, isShowStackTrace)
    }

    override fun d(message: String, isShowStackTrace: Boolean) {
        d("", message, isShowStackTrace)
    }

    override fun i(title: String, message: String, isShowStackTrace: Boolean) {
        print(Log.INFO, title, message, isShowStackTrace)
    }

    override fun i(message: String, isShowStackTrace: Boolean) {
        i("", message, isShowStackTrace)
    }

    override fun w(title: String, message: String, isShowStackTrace: Boolean) {
        print(Log.WARN, title, message, isShowStackTrace)
    }

    override fun w(message: String, isShowStackTrace: Boolean) {
        w("", message, isShowStackTrace)
    }

    override fun e(title: String, message: String, isShowStackTrace: Boolean) {
        print(Log.ERROR, title, message, isShowStackTrace)
    }

    override fun e(message: String, isShowStackTrace: Boolean) {
        e("", message, isShowStackTrace)
    }

    override fun println(message: String) {
        println(Log.INFO, message)
    }

    override fun println(logType: Int, message: String) {
        scope.launch {
            mutex.withLock {
                log(logType, message)
            }
        }
    }

    private fun print(logType: Int, title: String, message: String, isShowStackTrace: Boolean) {
        var sourceStr = ""
        if (isShowStackTrace) {
            sourceStr = getSourceStr()
        }
        scope.launch {
            mutex.withLock {
                printBorder(logType, topBorder)
                if (title.isNotEmpty() || sourceStr.isNotEmpty()) {
                    printTitle(logType, title, sourceStr)
                    printBorder(logType, middleLine)
                }
                printContent(logType, message)
                printBorder(logType, bottomBorder)
            }
        }
    }

    private fun getSourceStr(): String {
        val stacks = Thread.currentThread().stackTrace
        var stackIndex = -1
        stacks.forEachIndexed { index, stackTraceElement ->
            if (stackTraceElement.className == javaClass.name) {
                stackIndex = index + 1
            }
        }
        if (stacks.size >= stackIndex) {
            return stacks[stackIndex].toString()
        }
        return ""
    }

    private suspend fun printTitle(logType: Int, title: String, subTitle: String) {
        val sb = StringBuilder(verticalLine)
        if (title.isNotEmpty()) {
            sb.append("$titleStart$title$titleEnd")
        }
        if (subTitle.isNotEmpty()) {
            sb.append("  ")
            sb.append(subTitle)
        }
        log(logType, sb.toString())

    }

    private suspend fun printBorder(logType: Int, borderType: String) {
        val sb = StringBuilder()
        sb.append(borderType)
        sb.append(horizontalLine)
        log(logType, sb.toString())
    }

    private suspend fun printContent(logType: Int, content: String?) {
        var str = content
        if (str == null) {
            str = "null"
        }
        var msgLength = 0
        val msgSb = StringBuilder(verticalLine)
        str.forEach { char ->
            if (char.code == 10) {  // '\n' (newline character)
                log(logType, msgSb.toString())
                msgSb.clear()
                msgSb.append(verticalLine)
                msgLength = 0
            } else {
                val charLength = if (char.code in 0x4E00..0x9FFF) 2 else 1
                if (msgLength + charLength > lineLength) {
                    log(logType, msgSb.toString())
                    msgSb.clear()
                    msgSb.append(verticalLine)
                    msgLength = 0
                }
                msgSb.append(char)
                msgLength += charLength
            }
        }
        if (msgSb.length >= verticalLine.length) {
            log(logType, msgSb.toString())
        }
    }


    private suspend fun log(logType: Int, content: String) {
        if (!isDebug) {
            return
        }

        when (logType) {
            Log.VERBOSE -> Log.v(logTag, content)
            Log.DEBUG -> Log.d(logTag, content)
            Log.INFO -> Log.i(logTag, content)
            Log.WARN -> Log.w(logTag, content)
            Log.ERROR -> Log.e(logTag, content)
        }

        if (linesPerSecond > 0) {
            if (System.currentTimeMillis() - blockTimeMillis < 1000) {
                currentCount++
            } else {
                currentCount = 0
                blockTimeMillis = System.currentTimeMillis()
            }

            if (currentCount >= linesPerSecond) {
                currentCount = 0
                blockTimeMillis = System.currentTimeMillis()
                delay(1000)
            }
        }
    }
}
