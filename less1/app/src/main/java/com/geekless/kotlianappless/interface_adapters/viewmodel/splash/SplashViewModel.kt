package com.geekless.kotliappless

import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.interactors.splash.ISplashModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.io.NotActiveException
import kotlin.coroutines.CoroutineContext

class SplashViewModel(val splashModel: ISplashModel) : ViewModel(), CoroutineScope {
    private val viewStateChannel = BroadcastChannel<Boolean>(Channel.CONFLATED)
    private val errorChannel = Channel<Throwable>()

    fun getViewState(): ReceiveChannel<Boolean> = viewStateChannel.openSubscription()
    fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel


    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    fun requestUser() = launch {
        splashModel.getDefaultUser()?.let {
            setData(true)
        } ?: let { setError(NotActiveException()) }
    }

    fun setError(e: Throwable) = launch {
        errorChannel.send(e)
    }

    protected fun setData(data: Boolean) {
        launch {
            viewStateChannel.send(data)
        }
    }

    override fun onCleared() {
        viewStateChannel.close()
        errorChannel.close()
        coroutineContext.cancel()
        super.onCleared()
    }
}
