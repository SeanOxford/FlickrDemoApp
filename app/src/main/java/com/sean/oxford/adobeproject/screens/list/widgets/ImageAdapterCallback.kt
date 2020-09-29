package com.sean.oxford.adobeproject.screens.list.widgets

import com.sean.oxford.adobeproject.model.FlickrImage

interface ImageAdapterCallback {
    fun onImageClicked(flickrImage: FlickrImage)

    fun onScrollThresholdReached()
}