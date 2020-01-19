package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("tag")
    var tag: List<Tag?>? = listOf()
)