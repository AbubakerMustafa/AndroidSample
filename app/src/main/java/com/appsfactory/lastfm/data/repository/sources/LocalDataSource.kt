package com.appsfactory.lastfm.data.repository.sources

import androidx.paging.DataSource
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.server.models.AlbumDetail
import com.appsfactory.lastfm.data.server.models.Tracks
import com.appsfactory.lastfm.data.repository.db.AppDb
import com.appsfactory.lastfm.data.repository.db.entities.AlbumEntity
import com.appsfactory.lastfm.data.repository.db.entities.TrackEntity

class LocalDataSource(private var appDatabase: AppDb) {

    fun saveAlbum(albumDetail: AlbumDetail, imageUrl: String) {
        val id = appDatabase.albumDao().insert(
            AlbumEntity(
                name = albumDetail.name,
                mbid = albumDetail.mbid,
                playcount = albumDetail.playcount,
                artist = albumDetail.artist,
                image = imageUrl
            )
        )
        albumDetail.tracks.track.let { tracks ->
            for (track in tracks) {
                track?.let {
                    appDatabase.albumDao().insertAll(
                        TrackEntity(
                            name = it.name,
                            albumId = id,
                            artist = it.artist?.name,
                            duration = it.duration,
                            url = it.url
                        )
                    )
                }
            }
        }
    }

    fun getAlbums(): DataSource.Factory<Int, Album> =
        appDatabase.albumDao().getAlbums().mapByPage { input ->
            input?.map { item ->
                val album = item.toAlbum()
                album
            }
        }

    fun getAlbumDetail(id: Long): AlbumDetail {
        val item = appDatabase.albumDao().getAlbum(id)
        val albumDetail = item.toAlbumDetail()
        val tracks = appDatabase.albumDao().getTracks(item.id).map {
            it.toTrack()
        }
        albumDetail.tracks = Tracks(track = tracks)
        return albumDetail
    }

    fun isAlbumExist(name: String): Boolean {
        return appDatabase.albumDao().isAlbumExist(name).isNotEmpty()
    }

    fun removeAlbum(albumId: Long): Boolean {
        val album = appDatabase.albumDao().getAlbum(albumId)
        return removeAlbum(album)
    }

    fun removeAlbum(mbid: String): Boolean {
        val album = appDatabase.albumDao().getAlbumByMbid(mbid)
        return removeAlbum(album)
    }

    fun removeAlbum(name: String, artist: String): Boolean {
        val album = appDatabase.albumDao().getAlbumNameAndArtist(name, artist)
        return removeAlbum(album)
    }

    private fun removeAlbum(album: AlbumEntity): Boolean {
        var removed: Int
        album.let {
            appDatabase.albumDao().deleteTracksByAlbumId(it.id)
            removed = appDatabase.albumDao().deleteAlbumById(it.id)

        }
        return removed > 0
    }
}