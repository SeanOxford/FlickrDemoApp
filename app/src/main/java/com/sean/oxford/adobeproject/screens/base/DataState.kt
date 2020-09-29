package com.sean.oxford.adobeproject.screens.base

import com.sean.oxford.adobeproject.network.MessageModel
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
data class DataState<T>(
    val messageModel: MessageModel? = null,
    val data: T? = null,
    val stateEvent: StateEvent? = null,
    val returnAction: ReturnAction? = null
){
    fun isJobCompleted(): Boolean = stateEvent != null
}