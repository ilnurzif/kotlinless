package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IDataSource {
    fun getData(): BehaviorSubject<NoteResult>
    fun getCurrentNodeBehaviorSubject(): BehaviorSubject<NoteResult>
    fun saveNote(note: Note)
    fun loadNote(noteId: String)
    fun getDefaultUser():BehaviorSubject<User>
}