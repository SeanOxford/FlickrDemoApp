package com.sean.oxford.adobeproject.screens.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.sean.oxford.adobeproject.AdobeApplication
import com.sean.oxford.adobeproject.R
import com.sean.oxford.adobeproject.activity.MainActivity
import com.sean.oxford.adobeproject.network.AreYouSureCallback
import com.sean.oxford.adobeproject.network.MessageModel
import com.sean.oxford.adobeproject.network.MessageType
import com.sean.oxford.adobeproject.network.UIComponentType
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
abstract class BaseFragmentView<vs : ViewState>(
    private val viewModel: BaseViewModel<vs>,
    context: Context
) : FrameLayout(context) {

    protected lateinit var loadingView: LoadingView
    private var dialogInView: MaterialDialog? = null
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var omitLoadingView = false


    init {
        id = initId()
        inflate(context, getLayoutRes(), this)
    }

    private fun initObservers() {
        viewModel.dataChannel.numActiveJobs.observe(context as AppCompatActivity, { resolveLoadingView(it) })
        viewModel.stateMessage.observe(context as AppCompatActivity, { resolveMessageModel(it) })
        viewModel.returnAction.observe(context as AppCompatActivity, { resolveReturnAction(it) })
        viewModel.viewState.observe(context as AppCompatActivity, { onViewStateChanged(it as vs) })
    }

    private fun resolveLoadingView(numberOfJobs: Int) {
        if (numberOfJobs > 0) {
            if (!omitLoadingView) {
                loadingView.visibility = View.VISIBLE
            }
        } else {
            swipeRefreshLayout?.isRefreshing = false
            loadingView.visibility = View.GONE
        }
    }

    private fun resolveMessageModel(messageModel: MessageModel?) {
        messageModel?.let {
            val errorModelCallback = object : ErrorModelCallback {
                override fun removeMessageFromStack() {
                    viewModel.clearErrorMessage()
                    if (messageModel.returnAction != null) {
                        resolveReturnAction(messageModel.returnAction)
                    }
                }
            }
            swipeRefreshLayout?.isRefreshing = false
            when (messageModel.uiComponentType) {
                is UIComponentType.AreYouSureDialog -> messageModel.message?.let {
                    areYouSureDialog(
                        it,
                        messageModel.uiComponentType.callback,
                        errorModelCallback
                    )
                }
                is UIComponentType.Toast -> messageModel.message?.let {
                    displayToast(
                        it,
                        errorModelCallback
                    )
                }
                is UIComponentType.Dialog -> displayDialog(messageModel, errorModelCallback)
                is UIComponentType.None -> errorModelCallback.removeMessageFromStack()
            }
        }
    }

    private fun resolveReturnAction(returnAction: ReturnAction) {
        if (!returnAction.hasBeenUsed()) {
            resolveAction(returnAction)
            returnAction.markActionUsed()
        }
    }

    private fun displayToast(message: String, errorModelCallback: ErrorModelCallback) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        errorModelCallback.removeMessageFromStack()
    }

    private fun displayDialog(messageModel: MessageModel, errorModelCallback: ErrorModelCallback) {
        val title = when (messageModel.messageType) {
            is MessageType.Error -> R.string.dialog_title_error
            is MessageType.Success -> R.string.dialog_title_success
            is MessageType.Info -> R.string.dialog_title_info
            is MessageType.None -> R.string.dialog_title_error
        }
        val dialog = MaterialDialog(context)
            .show {
                title(title)
                message(text = messageModel.message)
                positiveButton(R.string.ok) {
                    errorModelCallback.removeMessageFromStack()
                    dismiss()
                }
                onDismiss {
                    errorModelCallback.removeMessageFromStack()
                    it.dismiss()
                }
                cancelable(false)
            }
        dialog.show()
    }

    private fun areYouSureDialog(
        message: String,
        callback: AreYouSureCallback,
        errorModelCallback: ErrorModelCallback
    ): MaterialDialog {
        return MaterialDialog(context)
            .show {
                title(R.string.dialog_title_are_you_sure)
                message(text = message)
                negativeButton(R.string.dialog_option_cancel) {
                    callback.cancel()
                    errorModelCallback.removeMessageFromStack()
                    dismiss()
                }
                positiveButton(R.string.dialog_option_yes) {
                    callback.proceed()
                    errorModelCallback.removeMessageFromStack()
                    dismiss()
                }
                onDismiss {
                    dialogInView = null
                }
                cancelable(false)
            }
    }


    private fun initLoadingView() {
        loadingView = LoadingView(context)
        addView(loadingView)
    }

    fun initAll(menu: Menu?) {
        initLoadingView()
        initViews(menu)
        initObservers()
        if (!viewModel.hasInitialized) {
            initialAction()
            viewModel.hasInitialized = true
        }
    }

    protected fun setStateEvent(stateEvent: StateEvent) {
        viewModel.setStateEvent(stateEvent)
    }

    open fun onOptionsItemSelected(item: MenuItem) {}

    abstract fun onViewStateChanged(viewState: vs)

    abstract fun getLayoutRes(): Int

    abstract fun initViews(menu: Menu?)

    open fun initToolbarView(toolbarView: View) {}

    protected open fun resolveAction(action: ReturnAction) {}

    abstract fun initTitle(): String

    protected open fun initialAction() {}

    protected fun getCurrentViewState() = viewModel.viewState.value as vs

    open protected fun initId(): Int = -1

    protected fun navigate(navId: Int, bundle: Bundle?) {
        if (context is MainActivity) {
            (context as MainActivity).findNavController(R.id.nav_host_fragment)
                .navigate(navId, bundle)
        } else {
            (context as FragmentScenario.EmptyFragmentActivity).findNavController(id)
                .navigate(navId, bundle)
        }
    }

    interface ErrorModelCallback {
        fun removeMessageFromStack()
    }

}