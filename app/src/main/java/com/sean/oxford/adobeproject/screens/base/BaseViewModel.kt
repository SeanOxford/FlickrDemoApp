package com.sean.oxford.adobeproject.screens.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sean.oxford.adobeproject.network.MessageModel
import com.sean.oxford.adobeproject.repository.DataChannelManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
abstract class BaseViewModel<vs: ViewState> : ViewModel() {

    var hasInitialized: Boolean = false

    private val _viewState: MutableLiveData<vs> = MutableLiveData()
    private val _returnAction: MutableLiveData<ReturnAction> = MutableLiveData()

    val dataChannel: DataChannelManager<vs> = object: DataChannelManager<vs>(){
        override fun handleViewState(viewState: vs) {
            _viewState.value = viewState
        }

        override fun handleReturnAction(returnAction: ReturnAction?) {
            _returnAction.value = returnAction
        }
    }

    val stateMessage: LiveData<MessageModel>
        get() = dataChannel.messageQueue.stateMessage

    val returnAction: LiveData<ReturnAction>
        get() = _returnAction

    val viewState: LiveData<vs>
        get() = _viewState

    init {
        if(_viewState.value == null){
            _viewState.value = initViewState()
        }
    }

    fun setStateEvent(event: StateEvent){
        val job = getStateEventFlow(event)
        dataChannel.launchJob(event, job)
    }

    abstract fun getStateEventFlow(event: StateEvent): Flow<DataState<vs>>

    abstract fun initViewState(): vs

    override fun onCleared() {
        dataChannel.cancelJobs()
    }

    fun clearErrorMessage(){
        dataChannel.clearStateMessage()
    }



}