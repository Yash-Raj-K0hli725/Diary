package com.example.diary.Main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
        //this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (sharedVM.singleRun) {
            //Toast.makeText(this,"yash",Toast.LENGTH_SHORT).show()
            sharedVM.getData(supportFragmentManager)
        }

        //end
    }


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            val vP = findViewById<ViewPager2>(R.id.MF_VP2)
            if (vP.currentItem != 0) {
                vP.currentItem -= vP.currentItem
            } else {
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }
}