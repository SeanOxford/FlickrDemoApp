package com.sean.oxford.adobeproject.di.module

import com.sean.oxford.adobeproject.network.ApiService
import com.sean.oxford.adobeproject.persistence.AppDao
import com.sean.oxford.adobeproject.repository.AppRepository
import com.sean.oxford.adobeproject.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProdRepository(apiService: ApiService, appDao: AppDao): AppRepository =
        AppRepositoryImpl(apiService, appDao)


}