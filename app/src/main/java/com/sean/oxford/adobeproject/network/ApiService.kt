package com.sean.oxford.adobeproject.network

import com.sean.oxford.adobeproject.model.response.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("rest")
    suspend fun getImages(
        @Query("method") method: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: String,
        @Query("has_geo") hasGeo: String,
        @Query("text") text: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): ImagesResponse



}