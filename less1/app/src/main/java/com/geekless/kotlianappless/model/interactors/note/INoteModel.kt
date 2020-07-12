package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject

interface INoteModel {
    fun saveNote(note: Note)
    fun getDefaultNoteBehaviorSubject():BehaviorSubject<Note>
}