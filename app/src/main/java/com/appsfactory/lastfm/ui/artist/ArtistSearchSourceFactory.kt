package com.appsfactory.lastfm.ui.artist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo


class ArtistSearchSourceFactory(
    private val repository: AlbumsRepo,
    var query: String
) :
    DataSource.Factory<Int, Artist>() {
    lateinit var dataSourceClass: ArtistSearchDataSource
    private val mutableLiveData: MutableLiveData<ArtistSearchDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Artist> {
        dataSourceClass = ArtistSearchDataSource(repository, query)
        mutableLiveData.postValue(dataSourceClass)
        return dataSourceClass
    }

    fun getSourceLiveData(): MutableLiveData<ArtistSearchDataSource> {
        return mutableLiveData
    }
}