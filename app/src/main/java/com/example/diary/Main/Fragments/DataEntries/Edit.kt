package com.example.diary.Main.Fragments.DataEntries

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.Table_Diary
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.databinding.FragmentAddinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Edit : Fragment() {
    private lateinit var bind: FragmentAddinBinding
    private val sharedVM: SharedModel by viewModels()
    private lateinit var cItem: Table_Diary
    private lateinit var args: EditArgs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = FragmentAddinBinding.inflate(inflater, container, false)
        args = EditArgs.fromBundle(requireArguments())
        cItem = args.item
        requireActivity().onBackPressedDispatcher.addCallback(onBackSave)
        //end
        return bind.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setData
        bind.apply {
            val cDate = Calendar.getInstance().get(Calendar.DATE).toString()
            val cMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(cItem.date)
            date.text = "$cMonth $cDate"
            day.text = SimpleDateFormat("EEEE", Locale.getDefault()).format(cItem.date)
            inpTitle.setText(cItem.title)
            data.setText(cItem.text)
            back.setOnClickListener {
                saveProgress()
                findNavController().popBackStack()
            }
        }

    }

    fun check(): Boolean {
        return bind.data.text.toString() != args.item.text || bind.inpTitle.text.toString() != args.item.title
    }

    private val onBackSave = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            saveProgress()
            findNavController().popBackStack()
        }
    }

    private fun saveProgress() {
        if (check()) {
            val mText = bind.data.text.toString()
            val mTitle = bind.inpTitle.text.toString()
            val mId = cItem.id
            val notes = Table_Diary(mTitle, mText, cItem.date, mId)
            CoroutineScope(Dispatchers.IO).launch {
                sharedVM.updateNotes(notes)
            }
        }
    }
}