package ir.pooriak.core.base.interceptor

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by POORIAK on 14,September,2023
 */
@Suppress("DEPRECATION")
internal val RequestHeaderInterceptor.isConnected: Boolean
    get() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }