package com.example.diary.Login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.DataBase.LoginData
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Login : Fragment() {
    lateinit var bind: FragmentLoginBinding
    lateinit var sharedVM: MainVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            if(!sharedVM.isSkipped()){
                RegisterOrLogin()
            }
            else{
                view?.findNavController()?.navigate(R.id.action_login_to_mainFrameList)
            }
        }
        // Inflate the layout for this fragment
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //checks entry
    }

    fun RegisterOrLogin() {

        //Animatons
        val slideLeft = android.view.animation.AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_left
        )

        val slideRight = android.view.animation.AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_right
        )


        CoroutineScope(Dispatchers.Main).launch {

            val haveAccount = sharedVM.database.EDBDao().checkLogin()
            Log.d("Yash","$haveAccount")

            if (haveAccount == 0) {

                bind.skipBtn.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch{
                        sharedVM.skipBtn(true)
                    }
                    view?.findNavController()?.navigate(R.id.action_login_to_mainFrameList)
                }

                bind.loginPanel.visibility = View.GONE
                bind.registerPanel.visibility = View.VISIBLE
                bind.registerPanel.startAnimation(slideLeft)


                bind.Rbutton.setOnClickListener {
                    if (checkRegister()) {
                        val diaryName = bind.diaryName.text.toString()
                        val password = bind.rPass.text.toString()
                        val loginInfo = LoginData(password, diaryName, 2013)
                        CoroutineScope(Dispatchers.IO).launch {
                            sharedVM.database.EDBDao().insertLoginInfo(loginInfo)
                        }
                        bind.registerPanel.visibility = View.GONE
                        bind.loginPanel.startAnimation(slideRight)
                        bind.loginPanel.visibility = View.VISIBLE
                    }
                }
                bind.Lbutton.setOnClickListener{
                    CoroutineScope(Dispatchers.Main).launch {
                        PassCheck()
                    }
                }
            } else {
                bind.loginPanel.visibility = View.VISIBLE
                bind.loginPanel.startAnimation(slideRight)
                bind.Lbutton.setOnClickListener{
                    CoroutineScope(Dispatchers.Main).launch {
                        PassCheck()
                    }
                }
            }
        }

    }

    suspend fun PassCheck() {

        val idk: Boolean = sharedVM.database.EDBDao()
            .fetchPassword().Pass == bind.password.text.toString()
        //
        Log.d("Yash","Pressed")

        if (idk) {
            requireView().findNavController()
                .navigate(R.id.action_login_to_mainFrameList)

        }

        else if (bind.password.text.isEmpty()) {
            Snackbar.make(
                requireView(),
                "Password field cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        else {
            Snackbar.make(requireView(), "Incorrect Password", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    fun checkRegister(): Boolean {
        return bind.rPass.text.isNotEmpty() && bind.diaryName.text.isNotEmpty()
    }
}