package com.sean.oxford.adobeproject.screens.list

import com.sean.oxford.adobeproject.model.FlickrImage
import com.sean.oxford.adobeproject.screens.base.ViewState

data class ListViewState(val images: List<FlickrImage>, val keyword: String) : ViewState()