package com.example.diary.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.diary.DataBase.LoginData
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentSetPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetPassword : Fragment() {
    lateinit var bind: FragmentSetPasswordBinding
     private val sharedVM: SharedModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_set_password, container, false)
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.Rbutton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                if (checkRegister()) {
//                    registerUser()
                }
            }
        }

    }

//    suspend fun registerUser() {
//        val diaryName = bind.diaryName.text.toString()
//        val password = bind.rPass.text.toString()
//        val loginInfo = LoginData(password, diaryName, 102)
//        val registerUser = CoroutineScope(Dispatchers.IO).launch {
//            sharedVM.signUpUser(loginInfo)
//            sharedVM.skipBtn(false)
//        }
//        registerUser.join()
//        view?.findNavController()?.popBackStack()
//
//    }

    fun checkRegister(): Boolean {
        return bind.rPass.text!!.isNotEmpty() && bind.diaryName.text!!.isNotEmpty()
    }

}