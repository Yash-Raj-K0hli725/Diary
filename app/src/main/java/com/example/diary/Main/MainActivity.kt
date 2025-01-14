package com.example.diary.Main

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.ModelV.MainVMFactory
import com.example.diary.R
import com.example.diary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var database: EdataBase
    lateinit var bind: ActivityMainBinding
    lateinit var sharedVM: MainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
           window.insetsController?.hide(WindowInsets.Type.statusBars())
       }
        //Initialization of ViewModel
        sharedVM = ViewModelProvider(this, MainVMFactory(this))[MainVM::class.java]

        //Initialization of dataBinding
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Creating DataStore
        sharedVM.Settings = createDataStore(name = "Settings")
        //
        //setting Navigation StartDestination_Programmatically
        val navFinder = findNavController(R.id.hoster)
        val navGraph = navFinder.navInflater.inflate(R.navigation.navo)

        CoroutineScope(Dispatchers.Main).launch {
            if(sharedVM.isSkipped()) {
                navGraph.setStartDestination(R.id.mainFrameList)
            }
            navFinder.setGraph(navGraph,null)
        }

        //
        //BackPress_Handing
        onBackPressedDispatcher.addCallback(this,object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (!navFinder.popBackStack()) {
                    val vP = findViewById<ViewPager2>(R.id.MF_VP2)
                    if (vP.currentItem != 0) {
                        vP.currentItem -= vP.currentItem
                    } else {
                        finish()
                    }
                }
            }
        })
        //end
    }
}