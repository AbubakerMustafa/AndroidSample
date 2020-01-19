package com.appsfactory.lastfm.ui.base

import android.content.Intent
import android.os.Bundle
import com.appsfactory.lastfm.ui.detail.AlbumDetailActivity
import com.appsfactory.lastfm.ui.albums.TopAlbumActivity
import com.appsfactory.lastfm.ui.artist.ArtistSearchActivity

class Navigator {
    private fun startActivity(activity: BaseActivity, intent: Intent) {
        activity.startActivity(intent)
    }

    fun gotoArtistSearchActivity(activity: BaseActivity, bundle: Bundle) {
        val intent = Intent(activity, ArtistSearchActivity::class.java)
        intent.putExtras(bundle)
        startActivity(activity, intent)
    }

    fun gotoTopAlbumActivity(activity: BaseActivity, bundle: Bundle) {
        val intent = Intent(activity, TopAlbumActivity::class.java)
        intent.putExtras(bundle)
        startActivity(activity, intent)
    }

    fun gotoAlbumDetailActivity(activity: BaseActivity, bundle: Bundle) {
        val intent = Intent(activity, AlbumDetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(activity, intent)
    }
}