package com.example.diary.Main.Fragments.DataEntries

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentAddReminderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.Calendar


class add_Edit_Reminder : BottomSheetDialogFragment() {
    lateinit var bind: FragmentAddReminderBinding
     private val sharedVM: SharedModel by viewModels()
    var data: DataOO? = null
    private var updatePermission = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder, container, false)
        data = add_Edit_ReminderArgs.fromBundle(requireArguments()).data

        if (data != null) {
            bind.task.setText(data?.Title)
            bind.checked.isChecked = data?.Condition.toBoolean()
            updatePermission = true

        }

        val calendar = android.icu.util.Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR)
        val minutes = calendar.get(Calendar.MINUTE)
        val picker = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->

            Toast.makeText(
                requireContext(), "This Feature is yet to be implemented", Toast.LENGTH_SHORT
            ).show()

        }, hours, minutes, false)


        bind.setReminder.setOnClickListener {
            picker.show()
        }


        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.DONE.setOnClickListener {
            if (!updatePermission) {
                CoroutineScope(Dispatchers.IO).launch {
                    addTask()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    editTask()
                }
            }
        }
    }


     fun addTask() {
        if (checkInputs()) {
            val checkBox = bind.checked.isChecked
            val txt = bind.task.text.toString()
            val addTask = DataOO(0, txt, checkBox.toString(), Time(System.currentTimeMillis()))
            sharedVM.insertReminder(addTask)
            dismiss()
        }
    }

     fun editTask() {
        if (updatesCheck()) {
            val checkBox = bind.checked.isChecked
            val txt = bind.task.text.toString()
            val updateTask = DataOO(
                data!!.id,
                txt,
                checkBox.toString(),
                Time(System.currentTimeMillis())
            )
            sharedVM.updateReminder(updateTask)
            dismiss()
        }
    }

    private fun checkInputs(): Boolean {
        return bind.task.text.isNotEmpty()
    }

    private fun updatesCheck(): Boolean {
        return bind.task.text.toString() != data!!.Title
                || bind.checked.isChecked != data!!.Condition.toBoolean()
    }

    companion object {
        val tag = "ADD_REMINDER"
    }
}
