package com.example.calendarmobileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendarmobileapp.R
import com.example.calendarmobileapp.adapters.NoteAdapter
import com.example.calendarmobileapp.data.entities.Note
import com.example.calendarmobileapp.databinding.FragmentBlankBinding
import com.example.calendarmobileapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BlankFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding:FragmentBlankBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dodajNotatke.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_addNote)
        }

        val adapter = NoteAdapter(
            notes = emptyList(),
            onNoteClick = { note: Note ->
                mainVm.setNote(note)
                findNavController().navigate(R.id.action_blankFragment_to_noteFragment)
            }
        )

        binding.SpisNotatek.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.SpisNotatek.adapter = adapter

        // Fetch notes in a separate thread using coroutines
        viewLifecycleOwner.lifecycleScope.launch {
            val notes = withContext(Dispatchers.IO) {
                mainVm.getNotes()
            }
            adapter.updateNotes(notes)
        }
    }
    override fun onResume() {
        super.onResume()
        view?.invalidate()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}