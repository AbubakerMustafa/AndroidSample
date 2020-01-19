package com.appsfactory.lastfm.data.repository.sources

import com.appsfactory.lastfm.data.server.models.AlbumDetailResponse
import com.appsfactory.lastfm.data.server.models.ArtistSearchResponse
import com.appsfactory.lastfm.data.server.models.TopAlbumsResponse
import io.reactivex.Observable

interface BaseDataSource {
    fun searchArtist(query: String, page: Int): Observable<ArtistSearchResponse>
    fun getTopAlbums(query: String, page: Int): Observable<TopAlbumsResponse>
    fun getAlbumDetail(album: String, artist: String?) : Observable<AlbumDetailResponse>
}