package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class ArtistMatches(
    @SerializedName("artist")
    var artist: List<Artist?>? = listOf()
)