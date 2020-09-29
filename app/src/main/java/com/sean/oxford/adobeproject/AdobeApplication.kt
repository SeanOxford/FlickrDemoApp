package com.sean.oxford.adobeproject

import android.app.Application
import com.sean.oxford.adobeproject.di.AdobeComponent
import com.sean.oxford.adobeproject.di.DaggerAdobeComponent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
open class AdobeApplication: Application() {

    lateinit var appComponent: AdobeComponent

    companion object {
        lateinit var sApplication: AdobeApplication
    }

    override fun onCreate() {
        sApplication = this
        super.onCreate()
        initAppComponent()
    }

    protected open fun initAppComponent(){
        appComponent = DaggerAdobeComponent.factory().create(this)
    }

}