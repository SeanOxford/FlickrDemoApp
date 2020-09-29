package com.sean.oxford.adobeproject.persistence

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sean.oxford.adobeproject.model.FlickrImage

@Dao
interface AppDao {

    @Query("SELECT * FROM flickr_image")
    suspend fun getFlickrImages(): List<FlickrImage>

    @Query("SELECT * FROM flickr_image WHERE tags LIKE '%' || :tag ||  '%'")
    suspend fun getFlickrImagesByTag(tag: String): List<FlickrImage>

    @Query("SELECT * FROM flickr_image WHERE id == :id")
    suspend fun getFlickrImageById(id: String): FlickrImage

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlickrImage(flickrImage: FlickrImage): Long

}