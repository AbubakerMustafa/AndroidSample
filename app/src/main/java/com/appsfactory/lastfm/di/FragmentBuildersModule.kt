package com.appsfactory.lastfm.di

import com.appsfactory.lastfm.ui.detail.AlbumDetailFragment
import com.appsfactory.lastfm.ui.albums.TopAlbumFragment
import com.appsfactory.lastfm.ui.artist.ArtistSearchFragment
import com.appsfactory.lastfm.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeArtistSearchFragment(): ArtistSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeTopAlbumFragment(): TopAlbumFragment

    @ContributesAndroidInjector
    abstract fun contributeAlbumDetailFragment(): AlbumDetailFragment

}