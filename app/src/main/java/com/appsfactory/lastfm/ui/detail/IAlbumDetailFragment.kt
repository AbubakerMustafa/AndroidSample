package com.appsfactory.lastfm.ui.detail

import com.appsfactory.lastfm.data.server.models.Track

interface IAlbumDetailFragment {
    fun onTrackClicked(position: Int, track: Track)
    fun onSaveClicked()
}