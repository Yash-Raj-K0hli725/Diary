package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.util.Log
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


class addReminder(private val data:DataOO?) : BottomSheetDialogFragment() {
        lateinit var bind:FragmentAddReminderBinding
        lateinit var sharedVM:MainVM
        private var updatePermission = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder,container,false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]

        if(data!=null){
            bind.task.setText(data.Title)
            bind.checked.isChecked = data.Condition.toBoolean()
            updatePermission = true

        }
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.DONE.setOnClickListener {
            if(!updatePermission){
                CoroutineScope(Dispatchers.IO).launch {
                    addTask()
                }
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    editTask()
                }
            }
        }
    }


    suspend fun addTask(){
        if(checkInputs()){
            val checkBox = bind.checked.isChecked
            val txt = bind.task.text.toString()
            val addTask = DataOO(0,txt,checkBox.toString(),Time(System.currentTimeMillis()))
            sharedVM.database.EDBDao().insertReminders(addTask)
            dismiss()
        }
    }

    suspend fun editTask(){
        if(updatesCheck()){
            val checkBox = bind.checked.isChecked
            val txt = bind.task.text.toString()
            val addTask = DataOO(data!!.id,txt,checkBox.toString(),Time(System.currentTimeMillis()))
            sharedVM.database.EDBDao().updateReminders(addTask)
            dismiss()
        }
    }

    private fun checkInputs():Boolean{
       return  bind.task.text.isNotEmpty()
    }

    private fun updatesCheck():Boolean{
        return  bind.task.text.toString() != data!!.Title
                || bind.checked.isChecked != data.Condition.toBoolean()
    }

    companion object {
            val tag = "ADD_REMINDER"
    }
}
