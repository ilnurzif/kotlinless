package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject

interface IMyRepository {
   fun getData(): BehaviorSubject<List<Note>>
   fun saveNote(note:Note)
   fun setDefaultNote(note: Note)
   fun getDefaultNoteBehaviorSubject(): BehaviorSubject<Note>
}