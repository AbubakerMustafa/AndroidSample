package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Wiki(
    @SerializedName("content")
    var content: String? = "",
    @SerializedName("published")
    var published: String? = "",
    @SerializedName("summary")
    var summary: String? = ""
)