package com.geekless.kotlianappless.model.interactors.main

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class MainModelImpl(val repository: IMyRepository): IMainModel {
    override fun getData(): BehaviorSubject<NoteResult> = repository.getData()
    override fun setDefaultNote(note: Note) =repository.setDefaultNote(note)
}