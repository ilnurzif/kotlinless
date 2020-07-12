package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.subjects.BehaviorSubject

class BaseRepositoryImpl(val dataSource: IDataSource) : IMyRepository {
    var dataObservable: BehaviorSubject<List<Note>> = dataSource.getData()
    override fun getData(): BehaviorSubject<List<Note>> {
        return dataObservable;
    }

    override fun saveNote(note: Note) {
        dataSource.saveNote(note)
    }

    override fun setDefaultNote(note: Note) {
        dataSource.setDefaultNote(note)
    }

    override fun getDefaultNoteBehaviorSubject(): BehaviorSubject<Note> {
        return dataSource.getCurrentNodeBehaviorSubject()
    }
}