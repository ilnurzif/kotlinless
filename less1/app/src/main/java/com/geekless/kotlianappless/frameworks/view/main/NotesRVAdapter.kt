package com.geekless.kotlianappless.frameworks.view.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekless.kotlianappless.R
import com.geekless.kotlianappless.frameworks.common.getColorInt
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.utility.IUtility
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null, val utility: IUtility) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.item_note,
                    parent,
                    false
            )
    )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text
            setBackgroundColor(note.color.getColorInt(context)/*   utility.NoteColorToRes(note.color)*/)
            context
            setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}