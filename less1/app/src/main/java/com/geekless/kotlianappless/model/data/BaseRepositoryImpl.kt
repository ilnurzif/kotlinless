package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import com.geekless.kotlianappless.model.repositories.IMyRepository
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class BaseRepositoryImpl(val dataSource: IDataSource) : IMyRepository {
    override suspend fun getData(): ReceiveChannel<NoteResult> {
        return dataSource.getData();
    }

    override suspend fun loadNote(id: String): Note {
        return dataSource.loadNote(id)
    }

    override suspend fun getDefaultUser(): User? {
        return dataSource.getDefaultUser()
    }

    override suspend fun deleteNote(note: Note) {
        return dataSource.deleteNote(note)
    }

    override suspend fun saveNote(note: Note): Note {
        return dataSource.saveNote(note)
    }

}