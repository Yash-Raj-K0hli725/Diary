package com.example.diary.Main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.animation.content.Content
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.ModelV.MainVMFactory
import com.example.diary.R
import com.example.diary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var database: EdataBase
    lateinit var bind: ActivityMainBinding
    lateinit var sharedVM: MainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedVM = ViewModelProvider(this, MainVMFactory(this))[MainVM::class.java]
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        database = EdataBase.getData(this)
         val navFinder = findNavController(R.id.hoster)
        //this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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