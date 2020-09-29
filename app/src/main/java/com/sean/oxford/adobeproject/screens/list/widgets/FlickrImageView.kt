package com.sean.oxford.adobeproject.screens.list.widgets

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.sean.oxford.adobeproject.AdobeApplication
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.model.FlickrImage
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class FlickrImageView(context: Context) : FrameLayout(context) {

    @Inject
    lateinit var glide: RequestManager

    private val imageView : ImageView

    init {
        AdobeApplication.sApplication.appComponent.inject(this)
        inflate(context, R.layout.rowview_flickr_image, this)
        imageView = findViewById(R.id.ImageView_flickr_image)
    }

    fun setImage(flickrImage: FlickrImage){
        glide.load(flickrImage.imgUrl).into(imageView)
    }


}