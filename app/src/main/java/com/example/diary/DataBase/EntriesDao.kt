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
    suspend fun InsertData(data: Table_Diary)

    @Update
    suspend fun UpdateData(data: Table_Diary)

    @Delete
    suspend fun DeleteData(data: Table_Diary)

    @Query("SELECT * FROM Table_Diary")
    fun getDataInfo(): LiveData<List<Table_Diary>>
    //<--

    //remindersList-->
    @Insert
    suspend fun insertReminders(reminder: Reminder)

    @Update
    suspend fun updateReminders(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM Reminder")
    fun getReminderInfo(): LiveData<List<Reminder>>
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