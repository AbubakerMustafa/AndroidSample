package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("rank")
    var rank: String? = "",
    @SerializedName("for")
    var forX: String? = "",
    @SerializedName("artist")
    var artist: String? = "",
    @SerializedName("page")
    var page: Long = 0,
    @SerializedName("perPage")
    var perPage: Long = 0,
    @SerializedName("total")
    var total: Long = 0,
    @SerializedName("totalPages")
    var totalPages: Long = 0
)