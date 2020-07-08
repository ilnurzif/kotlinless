package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable

class MyRepositoryImpl(dataSource:IDataSource):IMyRepository {
    var dataObservable : Observable<List<Note>> = dataSource.getData()
    override fun getData(): Observable<List<Note>> {
        return dataObservable;
    }
}