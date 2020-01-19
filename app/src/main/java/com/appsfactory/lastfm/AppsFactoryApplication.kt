package com.appsfactory.lastfm

import android.app.Application
import android.content.Context
import com.appsfactory.lastfm.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppsFactoryApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    init {
        instance = this
    }

    companion object {
        private var instance: AppsFactoryApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}