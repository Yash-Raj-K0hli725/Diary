package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentAddinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class Addin : Fragment() {
    lateinit var bind: FragmentAddinBinding
    lateinit var sharedVM: MainVM
    var Title = ""
    var Textt = "NoText"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_addin, container, false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun check(): Boolean {
        return (bind.Data.text.isNotEmpty() || bind.Title.text.isNotEmpty())
    }

    override fun onDestroy() {
        if (check()) {
            Title = bind.Title.text.toString()
            if (bind.Data.text.isNotEmpty()) {
                Textt = bind.Data.text.toString()
            }
            val data = DataCC(0, Title, Textt, Date())
            sharedVM.addinPermission = true
            sharedVM.addinItem = data
        }
        super.onDestroy()
    }
}