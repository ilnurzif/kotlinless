package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Observable

interface IMyRepository {
   fun getData(): Observable<List<Note>>
}