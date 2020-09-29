package com.sean.oxford.adobeproject.screens.detail

import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
sealed class DetailStateEvent: StateEvent() {

    class RetrieveImageFromDbStateEvent(val imageId: String): DetailStateEvent()


}