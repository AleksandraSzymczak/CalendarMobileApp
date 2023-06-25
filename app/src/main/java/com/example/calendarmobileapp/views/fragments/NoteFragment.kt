package com.example.calendarmobileapp.views.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import java.lang.System.currentTimeMillis

class NoteFragment : Fragment(), SensorEventListener {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding:FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private  var lastShakeTime: Long = 0
    private val cooldownDuration: Long = 1000
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
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
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val acceleration = Math.sqrt((x * x).toDouble())
            val currentTime = System.currentTimeMillis()
            if(acceleration > 8 &&
                currentTime - lastShakeTime >= cooldownDuration) {
                lastShakeTime = currentTime
                mainVm.getNote()?.let { note ->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            mainVm.deleteNote(note)
                            mainVm.loadDataFromDBSpecificDate()
                        }
                        Toast.makeText(requireContext(), "Usunięto notatkę", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sensorManager.unregisterListener(this)
    }

}