package com.example.calendarmobileapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calendarmobileapp.data.constant.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Note(
    @PrimaryKey(autoGenerate = true) val nid: Int = 0,
    val tytul:String,
    val opis:String,
    val date:String
    )