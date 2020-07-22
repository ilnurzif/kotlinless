package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMyRepository {
   fun getData(): BehaviorSubject<NoteResult>
   fun saveNote(note:Note)
   fun getDefaultNoteBehaviorSubject(): BehaviorSubject<NoteResult>
   fun loadNote(noteId: String)
   fun getDefaultUser(): BehaviorSubject<User>
}