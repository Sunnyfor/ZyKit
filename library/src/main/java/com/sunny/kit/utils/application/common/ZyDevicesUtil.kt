package com.sunny.kit.utils.application.common

interface ZyDevicesUtil {

    /**
     * 获取设备类型
     */
    fun getDeviceType(): String

    /**
     * 获得SD卡总大小
     */
    fun getSDCardTotalSize(): Long

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    fun getSDAvailableSize(): Long

    /**
     * 设备是否已 root
     */
    fun isDeviceRooted(): Boolean

    /**
     *  ADB 是否启用。
     */
    fun isAdbEnabled(): Boolean

    /**
     * 获取设备唯一标识
     */
    fun getDeviceId(): String

    /**
     * 是否是模拟器
     */
    fun isEmulator(): Boolean


}