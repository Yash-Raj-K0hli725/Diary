package com.example.diary.DataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Time

@Parcelize
@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title:String,
    val Condition:String,
    val Timer: Time
):Parcelable