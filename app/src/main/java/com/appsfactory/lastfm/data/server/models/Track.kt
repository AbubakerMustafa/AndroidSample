package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("artist")
    var artist: Artist? = Artist(),
    @SerializedName("@attr")
    var attr: Attr? = Attr(),
    @SerializedName("duration")
    var duration: Long? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("streamable")
    var streamable: Streamable? = Streamable(),
    @SerializedName("url")
    var url: String? = ""
)