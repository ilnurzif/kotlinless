package com.geekless.kotlianappless.model.data

import io.reactivex.Observable

interface IDataSource {
    fun getData(): Observable<String>
}