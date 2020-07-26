package com.geekless.kotlianappless.model.repositories

import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMyRepository {
    fun getData(): BehaviorSubject<NoteResult>
    fun saveNote(note: Note): Completable
    fun loadNote(noteId: String): Single<NoteResult>
    fun getDefaultUser(): BehaviorSubject<SplashViewState>
    fun deleteNote(note: Note): Completable
}