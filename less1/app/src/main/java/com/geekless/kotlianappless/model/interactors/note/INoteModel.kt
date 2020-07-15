package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface INoteModel {
    fun saveNote(note: Note)
    fun getDefaultNoteBehaviorSubject():BehaviorSubject<NoteResult>
    fun loadNote(noteId: String)
}