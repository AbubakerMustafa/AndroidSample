package com.appsfactory.lastfm.ui.artist

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PageKeyedDataSource
import com.appsfactory.lastfm.ui.base.LoadingType
import com.appsfactory.lastfm.ui.base.Resource
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import com.appsfactory.lastfm.util.GlobalConstants

class ArtistSearchDataSource(
    var repository: AlbumsRepo,
    val query: String
) : PageKeyedDataSource<Int, Artist>() {

    val networkStateLiveData: MediatorLiveData<Resource<Any>?> = MediatorLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Artist>
    ) {

        if (query.isNotEmpty()) {
            try {
                networkStateLiveData.postValue(Resource.showLoader(LoadingType.FULL_LOADING))
                val response = repository.searchArtists(query, 1).blockingFirst()
                response.results?.artistMatches?.artist?.let {
                    callback.onResult(
                        it,
                        response.results?.opensearchStartIndex!!.toInt(),
                        response.results?.opensearchTotalResults!!.toInt(),
                        null,
                        if (response.results?.opensearchStartIndex?.plus(GlobalConstants.ARTIST_PER_PAGE)!! >= response.results?.opensearchTotalResults!!)
                            null else 2
                    )
                }
                networkStateLiveData.postValue(Resource.hideLoader(LoadingType.FULL_LOADING))
            } catch (e: Exception) {
                networkStateLiveData.postValue(Resource.handleError(e))
                Log.e("error", e.message, e)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        try {
            val response = repository.searchArtists(query, params.key)
                .blockingFirst()
            response.results?.artistMatches?.artist?.let {
                callback.onResult(
                    it,
                    if (response.results?.opensearchStartIndex?.plus(GlobalConstants.ARTIST_PER_PAGE)!! >= response.results?.opensearchTotalResults!!) null else params.key + 1
                )
            }
        } catch (e: Exception) {
            Log.e("error", e.message, e)
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {

    }

}
