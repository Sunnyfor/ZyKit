package com.sunny.kit.utils.api.common

import android.view.View

interface ZyDensityUtil {

    /**
     * 将 px 像素转换为 dp
     */
    fun px2dp(px: Float): Int

    /**
     * 将 dp 转换为像素（px）
     */
    fun dp2px(dp: Float): Int

    /**
     * 将 sp 转换为像素（px）
     */
    fun sp2px(spValue: Float): Int

    /**
     * 将 px 像素转换为 dp
     */
    fun px2dpFloat(px: Float): Float

    /**
     * 将 dp 转换为像素（px）
     */
    fun dp2pxFloat(dp: Float): Float

    /**
     * 将 sp 转换为像素（px）
     */
    fun sp2pxFloat(sp: Float): Float

    /**
     * 获取手机屏幕的宽度
     */
    fun screenWidth(): Int

    /**
     * 获取手机屏幕的高度
     */
    fun screenHeight(): Int

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
    fun getDeviceSize(): Double

    /**
     * 获取设备状态栏的高度
     */
    fun getStatusBarHeight(): Int

    /**
     * 获取标题栏高度
     */
    fun getToolBarHeight(): Int
}