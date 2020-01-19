package com.appsfactory.lastfm.ui.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.BaseActivity
import com.appsfactory.lastfm.databinding.ActivityFrameBinding

class AlbumDetailActivity : BaseActivity() {

    lateinit var binding: ActivityFrameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_frame, null, false)
        setContentView(binding.root)
        showToolbar(true)
        setToolbarTitle(getString(R.string.album_detail))
        AlbumDetailFragment().apply {
            arguments = intent.extras
            replaceFragment(this, false, R.id.content_frame, this::class.java.simpleName)
        }
    }
}
