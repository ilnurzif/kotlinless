package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.repositories.IMyRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class BaseRepositoryImpl(val dataSource: IDataSource) : IMyRepository {
    override fun getData(): BehaviorSubject<NoteResult> {
        return  dataSource.getData();
    }

    override fun saveNote(note: Note): Completable {
        return dataSource.saveNote(note)
    }

    override fun loadNote(noteId: String): Single<NoteResult> {
        return dataSource.loadNote(noteId)
    }

    override fun getDefaultUser(): BehaviorSubject<SplashViewState> {
        return dataSource.getDefaultUser()
    }

    override fun deleteNote(note: Note): Completable {
      return dataSource.deleteNote(note)
    }

}