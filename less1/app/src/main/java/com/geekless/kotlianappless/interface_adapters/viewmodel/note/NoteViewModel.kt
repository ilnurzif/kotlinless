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

class NoteViewModel(val noteModel: INoteModel, val utility: Utility) : ViewModel() {
    private val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
    private val noteViewState = MutableLiveData<NoteViewState>()
    private var changedNote: Note? = null

    init {
        noteViewState.value = NoteViewState()
        noteModel.getDefaultNoteBehaviorSubject().subscribe { noteResult ->
            noteResult.let {
                when (noteResult) {
                    is NoteResult.Success<*> -> {
                        var n = noteResult.data as? Note
                        val newNoteViewSate = NoteViewState(note = n)
                        newNoteViewSate.toolbarTitle = formatDate(n?.lastChanged ?: Date())
                        n?.color?.let { newNoteViewSate.toolbarColor = utility.NoteColorToRes(it) }
                        n?.let { changedNote = n }
                        noteViewState.value = newNoteViewSate
                    }
                    is NoteResult.Error -> noteViewState.value = NoteViewState(error = noteResult.error)
                }
            }
        }
    }

    fun loadNote(noteId: String?) {
        if (noteId != null) {
            noteModel.loadNote(noteId)
        } else
            newNote()
    }

    fun save(newTitle: String, newText: String) {
        changedNote = changedNote?.let {
            changedNote?.copy(
                    title = newTitle,
                    text = newText,
                    lastChanged = Date()
            )
        }
                ?: Note(UUID.randomUUID().toString(), title = newTitle, text = newText, lastChanged = Date(), color = utility.getColor())
    }

    fun viewNote(): LiveData<NoteViewState> = noteViewState

    override fun onCleared() {
        changedNote?.let {
            noteModel.saveNote(it)
        }
    }

    fun formatDate(date: Date) = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(date)

    fun newNote() {
        changedNote = null
        val newNoteViewSate = NoteViewState()
        newNoteViewSate.toolbarTitle = utility.getStringResource("note_new")
        noteViewState.value = newNoteViewSate
    }
}