package com.sean.oxford.adobeproject.screens.base

import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBar.*
import androidx.fragment.app.Fragment
import com.sean.oxford.adobeproject.activity.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
abstract class BaseFragment : Fragment() {

    private lateinit var baseView: BaseFragmentView<ViewState>
    private var toolbarView: View? = null
    private var hasInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        toolbarView = getToolbarView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!hasInitialized) {
            baseView = initBaseView()
        }

        //If electing to use custom Toolbar
        if (toolbarView != null) {
            (context as MainActivity).supportActionBar?.title = ""
            (context as MainActivity).supportActionBar?.setDisplayShowCustomEnabled(true)
            val params = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            (context as MainActivity).supportActionBar?.setCustomView(toolbarView, params)
        } else {
            (context as MainActivity).title = initTitle()
        }
        return baseView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Latest view-initializing callback
        if (!hasInitialized) {
            baseView.initAll(menu)
            if (toolbarView != null) {
                baseView.initToolbarView(toolbarView)
            } else {
                inflater.inflate(getToolbarMenu(), menu)
            }
        }
        hasInitialized = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        baseView.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    private fun initTitle(): String = baseView.initTitle()

    abstract fun getToolbarMenu(): Int

    abstract fun initBaseView(): BaseFragmentView<ViewState>

    protected open fun getToolbarView(): View? = null


}