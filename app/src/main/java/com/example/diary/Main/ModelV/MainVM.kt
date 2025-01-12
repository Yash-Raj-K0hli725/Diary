package com.example.diary.Main.ModelV

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.MainFrameList
import com.example.diary.R
import kotlinx.coroutines.flow.first
import java.util.prefs.Preferences


class MainVM(context: Context) : ViewModel() {
    //DataBase
    val database: EdataBase = EdataBase.getData(context)
    var read = database.EDBDao().getDataInfo()

    //DataStore
    lateinit var Settings: DataStore<androidx.datastore.preferences.core.Preferences>
    //UpdateFragment
    //var updatespermission: Boolean = true

    //AddinFragment
    var addinItem: DataCC? = null
    var addinPermission: Boolean = false

    suspend fun skipBtn(value:Boolean){
        val isEnable = preferencesKey<Boolean>("skip")
            Settings.edit {
                it[isEnable] = value
            }
    }

    suspend fun isSkipped():Boolean{
        val dataKey = preferencesKey<Boolean>("skip")
        val preference = Settings.data.first()
        return preference[dataKey]?:false
    }

}