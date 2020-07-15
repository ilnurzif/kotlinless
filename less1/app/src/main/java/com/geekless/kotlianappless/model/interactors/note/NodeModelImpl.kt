package com.geekless.kotlianappless.model.interactors.note

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class NodeModelImpl(val repository: IMyRepository): INoteModel {
    override fun saveNote(note: Note) {
        repository.saveNote(note)
    }

    override fun getDefaultNoteBehaviorSubject(): BehaviorSubject<NoteResult> {
        return repository.getDefaultNoteBehaviorSubject()
    }

    override fun loadNote(noteId: String)  {
        return repository.loadNote(noteId)
    }
}