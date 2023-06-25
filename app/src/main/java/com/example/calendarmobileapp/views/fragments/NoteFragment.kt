package com.example.calendarmobileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calendarmobileapp.viewmodel.MainViewModel
import com.example.calendarmobileapp.data.entities.Note
import com.example.calendarmobileapp.databinding.FragmentNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        binding.DeleteButton.setOnClickListener {
            mainVm.getNote()?.let { note ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        mainVm.deleteNote(note)
                        mainVm.loadDataFromDBSpecificDate()
                    }
                    Toast.makeText(requireContext(), "Usunięto notatkę", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }
    private fun bindNoteData(note: Note){
        binding.noteTytul.text = note.tytul
        binding.noteOpis.text = note.opis
        binding.noteDate.text = note.date
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