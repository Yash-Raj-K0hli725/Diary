package com.example.diary.Login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.LoginData
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Register : Fragment() {
    lateinit var bind:FragmentRegisterBinding
    lateinit var sharedVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        val errorMessage = bind.regErrorMessage
        bind.regBtn.setOnClickListener{
            if(istextfieldsEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    registerUser()
                }
                view?.findNavController()?.navigate(RegisterDirections.actionRegisterToLogin())
            }
            else{
                    errorMessage.animate().alpha(1f).setDuration(400L).withEndAction {
                        errorMessage.alpha = 1f
                    }.start()
                Handler(Looper.getMainLooper()).postDelayed({
                    errorMessage.animate().alpha(0f).setDuration(600L).withEndAction {
                        errorMessage.alpha = 0f
                    }.start()
                },1300L)
            }
        }
        // Inflate the layout for this fragment
        return bind.root
    }

    private suspend fun registerUser(){
            val name = bind.regDiaryName.text.toString()
            val password = bind.regPassword.text.toString()
            val registerDetails = LoginData(password,name,Math.random().toInt())
            sharedVM.database.EDBDao().insertLoginInfo(registerDetails)
    }

    fun istextfieldsEmpty():Boolean{
        return bind.regPassword.text!!.isNotEmpty() && bind.regDiaryName.text!!.isNotEmpty()
    }

}