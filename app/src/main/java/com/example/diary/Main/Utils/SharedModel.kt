package com.example.diary.Main.Utils

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diary.DataBase.Reminder
import com.example.diary.DataBase.Table_Diary
import com.example.diary.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    val userSession: UserSession by lazy {
        UserSession(context)
    }
    val repos = Repos(context)
    var thumbnail: Drawable? = null

    init {
        getThumbnail()
    }

    //<--Notes-->
    fun makeDiaryEntry(note: Table_Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.createNotes(note)
        }
    }
    fun readNotes(): LiveData<List<Table_Diary>> {
        return repos.readNotes()
    }

    fun updateNotes(notes: Table_Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.updateNotes(notes)
        }
    }

    fun deleteNotes(notes: Table_Diary) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.deleteNotes(notes)
        }
    }
    //<--

    //Reminders-->
    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.insertReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            repos.updateReminder(reminder)
        }
    }

    fun readReminder(): LiveData<List<Reminder>> {
        return repos.readReminder()
    }
    //<--

    private fun getThumbnail() {
        val image = listOf(
            R.drawable.thumb1,
            R.drawable.thumb2,
            R.drawable.thumb3,
            R.drawable.thumb4,
            R.drawable.thumb5,
            R.drawable.thumb6
        ).random()
        thumbnail = ContextCompat.getDrawable(context, image)!!
    }


}