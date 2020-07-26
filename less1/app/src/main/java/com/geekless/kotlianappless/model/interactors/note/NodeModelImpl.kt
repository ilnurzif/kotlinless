package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class NodeModelImpl(val repository: IMyRepository): INoteModel {
    override fun saveNote(note: Note): Completable {
        return repository.saveNote(note)
    }

    override fun loadNote(noteId: String): Single<NoteResult> {
        return repository.loadNote(noteId)
    }

    override fun deleteNote(note: Note): Completable {
       return repository.deleteNote(note)
    }
}