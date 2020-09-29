package com.sean.oxford.adobeproject.di.module

import androidx.fragment.app.FragmentFactory
import com.sean.oxford.adobeproject.factory.AppFragmentFactory
import com.sean.oxford.adobeproject.factory.AppViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
class FragmentFactoryModule {


    @Provides
    @Singleton
    fun provideAppFragmentFactory(viewModelProvider: AppViewModelFactory): FragmentFactory =
        AppFragmentFactory(viewModelProvider)


}