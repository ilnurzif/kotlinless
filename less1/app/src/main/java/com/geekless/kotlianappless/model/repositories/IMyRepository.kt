package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMyRepository {
   fun getData(): BehaviorSubject<NoteResult>
   fun saveNote(note:Note)
   fun setDefaultNote(note: Note)
   fun getDefaultNoteBehaviorSubject(): BehaviorSubject<NoteResult>
   fun loadNote(noteId: String)
}