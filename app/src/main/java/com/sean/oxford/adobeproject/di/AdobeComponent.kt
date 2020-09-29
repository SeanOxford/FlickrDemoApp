package com.sean.oxford.adobeproject.di

import android.app.Application
import com.sean.oxford.adobeproject.activity.AppNavHostFragment
import com.sean.oxford.adobeproject.di.module.*
import com.sean.oxford.adobeproject.screens.base.BaseFragmentView
import com.sean.oxford.adobeproject.screens.base.ViewState
import com.sean.oxford.adobeproject.screens.detail.DetailFragmentView
import com.sean.oxford.adobeproject.screens.list.widgets.FlickrImageView
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@InternalCoroutinesApi
@Singleton
@Component(
    modules = [ViewModelModule::class,
        FragmentFactoryModule::class,
        RepositoryModule::class,
        PersistenceModule::class,
        GlideModule::class,
        NetworkModule::class]
)
interface AdobeComponent {

    fun inject(navHostFragment: AppNavHostFragment)
    fun inject(flickrImageView: FlickrImageView)
    fun inject(detailFragmentView: DetailFragmentView)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AdobeComponent
    }


}