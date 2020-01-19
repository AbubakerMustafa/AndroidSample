package com.appsfactory.lastfm.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.AppExecutors
import com.appsfactory.lastfm.ui.base.DataBoundListAdapter
import com.appsfactory.lastfm.databinding.ItemTrackBinding
import com.appsfactory.lastfm.data.server.models.Track
import com.appsfactory.lastfm.ui.detail.IAlbumDetailFragment

class TracksAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private var iAlbumDetailFragment: IAlbumDetailFragment
) : DataBoundListAdapter<Track, ItemTrackBinding>(
    appExecutors = appExecutors,
    diffCallback = diffCallback
) {

    override fun createBinding(parent: ViewGroup): ItemTrackBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_track,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: ItemTrackBinding, item: Track, position: Int) {
        binding.track = item
        binding.root.setOnClickListener {
            iAlbumDetailFragment.onTrackClicked(position, item)
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name && oldItem.artist?.name == newItem.artist?.name
    }
}