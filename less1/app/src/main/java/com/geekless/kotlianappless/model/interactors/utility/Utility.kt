package com.geekless.kotlianappless.model.interactors.utility

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.model.entities.Note

class Utility(val context: Context):IUtility {
    override fun NoteColorToRes(color: Note.Color):Int {
             val color = when (color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
                else -> R.color.white
            }
        return ResourcesCompat.getColor(context.resources, color, null)
    }
}