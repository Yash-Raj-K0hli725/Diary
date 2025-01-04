package com.example.diary.DataBase

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntriesDao {
    @Insert
    suspend fun InsertData(data:DataCC)

    @Insert
    suspend fun insertReminders(reminder:DataOO)

    @Insert
    suspend fun insertLoginInfo(login:LoginData)

    @Delete
    suspend fun DeleteData(data:DataCC)

    @Delete
    suspend fun delete(reminder:DataOO)

    @Update
    suspend fun UpdateData(data:DataCC)

    @Update
    suspend fun updateReminders(reminder:DataOO)

    @Update
    suspend fun updateLogin(login:LoginData)

    @Query("SELECT * FROM DataCC")
    fun getDataInfo():LiveData<List<DataCC>>

    @Query("SELECT * FROM DataOO")
    fun getReminderInfo():LiveData<List<DataOO>>

    @Query("SELECT * FROM LoginData")
    fun getLoginInfo():LiveData<LoginData>
}