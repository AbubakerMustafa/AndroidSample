package com.appsfactory.lastfm.ui.albums

import com.appsfactory.lastfm.data.server.models.Album

interface ITopAlbumFragment {
    fun onAlbumClicked(position: Int, album: Album)
}