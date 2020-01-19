package com.appsfactory.lastfm.data.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appsfactory.lastfm.data.repository.db.dao.AlbumDao
import com.appsfactory.lastfm.data.repository.db.entities.AlbumEntity
import com.appsfactory.lastfm.data.repository.db.entities.TrackEntity

@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}