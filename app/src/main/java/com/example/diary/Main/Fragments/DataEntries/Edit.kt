package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.Table_Diary
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Edit : Fragment() {
    private lateinit var bind: FragmentEditBinding
    private val sharedVM: SharedModel by viewModels()
    private lateinit var cItem: Table_Diary
    private lateinit var args: EditArgs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        args = EditArgs.fromBundle(requireArguments())
        cItem = args.item

        //setData
        bind.Data.setText(cItem.text)
        bind.Titlee.setText(cItem.title)
        bind.Time.text = cItem.date.toString()
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
        return bind.Data.text.toString() != args.item.text || bind.Titlee.text.toString() != args.item.title
    }

    override fun onDestroy() {
        if (check()) {
            val mText = bind.Data.text.toString()
            val mTitle = bind.Titlee.text.toString()
            val mId = cItem.id
            val notes = Table_Diary(mTitle, mText, cItem.date,mId)
            CoroutineScope(Dispatchers.IO).launch {
                sharedVM.updateNotes(notes)
            }
        }
        super.onDestroy()
    }
}