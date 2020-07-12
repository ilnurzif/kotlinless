package com.geekless.kotlianappless.model.interactors.main

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface IMainModel {
    fun getData(): BehaviorSubject<List<Note>>
    fun setDefaultNote(note: Note)
}
