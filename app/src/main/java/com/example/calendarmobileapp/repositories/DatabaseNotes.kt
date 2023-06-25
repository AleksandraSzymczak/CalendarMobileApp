package com.example.calendarmobileapp.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calendarmobileapp.data.NoteDao
import com.example.calendarmobileapp.data.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class DatabaseNotes : RoomDatabase(){
    abstract fun noteDao(): NoteDao
}
object NoteDb{
    private var db: DatabaseNotes? = null

    fun getInstance(context: Context): DatabaseNotes {
        if(db == null){
            db = Room.databaseBuilder(
                context,
                DatabaseNotes::class.java,
                "note-database"
            ).build()
        }
        return db!!
    }
}

