package com.sean.oxford.adobeproject.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "flickr_image")
data class FlickrImage(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "owner")
    val owner: String,

    @ColumnInfo(name = "secret")
    val secret: String,

    @ColumnInfo(name = "server")
    val server: Int,

    @ColumnInfo(name = "farm")
    val farm: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "ispublic")
    val ispublic: Int,

    @ColumnInfo(name = "isfriend")
    val isfriend: Int,

    @ColumnInfo(name = "isfamily")
    val isfamily: String,

    @ColumnInfo(name = "imgUrl")
    var imgUrl: String?,

    @ColumnInfo(name = "tags")
    var tags: MutableList<String>

    ) : Parcelable