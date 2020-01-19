package com.appsfactory.lastfm.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appsfactory.lastfm.ui.base.BaseViewModel
import com.appsfactory.lastfm.ui.base.Resource
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import javax.inject.Inject

open class ArtistSearchViewModel @Inject constructor(private var albumsRepo: AlbumsRepo) :
    BaseViewModel() {
    private lateinit var networkLiveData: MediatorLiveData<Resource<Any>?>
    private lateinit var artistDataSourceLiveData: LiveData<PagedList<Artist>>
    private var artistsLiveData = MediatorLiveData<PagedList<Artist>>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(30)
        .setPageSize(30)
        .build()
    var query: String = ""
        set(value) {
            if (field != value) {
                field = value
                searchArtist()
            }
        }

    private fun searchArtist() {
        if (this::networkLiveData.isInitialized)
            stateLiveData.removeSource(networkLiveData)
        if (this::artistDataSourceLiveData.isInitialized)
            artistsLiveData.removeSource(artistDataSourceLiveData)
        val dataSourceFactory = ArtistSearchSourceFactory(albumsRepo, query)
        artistDataSourceLiveData = LivePagedListBuilder(dataSourceFactory, pageListConfig).build()
        networkLiveData =
            Transformations.switchMap(dataSourceFactory.getSourceLiveData()) { dataSource ->
                dataSource.networkStateLiveData
            } as MediatorLiveData<Resource<Any>?>
        artistsLiveData.addSource(artistDataSourceLiveData) {
            artistsLiveData.value = it
        }
        stateLiveData.addSource(networkLiveData) {
            stateLiveData.value = it
        }
    }


    fun getArtistLiveData() = artistsLiveData
    fun getErrorLiveData() = errorLiveData
}