package com.sean.oxford.adobeproject.repository.apihandlers.base

import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.getString
import com.sean.oxford.adobeproject.network.ApiResult
import com.sean.oxford.adobeproject.network.MessageModel
import com.sean.oxford.adobeproject.network.MessageType.*
import com.sean.oxford.adobeproject.network.UIComponentType.Dialog
import com.sean.oxford.adobeproject.persistence.CacheResult
import com.sean.oxford.adobeproject.persistence.CacheResult.Success
import com.sean.oxford.adobeproject.repository.buildError
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow

@InternalCoroutinesApi
abstract class CacheRetrievingApiHandler<NetworkObject, CacheObject, ViewState> constructor(
    private val stateEvent: StateEvent,
    private val cacheCall: (suspend () -> CacheObject),
    apiCall: suspend () -> NetworkObject
) : CachingApiHandler<NetworkObject, CacheObject, ViewState>(stateEvent, apiCall) {

    override val result = flow {
        emit(returnCache(false))
        val apiResult = safeApiCall()
        emit(resolveApiResult(apiResult))
    }

    private suspend fun returnCache(markJobComplete: Boolean): DataState<ViewState> {
        val cacheResult = safeCacheCall(cacheCall)
        var jobCompleteMarker: StateEvent? = null
        //First DataState emission doesn't include the original stateEvent as it's purpose
        //is to mark that particular job as complete
        if (markJobComplete) {
            jobCompleteMarker = stateEvent
        }

        return when (cacheResult) {
            is Success -> createSuccessViewStateFromCache(cacheResult.data, jobCompleteMarker)
            is CacheResult.Error -> buildError(cacheResult.error, Dialog(), stateEvent)
        }
    }

    override suspend fun createSuccessViewState(
        data: NetworkObject,
        stateEvent: StateEvent?
    ): DataState<ViewState> = returnCache(true)


    abstract fun createSuccessViewStateFromCache(
        data: CacheObject,
        stateEvent: StateEvent?
    ): DataState<ViewState>

    private suspend fun <T> safeCacheCall(cacheCall: suspend () -> T): CacheResult<T> =
        try {
            Success(cacheCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> CacheResult.Error(getString(R.string.dialog_message_error_cache_timeout_error))
                else -> CacheResult.Error(throwable.message)
            }
        }


}