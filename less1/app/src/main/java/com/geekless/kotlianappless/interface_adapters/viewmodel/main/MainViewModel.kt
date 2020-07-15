package com.geekless.kotliana

import com.geekless.kotlianappless.interface_adapters.viewmodel.main.MyViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.main.IMainModel
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import java.util.*

class MainViewModel(val model: IMainModel): ViewModel() {
    private val viewStateData = MutableLiveData<MyViewState>()

    init {model.getData().subscribe{
       result: NoteResult ->
        when(result){
            is NoteResult.Success<*> -> {viewStateData.value=MyViewState(result.data as? List<Note>)}
            is NoteResult.Error -> viewStateData.value = MyViewState(error = result.error)
        }
    }}

    fun viewState(): LiveData<MyViewState> = viewStateData

    fun setDefaultNote(note: Note) {
        model.setDefaultNote(note)
    }

    fun createNote() {
        val colorArr=Note.Color.values()
        val size= (viewStateData.value?.notes?.size ?: 0)
        val newColor=colorArr[size % colorArr.size]
        val note=Note(UUID.randomUUID().toString(), title = "", text = "", color = newColor)
        model.setDefaultNote(note)
    }
}


