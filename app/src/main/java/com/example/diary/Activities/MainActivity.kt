package com.example.diary.Activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navFinder: NavController
    lateinit var bind: ActivityMainBinding
    private val sharedVM: SharedModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(bind.main) { v, inset ->
            val navBar = inset.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            bind.main.updatePadding(bottom = navBar)
            inset
        }
        navFinder = findNavController(R.id.hoster)

        checkNavGraph()

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

    private fun checkNavGraph() {
        //setting Navigation StartDestination_Programmatically
        val navGraph = navFinder.navInflater.inflate(R.navigation.navo)
        if (sharedVM.userSession.isLoggedIn()) {
            if (sharedVM.userSession.isPasswordRequired()) {
                navGraph.setStartDestination(R.id.login)
            } else {
                navGraph.setStartDestination(R.id.home)
            }
        }
        navFinder.setGraph(navGraph, null)
    }

}