package com.example.diary.Login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diary.DataBase.LoginData
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentSetPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class SetPassword : Fragment() {
    lateinit var bind:FragmentSetPasswordBinding
    lateinit var sharedVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_set_password,container,false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]

        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.Rbutton.setOnClickListener {
            if (checkRegister()) {
                val diaryName = bind.diaryName.text.toString()
                val password = bind.rPass.text.toString()
                val loginInfo = LoginData(password, diaryName,102)
                CoroutineScope(Dispatchers.IO).launch {
                    sharedVM.database.EDBDao().insertLoginInfo(loginInfo)
                    sharedVM.skipBtn(false)
                }
                view.findNavController().popBackStack()
            }
        }
    }

    fun checkRegister(): Boolean {
        return bind.rPass.text.isNotEmpty() && bind.diaryName.text.isNotEmpty()
    }

}