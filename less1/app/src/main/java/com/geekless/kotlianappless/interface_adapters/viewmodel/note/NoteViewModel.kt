package com.geekless.kotlia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.note.INoteModel
import com.geekless.kotlianappless.model.interactors.utility.Utility
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(val noteModel:INoteModel, val utility: Utility): ViewModel() {
    private val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
    private val noteViewState = MutableLiveData<NoteViewState>()
    lateinit var changedNote: Note
   init {
        noteModel.getDefaultNoteBehaviorSubject().subscribe{note->note.let{
            noteViewState.value= NoteViewState(it)
            it.lastChanged.let { noteViewState.value!!.toolbarTitle= formatDate(it)}
            it.color.let { noteViewState.value!!.toolbarColor=utility.NoteColorToRes(it)}
            changedNote=it}}
    }

    fun save(newTitle:String, newText:String) {
          changedNote= changedNote?.copy(title=newTitle, text = newText, lastChanged = Date())
    }

    fun viewNote(): LiveData<NoteViewState> = noteViewState

    override fun onCleared() {
         changedNote.let {
           noteModel.saveNote(it)
        }
    }

    fun formatDate(date:Date)=SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date)
}