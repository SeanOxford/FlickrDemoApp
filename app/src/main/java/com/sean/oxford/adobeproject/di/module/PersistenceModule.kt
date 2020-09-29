package com.sean.oxford.adobeproject.di.module

import android.app.Application
import androidx.room.Room
import com.sean.oxford.adobeproject.persistence.AppDao
import com.sean.oxford.adobeproject.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideAppDao(db: AppDatabase): AppDao = db.getAppDao()
}