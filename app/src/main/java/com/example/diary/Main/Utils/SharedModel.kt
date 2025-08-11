package com.example.diary.Main.Utils

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.DataOO
import com.example.diary.DataBase.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SharedModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val userSession: UserSession by lazy {
        UserSession(context)
    }
    val repos = Repos(context)
    lateinit var Settings: DataStore<Preferences>

    //<--LiveData-->
    val liveSkip = MutableLiveData<Boolean>()
    //<---->

    //<--DataStore-->
    suspend fun skipBtn(value: Boolean) {
        val isSkip = preferencesKey<Boolean>("skip")
        Settings.edit {
            it[isSkip] = value
        }
    }

    suspend fun isSkipped(): Boolean {
        val dataKey = preferencesKey<Boolean>("skip")
        val preference = Settings.data.first()
        liveSkip.value = preference[dataKey] ?: false
        return preference[dataKey] ?: false
    }

    //<--Notes-->
    fun createNotes(notes: DataCC) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.createNotes(notes)
        }
    }

    fun readNotes(): LiveData<List<DataCC>> {
        return repos.readNotes()
    }

    fun updateNotes(notes: DataCC) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.updateNotes(notes)
        }
    }

    fun deleteNotes(notes: DataCC) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.deleteNotes(notes)
        }
    }
    //<--

    //Reminders-->
    fun insertReminder(reminder: DataOO) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.insertReminder(reminder)
        }
    }

    fun updateReminder(reminder: DataOO) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.updateReminder(reminder)
        }
    }

    fun readReminder(): LiveData<List<DataOO>> {
        return repos.readReminder()
    }
    //<--

    //LoginInfo-->

    fun signUpUser(signUpDetails: LoginData) {
        viewModelScope.launch {
            repos.signUp(signUpDetails)
        }
    }

    fun fetchPassword(): String {
        return userSession.getUserPassword()
    }

    fun removeLoginInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            repos.removeLogin()
        }
    }

    fun checkIfUserExist(): Boolean {
        return userSession.getUserPassword().isNotEmpty()
    }
    //<--


}