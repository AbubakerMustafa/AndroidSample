package com.appsfactory.lastfm.ui.home

import com.appsfactory.lastfm.data.server.models.Album

interface IHomeFragment {
    fun onAlbumClicked(position: Int, item: Album)
    fun onSearchClicked()
}