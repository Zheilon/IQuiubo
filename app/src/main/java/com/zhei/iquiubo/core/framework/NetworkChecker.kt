package com.zhei.iquiubo.core.framework
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false

        val cond1 = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val cond2 = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        return cond1 && cond2
    }
}