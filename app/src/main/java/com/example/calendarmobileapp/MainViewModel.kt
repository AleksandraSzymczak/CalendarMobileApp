package com.example.calendarmobileapp

import androidx.lifecycle.ViewModel

interface Server{
    fun loadDataFromDB():List<Note>
}
class MainViewModel : ViewModel(), Server{
    private var note: Note? = null
    override fun loadDataFromDB(): List<Note> = buildList{
        for(i in 0..100){
            val newNote = Note("tytul","opis","20.03.2023")
            add(newNote)
        }
    }
    fun setNote(note:Note){
        this.note = note
    }
    fun getNote() = note
}