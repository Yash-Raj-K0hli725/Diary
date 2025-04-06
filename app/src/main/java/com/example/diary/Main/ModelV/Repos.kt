package com.example.diary.Main.ModelV

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.DataOO
import com.example.diary.DataBase.EdataBase
import com.example.diary.DataBase.LoginData

class Repos(context: Context) {

    private val dataBase = EdataBase.getData(context).EDBDao()

    suspend fun createNotes(notes: DataCC){
        dataBase.InsertData(notes)
    }

    fun readNotes():LiveData<List<DataCC>>{
        return dataBase.getDataInfo()
    }

    suspend fun updateNotes(notes:DataCC){
        dataBase.UpdateData(notes)
    }

    suspend fun deleteNotes(notes: DataCC){
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

    suspend fun insertReminder(reminder:DataOO){
        dataBase.insertReminders(reminder)
    }

    suspend fun updateReminder(reminder:DataOO){
        dataBase.updateReminders(reminder)
    }

    fun readReminder():LiveData<List<DataOO>>{
        return dataBase.getReminderInfo()
    }


}