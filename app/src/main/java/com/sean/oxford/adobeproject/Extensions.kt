package com.sean.oxford.adobeproject

import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.math.roundToInt


@OptIn(InternalCoroutinesApi::class)
fun getString(id: Int): String {
    return AdobeApplication.sApplication.getString(id)
}


@OptIn(InternalCoroutinesApi::class)
fun getString(id: Int, extra: Any): String {
    return AdobeApplication.sApplication.getString(id, extra)
}