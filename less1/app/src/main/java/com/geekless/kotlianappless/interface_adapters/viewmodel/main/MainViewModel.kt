package com.geekless.kotliana

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class MainViewModel(val model: IMainModel ) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }

    private val notesChannel = model.getData()

    private val viewStateChannel = BroadcastChannel<List<Note>?>(Channel.CONFLATED)
    private val errorChannel = Channel<Throwable>()

    fun getViewState(): ReceiveChannel<List<Note>?> = viewStateChannel.openSubscription()
    fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel

    init {
        launch {
            notesChannel.consumeEach {
                when(it){
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>?)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    fun setData(data: List<Note>?){
        launch {
            if (data != null) {
                viewStateChannel.send(data)
            }
        }
    }

     fun setError(e: Throwable) = launch {
        errorChannel.send(e)
    }

}


