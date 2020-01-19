package com.appsfactory.lastfm.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.appsfactory.lastfm.R

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkConnectionInterceptor(private val mContext: Context) : Interceptor {

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException(mContext.getString(R.string.network_error))
            // Throwing our custom exception 'NoConnectivityException'
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}