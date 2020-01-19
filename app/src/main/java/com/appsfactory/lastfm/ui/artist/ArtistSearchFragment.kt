package com.appsfactory.lastfm.ui.artist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.*
import com.appsfactory.lastfm.databinding.FragmentSearchBinding
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.ui.artist.adapter.ArtistSearchAdapter
import com.appsfactory.lastfm.util.GlobalConstants
import com.appsfactory.lastfm.util.hideKeyboard
import javax.inject.Inject

class ArtistSearchFragment : BaseFragment(), IArtistSearchFragment, SearchView.OnQueryTextListener {

    lateinit var binding: FragmentSearchBinding

    lateinit var artistAdapter: ArtistSearchAdapter
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent = MyDataBindingComponent(this)
    lateinit var searchViewModel: ArtistSearchViewModel

    var query: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, null, false)
        binding.iSearchFragment = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun initUI() {
        artistAdapter = ArtistSearchAdapter(
            dataBindingComponent,
            appExecutors,
            this
        )
        binding.rvArtists.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvArtists.adapter = artistAdapter

    }

    private fun searchArtist(query: String) {
        hideKeyboard()
        searchViewModel.query = query
        // As the query is changed result will be changed, setting empty list to avoid the blink due to changed data set.
        artistAdapter.submitList(null)
    }

    override fun initViewModel(): BaseViewModel? {
        searchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ArtistSearchViewModel::class.java)

        searchViewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "some error occured", Toast.LENGTH_SHORT).show()
        })
        searchViewModel.getArtistLiveData().observe(viewLifecycleOwner, Observer {
            showEmptyList(it.isEmpty())
            artistAdapter.submitList(it)
        })
        return searchViewModel
    }

    private fun showEmptyList(empty: Boolean) = if (empty) {
        binding.rvArtists.visibility = View.GONE
        binding.txtEmptyAlbums.visibility = View.VISIBLE
    } else {
        binding.rvArtists.visibility = View.VISIBLE
        binding.txtEmptyAlbums.visibility = View.GONE
    }

    override fun onArtistClicked(position: Int, artist: Artist) {
        artist.mbid?.let {
            val bundle = Bundle()
            bundle.putString(GlobalConstants.MBID, artist.mbid)
            bundle.putParcelable(GlobalConstants.ARTIST, artist)
            navigator.gotoTopAlbumActivity(activity as BaseActivity, bundle)
        }
    }

    override fun onSearchClicked() {
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        // Optional: if you want to expand SearchView from icon to edittext view
        searchItem?.expandActionView()
        val searchView = searchItem?.actionView as SearchView
        arguments?.let {
            if (it.containsKey(GlobalConstants.SEARCH_QUERY))
                query = it.getString(GlobalConstants.SEARCH_QUERY, "")
        }
        searchView.queryHint = getString(R.string.search_artist)
        searchView.setOnQueryTextListener(this)
        if (query.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(query, true)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            this.query = it
            searchArtist(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(GlobalConstants.SEARCH_QUERY, query)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            query = it.getString(GlobalConstants.SEARCH_QUERY, "")
        }
    }
}