package com.appsfactory.lastfm.ui.base

import android.accounts.NetworkErrorException
import android.util.Log
import com.appsfactory.lastfm.util.NoConnectivityException
import java.net.ConnectException
import java.net.UnknownHostException

class Resource<T>(val apiState: ApiState, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ApiState.SUCCESS, data, null)
        }

        fun <T> error(error: String): Resource<T> {
            return Resource(ApiState.ERROR, null, error)
        }

        fun <T> error(): Resource<T> {
            return Resource(ApiState.GENERAL_ERROR, null, null)
        }

        fun <T> error(t: Throwable): Resource<T> {
            Log.d("error : ", "internal error", t)
            return Resource(ApiState.GENERAL_ERROR, null, null)
        }

        fun <T> networkError(): Resource<T> {
            return Resource(ApiState.NETWORK_ERROR, null, null)
        }

        fun <T> showLoader(data: T?): Resource<T> {
            return Resource(ApiState.SHOW_LOADER, data, null)
        }

        fun <T> hideLoader(data: T?): Resource<T> {
            return Resource(ApiState.HIDE_LOADER, data, null)
        }

        fun <T> noDataFound(): Resource<T> {
            return Resource(ApiState.NO_DATA, null, null)
        }

        fun <T> sessionExpired(message: String): Resource<T> {
            return Resource(ApiState.SESSION_EXPIRED, null, message)
        }

        fun handleError(throwable: Throwable?): Resource<Any>? {
            return if (throwable != null && (throwable.cause is NetworkErrorException ||
                        throwable.cause is ConnectException ||
                        throwable.cause is UnknownHostException
                        || throwable.cause is NoConnectivityException)
            ) {
                networkError()
            } else {
                error()
            }
        }
    }
}