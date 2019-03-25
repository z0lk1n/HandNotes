package online.z0lk1n.android.handnotes.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.common.getColorResId
import online.z0lk1n.android.handnotes.data.entity.Note

class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NotesRVAdapter.ViewHolder, position: Int) =
        holder.bind(notes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.tv_title)
        private val textTextView = itemView.findViewById<TextView>(R.id.tv_text)

        fun bind(note: Note) = with(note) {
            titleTextView.text = note.title
            textTextView.text = note.text

            itemView.setBackgroundColor(note.color.getColorResId(itemView.context))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}