package com.example.diary.Login

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.LoginData
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentRegisterBinding

class Register : Fragment() {
    lateinit var bind:FragmentRegisterBinding
    lateinit var sharedVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = FragmentRegisterBinding.inflate(inflater,container,false )
        bind.apply {
            blurView.setupWith(requireActivity().findViewById(android.R.id.content)).setBlurRadius(5f).setBlurAutoUpdate(true)
        }
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun registerUser(){
//            val name = bind.regInpDiaryName.text.toString().trim()
//            val password = bind.regInpPassword.text.toString().trim()
//            val registerDetails = LoginData(password,name,Math.random().toInt())
//            sharedVM.signUpUser(registerDetails)
//    }
//
//    private fun fieldCheck():Boolean{
//        return bind.regInpPassword.text!!.isNotEmpty() && bind.regInpDiaryName.text!!.isNotEmpty()
//    }

}