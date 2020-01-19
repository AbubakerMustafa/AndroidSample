package com.appsfactory.lastfm.ui.home

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.appsfactory.lastfm.ui.base.BaseViewModel
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import javax.inject.Inject


class HomeViewModel @Inject constructor(albumsRepo: AlbumsRepo) : BaseViewModel() {
    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(30)
        .setPageSize(30)
        .build()
    private val myAlbumsDataSource: DataSource.Factory<Int, Album> =
        albumsRepo.getAlbums()

    fun getSavedAlbum(): LiveData<PagedList<Album>> =
        myAlbumsDataSource.toLiveData(config = pageListConfig)

}