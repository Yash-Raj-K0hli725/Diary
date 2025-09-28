package com.example.diary.Login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.databinding.FragmentLoginBinding

class Login : Fragment() {
    lateinit var bind: FragmentLoginBinding
    private val sharedVM: SharedModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentLoginBinding.inflate(inflater, container, false)
        bind.apply {
            blurView.setupWith(requireActivity().findViewById(android.R.id.content))
                .setBlurRadius(5f).setBlurAutoUpdate(true)
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.apply {
            btnDone.setOnClickListener {
                loginUser()
            }
            inpPassword.apply {
                setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                        loginUser()
                    }
                    true
                }
                onFocusChangeListener = View.OnFocusChangeListener { p0, focus ->
                    if (focus) {
                        bear.progress = 0.5f
                        bear.pauseAnimation()
                    } else {
                        bear.resumeAnimation()
                    }
                }
            }

//            val editText = lay.editText
//            val isPasswordVisible =
//                editText?.transformationMethod !is PasswordTransformationMethod
//            if (isPasswordVisible) {
//                bear.resumeAnimation()
//            } else {
//                bear.progress = 0.5f
//                bear.pauseAnimation()
//            }
            val et = inpPassword
            password.setEndIconOnClickListener {
                if (et.transformationMethod is PasswordTransformationMethod) {
                    bear.resumeAnimation()
                    et.transformationMethod = null
                } else {
                    if (et.hasFocus()) {
                        bear.apply {
                            progress = 0.4f
                            Handler(Looper.getMainLooper()).postDelayed(
                                { bear.pauseAnimation() }, 400
                            )
                        }
                    }
                    // Hide password
                    et.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                // Move cursor to end after transformation
                et.setSelection(et.text?.length ?: 0)
            }
        }
    }

    private fun loginUser() {
        if (bind.inpPassword.text.isNullOrEmpty()) {
            //todo give error message
            Toast.makeText(requireContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val password = bind.inpPassword.text.toString().trim()
        if (sharedVM.userSession.getUserPassword().equals(password, false)) {
            bind.bear.apply {
                setAnimation("bear_happy.lottie")
                setMinProgress(0.4f)
                setMaxProgress(0.5f)
                resumeAnimation()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(
                    LoginDirections.actionLoginToHome()
                )
            }, 2000)
        } else {
            Toast.makeText(requireContext(), "Wrong password", Toast.LENGTH_SHORT).show()
        }

    }
}