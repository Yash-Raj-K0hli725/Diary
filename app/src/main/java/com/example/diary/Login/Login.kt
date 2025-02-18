package com.example.diary.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        bind.main.visibility = View.VISIBLE
        bind.main.animate().alpha(1f).setStartDelay(600L).setDuration(600L).start()
        bind.welcomeSplash.animate().alpha(0f).setStartDelay(500L).setDuration(500L).withEndAction {
            bind.welcomeSplash.visibility = View.GONE
        }.start()

        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.confirmBtn.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                PassCheck()
            }
        }

    }

//    suspend fun RegisterOrLogin() {
//
//        //Animations
//        val slideLeft = android.view.animation.AnimationUtils.loadAnimation(
//            requireContext(),
//            R.anim.slide_left
//        )
//        slideLeft.interpolator = DecelerateInterpolator(1.8f)
//
//
//        val slideRight = android.view.animation.AnimationUtils.loadAnimation(
//            requireContext(),
//            R.anim.slide_right
//        )
//        slideRight.interpolator = DecelerateInterpolator(1.8f)
//
//            val haveAccount = sharedVM.database.EDBDao().checkLogin()
//
//            if (haveAccount == 0) {
//                bind.skipBtn.setOnClickListener {
//                    CoroutineScope(Dispatchers.IO).launch{
//                        sharedVM.skipBtn(true)
//                    }
//                    view?.findNavController()?.navigate(R.id.action_login_to_mainFrameList)
//                }
//
//                bind.loginPanel.visibility = View.GONE
//                bind.registerPanel.visibility = View.VISIBLE
//                bind.registerPanel.startAnimation(slideLeft)
//
//                bind.Rbutton.setOnClickListener {
//                    if (checkRegister()) {
//                        val diaryName = bind.diaryName.text.toString()
//                        val password = bind.rPass.text.toString()
//                        val loginInfo = LoginData(password, diaryName, 102)
//                        CoroutineScope(Dispatchers.IO).launch {
//                            sharedVM.database.EDBDao().insertLoginInfo(loginInfo)
//                        }
//                        bind.registerPanel.visibility = View.GONE
//                        bind.loginPanel.startAnimation(slideRight)
//                        bind.loginPanel.visibility = View.VISIBLE
//                    }
//                }
//            } else {
//                bind.loginPanel.visibility = View.VISIBLE
//                bind.loginPanel.startAnimation(slideRight)
//                }
//            bind.Lbutton.setOnClickListener{
//                CoroutineScope(Dispatchers.Main).launch {
//                    PassCheck()
//                }
//            }
//    }
//
    suspend fun PassCheck() {

        var flag = false

        val checkFlag = CoroutineScope(Dispatchers.IO).launch {
           flag = sharedVM.database.EDBDao().fetchPassword().Pass == bind.password.text.toString()
        }
        checkFlag.join()
        //
        if (flag) {
            requireView().findNavController()
                .navigate(R.id.action_login_to_mainFrameList)

        }
        else if (bind.password.text!!.isEmpty()) {
            Snackbar.make(
                requireView(),
                "Password field cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        else {
            Snackbar.make(
                requireView(),
                "Incorrect Password",
                Snackbar.LENGTH_SHORT)
                .show()
        }
    }

}