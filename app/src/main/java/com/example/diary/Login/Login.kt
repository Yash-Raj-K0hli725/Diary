package com.example.diary.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentLoginBinding

class Login : Fragment() {
    lateinit var bind:FragmentLoginBinding
    lateinit var mainModel:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        mainModel = ViewModelProvider(requireActivity())[MainVM::class.java]

        bind.Lbutton.setOnClickListener {
            if(check()){
                view?.findNavController()?.navigate(R.id.action_login_to_mainFrameList)
            }

        }

        // Inflate the layout for this fragment
        return bind.root
    }
    fun check():Boolean{
        return true
    }
}