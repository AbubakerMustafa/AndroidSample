package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class ArtistSearchResponse(
    @SerializedName("results")
    var results: Results? = Results()
) : BaseResponse()