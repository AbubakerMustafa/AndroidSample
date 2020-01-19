package com.appsfactory.lastfm.data.server.models

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("code")
    open var code: Int = 0
    @SerializedName("message")
    open var message: String = ""
}