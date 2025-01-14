package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentAddReminderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time


class addReminder : BottomSheetDialogFragment() {
        lateinit var bind:FragmentAddReminderBinding
        lateinit var sharedVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder,container,false)

        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.DONE.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                addTask()
            }
            dismiss()
        }
    }

    suspend fun addTask(){
        if(checkInputs()){
            val checkBox = bind.checked.isChecked
            val txt = bind.task.text.toString()
            val addTask = DataOO(0,txt,checkBox.toString(),Time(System.currentTimeMillis()))
            sharedVM.database.EDBDao().insertReminders(addTask)
        }
    }

    private fun checkInputs():Boolean{
       return  bind.task.text.isNotEmpty()
    }

    companion object {
            val tag = "ADD_REMINDER"
    }
}
