package com.sunny.kit.utils.domain.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.format.Formatter
import android.util.Base64
import com.sunny.kit.utils.application.ZyKit
import com.sunny.kit.utils.application.common.ZyDevicesUtil
import java.io.File
import java.util.Locale

class ZyDevicesUtilImpl : ZyDevicesUtil {

    /**
     * 获取设备类型
     */
    override fun getDeviceType(): String {
        val resources = ZyKit.getContext().resources
        val screenWidthDp = resources.displayMetrics.widthPixels / resources.displayMetrics.density
        val screenHeightDp = resources.displayMetrics.heightPixels / resources.displayMetrics.density
        return when {
            screenWidthDp < 600 -> "phone"
            screenWidthDp in 600.0..1024.0 -> "tablet"
            screenWidthDp > 1024 || screenHeightDp > 1024 -> "tv"
            else -> "unknown"
        }
    }

    /**
     * 获得SD卡总大小
     */
    override fun getSDCardTotalSize(): Long {
        val path = Environment.getExternalStorageDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        return blockSize * totalBlocks
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    override fun getSDAvailableSize(): Long {
        val path = Environment.getExternalStorageDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return blockSize * availableBlocks
    }

    /**
     * 判断设备是否root
     */
    override fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        )
        for (location in locations) {
            if (File(location + su).exists()) {
                return true
            }
        }

        return false
    }

    /**
     * 判断是否开启调试
     */
    override fun isAdbEnabled(): Boolean {
        return Settings.Secure.getInt(ZyKit.getContext().contentResolver, Settings.Global.ADB_ENABLED, 0) > 0
    }


    /**
     * 获取设备唯一标识
     */
    override fun getDeviceId(): String {
        val sbDeviceId = StringBuilder()
        val androidId = getAndroidId()
        val uuid = getDeviceUUID()

        if (androidId.isNotEmpty()) {
            sbDeviceId.append(androidId)
            sbDeviceId.append("|")
        }

        if (uuid.isNotEmpty()) {
            sbDeviceId.append(uuid)
        }
        return ZyKit.encrypt.sha1(sbDeviceId.toString())
    }

    /**
     * 获取AndroidId
     */
    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        return try {
            Settings.Secure.getString(ZyKit.getContext().contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取设备唯一标识
     */
    private fun getDeviceUUID(): String {
        try {
            val dev =
                getAndroidId() + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.HARDWARE.length % 10 + Build.ID.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
            return ZyKit.encrypt.md5(Base64.encodeToString(dev.toByteArray(), Base64.DEFAULT))
        } catch (e: Exception) {
            return ""
        }
    }

    /**
     * 判断是否是模拟器
     */
    override fun isEmulator(): Boolean {
        val checkProperty = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))) || "google_sdk" == Build.PRODUCT
        if (checkProperty) {
            return true
        }

        var operatorName = ""
        val tm = ZyKit.getContext().getSystemService(Context.TELEPHONY_SERVICE)
        if (tm is TelephonyManager) {
            val name = tm.networkOperatorName
            if (name != null) {
                operatorName = name
            }
        }
        val checkOperatorName = operatorName.lowercase(Locale.getDefault()) == "android"
        return checkOperatorName
    }
}