package com.appsfactory.lastfm.util


object ServiceNames {

    const val SEARCH_ALBUMS = "albums"
    const val SEARCH_ARTIST = "?method=artist.search"
    const val GET_TOP_ALBUMS = "?method=artist.gettopalbums"
    const val GET_ALBUMS_DETAIL = "?method=album.getinfo"
}

object GlobalConstants {
    const val DB_NAME: String ="local.db"
    const val ALBUM_ID: String = "ALBUM_ID"
    const val SEARCH_QUERY: String = "SEARCH_QUERY"
    const val ARTIST: String = "ARTIST"
    const val ALBUM: String = "ALBUM"
    const val ARTIST_PER_PAGE: Long = 30
    const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"
    const val API_FORMAT = "json"
    const val API_KEY = "4ff5f5b3a3852f8fcc4e400ca11a2d64"
    const val SUCCESS_CODE = 0
    const val CONNECTION_TIMEOUT: Long = 300

    const val MBID = "mbid"
    const val NAME = "name"

    const val SPAN_COUNT = 2

}