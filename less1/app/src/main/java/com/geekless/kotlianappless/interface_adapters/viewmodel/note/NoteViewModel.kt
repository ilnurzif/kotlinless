package com.geekless.kotlia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.note.INoteModel
import com.geekless.kotlianappless.model.interactors.utility.Utility
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(val noteModel:INoteModel, val utility: Utility): ViewModel() {
    private val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
    private val noteViewState = MutableLiveData<NoteViewState>()
    lateinit var changedNote: Note

   init {
       noteViewState.value = NoteViewState()
        noteModel.getDefaultNoteBehaviorSubject().subscribe{
          noteResult->noteResult.let{
/*          noteViewState.value= NoteViewState(it)
            it.lastChanged.let { noteViewState.value!!.toolbarTitle= formatDate(it)}
            it.color.let { noteViewState.value!!.toolbarColor=utility.NoteColorToRes(it)}
            changedNote=it*/
            when (noteResult) {
                is NoteResult.Success<*> ->{
                    var l= noteResult.data as? Note
                    val newNoteViewSate=NoteViewState(note = l)
                    newNoteViewSate.toolbarTitle= formatDate(l?.lastChanged?:Date())
                    newNoteViewSate.toolbarColor.let {l?.color?.let {utility.NoteColorToRes(it)}}
                    noteViewState.value=NoteViewState(note = l)
                }
                    is NoteResult.Error -> noteViewState.value = NoteViewState(error = noteResult.error)
                }
            }
        }
      }


    fun loadNote(noteId: String) {
        noteModel.loadNote(noteId)
    }

    fun save(newTitle:String, newText:String) {
       //  changedNote= changedNote.copy(title=newTitle, text = newText, lastChanged = Date()) ?: Note(UUID.randomUUID().toString(), title=newTitle, text = newText, lastChanged = Date(), color = Note.Color.WHITE)
        changedNote = /*if (changedNote!=null)
        {changedNote?.copy(
                title = newTitle,
                text = newText,
                lastChanged = Date()
        )} else*/
       Note(UUID.randomUUID().toString(), title=newTitle, text = newText, lastChanged = Date(), color = Note.Color.WHITE)
    }

    fun viewNote(): LiveData<NoteViewState> = noteViewState

    override fun onCleared() {
         changedNote.let {
           noteModel.saveNote(it)
        }
    }

    fun formatDate(date:Date)=SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date)

}