package com.geekless.kotlianappless.model.interactors

import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable

class ModelImpl(repository: IMyRepository):IModel {
    var dataObservable : Observable<String> = repository.getData()
    override fun getData(): Observable<String> = dataObservable
}