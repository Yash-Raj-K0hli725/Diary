package com.example.diary.Activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.ModelV.MainVMFactory
import com.example.diary.R
import com.example.diary.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var navFinder: NavController
    lateinit var bind: ActivityMainBinding
    private lateinit var sharedVM: MainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Initialization of dataBinding
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(bind.main){v,inset->
            val navBar = inset.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            bind.main.updatePadding(bottom = navBar)
            inset
        }
        //Initialization of ViewModel
        sharedVM = ViewModelProvider(this, MainVMFactory(this))[MainVM::class.java]
        sharedVM.Settings = createDataStore(name = "Settings")
        //setting Navigation StartDestination_Programmatically
        navFinder = findNavController(R.id.hoster)
        //Creating DataStore

        lifecycleScope.launch {
            checkNavGraph()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }

        //BackPress_Handing
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navFinder.popBackStack()) {
                        finish()
                }
            }
        })
        //end
    }

    suspend fun checkNavGraph() {
        val navGraph = navFinder.navInflater.inflate(R.navigation.navo)
        if (sharedVM.isSkipped()) {
            navGraph.setStartDestination(R.id.mainFrameList)
        } else if (sharedVM.checkIfUserExist()){
            navGraph.setStartDestination(R.id.login)
        } else {
            //nothing as Default
        }
        navFinder.setGraph(navGraph, null)
    }

}