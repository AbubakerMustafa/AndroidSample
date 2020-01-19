package com.appsfactory.lastfm.ui.albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.AppExecutors
import com.appsfactory.lastfm.ui.base.PagedDataListAdapter
import com.appsfactory.lastfm.databinding.ItemAlbumBinding
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.ui.albums.ITopAlbumFragment

class TopAlbumAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    var iTopAlbumFragment: ITopAlbumFragment
) : PagedDataListAdapter<Album, ItemAlbumBinding>(
    appExecutors = appExecutors,
    diffCallback = diffCallback
) {

    override fun createBinding(parent: ViewGroup): ItemAlbumBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_album,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: ItemAlbumBinding, item: Album, position: Int) {
        binding.album = item
        binding.root.setOnClickListener {
            iTopAlbumFragment.onAlbumClicked(position, item)
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.name == newItem.name && oldItem.artist.name == newItem.artist.name
    }
}