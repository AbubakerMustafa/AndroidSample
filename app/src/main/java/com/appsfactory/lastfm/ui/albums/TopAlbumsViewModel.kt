package com.appsfactory.lastfm.ui.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appsfactory.lastfm.ui.base.BaseViewModel
import com.appsfactory.lastfm.ui.base.Resource
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import com.appsfactory.lastfm.util.lazyMap
import javax.inject.Inject

class TopAlbumsViewModel @Inject constructor(private var albumsRepo: AlbumsRepo) : BaseViewModel() {
    private var errorLiveData = MutableLiveData<Boolean>()
    private lateinit var networkLiveData: LiveData<Resource<Any>?>
    private val pageListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(30)
        .setPageSize(30)
        .build()


    private var albumsLiveData: Map<String, LiveData<PagedList<Album>>> = lazyMap { mbid ->
        if (this::networkLiveData.isInitialized)
            stateLiveData.removeSource(networkLiveData)
        val dataSourceFactory = TopAlbumSourceFactory(albumsRepo, mbid)
        val liveData = LivePagedListBuilder(dataSourceFactory, pageListConfig).build()
        networkLiveData = Transformations.switchMap(dataSourceFactory.getSourceLiveData()) {
            it.networkStateLiveData
        }
        stateLiveData.addSource(networkLiveData) { resource ->
            resource?.let {
                stateLiveData.value = it
            }
        }
        liveData
    }

    fun getErrorLiveData() = errorLiveData
    fun getTopAlbums(mbid: String): LiveData<PagedList<Album>> = albumsLiveData.getValue(mbid)


}