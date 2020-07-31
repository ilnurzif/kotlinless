package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository

class NodeModelImpl(val repository: IMyRepository): INoteModel {
    override suspend fun loadNote(id: String): Note {
        return repository.loadNote(id)
    }

    override suspend fun deleteNote(note: Note) {
        return repository.deleteNote(note)
    }

    override suspend fun saveNote(note: Note): Note {
        return repository.saveNote(note)
    }

}