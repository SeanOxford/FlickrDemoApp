package com.sean.oxford.adobeproject.repository

import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent.RetrieveImageFromDbStateEvent
import com.sean.oxford.adobeproject.screens.detail.DetailViewState
import com.sean.oxford.adobeproject.screens.list.ListStateEvent
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.*
import com.sean.oxford.adobeproject.screens.list.ListViewState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
interface AppRepository{

    fun fetchFlickrImages(stateEvent: FetchImagesStateEvent, page: Int) : Flow<DataState<ListViewState>>

    fun fetchImageById(stateEvent: RetrieveImageFromDbStateEvent) : Flow<DataState<DetailViewState>>


}