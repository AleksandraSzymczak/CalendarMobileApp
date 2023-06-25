package com.example.calendarmobileapp.repositories

import android.content.Context
import com.example.calendarmobileapp.data.NoteDao
import com.example.calendarmobileapp.data.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(context: Context):NoteDao {

    private val dao = NoteDb.getInstance(context).noteDao()
    override suspend fun insertAll(notes: List<Note>)  = withContext(Dispatchers.IO)
    {
        dao.insertAll(notes)
    }

    override suspend fun delete(note: Note) = withContext(Dispatchers.IO)
    {
        dao.delete(note)
    }

    override suspend fun update(note: Note) = withContext(Dispatchers.IO)
    {
        dao.update(note)
    }

    override fun getAll(): List<Note> {
        return dao.getAll()
    }

    override suspend fun dropDatabase() = withContext(Dispatchers.IO)
    {
        dao.dropDatabase()
    }
    override fun getNotesByDate(data: String): List<Note>{
        return dao.getNotesByDate(data)
    }
    override suspend fun insert(note: Note){
        dao.insert(note)
    }
}