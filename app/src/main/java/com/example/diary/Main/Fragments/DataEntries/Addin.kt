package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.ModelV.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentAddinBinding
import java.util.Date


class Addin : Fragment() {
    lateinit var bind: FragmentAddinBinding
     private val sharedVM: SharedModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_addin, container, false)
        bind.Timern.text = Date().toString()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.back.setOnClickListener {
            confirmDairyEntry()
            findNavController().popBackStack()
        }

        val backPressedDispatcher = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                confirmDairyEntry()
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedDispatcher
        )
    }
    private fun checkInputs(): Boolean {
        return (bind.Data.text.isNotEmpty() || bind.Title.text.isNotEmpty())
    }

    private fun confirmDairyEntry() {
        val navController = requireActivity().findNavController(R.id.hoster)
        val data = if (checkInputs()) {
            val title = bind.Title.text.toString()
            val text = bind.Data.text.toString()
            DataCC(0, title, text, Date())
        } else {
            null
        }
        navController.previousBackStackEntry?.savedStateHandle?.set("addin", data)
    }
}