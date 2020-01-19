/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appsfactory.lastfm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appsfactory.lastfm.ui.detail.AlbumDetailViewModel
import com.appsfactory.lastfm.ui.home.HomeViewModel
import com.appsfactory.lastfm.ui.albums.TopAlbumsViewModel
import com.appsfactory.lastfm.ui.artist.ArtistSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopAlbumsViewModel::class)
    abstract fun topAlbumsViewModel(topAlbumsViewModel: TopAlbumsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArtistSearchViewModel::class)
    abstract fun artistSearchViewModel(artistSearchViewModel: ArtistSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailViewModel::class)
    abstract fun albumDetailViewModel(albumDetailViewModel: AlbumDetailViewModel): ViewModel
}