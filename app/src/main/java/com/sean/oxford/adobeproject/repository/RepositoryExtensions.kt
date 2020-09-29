package com.sean.oxford.adobeproject.repository

import android.content.Context
import android.net.ConnectivityManager
import com.sean.oxford.adobeproject.AdobeApplication
import com.sean.oxford.adobeproject.network.MessageModel
import com.sean.oxford.adobeproject.network.MessageType
import com.sean.oxford.adobeproject.network.UIComponentType
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
fun <ViewState> buildError(
    message: String?,
    uiComponentType: UIComponentType,
    stateEvent: StateEvent?
): DataState<ViewState> =
    DataState(MessageModel(message, uiComponentType, MessageType.Error()), null, stateEvent, null)



@InternalCoroutinesApi
fun hasConnectivity(): Boolean {
    val connectivityManager =
        AdobeApplication.sApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}