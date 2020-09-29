package com.sean.oxford.adobeproject.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sean.oxford.adobeproject.model.*
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromStringToFlickrImages(value: String): List<FlickrImage> {
        val flickrImageListType: Type = object : TypeToken<List<FlickrImage>>() {}.type
        return Gson().fromJson(value, flickrImageListType)
    }

    @TypeConverter
    fun fromFlickrImagesToString(list: List<FlickrImage>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToFlickrImage(value: String): FlickrImage {
        val flickrImageType: Type = object : TypeToken<FlickrImage>() {}.type
        return Gson().fromJson(value, flickrImageType)
    }

    @TypeConverter
    fun fromFlickrImageToString(flickrImage: FlickrImage): String {
        val gson = Gson()
        return gson.toJson(flickrImage)
    }


    @TypeConverter
    fun fromStringToStringList(value: String): List<String> {
        val stringListType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, stringListType)
    }

    @TypeConverter
    fun fromStringListToString(list: List<String>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }



}