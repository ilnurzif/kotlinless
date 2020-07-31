package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface INoteModel {
    suspend fun loadNote(id: String): Note
    suspend fun deleteNote(note: Note)
    suspend fun saveNote(note: Note): Note
}