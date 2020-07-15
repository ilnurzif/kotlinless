package com.geekless.kotlianappless.model.interactors.main

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMainModel {
    fun getData(): BehaviorSubject<NoteResult>
    fun setDefaultNote(note: Note)
}
