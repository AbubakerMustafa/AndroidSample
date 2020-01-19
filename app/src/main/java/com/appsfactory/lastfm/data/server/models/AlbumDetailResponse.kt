package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class AlbumDetailResponse(
    @SerializedName("album")
    var album: AlbumDetail? = AlbumDetail()
) : BaseResponse()