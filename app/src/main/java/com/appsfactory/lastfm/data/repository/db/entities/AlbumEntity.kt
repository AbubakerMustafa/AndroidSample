package com.appsfactory.lastfm.data.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.server.models.AlbumDetail
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.data.server.models.Image

@Entity(tableName = "album")
data class AlbumEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "artist")
    var artist: String = "",
    @ColumnInfo(name = "mbid")
    var mbid: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "playcount")
    var playcount: Long = 0,
    @ColumnInfo(name = "image")
    var image: String = ""
) {
    fun toAlbum(): Album {
        val images = ArrayList<Image>()
        images.add(Image(text = image, size = ""))
        return Album(
            _id = id,
            name = name,
            playcount = playcount,
            url = "",
            artist = Artist(name = artist),
            image = images,
            mbid = mbid
        )
    }

    fun toAlbumDetail(): AlbumDetail {
        val images = ArrayList<Image>()
        images.add(Image(text = image, size = ""))
        return AlbumDetail(
            _id = id,
            name = name,
            playcount = playcount,
            url = "",
            artist = artist,
            image = images
        )
    }
}