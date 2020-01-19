package com.appsfactory.lastfm.data.repository.sources

import com.appsfactory.lastfm.data.server.models.AlbumDetailResponse
import com.appsfactory.lastfm.data.server.models.ArtistSearchResponse
import com.appsfactory.lastfm.data.server.models.TopAlbumsResponse
import com.appsfactory.lastfm.data.repository.ApiInterface
import com.appsfactory.lastfm.util.GlobalConstants
import io.reactivex.Observable
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private var apiInterface: ApiInterface) :
    BaseDataSource {

    override fun searchArtist(query: String, page: Int): Observable<ArtistSearchResponse> {
        return apiInterface.searchArtist(
            query,
            GlobalConstants.API_KEY,
            GlobalConstants.API_FORMAT,
            page
        )
    }

    override fun getTopAlbums(query: String, page: Int): Observable<TopAlbumsResponse> {
        return apiInterface.getTopAlbums(
            query,
            GlobalConstants.API_KEY,
            GlobalConstants.API_FORMAT,
            page
        )
    }

    override fun getAlbumDetail(album: String, artist: String?): Observable<AlbumDetailResponse> {
        return apiInterface.getAlbumDetail(
            album,
            artist!!,
            GlobalConstants.API_KEY,
            GlobalConstants.API_FORMAT
        )
    }
}