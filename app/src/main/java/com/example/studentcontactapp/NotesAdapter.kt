package com.example.studentcontactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.databinding.ItemNoteBinding

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class NotesAdapter(private val notes: List<Pair<String, Long>>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val (fileName, fileSize) = notes[position]
        holder.binding.tvFileName.text = fileName
        holder.binding.tvFileSize.text = holder.itemView.context.getString(R.string.status_saved, fileSize)
    }

    override fun getItemCount(): Int = notes.size
}
