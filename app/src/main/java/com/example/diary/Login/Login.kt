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
            bear.setMaxProgress(0.3f)
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
                        bear.apply {
                            progress = 0.13f
                            Handler(Looper.getMainLooper()).postDelayed(
                                { bear.pauseAnimation() }, 500
                            )
                        }
                    } else {
                        bear.resumeAnimation()
                    }
                }
            }

            val et = inpPassword
            password.setEndIconOnClickListener {
                if (et.transformationMethod is PasswordTransformationMethod) {
                    bear.resumeAnimation()
                    et.transformationMethod = null
                } else {
                    if (et.hasFocus()) {
                        bear.apply {
                            progress = 0.13f
                            Handler(Looper.getMainLooper()).postDelayed(
                                { bear.pauseAnimation() }, 500
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
            bind.bear.apply {
                resumeAnimation()
                setMaxProgress(0.68f)
                setMinProgress(0.3f)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (bind.inpPassword.transformationMethod is PasswordTransformationMethod) {
                        setMaxProgress(1f); setMinProgress(0f)
                        progress = 0.13f
                        Handler(Looper.getMainLooper()).postDelayed(
                            { pauseAnimation() }, 500
                        )
                    } else {
                        setMaxProgress(0.3f)
                        setMinProgress(0f)
                    }
                }, 2500)
            }
            Toast.makeText(requireContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val password = bind.inpPassword.text.toString().trim()
        if (sharedVM.userSession.getUserPassword().equals(password, false)) {
            bind.apply {
                bear.apply {
                    resumeAnimation()
                    setMaxProgress(0.87f)
                    setMinProgress(0.84f)
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(LoginDirections.actionLoginToHome())
                    }, 2000)
                }
                btnDone.isEnabled = false
            }
        } else {
            bind.bear.apply {
                resumeAnimation()
                setMaxProgress(0.68f)
                setMinProgress(0.3f)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (bind.inpPassword.transformationMethod is PasswordTransformationMethod) {
                        setMaxProgress(1f); setMinProgress(0f)
                        progress = 0.13f
                        Handler(Looper.getMainLooper()).postDelayed(
                            { pauseAnimation() }, 500
                        )

                    } else {
                        setMaxProgress(0.3f)
                        setMinProgress(0f)
                    }
                }, 2500)
            }
            Toast.makeText(requireContext(), "Wrong password", Toast.LENGTH_SHORT).show()
        }

    }
}