package com.appsfactory.lastfm.data.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.data.server.models.Track

@Entity(
    tableName = "track", foreignKeys = arrayOf(
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("album_id")
        )
    )
)
data class TrackEntity constructor(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "artist")
    var artist: String? = "",
    @ColumnInfo(name = "name")
    var name: String? = "",
    @ColumnInfo(name = "duration")
    var duration: Long? = 0,
    @ColumnInfo(name = "url")
    var url: String? = "",
    @ColumnInfo(name = "album_id")
    val albumId: Long
) {

    fun toTrack(): Track {
        return Track(
            name = name,
            artist = Artist(name = artist),
            url = url,
            duration = duration
        )
    }
}