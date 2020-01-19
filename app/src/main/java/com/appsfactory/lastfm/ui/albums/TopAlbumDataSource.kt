package com.appsfactory.lastfm.ui.albums

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.appsfactory.lastfm.ui.base.LoadingType
import com.appsfactory.lastfm.ui.base.Resource
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo

class TopAlbumDataSource(
    var repository: AlbumsRepo,
    val query: String
) : PageKeyedDataSource<Int, Album>() {

    val networkStateLiveData: MutableLiveData<Resource<Any>?> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Album>
    ) {

        try {
            networkStateLiveData.postValue(Resource.showLoader(LoadingType.FULL_LOADING))
            val response = repository.getTopAlbums(query, 1).blockingFirst()
            response.topalbums?.album?.let {
                callback.onResult(
                    it,
                    null,
                    if (response.topalbums?.attr?.page!! >= response.topalbums?.attr?.totalPages!!)
                        null else 2
                )
            }
            networkStateLiveData.postValue(Resource.hideLoader(LoadingType.FULL_LOADING))
        } catch (e: Exception) {
            networkStateLiveData.postValue(Resource.handleError(e))
            Log.e("error", e.message, e)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Album>) {
        try {
            val response = repository.getTopAlbums(query, params.key)
                .blockingFirst()
            response.topalbums?.album?.let {
                callback.onResult(
                    it,
                    if (response.topalbums?.attr?.page!! >= response.topalbums?.attr?.totalPages!!)
                        null else params.key + 1
                )
            }
        } catch (e: Exception) {
            Log.e("error", e.message, e)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Album>) {

    }

}
