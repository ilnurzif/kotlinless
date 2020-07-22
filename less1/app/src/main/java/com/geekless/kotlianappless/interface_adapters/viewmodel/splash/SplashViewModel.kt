package com.geekless.kotliappless

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.entities.User
import com.geekless.kotlianappless.model.interactors.splash.ISplashModel
import ru.geekbrains.gb_kotlin.data.error.NoAuthException


class SplashViewModel(val splashModel: ISplashModel) : ViewModel() {
    private val splashViewState = MutableLiveData<SplashViewState>()

    init {
        splashModel.getCurrentUserBehaviorSubject().subscribe{ user: User ->
            user?.let {
                splashViewState.value=SplashViewState(authenticated = true)
            } ?: let {
                splashViewState.value=SplashViewState(error = NoAuthException())
            }
            }
        }

    fun viewState(): LiveData<SplashViewState> = splashViewState

    override fun onCleared() {}
}
