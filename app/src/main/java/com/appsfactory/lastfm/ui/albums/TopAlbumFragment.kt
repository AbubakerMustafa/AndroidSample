package com.appsfactory.lastfm.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.*
import com.appsfactory.lastfm.databinding.FragmentAlbumsBinding
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.ui.albums.adapter.TopAlbumAdapter
import com.appsfactory.lastfm.util.GlobalConstants
import com.appsfactory.lastfm.util.GlobalConstants.SPAN_COUNT
import javax.inject.Inject

class TopAlbumFragment : BaseFragment(), ITopAlbumFragment {

    lateinit var binding: FragmentAlbumsBinding

    lateinit var topAlbumAdapter: TopAlbumAdapter
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent = MyDataBindingComponent(this)
    lateinit var topAlbumsViewModel: TopAlbumsViewModel
    @Inject
    lateinit var navigator: Navigator

    val mbid: String by lazy { arguments?.getString(GlobalConstants.MBID) ?: "" }
    val artist: Artist? by lazy {
        arguments?.getParcelable(GlobalConstants.ARTIST) as Artist
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_albums,
            null,
            false,
            dataBindingComponent
        )
        binding.iTopAlbumsFragment = this
        binding.artist = artist
        return binding.root
    }

    override fun initUI() {
        topAlbumAdapter = TopAlbumAdapter(
            dataBindingComponent,
            appExecutors,
            this
        )
        binding.rvArtists.layoutManager =
            GridLayoutManager(activity, SPAN_COUNT)
        binding.rvArtists.adapter = topAlbumAdapter
    }

    override fun initViewModel(): BaseViewModel? {
        topAlbumsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TopAlbumsViewModel::class.java)

        topAlbumsViewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "some error occured", Toast.LENGTH_SHORT).show()
        })
        topAlbumsViewModel.getTopAlbums(mbid).observe(viewLifecycleOwner, Observer {
            showEmptyList(it.isEmpty())
            topAlbumAdapter.submitList(it)
        })
        return topAlbumsViewModel
    }

    private fun showEmptyList(empty: Boolean) = if (empty) {
        binding.rvArtists.visibility = View.GONE
        binding.txtEmptyAlbums.visibility = View.VISIBLE
    } else {
        binding.rvArtists.visibility = View.VISIBLE
        binding.txtEmptyAlbums.visibility = View.GONE
    }

    override fun onAlbumClicked(position: Int, album: Album) {
        val bundle = Bundle()
        bundle.putParcelable(GlobalConstants.ALBUM, album)
        navigator.gotoAlbumDetailActivity(activity as BaseActivity, bundle)
    }

}