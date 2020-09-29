package com.sean.oxford.adobeproject.di.module

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GlideModule {

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions =
        RequestOptions()

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager = Glide.with(application).setDefaultRequestOptions(requestOptions)


}