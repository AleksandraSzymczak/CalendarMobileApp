package com.example.calendarmobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarmobileapp.databinding.FragmentSingleNoteStructureBinding
class NoteAdapter(
    private val notes:List<Note>,
    private val onNoteClick: (Note) -> Unit
) :
RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(binding:FragmentSingleNoteStructureBinding) : RecyclerView.ViewHolder(binding.root) {
        val tytul = binding.noteTytul;
        val opis = binding.noteOpis;
        val date = binding.noteDate;

        init{
            binding.root.setOnClickListener{
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
        holder.date.text = notes[position].date
        holder.tytul.text = notes[position].tytul
        holder.opis.text = notes[position].opis
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
