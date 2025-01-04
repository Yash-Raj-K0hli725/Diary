package com.example.diary.DataBase

import androidx.room.TypeConverter
import java.sql.Time
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDateToLong(value: Date):Long{
        return value.time
    }
    @TypeConverter
    fun fromLongToDate(value:Long): Date {
        return Date(value)
    }
    @TypeConverter
    fun fromTimetoLong(value: Time):Long{
        return value.time
    }
    @TypeConverter
    fun fromTimetoLong(value: Long):Time{
        return Time(value)
    }
}