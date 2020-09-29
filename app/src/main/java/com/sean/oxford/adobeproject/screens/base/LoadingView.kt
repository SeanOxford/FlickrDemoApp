package com.sean.oxford.adobeproject.screens.base

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.sean.oxford.adobeproject.R

class LoadingView(context: Context): FrameLayout(context) {

    init {
        View.inflate(context, R.layout.view_loading, this)
        visibility = GONE
    }
}