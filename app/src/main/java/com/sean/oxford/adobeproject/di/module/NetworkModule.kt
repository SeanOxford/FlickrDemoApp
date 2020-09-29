package com.sean.oxford.adobeproject.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.sean.oxford.adobeproject.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(5, TimeUnit.SECONDS)
        httpClient.readTimeout(5, TimeUnit.SECONDS)
        httpClient.writeTimeout(5, TimeUnit.SECONDS)

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://api.flickr.com/services/").client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson)).build()

        return retrofit
    }


    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}