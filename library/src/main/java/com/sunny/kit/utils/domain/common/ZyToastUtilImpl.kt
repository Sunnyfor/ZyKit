package com.sunny.kit.utils.domain.common

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyToastUtil

class ZyToastUtilImpl : ZyToastUtil {

    private val handler = Handler(Looper.getMainLooper())

    private var durationLong = 3500L
    private var durationShort = 2000L

    private var toast: Toast? = null

    override fun show(content: String, duration: Int) {
        handler.removeCallbacksAndMessages(null)
        cancel()
        toast = Toast.makeText(ZyKit.getContext(), content, duration)
        toast?.show()
        val delay = if (duration == Toast.LENGTH_SHORT) durationShort else durationLong
        handler.postDelayed({
            cancel()
        }, delay)
    }

    override fun show(contentRes: Int, duration: Int) {
        show(ZyKit.getContext().resources.getString(contentRes), duration)
    }

    override fun show(obj: Any, duration: Int) {
        show(obj.toString(), duration)
    }

    override fun cancel() {
        if (toast != null) {
            toast?.cancel()
            toast = null
        }
    }
}