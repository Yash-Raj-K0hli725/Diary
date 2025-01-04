package com.example.diary.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity
data class DataOO(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title:String,
    val Condition:String,
    val Timer: Time
)