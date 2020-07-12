package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject
import org.w3c.dom.Node

interface IDataSource {
    fun getData(): BehaviorSubject<List<Note>>
    fun getCurrentNodeBehaviorSubject(): BehaviorSubject<Note>
    fun saveNote(note: Note)
    fun setDefaultNote(note: Note)
}