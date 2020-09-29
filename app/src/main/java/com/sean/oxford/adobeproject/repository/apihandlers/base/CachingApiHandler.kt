package com.sean.oxford.adobeproject.repository.apihandlers.base

import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
abstract class CachingApiHandler<NetworkObj, CacheObj, ViewState> constructor(
    private val stateEvent: StateEvent,
    apiCall: suspend () -> NetworkObj
) : BaseApiHandler<ViewState, NetworkObj>(stateEvent, apiCall) {

    override suspend fun handleApiCallSuccess(data: NetworkObj): DataState<ViewState> {
        saveToCache(data)
        return createSuccessViewState(data, stateEvent)
    }

    abstract suspend fun saveToCache(data: NetworkObj)

}