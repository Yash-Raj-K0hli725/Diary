package com.example.diary.Main.ModelV

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.MainFrameList
import com.example.diary.R


class MainVM(context: Context) : ViewModel() {
    val database: EdataBase = EdataBase.getData(context)
    var read = database.EDBDao().getDataInfo()

    lateinit var SFM: FragmentManager
    var singleRun = true

    //UpdateFragment
    val CurrentItemad = MutableLiveData<DataCC>()
    var updatespermission: Boolean = true

    //AddinFragment
    var addinItem: DataCC? = null
    var addinPermission: Boolean = false

    fun getData(sfm: FragmentManager) {
        this.SFM = sfm
        if (singleRun == true) {
            SFM.beginTransaction()
                .replace(R.id.MFrameHolder, MainFrameList())
                .addToBackStack(null)
                .commit()
            singleRun = false
        }
    }
}