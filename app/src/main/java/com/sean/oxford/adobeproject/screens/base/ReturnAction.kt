package com.sean.oxford.adobeproject.screens.base

open class ReturnAction{

    private var hasBeenUsed: Boolean = false

    fun markActionUsed(){
        hasBeenUsed = true
    }

    fun hasBeenUsed(): Boolean = hasBeenUsed
}