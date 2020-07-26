package com.geekless.kotlianappless.model.interactors.splash

import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import io.reactivex.subjects.BehaviorSubject

interface ISplashModel {
    fun getCurrentUserBehaviorSubject(): BehaviorSubject<SplashViewState>
}