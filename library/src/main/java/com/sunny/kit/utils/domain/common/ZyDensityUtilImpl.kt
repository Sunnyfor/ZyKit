package com.sunny.kit.utils.domain.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyDensityUtil
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
     * 设计稿宽度
     */
    override var designBaselineWidth = 360

    override var isSmallestWidth = false

    override var landscapeOffset = 0

    /**
     * 将 value 转换为设计稿宽度比例的dp
     * @param value 值
     * @return 最小宽度等比例的DIP
     */
    override fun convertScreenWidthPx(value: Float, designBaselineWidth: Int, isSmallestWidth: Boolean, context: Context): Int {
        var screenWidth = screenWidth(context)
        if (isLandscape(context)) {
            if (isSmallestWidth) {
                screenWidth = screenHeight(context)
            } else {
                screenWidth += landscapeOffset
            }
        }
        val result = (value * screenWidth / designBaselineWidth + 0.5)
        return result.toInt()
    }

    override fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * 将 px 像素转换为 dp
     */
    override fun px2dp(px: Float, context: Context): Int {
        return (px2dpFloat(px) + 0.5f).toInt()
    }

    /**
     * 将 dp 转换为像素（px）
     */
    override fun dp2px(dp: Float, context: Context): Int {
        return (dp2pxFloat(dp) + 0.5f).toInt()
    }

    /**
     * 将 sp 转换为像素（px）
     */
    override fun sp2px(spValue: Float, context: Context): Int {
        return (sp2pxFloat(spValue) + 0.5f).toInt()
    }

    /**
     * 将 px 像素转换为 dp
     */
    override fun px2dpFloat(px: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }

    /**
     * 将 dp 转换为像素（px）
     */
    override fun dp2pxFloat(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    /**
     * 将 sp 转换为像素（px）
     */
    override fun sp2pxFloat(sp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.scaledDensity
        return sp * density
    }

    /**
     * 获取手机屏幕的高度
     */
    override fun screenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取手机屏幕的宽度
     */
    override fun screenWidth(context: Context): Int {

        return context.resources.displayMetrics.widthPixels
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
    override fun getDeviceSize(context: Context): Double {
        val dm = context.resources.displayMetrics
        val sqrt = sqrt(dm.widthPixels.toDouble().pow(2.0) + dm.heightPixels.toDouble().pow(2.0))
        return sqrt / (160 * dm.density)
    }

    /**
     * 获取设备状态栏的高度
     */
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    override fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return (getToolBarHeight() / 1.55).toInt()
    }

    /**
     * 获取标题栏高度
     */
    override fun getToolBarHeight(context: Context): Int {
        val tv = TypedValue()
        return if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        } else ZyKit.density.convertScreenWidthPx(46f, context = context)
    }

}