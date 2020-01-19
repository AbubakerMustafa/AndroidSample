package com.appsfactory.lastfm.ui.detail

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.ui.base.AppExecutors
import com.appsfactory.lastfm.ui.base.BaseFragment
import com.appsfactory.lastfm.ui.base.BaseViewModel
import com.appsfactory.lastfm.ui.base.MyDataBindingComponent
import com.appsfactory.lastfm.databinding.FragmentAlbumDetailBinding
import com.appsfactory.lastfm.data.server.models.Album
import com.appsfactory.lastfm.data.server.models.AlbumDetail
import com.appsfactory.lastfm.data.server.models.Track
import com.appsfactory.lastfm.ui.detail.adapter.TracksAdapter
import com.appsfactory.lastfm.util.GlobalConstants
import kotlinx.android.synthetic.main.fragment_album_detail.*
import javax.inject.Inject

class AlbumDetailFragment : BaseFragment(), IAlbumDetailFragment {

    private val STORAGE_PERMISSION: Int = 1000
    private var albumId: Long? = null
    private var albumDetail: AlbumDetail? = null
    lateinit var binding: FragmentAlbumDetailBinding

    lateinit var tracksAdapter: TracksAdapter
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent = MyDataBindingComponent(this)
    lateinit var albumDetailViewModel: AlbumDetailViewModel

    val album: Album? by lazy {
        return@lazy arguments?.let {
            it.containsKey(GlobalConstants.ALBUM).let { exist ->
                return@let if (exist)
                    it.getParcelable(GlobalConstants.ALBUM) as Album
                else null
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album_detail,
            null,
            false,
            dataBindingComponent
        )
        binding.iAlbumDetailFragment = this
        binding.saved = false
        return binding.root
    }

    override fun initUI() {
        tracksAdapter = TracksAdapter(
            dataBindingComponent,
            appExecutors,
            this
        )
        binding.rvTracks.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvTracks.adapter = tracksAdapter

    }

    override fun initViewModel(): BaseViewModel? {
        albumDetailViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AlbumDetailViewModel::class.java)

        albumDetailViewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "some error occured", Toast.LENGTH_SHORT).show()
        })
        arguments?.let {
            if (it.containsKey(GlobalConstants.ALBUM_ID))
                albumId = it.getLong(GlobalConstants.ALBUM_ID)
        }
        albumId?.let {
            albumDetailViewModel.getSavedAlbumLiveData()
                .observe(viewLifecycleOwner, Observer { albumDetail ->
                    this.albumDetail = albumDetail
                    setData(albumDetail)
                })
            albumDetailViewModel.getAlbumDetail(it)
        } ?: run {
            album?.let {
                albumDetailViewModel.getAlbumDetail(it)
                    .observe(viewLifecycleOwner, Observer { albumDetail ->
                        this.albumDetail = albumDetail
                        setData(albumDetail)
                    })
            }
        }
        albumDetailViewModel.getAlbumExistLiveData().observe(viewLifecycleOwner, Observer {
            binding.saved = it
        })
        albumDetailViewModel.getInsertAlbumLiveData().observe(viewLifecycleOwner, Observer {
            binding.saved = it
            if (it)
                Toast.makeText(activity, "Album saved", Toast.LENGTH_SHORT).show()
        })
        albumDetailViewModel.getRemoveAlbumLiveData().observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.saved = false
                Toast.makeText(activity, "Album removed", Toast.LENGTH_SHORT).show()
            }
        })
        return albumDetailViewModel
    }

    private fun setData(albumDetail: AlbumDetail) {
        binding.albumDetail = albumDetail
        showEmptyData(albumDetail.tracks.track.isEmpty())
        tracksAdapter.submitList(albumDetail.tracks.track)
        albumDetailViewModel.isAlbumExist(albumDetail.name)
    }

    private fun showEmptyData(empty: Boolean) = if (empty) {
        binding.rvTracks.visibility = View.GONE
        binding.txtEmptyAlbums.visibility = View.VISIBLE
    } else {
        binding.rvTracks.visibility = View.VISIBLE
        binding.txtEmptyAlbums.visibility = View.GONE
    }

    override fun onTrackClicked(position: Int, track: Track) {

    }

    override fun onSaveClicked() {
        binding.saved?.let {
            if (it) {
                albumId?.let { albumId ->
                    albumDetailViewModel.removeAlbum(albumId)

                } ?: albumDetail?.let { albumDetail ->
                    albumDetailViewModel.removeAlbum(albumDetail.name, albumDetail.artist)
                    iv_favorite.setImageResource(R.drawable.ic_add_to_my_list)
                }
            } else {
                saveAlbum()
            }
        }
    }

    private fun saveAlbum() {
        activity?.let { activity ->
            if (ActivityCompat.checkSelfPermission(
                    activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                albumDetail?.let { albumDetail ->
                    albumDetailViewModel.saveAlbum(albumDetail)
                    iv_favorite.setImageResource(R.drawable.ic_added_to_my_list)
                }
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveAlbum()
                }
            }
        }
    }


}