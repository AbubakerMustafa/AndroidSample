package com.appsfactory.lastfm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.*
import com.appsfactory.lastfm.databinding.FragmentHomeBinding
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.ui.home.adapter.HomeAlbumAdapter
import com.appsfactory.lastfm.util.GlobalConstants
import com.appsfactory.lastfm.util.GlobalConstants.SPAN_COUNT
import javax.inject.Inject

class HomeFragment : BaseFragment(), IHomeFragment, SearchView.OnQueryTextListener {
    private lateinit var topAlbumAdapter: HomeAlbumAdapter
    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent = MyDataBindingComponent(this)
    lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false)
        (activity as BaseActivity).setSearchQueryListener(this)
        binding.iHomeFragment = this
        return binding.root
    }

    override fun initUI() {
        topAlbumAdapter = HomeAlbumAdapter(
            dataBindingComponent,
            appExecutors,
            this
        )
        binding.rvAlbums.layoutManager =
            GridLayoutManager(activity, SPAN_COUNT)
        binding.rvAlbums.adapter = topAlbumAdapter
    }

    override fun initViewModel(): BaseViewModel? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getSavedAlbum().observe(viewLifecycleOwner, Observer {
            showEmptyList(it.isEmpty())
            topAlbumAdapter.submitList(it)
        })
        return homeViewModel
    }

    private fun showEmptyList(empty: Boolean) = if (empty) {
        binding.rvAlbums.visibility = View.GONE
        binding.txtEmptyAlbums.visibility = View.VISIBLE
    } else {
        binding.rvAlbums.visibility = View.VISIBLE
        binding.txtEmptyAlbums.visibility = View.GONE
    }


    override fun onAlbumClicked(position: Int, item: Album) {
        val bundle = Bundle()
        bundle.putLong(GlobalConstants.ALBUM_ID, item._id)
        navigator.gotoAlbumDetailActivity(activity as BaseActivity, bundle)
    }

    override fun onSearchClicked() {
        navigator.gotoArtistSearchActivity(activity as BaseActivity, Bundle())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        (activity as BaseActivity).searchView?.let {
            it.setQuery("", false)
            it.onActionViewCollapsed()
        }
        query?.let {
            val bundle = Bundle()
            bundle.putString(GlobalConstants.SEARCH_QUERY, query)
            navigator.gotoArtistSearchActivity(activity as BaseActivity, bundle)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}