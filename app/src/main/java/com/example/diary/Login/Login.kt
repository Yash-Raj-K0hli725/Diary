package com.example.diary.Login

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Login : Fragment() {
    lateinit var bind: FragmentLoginBinding
    private val sharedVM: SharedModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingViews()
    }

    private suspend fun passCheck() {
        val checkFlag = lifecycleScope.async(Dispatchers.IO) {
            sharedVM.fetchPassword().Pass == bind.password.text.toString()
        }
        //
        if (checkFlag.await()) {
            findNavController().navigate(R.id.action_login_to_mainFrameList)

        } else if (bind.password.text!!.isEmpty()) {
            Snackbar.make(
                requireView(),
                "Password field cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            Snackbar.make(
                requireView(),
                "Incorrect Password",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun bindingViews() {

        bind.confirm.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                passCheck()
            }
        }
        bind.main.animate().alpha(1f).setDuration(400).start()

        bind.password.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (v!!.text!!.isNotEmpty() && actionId == EditorInfo.IME_ACTION_DONE){
                    findNavController().navigate(R.id.action_login_to_mainFrameList)
                }
                return true
            }
        })
    }

}