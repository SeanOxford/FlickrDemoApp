package com.sean.oxford.adobeproject.screens.detail

import com.sean.oxford.adobeproject.repository.AppRepository
import com.sean.oxford.adobeproject.screens.base.BaseViewModel
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@InternalCoroutinesApi
class DetailViewModel
@Inject
constructor(val appRepsitory: AppRepository) : BaseViewModel<DetailViewState>() {
    override fun getStateEventFlow(event: StateEvent): Flow<DataState<DetailViewState>> = when (event){
        is RetrieveImageFromDbStateEvent -> appRepsitory.fetchImageById(event)
        else -> flow{}
    }

    override fun initViewState(): DetailViewState = DetailViewState(null)
}