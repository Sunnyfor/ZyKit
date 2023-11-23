package com.sunny.kit.utils.application.common

import android.widget.Toast
import androidx.annotation.StringRes

interface ZyToastUtil {
    fun show(content: String, duration: Int = Toast.LENGTH_SHORT)

    fun show(@StringRes contentRes: Int, duration: Int = Toast.LENGTH_SHORT)

    fun show(obj: Any, duration: Int = Toast.LENGTH_SHORT)

    fun cancel()
}