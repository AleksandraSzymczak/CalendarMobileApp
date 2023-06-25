package com.example.calendarmobileapp.views.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calendarmobileapp.viewmodel.MainViewModel
import com.example.calendarmobileapp.R
import com.example.calendarmobileapp.databinding.MainCalendarViewBinding
import com.example.calendarmobileapp.remote.ApiClient
import com.example.calendarmobileapp.remote.RetrofitClient
import com.example.calendarmobileapp.remote.WeatherResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale


class StartAppCalendarFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding:MainCalendarViewBinding? = null
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 5000L // 5 seconds
  private val binding get() = _binding!!

    private val updateTemperatureRunnable = object : Runnable {
        override fun run() {
            updateCurrentTemperature()
            handler.postDelayed(this, updateInterval)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateCurrentTemperature()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainCalendarViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mainVm.setDate(formattedDate)
            }
        }
        binding.pickDate.setOnClickListener{
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mainVm.loadDataFromDBSpecificDate()
            }
            findNavController().navigate(R.id.action_startAppCalendarFragment_to_blankFragment)
        }

    }
    override fun onResume() {
        super.onResume()
        //handler.postDelayed(updateTemperatureRunnable, updateInterval)
        updateTemperatureRunnable.run()
        view?.invalidate()
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateTemperatureRunnable)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateCurrentTemperature() {
        val apiClient = RetrofitClient.client?.create(ApiClient::class.java)
        val call = apiClient?.getWeather()
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val output = response.body()
                if (output != null) {
                    Log.d("TAG", output.current.cloud.toString())
                    binding.Temperatura.text = output.current.temp_c.toString()+"°C"
                    binding.Lokalizacja.text = output.location.name
                    binding.CurrTime.text ="last updated " + output.current.last_updated
                    Picasso.get()
                        .load("https:"+output.current.condition.icon)
                        .into(binding.pogodaZdj)
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(context, "Nieudane połączenie z api!", Toast.LENGTH_LONG).show()
            }
        })
    }

}