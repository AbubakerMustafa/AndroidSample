package com.appsfactory.lastfm.util

import java.io.IOException

class NoConnectivityException(private var customMessage: String) : IOException() {

    override fun getLocalizedMessage(): String {
        return customMessage
    }

    override val message: String?
        get() = customMessage
}