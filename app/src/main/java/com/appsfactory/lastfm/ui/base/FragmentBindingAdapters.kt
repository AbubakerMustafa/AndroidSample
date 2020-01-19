package com.appsfactory.lastfm.ui.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject


class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, defaultImage: Drawable? = null) {

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        if (defaultImage != null)
            requestOptions.placeholder(defaultImage)
        Glide.with(fragment).setDefaultRequestOptions(requestOptions).load(url)
            .into(imageView)
    }

    @BindingAdapter("imageResource")
    fun bindImageResource(imageView: ImageView, resourceId: Int?) {
        imageView.setImageResource(resourceId!!)
    }


}