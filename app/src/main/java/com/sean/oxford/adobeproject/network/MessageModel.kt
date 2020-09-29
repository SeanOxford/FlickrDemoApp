package com.sean.oxford.adobeproject.network

import com.sean.oxford.adobeproject.screens.base.ReturnAction

data class MessageModel(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType,
    val returnAction: ReturnAction? = null
)
sealed class UIComponentType {

    class Toast : UIComponentType()

    class Dialog : UIComponentType()

    class AreYouSureDialog(val callback: AreYouSureCallback) : UIComponentType()

    class None : UIComponentType()
}

sealed class MessageType {

    class Success : MessageType()

    class Error : MessageType()

    class Info : MessageType()

    class None : MessageType()
}

interface AreYouSureCallback {

    fun proceed()

    fun cancel()
}