package com.geekless.kotlianappless.model.interactors.main

import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMainModel {
    fun getData(): BehaviorSubject<NoteResult>
}
