package com.example.calendarmobileapp.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.calendarmobileapp.data.constant.COL_DATE
import com.example.calendarmobileapp.data.constant.COL_OPIS
import com.example.calendarmobileapp.data.constant.COL_TYTUL
import com.example.calendarmobileapp.data.constant.DATABASE_NAME
import com.example.calendarmobileapp.data.constant.TABLE_NAME
import com.example.calendarmobileapp.data.entities.Note

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    private val appContext = context.applicationContext
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Perform necessary migrations or updates here if needed
    }
    @SuppressLint("Range")
    fun getNotesForDate(date: String): List<Note> {
        val eventsList = mutableListOf<Note>()
        val db = this.readableDatabase
        val selection = "$COL_DATE = ?"
        val selectionArgs = arrayOf(date)
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val eventDate = cursor.getString(cursor.getColumnIndex(COL_DATE))
                val eventTitle = cursor.getString(cursor.getColumnIndex(COL_TYTUL))
                val eventDescription = cursor.getString(cursor.getColumnIndex(COL_OPIS))

                val note = Note(eventDate, eventTitle, eventDescription)
                eventsList.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return eventsList
    }

    fun insertNote(note: Note) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(COL_DATE, note.date)
            put(COL_TYTUL, note.tytul)
            put(COL_OPIS, note.opis)
        }
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(appContext, "Failed", Toast.LENGTH_LONG).show()
        }
    }
}
