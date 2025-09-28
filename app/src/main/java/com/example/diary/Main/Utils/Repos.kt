package com.example.diary.Main.Utils

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.diary.DataBase.Table_Diary
import com.example.diary.DataBase.Reminder
import com.example.diary.DataBase.EDataBase
import com.example.diary.DataBase.LoginData

class Repos(context: Context) {

    private val dataBase = EDataBase.getInstance(context).eDao()

    suspend fun createNotes(notes: Table_Diary){
        dataBase.InsertData(notes)
    }

    fun readNotes():LiveData<List<Table_Diary>>{
        return dataBase.getDataInfo()
    }

    suspend fun updateNotes(notes:Table_Diary){
        dataBase.UpdateData(notes)
    }

    suspend fun deleteNotes(notes: Table_Diary){
        dataBase.DeleteData(notes)
    }

    suspend fun signUp(signInDetails:LoginData){
        dataBase.insertLoginInfo(signInDetails)
    }

    suspend fun fetchPassword():LoginData{
        return dataBase.fetchPassword()
    }

    suspend fun removeLogin(){
        dataBase.removeLogin()
    }

    suspend fun checkIfUserExist():Int{
        return dataBase.checkIfUserExist()
    }

    suspend fun insertReminder(reminder:Reminder){
        dataBase.insertReminders(reminder)
    }

    suspend fun updateReminder(reminder:Reminder){
        dataBase.updateReminders(reminder)
    }

    fun readReminder():LiveData<List<Reminder>>{
        return dataBase.getReminderInfo()
    }


}