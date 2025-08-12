package com.example.diary.Login

import android.os.Bundle
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
            inpPassword.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    loginUser()
                }
                true
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
            findNavController().navigate(LoginDirections.actionLoginToMainFrameList())
        } else {
            Toast.makeText(requireContext(), "Wrong password", Toast.LENGTH_SHORT).show()
        }

    }
}