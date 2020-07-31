package com.geekless.kotlianappless.model.interactors.splash

import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.entities.User
import io.reactivex.subjects.BehaviorSubject

interface ISplashModel {
   suspend fun getDefaultUser(): User?
}