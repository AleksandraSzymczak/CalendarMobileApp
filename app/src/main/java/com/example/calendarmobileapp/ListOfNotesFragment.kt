package com.example.calendarmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendarmobileapp.databinding.FragmentAllNotesBinding


class ListOfNotesFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding:FragmentAllNotesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dodajNotatke.setOnClickListener{
            findNavController().navigate(R.id.action_listOfNotesFragment_to_noteFragment)
        }
        val adapter = NoteAdapter(
            notes = mainVm.loadDataFromDB(),
            onNoteClick = { note: Note ->
                mainVm.setNote(note)
                findNavController().navigate(R.id.action_listOfNotesFragment_to_noteFragment)
            }
        )
        binding.SpisNotatek.layoutManager = GridLayoutManager(requireContext(),1)
        binding.SpisNotatek.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createNotes(): List<Note> = buildList{
        for(i in 0..100){
            val newNote = Note("tytul","opis","20.03.2023")
            add(newNote)
        }
    }
}