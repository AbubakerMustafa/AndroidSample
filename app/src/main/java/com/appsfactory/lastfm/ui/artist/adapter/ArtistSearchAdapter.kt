package com.appsfactory.lastfm.ui.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.AppExecutors
import com.appsfactory.lastfm.ui.base.PagedDataListAdapter
import com.appsfactory.lastfm.data.server.models.Artist
import com.appsfactory.lastfm.databinding.ItemArtistBinding
import com.appsfactory.lastfm.ui.artist.IArtistSearchFragment

class ArtistSearchAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private var iArtistSearchFragment: IArtistSearchFragment
) : PagedDataListAdapter<Artist, ItemArtistBinding>(
    appExecutors = appExecutors,
    diffCallback = diffCallback
) {

    override fun createBinding(parent: ViewGroup): ItemArtistBinding {

        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_artist,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: ItemArtistBinding, item: Artist, position: Int) {
        binding.artist = item
        binding.root.setOnClickListener {
            iArtistSearchFragment.onArtistClicked(position, item)
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.name == newItem.name
    }
}