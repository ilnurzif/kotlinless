package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class BaseRepositoryImpl(val dataSource: IDataSource) : IMyRepository {
    override fun getData(): BehaviorSubject<NoteResult> {
        return  dataSource.getData();
    }

    override fun saveNote(note: Note) {
        dataSource.saveNote(note)
    }

     override fun getDefaultNoteBehaviorSubject(): BehaviorSubject<NoteResult> {
        return dataSource.getCurrentNodeBehaviorSubject()
    }

    override fun loadNote(noteId: String) {
        dataSource.loadNote(noteId)
    }
}