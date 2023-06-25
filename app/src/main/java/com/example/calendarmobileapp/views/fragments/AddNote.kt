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
import com.example.calendarmobileapp.R
import com.example.calendarmobileapp.data.entities.Note
import com.example.calendarmobileapp.databinding.FragmentAddNoteBinding
import com.example.calendarmobileapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddNote : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SaveButton.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        mainVm.insertNoteCreated(
                            Note(
                                opis = binding.editTextDescription.text.toString(),
                                tytul = binding.editTextTitle.text.toString(),
                                date = mainVm.getDate().toString()
                            )
                        )
                        mainVm.loadDataFromDBSpecificDate()
                    }
                    Toast.makeText(requireContext(), "Dodano notatkę", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
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