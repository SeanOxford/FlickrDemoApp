package com.sean.oxford.adobeproject.screens.list.widgets

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sean.oxford.adobeproject.R

class ListToolBar(context: Context): FrameLayout(context) {

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        inflate(context, R.layout.toolbar_list, this)
        isClickable = true
    }


}