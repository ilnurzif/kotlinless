package com.geekless.kotlianappless.model.interactors.splash

import com.geekless.kotlianappless.model.entities.User
import io.reactivex.subjects.BehaviorSubject


interface ISplashModel {
    fun getCurrentUserBehaviorSubject(): BehaviorSubject<User>
}