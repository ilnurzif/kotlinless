package com.geekless.kotlianappless.model.repositories

import io.reactivex.Observable

interface IMyRepository {
   fun getData(): Observable<String>
}