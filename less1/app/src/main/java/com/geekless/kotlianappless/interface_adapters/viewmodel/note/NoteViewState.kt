package com.geekless.kotlianappless.interface_adapters.viewmodel.note

import com.geekless.kotlianappless.model.entities.Note

class NoteViewState(val note: Note? = null,
                    val error: Throwable? = null,
                    val delOk:Boolean=false ,
                    val delError:Throwable?=null,
                    val saveOk:Boolean=false,
                    val saveErr:Throwable?=null) {
    val title=note?.title?:""
    val text=note?.text?:""
    var toolbarColor:Int = 0
    var toolbarTitle:String=""
}