package com.geekless.kotliappless

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.interactors.splash.ISplashModel


class SplashViewModel(val splashModel: ISplashModel) : ViewModel() {
    private val splashViewState = MutableLiveData<SplashViewState>()

    init {
        splashModel.getCurrentUserBehaviorSubject().subscribe(
                {splashViewState.value = it}
                ,{ error ->splashViewState.value = error.message?.let { SplashViewState(authApiError = it) }
                });
    }

    fun viewState(): LiveData<SplashViewState> = splashViewState

    override fun onCleared() {}
}
