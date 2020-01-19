package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("track")
    var track: List<Track?> = listOf()
)