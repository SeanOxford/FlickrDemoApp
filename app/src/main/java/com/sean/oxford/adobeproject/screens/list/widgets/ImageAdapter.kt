package com.sean.oxford.adobeproject.screens.list.widgets

import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.sean.oxford.adobeproject.model.FlickrImage
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ImageAdapter(private val callback: ImageAdapterCallback) :
    ListAdapter<FlickrImage, ImageAdapter.FlickrImageViewHolder>(DiffCallbackThing()) {

    class DiffCallbackThing : DiffUtil.ItemCallback<FlickrImage>() {
        override fun areItemsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean =
            oldItem == newItem
    }

    class FlickrImageViewHolder(val view: FlickrImageView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder =
        FlickrImageViewHolder(FlickrImageView(parent.context))

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        val flickrImage = currentList[position]
        if((currentList.size * 0.8).toInt() == position){
            callback.onScrollThresholdReached()
        }
        holder.view.setImage(flickrImage)
        holder.view.setOnClickListener { callback.onImageClicked(flickrImage) }
    }

}
