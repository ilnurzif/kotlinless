package com.geekless.kotlianappless.model.data

import io.reactivex.Observable

class MyDataSource:IDataSource {
    override fun getData(): Observable<String> {
         return Observable.just("MyStr");
    }
}