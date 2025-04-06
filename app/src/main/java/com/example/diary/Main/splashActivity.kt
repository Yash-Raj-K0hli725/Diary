package com.example.diary.Main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.diary.R
import com.example.diary.databinding.CustomlayoutActivityBinding

@SuppressLint("CustomSplashScreen")
class customSplashActivity:AppCompatActivity() {
    private lateinit var bind:CustomlayoutActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = DataBindingUtil.setContentView(this,R.layout.customlayout_activity)
        val dayFadeOut = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.splash_day)
        bind.splashDay.startAnimation(dayFadeOut)
        Handler(Looper.getMainLooper()).postDelayed({
            changeSplashText()
        },1300)

        Handler(Looper.getMainLooper()).postDelayed({
            splashAnim() },3000)
    }
    fun splashAnim(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade,R.anim.fade_out)
        finish()
    }
    private fun changeSplashText(){
        val diaryFadeIn = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.splash_diary)
        bind.splashDiary.visibility = View.VISIBLE
        bind.splashDiary.startAnimation(diaryFadeIn)
    }
}