package com.geekless.kotlia


import androidx.lifecycle.ViewModel
import com.geekless.kotlianappless.frameworks.common.getNewColor
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.note.INoteModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*
import kotlin.coroutines.CoroutineContext

class NoteViewModel(val noteModel: INoteModel) : ViewModel(), CoroutineScope {
    private val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
    private var changedNote: Note? = null

    private val pendingNote: Note?
        get() = getViewState().poll()?.note

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }

    private val viewStateChannel = BroadcastChannel<NoteViewState>(Channel.CONFLATED)
    private val errorChannel = Channel<Throwable>()

    fun getViewState(): ReceiveChannel<NoteViewState> = viewStateChannel.openSubscription()
    fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel

     fun setError(e: Throwable) = launch {
        errorChannel.send(e)
    }

     fun setData(data: NoteViewState){
        launch {
            viewStateChannel.send(data)
        }
    }

    fun loadNote(noteId: String) = launch {
        try {
            noteModel.loadNote(noteId).let {
                setData(NoteViewState(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
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

    override fun onCleared() {
        launch {
            pendingNote?.let {
                noteModel.saveNote(it)
            }
        }
        viewStateChannel.close()
        errorChannel.close()
        coroutineContext.cancel()
        super.onCleared()
        saveNote()
    }

    suspend fun newNote() {
        changedNote = null
        val newNoteViewSate = NoteViewState()
        newNoteViewSate.toolbarTitle = "note_new"
        viewStateChannel.send(newNoteViewSate)
    }

    fun pause() {
        saveNote()
    }

    fun saveNote() {
        launch {
            viewStateChannel.send(NoteViewState(note = changedNote))
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let {
                noteModel.deleteNote(it)
                setData(NoteViewState(delOk = true))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun setColor(color: Note.Color) {
        changedNote = changedNote?.copy(color = color)
    }
}