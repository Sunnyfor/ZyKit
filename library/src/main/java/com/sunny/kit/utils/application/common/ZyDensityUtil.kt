package com.sunny.kit.utils.application.common

import android.content.Context
import android.view.View
import com.sunny.kit.utils.application.ZyKit

interface ZyDensityUtil {

    /**
     * 设计稿宽度
     */
    var designBaselineWidth: Int

    /**
     * 是否为最小宽度适配
     */
    var isSmallestWidth: Boolean

    /**
     * 偏移值
     */
    var landscapeOffset: Int

    /**
     * 将 value 转换为设计稿宽度比例的PX
     * @param value 值
     * @param designBaselineWidth 设计稿宽度
     * @param isSmallestWidth 是否为最小宽度适配
     * @return 最小宽度等比例的PX
     */
    fun convertScreenWidthPx(
        value: Float,
        designBaselineWidth: Int = this.designBaselineWidth,
        isSmallestWidth: Boolean = this.isSmallestWidth,
        context: Context = ZyKit.getContext()
    ): Int

    /**
     * 是否为横屏
     */
    fun isLandscape(context: Context = ZyKit.getContext()): Boolean

    /**
     * 是否为竖屏
     */
    fun isPortrait(context: Context = ZyKit.getContext()): Boolean

    /**
     * 将 px 像素转换为 dp
     */
    fun px2dp(px: Float, context: Context = ZyKit.getContext()): Int

    /**
     * 将 dp 转换为像素（px）
     */
    fun dp2px(dp: Float, context: Context = ZyKit.getContext()): Int

    /**
     * 将 sp 转换为像素（px）
     */
    fun sp2px(spValue: Float, context: Context = ZyKit.getContext()): Int

    /**
     * 将 px 像素转换为 dp
     */
    fun px2dpFloat(px: Float, context: Context = ZyKit.getContext()): Float

    /**
     * 将 dp 转换为像素（px）
     */
    fun dp2pxFloat(dp: Float, context: Context = ZyKit.getContext()): Float

    /**
     * 将 sp 转换为像素（px）
     */
    fun sp2pxFloat(sp: Float, context: Context = ZyKit.getContext()): Float

    /**
     * 获取手机屏幕的宽度
     */
    fun screenWidth(context: Context = ZyKit.getContext()): Int

    /**
     * 获取手机屏幕的高度
     */
    fun screenHeight(context: Context = ZyKit.getContext()): Int

    /**
     * 获取控件的宽度
     */
    fun viewWidth(view: View): Int

    /**
     * 获取控件的高度
     */
    fun viewHeight(view: View): Int

    /**
     * 获取控件在窗体中的位置
     */
    fun getViewLocationInWindow(view: View): IntArray

    /**
     * 获取控件在整个屏幕中的 位置
     */
    fun getViewLocationInScreen(view: View): IntArray

    /**
     * 获取设备的尺寸
     */
    fun getDeviceSize(context: Context = ZyKit.getContext()): Double

    /**
     * 获取设备状态栏的高度
     */
    fun getStatusBarHeight(context: Context = ZyKit.getContext()): Int

    /**
     * 获取标题栏高度
     */
    fun getToolBarHeight(context: Context = ZyKit.getContext()): Int
}