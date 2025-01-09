package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Edit : Fragment() {
    private lateinit var bind: FragmentEditBinding
    private lateinit var sharedVM: MainVM
    private lateinit var currentItem: DataCC
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        val args = EditArgs.fromBundle(requireArguments()).item
        currentItem = args

        //setData
        bind.Data.setText(currentItem.Text)
        bind.Titlee.setText(currentItem.Title)
        //end
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.Update.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    fun check(): Boolean {
        return bind.Data.text.isNotEmpty() || bind.Titlee.text.isNotEmpty()
    }

    override fun onDestroy() {
        if (check()) {
            val texto = bind.Data.text.toString()
            val titlo = bind.Titlee.text.toString()
            val idd = currentItem.id
            val Datacc = DataCC(idd, titlo, texto, currentItem.Date)
            CoroutineScope(Dispatchers.IO).launch {
                sharedVM.database.EDBDao().UpdateData(Datacc)
            }
        }
        super.onDestroy()
    }
}