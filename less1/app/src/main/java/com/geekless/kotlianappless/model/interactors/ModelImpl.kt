package com.geekless.kotlianappless.model.interactors

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable

class ModelImpl(repository: IMyRepository):IModel {
    var dataObservable : Observable<List<Note>> = repository.getData()
    override fun getData(): Observable<List<Note>> = dataObservable
}