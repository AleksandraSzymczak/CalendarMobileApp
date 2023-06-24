package com.example.calendarmobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendarmobileapp.databinding.MainCalendarViewBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainCalendarViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainCalendarViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pickDate.setOnClickListener{ViewNotes()}
    }
    fun ViewNotes() {
        setContentView(R.layout.fragment_all_notes)
    }
}