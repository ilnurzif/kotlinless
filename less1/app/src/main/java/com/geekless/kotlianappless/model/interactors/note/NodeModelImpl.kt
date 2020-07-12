package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.subjects.BehaviorSubject

class NodeModelImpl(val repository: IMyRepository): INoteModel {
    override fun saveNote(note: Note) {
        repository.saveNote(note)
    }

    override fun getDefaultNoteBehaviorSubject(): BehaviorSubject<Note> {
        return repository.getDefaultNoteBehaviorSubject()
    }
}