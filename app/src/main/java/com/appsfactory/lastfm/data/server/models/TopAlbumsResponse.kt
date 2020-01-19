package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("topalbums")
    var topalbums: Topalbums? = Topalbums()
)