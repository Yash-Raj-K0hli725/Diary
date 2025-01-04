package com.example.diary.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginData(
    val Pass:String,
    val SQuestion:String,
    @PrimaryKey(autoGenerate = false)
    val Idk:Int
)