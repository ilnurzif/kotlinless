package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Observable

interface IDataSource {
    fun getData(): Observable<List<Note>>
}