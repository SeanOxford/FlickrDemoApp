package com.sean.oxford.adobeproject.screens.list

import com.sean.oxford.adobeproject.model.FlickrImage
import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
sealed class ListStateEvent : StateEvent() {

    class FetchImagesStateEvent(val tag: String) : ListStateEvent()
    class GoToDetailFragmentStateEvent(val flickrImage: FlickrImage) : ListStateEvent()

}