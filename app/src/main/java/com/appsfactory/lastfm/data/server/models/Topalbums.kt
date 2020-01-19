package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Topalbums(
    @SerializedName("album")
    var album: List<Album?>? = listOf(),
    @SerializedName("@attr")
    var attr: Attr? = Attr()
)