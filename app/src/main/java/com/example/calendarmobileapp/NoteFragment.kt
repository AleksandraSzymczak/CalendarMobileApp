package com.example.calendarmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calendarmobileapp.databinding.ActivityMainBinding
import com.example.calendarmobileapp.databinding.FragmentNoteBinding
import com.example.calendarmobileapp.databinding.MainCalendarViewBinding

class NoteFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding:FragmentNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVm.getNote()?.let { bindNoteData(it) }
    }
    private fun bindNoteData(note: Note){
        binding.noteTytul.text = note.tytul
        binding.noteOpis.text = note.opis
        binding.noteDate.text = note.date
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}