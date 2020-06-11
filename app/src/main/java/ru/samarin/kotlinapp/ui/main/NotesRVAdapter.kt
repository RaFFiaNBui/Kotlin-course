package ru.samarin.kotlinapp.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*
import ru.samarin.kotlinapp.R
import ru.samarin.kotlinapp.data.entity.Note

class NotesRVAdapter : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )


    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(notes[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            textView_title.text = note.title
            textView_text.text = note.text
            setBackgroundColor(note.color)
        }
    }
}