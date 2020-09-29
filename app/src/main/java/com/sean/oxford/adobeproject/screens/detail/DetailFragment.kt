package com.sean.oxford.adobeproject.screens.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.screens.base.BaseFragment
import com.sean.oxford.adobeproject.screens.base.BaseFragmentView
import com.sean.oxford.adobeproject.screens.base.ViewState
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class DetailFragment(viewModelProvider: ViewModelProvider.Factory) : BaseFragment() {

    companion object {
        const val BUNDLE_KEY_FLICKR_IMAGE_ID = "flickr_image"
    }

    private val viewModel: DetailViewModel by viewModels { viewModelProvider }

    override fun getToolbarMenu(): Int = R.menu.menu_detail

    override fun initBaseView(): BaseFragmentView<ViewState> {
        val flickrImage = arguments?.getString(BUNDLE_KEY_FLICKR_IMAGE_ID)
        return DetailFragmentView(flickrImage!!, viewModel, requireContext()) as BaseFragmentView<ViewState>
    }
}