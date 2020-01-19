package com.appsfactory.lastfm.data.server.models


import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("artistmatches")
    var artistMatches: ArtistMatches? = ArtistMatches(),
    @SerializedName("@attr")
    var attr: Attr? = Attr(),
    @SerializedName("opensearch:itemsPerPage")
    var opensearchItemsPerPage: Long = 0,
    @SerializedName("opensearch:Query")
    var opensearchQuery: OpensearchQuery? = OpensearchQuery(),
    @SerializedName("opensearch:startIndex")
    var opensearchStartIndex: Long? = 0,
    @SerializedName("opensearch:totalResults")
    var opensearchTotalResults: Long? = 0
)