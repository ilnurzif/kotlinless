package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface INoteModel {
    fun saveNote(note: Note): Completable
    fun loadNote(noteId: String): Single<NoteResult>
    fun deleteNote(note: Note): Completable
}