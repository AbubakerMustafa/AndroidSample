package com.appsfactory.lastfm.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.BaseActivity
import com.appsfactory.lastfm.databinding.ActivityFrameBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityFrameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_frame, null, false)
        setContentView(binding.root)
        setToolbar(R.layout.search_toolbar, false)
        setToolbarTitle(getString(R.string.home))
        HomeFragment().apply {
            arguments = intent.extras
            replaceFragment(this, false, R.id.content_frame, this::class.java.simpleName)
        }
    }
}
