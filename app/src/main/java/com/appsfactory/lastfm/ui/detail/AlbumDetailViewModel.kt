package com.appsfactory.lastfm.ui.detail

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appsfactory.lastfm.ui.base.BaseViewModel
import com.appsfactory.lastfm.ui.base.LoadingType
import com.appsfactory.lastfm.ui.base.Resource
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.server.models.AlbumDetail
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import com.appsfactory.lastfm.util.GlobalConstants
import com.appsfactory.lastfm.util.lazyMap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject


class AlbumDetailViewModel @Inject constructor(
    private var albumsRepo: AlbumsRepo,
    var application: Application
) : BaseViewModel() {
    private var errorLiveData = MutableLiveData<Boolean>()
    private var insertAlbumLiveData = MutableLiveData<Boolean>()
    private var albumExistLiveData = MutableLiveData<Boolean>()
    private var savedAlbumLiveData = MutableLiveData<AlbumDetail>()
    private var removeAlbumLiveData = MutableLiveData<Boolean>()

    fun getErrorLiveData() = errorLiveData
    fun getInsertAlbumLiveData() = insertAlbumLiveData
    fun getAlbumExistLiveData() = albumExistLiveData
    fun getSavedAlbumLiveData() = savedAlbumLiveData
    fun getRemoveAlbumLiveData() = removeAlbumLiveData

    private var albumsLiveData: Map<Album, LiveData<AlbumDetail>> = lazyMap { parameters ->
        val liveData = MutableLiveData<AlbumDetail>()
        parameters.name.let {
            albumsRepo.getAlbumDetail(it, parameters.artist.name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    stateLiveData.value = Resource.showLoader(LoadingType.FULL_LOADING)
                }.doOnTerminate {
                    //hide loader
                    stateLiveData.value = Resource.hideLoader(LoadingType.FULL_LOADING)
                }.subscribe({ response ->
                    if (response.code == GlobalConstants.SUCCESS_CODE) {
                        liveData.value = response.album
                    }
                }, { t ->
                    stateLiveData.value = Resource.handleError(t)
                })
        }

        return@lazyMap liveData
    }

    fun getAlbumDetail(query: Album): LiveData<AlbumDetail> = albumsLiveData.getValue(query)

    fun getAlbumDetail(mbid: Long) {
        mCompositeDisposable.add(
            albumsRepo.getAlbumDetail(mbid).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    savedAlbumLiveData.value = it
                }, {
                    albumExistLiveData.value = false
                    stateLiveData.value = Resource.handleError(it)
                })
        )

    }

    fun isAlbumExist(name: String) {
        mCompositeDisposable.add(
            albumsRepo.isAlbumExist(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    albumExistLiveData.value = it
                }, {
                    albumExistLiveData.value = false
                    stateLiveData.value = Resource.handleError(it)
                })
        )
    }

    fun removeAlbum(albumId: Long) {
        mCompositeDisposable.add(
            albumsRepo.removeAlbum(albumId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    removeAlbumLiveData.value = it
                }, {
                    removeAlbumLiveData.value = false
                    stateLiveData.value = Resource.handleError(it)
                })
        )
    }

    fun removeAlbum(name: String, artist: String) {
        mCompositeDisposable.add(
            albumsRepo.removeAlbum(name, artist)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    removeAlbumLiveData.value = it
                }, {
                    removeAlbumLiveData.value = false
                    stateLiveData.value = Resource.handleError(it)
                })
        )
    }

    fun saveAlbum(albumDetail: AlbumDetail) {
        GlobalScope.launch {
            var imageUrl = ""
            withContext(Dispatchers.IO) {
                val downloadUrl = albumDetail.image[albumDetail.image.size - 1].text
                imageUrl = saveImage(
                    loadBitmapFromUrl(
                        downloadUrl,
                        application
                    ), downloadUrl
                )
                Log.d("URL *****", imageUrl)
            }
            mCompositeDisposable.add(
                albumsRepo.saveAlbum(albumDetail, imageUrl)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        insertAlbumLiveData.value = it
                    }, {
                        insertAlbumLiveData.value = false
                        stateLiveData.value = Resource.handleError(it)
                    })
            )
        }
    }

    //Download the image using glide
    private fun loadBitmapFromUrl(url: String, context: Context): Bitmap? {

        if (url.isEmpty())
            return null
        try {
            return Glide.with(context).asBitmap().load(url).submit().get()
        } catch (ex: GlideException) {
            Log.e("error", ex.message, ex)
        } catch (ex: Exception) {
            Log.e("error", ex.message, ex)
        }
        return null
    }

    //Save avatar locally
    private fun saveImage(image: Bitmap?, downloadUrl: String): String {
        if (image == null)
            return ""
        val file = fileDir(downloadUrl)
        try {
            val fOut = file.outputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            Log.e("error", e.message, e)
        }
        return file.path
    }

    private fun fileDir(url: String): File {
        val imageFileName = createFileName(url)
        val contextWrapper = ContextWrapper(application)

        val directory = contextWrapper.cacheDir
        return File(directory, imageFileName)
    }

    private fun createFileName(url: String) =
        "${getParameterFromUrl(url)}.jpg"

    private fun getParameterFromUrl(url: String): String? =
        URLUtil.guessFileName(url, null, null)


}