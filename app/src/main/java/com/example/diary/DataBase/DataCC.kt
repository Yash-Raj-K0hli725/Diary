package com.example.diary.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity
data class DataCC (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val Title:String,
    val Text:String,
    val Date:Date
)

