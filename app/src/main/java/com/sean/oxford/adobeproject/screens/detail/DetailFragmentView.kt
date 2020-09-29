package com.sean.oxford.adobeproject.screens.detail

import android.content.Context
import android.view.Menu
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.sean.oxford.adobeproject.AdobeApplication
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.screens.base.BaseFragmentView
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent.*
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class DetailFragmentView(
    private val flickrImageId: String,
    viewModel: DetailViewModel,
    context: Context
) : BaseFragmentView<DetailViewState>(viewModel, context) {

    @Inject
    lateinit var glide: RequestManager

    private lateinit var mainImageView: ImageView

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun initViews(menu: Menu?) {
        AdobeApplication.sApplication.appComponent.inject(this)
        mainImageView = findViewById(R.id.ImageView_detail_flickr_image)
    }

    override fun initTitle(): String = "Detail"

    override fun onViewStateChanged(viewState: DetailViewState) {
        val image = viewState.flickrImage
        image?.let { glide.load(viewState.flickrImage.imgUrl).into(mainImageView) }
    }

    override fun initialAction() {
        setStateEvent(RetrieveImageFromDbStateEvent(flickrImageId))
    }
}