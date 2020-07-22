package com.geekless.kotlianappless.model.interactors.splash

import com.geekless.kotlianappless.model.entities.User
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.subjects.BehaviorSubject

class SplashModelImpl(val repository: IMyRepository): ISplashModel {
    override fun getCurrentUserBehaviorSubject(): BehaviorSubject<User> {
        return repository.getDefaultUser()
    }
}