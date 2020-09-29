package com.sean.oxford.adobeproject.screens.list

import android.content.Context
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.model.FlickrImage
import com.sean.oxford.adobeproject.screens.base.BaseFragmentView
import com.sean.oxford.adobeproject.screens.base.ReturnAction
import com.sean.oxford.adobeproject.screens.detail.DetailFragment.Companion.BUNDLE_KEY_FLICKR_IMAGE_ID
import com.sean.oxford.adobeproject.screens.list.ListReturnAction.*
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.*
import com.sean.oxford.adobeproject.screens.list.widgets.ImageAdapter
import com.sean.oxford.adobeproject.screens.list.widgets.ImageAdapterCallback
import com.sean.oxford.adobeproject.screens.list.widgets.LayoutManagerWrapper
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ListFragmentView(viewModel: ListViewModel, context: Context) :
    BaseFragmentView<ListViewState>(viewModel, context),
    ImageAdapterCallback {

    private val adapter = ImageAdapter(this)

    override fun onViewStateChanged(viewState: ListViewState) {
        Log.d("nnn", String.format("updating! count: ${viewState.images.size}"))
        adapter.submitList(viewState.images)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_list

    override fun initViews(menu: Menu?) {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView_list)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    override fun initTitle(): String = "List"

    override fun resolveAction(action: ReturnAction) {
        when (action) {
            is GoToDetailScreenReturnAction -> navigateToImageDetailScreen(action.flickrImage)
        }
    }


    override fun initToolbarView(toolbarView: View?) {
        val searchButton =
            toolbarView?.findViewById<ImageView>(R.id.ImageView_weather_toolbar_search)
        val cityEditText =
            toolbarView?.findViewById<EditText>(R.id.EditText_weather_toolbar)

        searchButton?.setOnClickListener {
            setStateEvent(FetchImagesStateEvent(cityEditText?.text.toString()))
        }

        cityEditText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                setStateEvent(FetchImagesStateEvent(cityEditText.text.toString()))
            }
            true
        }
    }

    private fun navigateToImageDetailScreen(flickrImage: FlickrImage) {
        val bundle = bundleOf(BUNDLE_KEY_FLICKR_IMAGE_ID to flickrImage.id)
        navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }

    override fun onImageClicked(flickrImage: FlickrImage) {
        setStateEvent(GoToDetailFragmentStateEvent(flickrImage))
    }

    override fun onScrollThresholdReached() {
        Log.d("nnn", String.format("TRIGGERED"))
        setStateEvent(FetchImagesStateEvent(getCurrentViewState().keyword))
    }

}