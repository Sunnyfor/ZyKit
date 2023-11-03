package com.sunny.kit.utils.impl

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.View
import com.sunny.kit.utils.api.ZyKit
import com.sunny.kit.utils.api.common.ZyDensityUtil
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Desc 屏幕密度工具类
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2020/2/13
 */
internal class ZyDensityUtilImpl : ZyDensityUtil {

    /**
     * 将 px 像素转换为 dp
     */
    override fun px2dp(px: Float): Int {
        return (px2dpFloat(px) + 0.5f).toInt()
    }

    /**
     * 将 dp 转换为像素（px）
     */
    override fun dp2px(dp: Float): Int {
        return (dp2pxFloat(dp) + 0.5f).toInt()
    }

    /**
     * 将 sp 转换为像素（px）
     */
    override fun sp2px(spValue: Float): Int {
        return (sp2pxFloat(spValue) + 0.5f).toInt()
    }

    /**
     * 将 px 像素转换为 dp
     */
    override fun px2dpFloat(px: Float): Float {
        val density = ZyKit.getContext().resources.displayMetrics.density
        return px / density
    }

    /**
     * 将 dp 转换为像素（px）
     */
    override fun dp2pxFloat(dp: Float): Float {
        val density = ZyKit.getContext().resources.displayMetrics.density
        return dp * density
    }

    /**
     * 将 sp 转换为像素（px）
     */
    override fun sp2pxFloat(sp: Float): Float {
        val density = ZyKit.getContext().resources.displayMetrics.scaledDensity
        return sp * density
    }

    /**
     * 获取手机屏幕的高度
     */
    override fun screenHeight(): Int {
        return ZyKit.getContext().resources.displayMetrics.heightPixels
    }

    /**
     * 获取手机屏幕的宽度
     */
    override fun screenWidth(): Int {
        return ZyKit.getContext().resources.displayMetrics.widthPixels
    }

    /**
     * 获取控件的宽度
     */
    override fun viewWidth(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredWidth
    }

    /**
     * 获取控件的高度
     */
    override fun viewHeight(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredHeight
    }

    /**
     * 获取控件在窗体中的位置
     */
    override fun getViewLocationInWindow(view: View): IntArray {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location
    }

    /**
     * 获取控件在整个屏幕中的 位置
     */
    override fun getViewLocationInScreen(view: View): IntArray {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return location
    }

    /**
     * 获取设备的尺寸
     */
    override fun getDeviceSize(): Double {
        val dm = ZyKit.getContext().resources.displayMetrics
        val sqrt = sqrt(dm.widthPixels.toDouble().pow(2.0) + dm.heightPixels.toDouble().pow(2.0))
        return sqrt / (160 * dm.density)
    }

    /**
     * 获取设备状态栏的高度
     */
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    override fun getStatusBarHeight(): Int {
        val resources = ZyKit.getContext().resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return (getToolBarHeight() / 1.55).toInt()
    }

    /**
     * 获取标题栏高度
     */
    override fun getToolBarHeight(): Int {
        val tv = TypedValue()
        return if (ZyKit.getContext().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, ZyKit.getContext().resources.displayMetrics)
        } else 0
    }

}