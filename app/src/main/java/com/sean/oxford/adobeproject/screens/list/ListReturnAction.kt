package com.sean.oxford.adobeproject.screens.list

import com.sean.oxford.adobeproject.model.FlickrImage
import com.sean.oxford.adobeproject.screens.base.ReturnAction

sealed class ListReturnAction : ReturnAction() {

    class GoToDetailScreenReturnAction(val flickrImage: FlickrImage) : ListReturnAction()
}