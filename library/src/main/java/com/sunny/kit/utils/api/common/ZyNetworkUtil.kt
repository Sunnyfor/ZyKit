package com.sunny.kit.utils.api.common

interface ZyNetworkUtil {
    var isAvailable: Boolean
    var netType: NetType

    fun setOnNetWorkChangerListener(onNetWorkChangerListener: OnNetWorkChangerListener?)

    interface OnNetWorkChangerListener {
        fun onNetWorkChanger(isAvailable: Boolean, netType: NetType)
    }

    enum class NetType {
        WIFI, WAP, OTHER,NONE
    }
}