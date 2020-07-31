package com.geekless.kotlianappless.model.interactors.main

import com.geekless.kotlianappless.model.repositories.IMyRepository
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class MainModelImpl(val repository: IMyRepository): IMainModel {
    override fun getData(): ReceiveChannel<NoteResult> = repository.getData()
}