package com.sean.oxford.adobeproject.repository.apihandlers.base

import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.getString
import com.sean.oxford.adobeproject.network.ApiResult
import com.sean.oxford.adobeproject.network.UIComponentType.Dialog
import com.sean.oxford.adobeproject.repository.buildError
import com.sean.oxford.adobeproject.repository.hasConnectivity
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


@InternalCoroutinesApi
abstract class BaseApiHandler<ViewState, NetworkObj> constructor(
    private val stateEvent: StateEvent,
    private val apiCall: suspend () -> NetworkObj
) {

    open val result = flow {
        val apiResult = safeApiCall()
        emit(resolveApiResult(apiResult))
    }

    protected suspend fun resolveApiResult(
        apiResult: ApiResult<NetworkObj>
    ): DataState<ViewState> = when (apiResult) {
        is ApiResult.GenericError -> buildError(apiResult.message, Dialog(), stateEvent)
        is ApiResult.Success -> if (apiResult.data == null) {
            buildError(getString(R.string.dialog_message_error_default), Dialog(), stateEvent)
        } else {
            handleApiCallSuccess(apiResult.data)
        }
    }

    suspend fun safeApiCall(): ApiResult<NetworkObj> {
        if (!hasConnectivity()) {
            return ApiResult.GenericError(40, getString(R.string.dialog_message_error_no_internet))
        }

        return withContext(IO) {
            try {
                withTimeout(5000) {
                    ApiResult.Success(apiCall.invoke())
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                getCorrectError(throwable)
            }
        }
    }

    private fun getCorrectError(throwable: Throwable): ApiResult<NetworkObj> =
        when (throwable) {
            is TimeoutCancellationException ->
                ApiResult.GenericError(408, getString(R.string.dialog_message_error_timeout))
            is IOException ->
                ApiResult.GenericError(70, getString(R.string.dialog_message_error_default))
            is HttpException -> {
                val errorBodyMessage = convertErrorBody(throwable)
                if (errorBodyMessage != null) {
                    ApiResult.GenericError(0, errorBodyMessage)
                } else {
                    ApiResult.GenericError(0, throwable.message())
                }
            }
            else -> ApiResult.GenericError(-1, stateEvent.errorString())
        }


    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            val jsonError = JSONObject(throwable.response()?.errorBody()?.string())
            jsonError.getString("message")
        } catch (exception: Exception) {
            getString(R.string.dialog_message_error_timeout)
        }
    }

    abstract suspend fun createSuccessViewState(
        data: NetworkObj,
        stateEvent: StateEvent?
    ): DataState<ViewState>

    open suspend fun handleApiCallSuccess(data: NetworkObj): DataState<ViewState> =
        createSuccessViewState(data, stateEvent)


}