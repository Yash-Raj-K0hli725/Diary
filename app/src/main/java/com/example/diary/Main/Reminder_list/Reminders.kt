package com.example.diary.Main.Reminder_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentRemindersBinding

class Reminders : Fragment() {

    lateinit var bind:FragmentRemindersBinding
    lateinit var shareVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_reminders,container,false)
        shareVM = ViewModelProvider(requireActivity())[MainVM::class.java]

        val listAdapter = R_listAdapter()
        bind.incompleteReminders.adapter = listAdapter
        bind.incompleteReminders.layoutManager = GridLayoutManager(requireContext(),2)
        listAdapter.getData(shareVM)
        shareVM.database.EDBDao().getReminderInfo().observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }

        // Inflate the layout for this fragment
        return bind.root
    }

}