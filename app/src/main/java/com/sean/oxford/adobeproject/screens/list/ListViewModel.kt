package com.sean.oxford.adobeproject.screens.list

import com.sean.oxford.adobeproject.repository.AppRepository
import com.sean.oxford.adobeproject.screens.base.BaseViewModel
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import com.sean.oxford.adobeproject.screens.list.ListReturnAction.GoToDetailScreenReturnAction
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.FetchImagesStateEvent
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.GoToDetailFragmentStateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@InternalCoroutinesApi
class ListViewModel
@Inject
constructor(val appRepsitory: AppRepository) : BaseViewModel<ListViewState>() {

    var currentPage = 1

    override fun getStateEventFlow(event: StateEvent): Flow<DataState<ListViewState>> =
        when (event) {
            is FetchImagesStateEvent -> appRepsitory.fetchFlickrImages(event, currentPage++)
            is GoToDetailFragmentStateEvent -> flow {
                val returnAction = GoToDetailScreenReturnAction(event.flickrImage)
                emit(DataState<ListViewState>(null, null, event, returnAction))
            }
            else -> flow { emit(DataState<ListViewState>(null, null, event, null)) }
        }

    override fun initViewState(): ListViewState = ListViewState(listOf(), "")
}