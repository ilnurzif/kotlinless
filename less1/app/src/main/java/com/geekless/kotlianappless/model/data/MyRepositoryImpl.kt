package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable

class MyRepositoryImpl(dataSource:IDataSource):IMyRepository {
    var dataObservable : Observable<String> = dataSource.getData()
    override fun getData(): Observable<String> {
        return dataObservable;
    }
}