package com.example.calendarmobileapp.data

import android.os.NetworkOnMainThreadException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.calendarmobileapp.data.constant.TABLE_NAME
import com.example.calendarmobileapp.data.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertAll(notes : List<Note>)

    @Delete
    suspend fun delete(note : Note)

    @Update
    suspend fun update(note : Note)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAll(): List<Note>

    @Query("DELETE FROM ${TABLE_NAME}")
    suspend fun dropDatabase()

    @Query("SELECT * FROM ${TABLE_NAME} WHERE date = :desiredDate")
    fun getNotesByDate(desiredDate: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)
}