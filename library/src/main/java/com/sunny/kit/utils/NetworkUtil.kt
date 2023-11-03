package com.sunny.kit.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.sunny.kit.utils.api.ZyKit

/**
 * 网络请求判断
 */
class NetworkUtil(private var callBack: (isAvailable: Boolean, netType: NetType) -> Unit) : ConnectivityManager.NetworkCallback() {

    private var isAvailable = false

    private var netType = NetType.OTHER

    private val manager by lazy {
        ZyKit.getContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE)
    }

    fun register() {
        if (manager is ConnectivityManager) {
            (manager as ConnectivityManager).unregisterNetworkCallback(this)
            (manager as ConnectivityManager).registerNetworkCallback(
                NetworkRequest.Builder().build(),
                this
            )
        } else {
            callBack.invoke(isAvailable, netType)
            LogUtil.i("没有获取到ConnectivityManager！")
        }
    }

    override fun onAvailable(network: Network) {
        isAvailable = true
        if (manager is ConnectivityManager) {
            (manager as ConnectivityManager).getNetworkCapabilities(network)?.let {
                netType = getNetType(it)
            }
        }
        callBack.invoke(isAvailable, netType)
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
        callBack.invoke(isAvailable, netType)
    }

    private fun getNetType(networkCapabilities: NetworkCapabilities): NetType {
        return if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            NetType.WIFI
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            NetType.WAP
        } else {
            NetType.OTHER
        }
    }


    fun unRegister() {
        if (manager is ConnectivityManager) {
            (manager as ConnectivityManager).unregisterNetworkCallback(this)
        }
    }

    enum class NetType {
        WIFI, WAP, OTHER
    }
}