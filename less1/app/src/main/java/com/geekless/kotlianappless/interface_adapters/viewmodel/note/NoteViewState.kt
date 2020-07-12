package com.geekless.kotlianappless.interface_adapters.viewmodel.note

import com.geekless.kotlianappless.model.entities.Note

class NoteViewState(var note: Note) {
    val title=note.title
    val text=note.text
    var toolbarColor:Int = 0
    lateinit var toolbarTitle:String
}