package com.example.diary.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
                passCheck()
            }
        }

    }

   private suspend fun passCheck() {
        val checkFlag = lifecycleScope.async {
            sharedVM.fetchPassword().Pass == bind.password.text.toString()
        }
        checkFlag.join()
        //
        if (checkFlag.await()) {
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