package com.sean.oxford.adobeproject.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.sean.oxford.adobeproject.AdobeApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AppNavHostFragment : NavHostFragment() {

    @Inject
    lateinit var mainFragmentFactory: FragmentFactory

    override fun onAttach(context: Context) {
        (activity?.application as AdobeApplication).appComponent.inject(this)
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onAttach(context)
    }

    companion object {
        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): AppNavHostFragment {
            var bundle: Bundle? = null
            if (graphId != 0) {
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                AppNavHostFragment()
            if (bundle != null) {
                result.arguments = bundle
            }
            return result
        }
    }


}