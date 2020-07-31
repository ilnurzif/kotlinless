package com.geekless.kotliana

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class MainViewModel(val model: IMainModel ) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }

/*    private val viewStateData = MutableLiveData<MyViewState>()

    init {
        model.getData().subscribe { result: NoteResult ->
            when (result) {
                is NoteResult.Success<*> -> {
                    val listNote = result.data as? List<Note>
                    listNote?.let {
                        viewStateData.value = MyViewState(listNote)
                        Note.noteCount = listNote.size
                    }
                }
                is NoteResult.Error -> viewStateData.value = MyViewState(error = result.error)
            }
        }
    }*/


    private val notesChannel = model.getData()

    init {
        launch {
            notesChannel.consumeEach {
                when(it){
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    protected fun setData(data: S){
        launch {
            notesChannel.send(data)
        }
    }

    //  fun viewState(): LiveData<MyViewState> = viewStateData
}


