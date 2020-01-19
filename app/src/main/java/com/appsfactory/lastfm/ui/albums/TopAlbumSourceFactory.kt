package com.appsfactory.lastfm.ui.albums

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo


class TopAlbumSourceFactory(private val repository: AlbumsRepo, private val mbid: String) :
    DataSource.Factory<Int, Album>() {

    private val mutableLiveData: MutableLiveData<TopAlbumDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Album> {
        val dataSourceClass = TopAlbumDataSource(repository, mbid)
        mutableLiveData.postValue(dataSourceClass)
        return dataSourceClass
    }

    fun getSourceLiveData(): MutableLiveData<TopAlbumDataSource> {
        return mutableLiveData
    }
}