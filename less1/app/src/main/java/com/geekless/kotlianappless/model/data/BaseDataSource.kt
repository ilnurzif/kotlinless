package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import java.util.*

object BaseDataSource/*:IDataSource*/ {
   /* private lateinit var currentNote:Note
    private val notes: MutableList<Note> = mutableListOf(
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.WHITE),
            Note(id = UUID.randomUUID().toString(),
                    title = "Вторая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.YELLOW),
            Note(id = UUID.randomUUID().toString(),
                    title = "Третья заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.GREEN),
            Note(id = UUID.randomUUID().toString(),
                    title = "Четвертая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.BLUE),
            Note(id = UUID.randomUUID().toString(),
                    title = "Пятая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.RED),
            Note(id = UUID.randomUUID().toString(),
                    title = "Шестая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.VIOLET),
            Note(id = UUID.randomUUID().toString(),
                    title = "Седьмая заметка",
                    text = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.PINK)
    )

    var noteListBehaviorSubject = BehaviorSubject.create<List<Note>>()
    var currentNoteBehaviorSubject = BehaviorSubject.create<Note>()

    override fun getData(): BehaviorSubject<List<NoteResult>> {
        noteListBehaviorSubject.onNext(notes)
        return noteListBehaviorSubject
    }

    override fun getCurrentNodeBehaviorSubject(): BehaviorSubject<Note> {
        currentNoteBehaviorSubject.onNext(currentNote)
        return currentNoteBehaviorSubject
    }

    override fun saveNote(note: Note){
        addOrReplace(note)
    }

    override fun setDefaultNote(note: Note) {
        currentNote=note
    }

    fun addOrReplace(note: Note){
        for(i in 0 until notes.size){
            if(notes.get(i) == note){
                notes[i]=note
                noteListBehaviorSubject.onNext(notes)
                return
            }
        }
        notes.add(note)
        noteListBehaviorSubject.onNext(notes)
    }*/
}