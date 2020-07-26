package com.geekless.kotlianappless.frameworks.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.model.entities.Note

fun Note.Color.getColorInt(context: Context): Int = ContextCompat.getColor(
        context, getColorRes()
)

fun Note.Color.getColorRes(): Int = when (this) {
        Note.Color.WHITE -> R.color.white
        Note.Color.YELLOW -> R.color.yellow
        Note.Color.GREEN -> R.color.green
        Note.Color.BLUE -> R.color.blue
        Note.Color.RED -> R.color.red
        Note.Color.VIOLET -> R.color.violet
        else -> R.color.white
}

fun Note.Color.getNewColor(): Note.Color {
        val colorArr=Note.Color.values()
        val size= Note.noteCount
        val newColor=colorArr[size % colorArr.size]
        return newColor
}