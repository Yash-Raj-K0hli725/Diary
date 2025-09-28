package com.example.diary.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.Main.Utils.UserSession
import com.example.diary.databinding.FragmentRegisterBinding

class Register : Fragment() {
    private lateinit var bind: FragmentRegisterBinding
    private val sharedVM: SharedModel by activityViewModels()
    private val userData: UserSession by lazy {
        UserSession(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentRegisterBinding.inflate(inflater, container, false)
        bind.apply {
            blurView.setupWith(requireActivity().findViewById(android.R.id.content))
                .setBlurRadius(5f).setBlurAutoUpdate(true)
        }
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.apply {
            doneButton.setOnClickListener {
                sharedVM.userSession.userLogin(true)
                saveUserInfo(inpName.text.toString().trim(), inpPassword.text.toString().trim())
            }
            skip.setOnClickListener {
                sharedVM.userSession.userLogin(true)
                sharedVM.userSession.usePassword(false)
                findNavController().navigate(RegisterDirections.actionRegisterToMainFrameList())
            }
        }
    }

    private fun saveUserInfo(name: String, password: String) {
        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        userData.saveUserPassword(password)
        userData.saveDiaryName(name)
        findNavController().navigate(RegisterDirections.actionRegisterToLogin())
    }

}