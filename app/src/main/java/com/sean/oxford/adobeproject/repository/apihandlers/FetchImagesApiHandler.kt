package com.sean.oxford.adobeproject.repository.apihandlers

import android.util.Log
import com.sean.oxford.adobeproject.model.FlickrImage
import com.sean.oxford.adobeproject.model.response.ImagesResponse
import com.sean.oxford.adobeproject.network.ApiService
import com.sean.oxford.adobeproject.persistence.AppDao
import com.sean.oxford.adobeproject.repository.apihandlers.base.CacheRetrievingApiHandler
import com.sean.oxford.adobeproject.screens.base.DataState
import com.sean.oxford.adobeproject.screens.base.StateEvent
import com.sean.oxford.adobeproject.screens.list.ListStateEvent.*
import com.sean.oxford.adobeproject.screens.list.ListViewState
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FetchImagesApiHandler(
    private val page: Int,
    private val stateEvent: FetchImagesStateEvent,
    private val apiService: ApiService,
    private val appDao: AppDao
) : CacheRetrievingApiHandler<ImagesResponse, List<FlickrImage>, ListViewState>(stateEvent,
    apiCall = {
        apiService.getImages(
            "flickr.photos.search",
            "2c28ed5f859dad50fdb9a3ad4a1023f9",
            "json",
            "1",
            "1",
            stateEvent.tag,
            50,
            page
        )
    },
    cacheCall = { appDao.getFlickrImagesByTag(stateEvent.tag) }) {

    override fun createSuccessViewStateFromCache(
        data: List<FlickrImage>,
        stateEvent: StateEvent?
    ): DataState<ListViewState> = DataState(null, ListViewState(data, this.stateEvent.tag), stateEvent, null)

    override suspend fun saveToCache(data: ImagesResponse) {
        val images = data.photos.photo
        for (networkImage in images) {
            val cachedImage: FlickrImage? = appDao.getFlickrImageById(networkImage.id)
            if (cachedImage == null) {
                insertNewImage(networkImage)
            } else {
                addTagsAndInsert(cachedImage, stateEvent.tag, networkImage)
            }
        }
    }

    private suspend fun insertNewImage(image: FlickrImage) {
        val tags = mutableListOf<String>()
        tags.add(stateEvent.tag)
        image.tags = tags

        val imgUrl = String.format(
            "http://farm%d.static.flickr.com/%d/%s_%s.jpg",
            image.farm,
            image.server,
            image.id,
            image.secret
        )
        image.imgUrl = imgUrl
        appDao.insertFlickrImage(image)
    }

    private suspend fun addTagsAndInsert(
        cachedImage: FlickrImage,
        tag: String,
        networkImage: FlickrImage
    ) {
        val tags = cachedImage.tags
        if (!tags.contains(tag)) {
            tags.add(tag)
        }
        networkImage.imgUrl = cachedImage.imgUrl
        networkImage.tags = tags
        appDao.insertFlickrImage(networkImage)
    }


}