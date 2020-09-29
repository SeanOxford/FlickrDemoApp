package com.sean.oxford.adobeproject.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sean.oxford.adobeproject.model.FlickrImage
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@Database(
    entities = [FlickrImage::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        const val DATABASE_NAME: String = "app_db"
    }
}
