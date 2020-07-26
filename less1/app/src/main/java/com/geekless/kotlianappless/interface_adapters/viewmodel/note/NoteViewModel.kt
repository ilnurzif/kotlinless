package com.geekless.kotlia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.frameworks.common.formatDate
import com.geekless.kotlianappless.frameworks.common.getNewColor
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.note.INoteModel
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import java.util.*

class NoteViewModel(val noteModel: INoteModel) : ViewModel() {
    private val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
    private val noteViewState = MutableLiveData<NoteViewState>()
    private var changedNote: Note? = null

    fun loadNote(noteId: String?) {
        noteViewState.value = NoteViewState()
        if (noteId != null) {
            noteModel.loadNote(noteId).subscribe { noteResult ->
                noteResult.let {
                    when (noteResult) {
                        is NoteResult.Success<*> -> {
                            var n = noteResult.data as? Note
                            val newNoteViewSate = NoteViewState(note = n)
                            newNoteViewSate.toolbarTitle = n?.lastChanged?.formatDate(DATE_TIME_FORMAT)
                                    ?: ""
                            n?.let { changedNote = n }
                            noteViewState.value = newNoteViewSate
                        }
                        is NoteResult.Error -> noteViewState.value = NoteViewState(error = noteResult.error)
                    }
                }
            }
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
                ?: Note(UUID.randomUUID().toString(), title = newTitle, text = newText, lastChanged = Date(), color = Note().color.getNewColor())
    }

    fun viewNote(): LiveData<NoteViewState> = noteViewState

    override fun onCleared() {
        saveNote()
    }

    fun newNote() {
        changedNote = null
        val newNoteViewSate = NoteViewState()
        newNoteViewSate.toolbarTitle = "note_new"
        noteViewState.value = newNoteViewSate
    }

    fun pause() {
        saveNote()
    }

    fun saveNote() {
        changedNote?.let {
            noteModel.saveNote(changedNote!!).subscribe(
                    {
                        changedNote = null
                        noteViewState.value = NoteViewState(saveOk = true)
                    },
                    { error ->
                        noteViewState.value = NoteViewState(saveErr = error)
                    });
        }
    }

    fun deleteNote() {
        changedNote?.let {
            noteModel.deleteNote(it).subscribe(
                    {
                        changedNote = null
                        noteViewState.value = NoteViewState(delOk = true)
                    },
                    { error ->
                        noteViewState.value = NoteViewState(delError = error)
                    });
        }
    }

    fun setColor(color: Note.Color) {
        changedNote = changedNote?.copy(color = color)
    }
}