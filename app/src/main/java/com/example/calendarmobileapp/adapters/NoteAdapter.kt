package com.example.calendarmobileapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarmobileapp.data.entities.Note
import com.example.calendarmobileapp.databinding.FragmentSingleNoteStructureBinding
import kotlinx.coroutines.flow.Flow

class NoteAdapter(
    private var notes: List<Note>,
    private val onNoteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
    inner class NoteViewHolder(binding: FragmentSingleNoteStructureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tytul = binding.noteTytul
        val opis = binding.noteOpis
        val date = binding.noteDate

        init {
            binding.root.setOnClickListener {
                onNoteClick(notes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val noteBinding = FragmentSingleNoteStructureBinding.inflate(inflater, parent, false)
        return NoteViewHolder(noteBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.date.text = note.date
        holder.tytul.text = note.tytul
        holder.opis.text = note.opis
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
