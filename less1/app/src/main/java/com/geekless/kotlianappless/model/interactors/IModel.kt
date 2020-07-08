package com.geekless.kotlianappless.model.interactors

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Observable

interface IModel {
    fun getData(): Observable<List<Note>>
}
