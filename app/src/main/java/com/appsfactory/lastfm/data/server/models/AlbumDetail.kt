package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class AlbumDetail(
    var _id: Long = 0,
    @SerializedName("artist")
    var artist: String = "",
    @SerializedName("image")
    var image: List<Image> = listOf(),
    @SerializedName("listeners")
    var listeners: String = "",
    @SerializedName("mbid")
    var mbid: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("playcount")
    var playcount: Long = 0,
    @SerializedName("tags")
    var tags: Tags = Tags(),
    @SerializedName("tracks")
    var tracks: Tracks = Tracks(),
    @SerializedName("url")
    var url: String = "",
    @SerializedName("wiki")
    var wiki: Wiki = Wiki()
) {
    fun getBestImage(): String {
        return if (image.isNotEmpty())
            image[image.size - 1].text else ""

    }
}