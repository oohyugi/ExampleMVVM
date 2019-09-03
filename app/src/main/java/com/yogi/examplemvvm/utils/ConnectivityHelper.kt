package com.yogi.examplemvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager


/**
 * Created by oohyugi on 2019-09-03.
 * github: https://github.com/oohyugi
 */


private const val TYPE_WIFI = 1
private const val TYPE_CELLULAR = 0


@SuppressLint("MissingPermission")
fun Context.getNetworkInfo(): Int? {
    var networkType = 0
    val connectivityMgr =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nc = connectivityMgr.getNetworkCapabilities(connectivityMgr.activeNetwork)
        if (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            // connected to mobile data
            networkType = TYPE_CELLULAR
        } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            // connected to wifi
            networkType = TYPE_WIFI
        }
    } else {
        val activeNetwork = connectivityMgr.activeNetworkInfo
        if (activeNetwork?.type == ConnectivityManager.TYPE_WIFI) {
            // connected to wifi
            networkType = TYPE_WIFI
        } else if (activeNetwork?.type == ConnectivityManager.TYPE_MOBILE) {
            // connected to mobile data
            networkType = TYPE_CELLULAR
        }
    }


    return networkType
}

@SuppressLint("MissingPermission")
fun Context.isNetworkConnected(): Boolean {

    try {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            val an = cm.activeNetwork ?: return false
            val capabilities = cm.getNetworkCapabilities(an) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val a = cm.activeNetworkInfo ?: return false
            a.isConnected && (a.type == ConnectivityManager.TYPE_WIFI || a.type == ConnectivityManager.TYPE_MOBILE)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false

}


/**
 * Check if there is any connectivity to a Wifi network
 * @return
 */
fun Context.isConnectedWifi(): Boolean {
    val info = getNetworkInfo()
    return info != null && isNetworkConnected() && info == TYPE_WIFI
}

/**
 * Check if there is any connectivity to a mobile network
 * @return
 */
fun Context.isConnectedMobile(): Boolean {
    val info = getNetworkInfo()
    return info != null && isNetworkConnected() && info == TYPE_CELLULAR
}

/**
 * Check if there is fast connectivity
 * @return
 */
fun Context.isConnectedFast(): Boolean {
    val info = getNetworkInfo()
    val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return info != null && isNetworkConnected() && isConnectionFast(info, tm.networkType)
}


private fun isConnectionFast(type: Int, subType: Int): Boolean {
    return when (type) {
        TYPE_WIFI -> true
        TYPE_CELLULAR -> when (subType) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
            TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps

            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
            // API level 11
            TelephonyManager.NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps
            // API level 9
            TelephonyManager.NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps
            // API level 13
            TelephonyManager.NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps
            // API level 8
            TelephonyManager.NETWORK_TYPE_IDEN -> false // ~25 kbps
            // API level 11
            TelephonyManager.NETWORK_TYPE_LTE -> true // ~ 10+ Mbps
            // Unknown
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
            else -> false
        }
        else -> false
    }
}
