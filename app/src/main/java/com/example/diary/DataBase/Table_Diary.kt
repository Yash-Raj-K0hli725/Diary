package com.example.diary.DataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Table_Diary(
    val title: String,
    val text: String,
    val date: Date,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

