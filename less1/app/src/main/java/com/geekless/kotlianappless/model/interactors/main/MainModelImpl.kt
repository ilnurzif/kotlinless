package com.geekless.kotlianappless.model.interactors.main

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class MainModelImpl(val repository: IMyRepository): IMainModel {
    val dataObservable : BehaviorSubject<List<Note>> = repository.getData()
    override fun getData(): BehaviorSubject<List<Note>> = dataObservable
    override fun setDefaultNote(note: Note) =repository.setDefaultNote(note)
}