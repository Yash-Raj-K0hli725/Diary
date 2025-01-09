package com.example.diary.DataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.util.Date

@Parcelize
@Entity
data class DataCC (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val Title:String,
    val Text:String,
    val Date:Date
): Parcelable

