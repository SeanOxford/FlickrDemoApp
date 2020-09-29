package com.sean.oxford.adobeproject.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.sean.oxford.adobeproject.screens.list.ListFragment
import com.sean.oxford.adobeproject.screens.detail.DetailFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AppFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            ListFragment::class.java.name -> return ListFragment(viewModelFactory)
            DetailFragment::class.java.name -> return DetailFragment(viewModelFactory)
        }
        return super.instantiate(classLoader, className)
    }
}