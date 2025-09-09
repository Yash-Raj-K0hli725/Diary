package com.example.diary.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Table_Diary::class,Reminder::class,LoginData::class], version = 1)
@TypeConverters(Converters::class)
abstract class EDataBase : RoomDatabase() {
    abstract fun eDao(): EntriesDao

    companion object {
        @Volatile
        private var Instance: EDataBase? = null

        fun getInstance(context: Context): EDataBase {
            if (Instance == null) {
                synchronized(this)
                {
                    if (Instance == null) {
                        Instance = Room.databaseBuilder(
                            context.applicationContext, EDataBase::class.java, "mDatabase.db"
                        ).build()
                    }
                }
            }
            return Instance!!
        }

    }
}