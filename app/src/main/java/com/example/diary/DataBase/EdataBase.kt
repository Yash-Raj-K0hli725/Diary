package com.example.diary.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DataCC::class,DataOO::class,LoginData::class], version = 1)
@TypeConverters(Converters::class)
abstract class EdataBase : RoomDatabase() {
    abstract fun EDBDao(): EntriesDao

    companion object {
        @Volatile
        private var Instance: EdataBase? = null

        fun getData(context: Context): EdataBase {

            if (Instance == null) {
                synchronized(this)
                {
                    if (Instance == null) {
                        Instance = Room.databaseBuilder(
                            context.applicationContext, EdataBase::class.java, "prePOP"
                        ).createFromAsset("prePopulated.db").build()
                    }
                }
            }
            return Instance!!
        }

    }
}