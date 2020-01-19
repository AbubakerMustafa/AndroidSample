package com.appsfactory.lastfm.ui.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    var stateLiveData = MediatorLiveData<Resource<Any>?>()

    protected var mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}
