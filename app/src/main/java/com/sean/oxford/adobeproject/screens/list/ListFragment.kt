package com.sean.oxford.adobeproject.screens.list

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.screens.base.BaseFragment
import com.sean.oxford.adobeproject.screens.base.BaseFragmentView
import com.sean.oxford.adobeproject.screens.base.ViewState
import com.sean.oxford.adobeproject.screens.list.widgets.ListToolBar
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ListFragment(viewModelProvider: ViewModelProvider.Factory) : BaseFragment() {

    private val viewModel: ListViewModel by viewModels{viewModelProvider}

    override fun getToolbarMenu(): Int = R.menu.menu_list

    override fun getToolbarView(): View? = ListToolBar(requireContext())

    override fun initBaseView(): BaseFragmentView<ViewState> = ListFragmentView(viewModel, requireContext()) as BaseFragmentView<ViewState>
}