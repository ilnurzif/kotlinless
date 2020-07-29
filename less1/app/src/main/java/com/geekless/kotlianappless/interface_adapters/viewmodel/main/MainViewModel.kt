package com.geekless.kotliana

import androidx.annotation.VisibleForTesting
import com.geekless.kotlianappless.interface_adapters.viewmodel.main.MyViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class MainViewModel(val model: IMainModel) : ViewModel() {
    private val viewStateData = MutableLiveData<MyViewState>()

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
    }

      fun viewState(): LiveData<MyViewState> = viewStateData
}


