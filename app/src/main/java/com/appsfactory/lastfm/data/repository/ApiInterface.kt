package com.appsfactory.lastfm.data.repository

import com.appsfactory.lastfm.data.server.models.AlbumDetailResponse
import com.appsfactory.lastfm.data.server.models.ArtistSearchResponse
import com.appsfactory.lastfm.data.server.models.TopAlbumsResponse
import com.appsfactory.lastfm.util.ServiceNames
import io.reactivex.Observable
import retrofit2.http.*

/**
 * The interface which provides methods to get result of webservices
 */


interface ApiInterface {

    @GET(ServiceNames.SEARCH_ARTIST)
    fun searchArtist(
        @Query("artist") query: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("page") page: Int
    ): Observable<ArtistSearchResponse>

    @GET(ServiceNames.GET_TOP_ALBUMS)
    fun getTopAlbums(
        @Query("mbid") query: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("page") page: Int
    ): Observable<TopAlbumsResponse>

    @GET(ServiceNames.GET_ALBUMS_DETAIL)
    fun getAlbumDetail(
        @Query("album") album: String,
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Observable<AlbumDetailResponse>

}