package com.appsfactory.lastfm.ui.artist

import com.appsfactory.lastfm.data.server.models.Artist

interface IArtistSearchFragment {
    fun onArtistClicked(position: Int, artist: Artist)
    fun onSearchClicked()
}