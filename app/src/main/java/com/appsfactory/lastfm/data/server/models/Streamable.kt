package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Streamable(
    @SerializedName("fulltrack")
    var fulltrack: String? = "",
    @SerializedName("#text")
    var text: String? = ""
)