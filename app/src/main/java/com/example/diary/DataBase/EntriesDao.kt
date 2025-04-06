package com.example.diary.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntriesDao {

    //notesList-->
    @Insert
    suspend fun InsertData(data: DataCC)

    @Update
    suspend fun UpdateData(data: DataCC)

    @Delete
    suspend fun DeleteData(data: DataCC)

    @Query("SELECT * FROM DataCC")
    fun getDataInfo(): LiveData<List<DataCC>>
    //<--

    //remindersList-->
    @Insert
    suspend fun insertReminders(reminder: DataOO)

    @Update
    suspend fun updateReminders(reminder: DataOO)

    @Delete
    suspend fun delete(reminder: DataOO)

    @Query("SELECT * FROM DataOO")
    fun getReminderInfo(): LiveData<List<DataOO>>
    //<--

    //LoginInfo-->
    @Insert
    suspend fun insertLoginInfo(login: LoginData)

    @Query("DELETE FROM LOGINDATA")
    suspend fun removeLogin()

    @Query("SELECT * FROM LoginData")
    suspend fun fetchPassword(): LoginData

    @Query("SELECT COUNT(*) FROM LoginData")
    suspend fun checkIfUserExist(): Int
    //<--
}