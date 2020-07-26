package com.geekless.kotlianappless.model.interactors.utility

import com.geekless.kotlianappless.model.entities.Note

interface IUtility {
    fun NoteColorToRes(color: Note.Color):Int
 //   fun getColor():Note.Color
    fun getStringResource(res:String):String
}