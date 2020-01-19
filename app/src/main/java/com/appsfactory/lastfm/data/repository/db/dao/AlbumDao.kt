package com.appsfactory.lastfm.data.repository.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appsfactory.lastfm.data.repository.db.entities.AlbumEntity
import com.appsfactory.lastfm.data.repository.db.entities.TrackEntity


@Dao
interface AlbumDao {

    @Query("SELECT * FROM album ")
    fun getAlbums(): DataSource.Factory<Int, AlbumEntity>

    @Query("SELECT * FROM album where id = :id")
    fun getAlbum(id: Long): AlbumEntity

    @Insert
    fun insertAll(vararg albums: AlbumEntity)

    @Insert
    fun insert(album: AlbumEntity): Long

    @Query("SELECT * FROM track where album_id = :album_id")
    fun getTracks(album_id: Long): List<TrackEntity>

    @Insert
    fun insertAll(vararg tracks: TrackEntity)

    @Insert
    fun insert(track: TrackEntity): Long

    @Query("SELECT * FROM album where name = :name")
    fun isAlbumExist(name: String): List<AlbumEntity>

    @Query("DELETE FROM album WHERE id = :album_id")
    fun deleteAlbumById(album_id: Long): Int

    @Query("DELETE FROM track WHERE album_id= :album_id")
    fun deleteTracksByAlbumId(album_id: Long): Int

    @Query("SELECT * FROM album where mbid = :mbid")
    fun getAlbumByMbid(mbid: String): AlbumEntity

    @Query("SELECT * FROM album where name = :name and artist = :artist")
    fun getAlbumNameAndArtist(name: String, artist: String): AlbumEntity

}