package com.sean.oxford.adobeproject.repository

import com.sean.oxford.adobeproject.network.ApiService
import com.sean.oxford.adobeproject.persistence.AppDao
import com.sean.oxford.adobeproject.repository.apihandlers.FetchImagesApiHandler
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent
import com.sean.oxford.adobeproject.screens.detail.DetailStateEvent.*
import com.sean.oxford.adobeproject.screens.detail.DetailViewState
import com.sean.oxford.adobeproject.screens.list.ListStateEvent
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.*
import com.sean.oxford.adobeproject.screens.list.ListViewState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@InternalCoroutinesApi
@Singleton
class AppRepositoryImpl
@Inject
constructor(private val apiService: ApiService, private val appDao: AppDao) : AppRepository {

    override fun fetchFlickrImages(stateEvent: FetchImagesStateEvent, page: Int): Flow<DataState<ListViewState>> =
        FetchImagesApiHandler(page, stateEvent, apiService, appDao).result

    override fun fetchImageById(stateEvent: RetrieveImageFromDbStateEvent): Flow<DataState<DetailViewState>> {
        return flow{
            val flickrImage = appDao.getFlickrImageById(stateEvent.imageId)
            emit(DataState(null, DetailViewState(flickrImage), stateEvent, null))
        }

    }


}