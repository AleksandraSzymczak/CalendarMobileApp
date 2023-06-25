package com.example.calendarmobileapp.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendarmobileapp.R
import com.example.calendarmobileapp.adapters.NoteAdapter
import com.example.calendarmobileapp.data.entities.Note
import com.example.calendarmobileapp.databinding.FragmentListOfNotesBinding

val notes = listOf(
    Note("tytul1", "opis1", "20-03-2023"),
    Note("tytul2", "opis2", "21-03-2023"),
    Note("tytul3", "opis3", "22-03-2023"),
    Note("tytul1", "opis1", "20-03-2023"),
    Note("tytul2", "opis2", "21-03-2023"),
    Note("tytul3", "opis3", "22-03-2023"),
    Note("tytul1", "opis1", "30-06-2023"),
    Note("tytul2", "opis2", "25-06-2023"),
    Note("tytul3", "opis3", "26-06-2023")
)
interface Server{
    fun loadDataFromDB():List<Note>
}
class MainViewModel : ViewModel(), Server {
    private var note: Note? = null
    private var date = MutableLiveData<String>()
    private var TempC = MutableLiveData<String>()
    private var ConditionText = MutableLiveData<String>()
    private var ConditionIcon = MutableLiveData<String>()
    override fun loadDataFromDB(): List<Note>{
        val selectedDate = date.value
        val selectedNotes = notes.filter { it.date == selectedDate}
        println(date)
        return selectedNotes
    }
    fun setNote(note: Note){
        this.note = note
    }
    fun getNote() = note
    fun setDate(date: String) {
        this.date.value = date
    }
    fun getDate() = date
}

class WeatherResponse {
}

class ListOfNotesFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentListOfNotesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfNotesBinding.inflate(layoutInflater, container, false)
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
        binding.SpisNotatek.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.SpisNotatek.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createNotes(): List<Note> = buildList{
        for(i in 0..100){
            val newNote = Note("tytul", "opis", "20.03.2023")
            add(newNote)
        }
    }
}