package com.sunny.kit.utils.impl.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.sunny.kit.utils.api.ZyKit
import com.sunny.kit.utils.api.common.ZyNetworkUtil

/**
 * 网络请求判断
 */
internal class ZyNetworkUtilImpl : ConnectivityManager.NetworkCallback(), ZyNetworkUtil {

    private var onNetWorkChangerListener: ZyNetworkUtil.OnNetWorkChangerListener? = null

    override var isAvailable = false

    override var netType = ZyNetworkUtil.NetType.NONE

    override fun setOnNetWorkChangerListener(onNetWorkChangerListener: ZyNetworkUtil.OnNetWorkChangerListener?) {
        this.onNetWorkChangerListener = onNetWorkChangerListener
    }


    private val manager by lazy {
        ZyKit.getContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE)
    }

    init {
        if (manager is ConnectivityManager) {
            (manager as ConnectivityManager).registerNetworkCallback(NetworkRequest.Builder().build(), this)
            ZyKit.log.i("开始监听网络状态！")
        } else {
            onNetWorkChangerListener?.onNetWorkChanger(isAvailable, netType)
            ZyKit.log.i("没有获取到ConnectivityManager！")
        }
    }


    override fun onAvailable(network: Network) {
        isAvailable = true
        if (manager is ConnectivityManager) {
            (manager as ConnectivityManager).getNetworkCapabilities(network)?.let {
                netType = getNetType(it)
            }
        }
        onNetWorkChangerListener?.onNetWorkChanger(isAvailable, netType)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            netType = getNetType(networkCapabilities)
        }
    }

    override fun onLost(network: Network) {
        isAvailable = false
        (manager as ConnectivityManager).getNetworkCapabilities(network)?.let {
            netType = getNetType(it)
        }
        onNetWorkChangerListener?.onNetWorkChanger(isAvailable, netType)
    }

    private fun getNetType(networkCapabilities: NetworkCapabilities): ZyNetworkUtil.NetType {
        return if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            ZyNetworkUtil.NetType.WIFI
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            ZyNetworkUtil.NetType.WAP
        } else {
            ZyNetworkUtil.NetType.OTHER
        }
    }
}