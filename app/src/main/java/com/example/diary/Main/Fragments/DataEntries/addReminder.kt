package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.diary.R
import com.example.diary.databinding.FragmentAddReminderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class addReminder : BottomSheetDialogFragment() {
        lateinit var bind:FragmentAddReminderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder,container,false)
        // Inflate the layout for this fragment
        return bind.root
    }

    companion object {
            val tag = "ADD_REMINDER"
    }
}
