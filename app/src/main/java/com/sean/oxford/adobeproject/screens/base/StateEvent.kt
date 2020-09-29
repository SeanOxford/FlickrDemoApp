package com.sean.oxford.adobeproject.screens.base

import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.getString
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
abstract class StateEvent{

    var showLoading: Boolean = false

    open fun errorString(): String = getString(R.string.dialog_message_error_default)

}
