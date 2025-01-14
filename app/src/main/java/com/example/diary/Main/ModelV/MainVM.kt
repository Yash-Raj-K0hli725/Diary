package com.example.diary.Main.ModelV

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.ViewModel
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import kotlinx.coroutines.flow.first


class MainVM(context: Context) : ViewModel() {
    //DataBase
    val database: EdataBase = EdataBase.getData(context)
    var read = database.EDBDao().getDataInfo()

    //DataStore
    lateinit var Settings: DataStore<androidx.datastore.preferences.core.Preferences>

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