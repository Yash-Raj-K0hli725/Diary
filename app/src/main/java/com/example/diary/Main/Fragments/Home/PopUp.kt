package com.example.diary.Main.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentPopUpBinding
import kotlinx.coroutines.launch


class popUp : DialogFragment() {
     private val sharedVM: SharedModel by viewModels()
    lateinit var bind: FragmentPopUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.apply {
            // Remove default background
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_pop_up, container, false)

        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.confirmAction.setOnClickListener {
            lifecycleScope.launch{
                removePassword()
            }
        }
    }

    private suspend fun removePassword() {
        val loggedDetail = sharedVM.fetchPassword()
        val inputPassword = bind.removePassInput.text.toString()
        if (inputPassword == loggedDetail.Pass) {
            sharedVM.removeLoginInfo()
            sharedVM.skipBtn(true)
            sharedVM.liveSkip.value = true
            dismiss()
        } else {
            //wrongPassword
        }
    }

    companion object {
        const val tag = "Remove Password Confirmations"
    }
}
