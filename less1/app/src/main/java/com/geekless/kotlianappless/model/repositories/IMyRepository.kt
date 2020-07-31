package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMyRepository {
    fun getData(): ReceiveChannel<NoteResult>
    suspend fun loadNote(id: String): Note
    suspend fun getDefaultUser(): User?
    suspend fun deleteNote(note: Note)
    suspend fun saveNote(note: Note): Note
}