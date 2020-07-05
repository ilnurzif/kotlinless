package com.geekless.kotlianappless.model.interactors

import io.reactivex.Observable

interface IModel {
    fun getData(): Observable<String>
}
