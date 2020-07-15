package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject
import org.w3c.dom.Node
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IDataSource {
    fun getData(): BehaviorSubject<NoteResult>
    fun getCurrentNodeBehaviorSubject(): BehaviorSubject<NoteResult>
    fun saveNote(note: Note)
    fun loadNote(noteId: String)
}