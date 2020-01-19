package com.appsfactory.lastfm.data.repository.repos

import androidx.paging.DataSource
import com.appsfactory.lastfm.data.server.models.*
import com.appsfactory.lastfm.data.repository.sources.BaseDataSource
import com.appsfactory.lastfm.data.repository.sources.LocalDataSource
import io.reactivex.Observable
import javax.inject.Inject

class AlbumsRepo @Inject constructor(
    var networkDataSource: BaseDataSource,
    var localDataSource: LocalDataSource
) {
    fun searchArtists(query: String, page: Int): Observable<ArtistSearchResponse> {
        return networkDataSource.searchArtist(query, page)
    }

    fun getTopAlbums(query: String, page: Int): Observable<TopAlbumsResponse> {
        return networkDataSource.getTopAlbums(query, page)
    }

    fun getAlbumDetail(album: String, artist: String?): Observable<AlbumDetailResponse> {
        return networkDataSource.getAlbumDetail(album, artist)
    }

    fun saveAlbum(albumDetail: AlbumDetail, imageUrl: String): Observable<Boolean> {
        return Observable.create {
            localDataSource.saveAlbum(albumDetail, imageUrl)
            it.onNext(true)
            it.onComplete()
        }
    }

    fun getAlbums(): DataSource.Factory<Int, Album> {
        return localDataSource.getAlbums()
    }

    fun isAlbumExist(name: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            it.onNext(localDataSource.isAlbumExist(name))
            it.onComplete()
        }
    }

    fun getAlbumDetail(mbid: Long): Observable<AlbumDetail> {
        return Observable.create { emitter ->
            emitter.onNext(localDataSource.getAlbumDetail(mbid))
            emitter.onComplete()
        }
    }

    fun removeAlbum(albumId: Long): Observable<Boolean> {
        return Observable.create<Boolean> {
            it.onNext(localDataSource.removeAlbum(albumId))
            it.onComplete()
        }
    }

    fun removeAlbum(mbid: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            it.onNext(localDataSource.removeAlbum(mbid))
            it.onComplete()
        }
    }

    fun removeAlbum(name: String, artist: String): Observable<Boolean> {
        return Observable.create<Boolean> {
            it.onNext(localDataSource.removeAlbum(name, artist))
            it.onComplete()
        }
    }

}