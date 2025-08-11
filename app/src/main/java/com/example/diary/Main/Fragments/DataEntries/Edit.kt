package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Edit : Fragment() {
    private lateinit var bind: FragmentEditBinding
    private val sharedVM: SharedModel by viewModels()
    private lateinit var currentItem: DataCC
    private lateinit var args: EditArgs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        args = EditArgs.fromBundle(requireArguments())
        currentItem = args.item

        //setData
        bind.Data.setText(currentItem.Text)
        bind.Titlee.setText(currentItem.Title)
        bind.Time.text = currentItem.Date.toString()
        //end
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.Update.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun check(): Boolean {
        return bind.Data.text.toString() != args.item.Text || bind.Titlee.text.toString() != args.item.Title
    }

    override fun onDestroy() {
        if (check()) {
            val texto = bind.Data.text.toString()
            val titlo = bind.Titlee.text.toString()
            val idd = currentItem.id
            val notes = DataCC(idd, titlo, texto, currentItem.Date)
            CoroutineScope(Dispatchers.IO).launch {
                sharedVM.updateNotes(notes)
            }
        }
        super.onDestroy()
    }
}