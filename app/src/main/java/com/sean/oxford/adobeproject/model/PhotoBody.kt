package com.sean.oxford.adobeproject.model

data class PhotoBody(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<FlickrImage>,
    val stat: String
)