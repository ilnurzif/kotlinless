package com.geekless.kotlianappless.model.interactors.main

import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface IMainModel {
    fun getData(): ReceiveChannel<NoteResult>
}
