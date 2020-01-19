package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class OpensearchQuery(
    @SerializedName("role")
    var role: String? = "",
    @SerializedName("searchTerms")
    var searchTerms: String? = "",
    @SerializedName("startPage")
    var startPage: String? = "",
    @SerializedName("#text")
    var text: String? = ""
)