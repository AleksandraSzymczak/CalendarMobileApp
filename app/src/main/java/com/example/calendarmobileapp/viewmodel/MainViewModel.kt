package com.example.calendarmobileapp.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calendarmobileapp.data.entities.Note
import kotlinx.coroutines.launch
import com.example.calendarmobileapp.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface Server {
    fun loadDataFromDB(): List<Note>
    fun loadDataFromDBSpecificDate()
    suspend fun insertNote(note:Note)
}

class MainViewModel(app: Application) : AndroidViewModel(app), Server {
    private val repo = Repository(app.applicationContext)
    private var notes: List<Note> = listOf()
    private var note: Note? = null
    private var date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    init {
        viewModelScope.launch {
            val note = Note(tytul = "EGZAMINBEDZIE?", opis = "...juz we WTOREK", date =  "27-06-2023")
            insertNote(note)
        }
    }

    override fun loadDataFromDB(): List<Note> {
        return repo.getAll()
    }
    override fun loadDataFromDBSpecificDate(){
        notes = repo.getNotesByDate(date)
    }
    override suspend fun insertNote(note:Note){
        repo.insert(note)
    }
    fun getDataFromDate(): List<Note>{
        return notes.filter { note-> note.date == date }
    }
    fun setNote(note: Note){this.note=note}
    fun getNote(): Note? = note
    fun setDate(date: String){this.date=date}
    fun getDate(): String? = date
    fun getNotes(): List<Note> = notes
}
