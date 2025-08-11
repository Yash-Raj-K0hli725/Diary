package com.example.diary.Main.Reminder_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.ModelV.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentRemindersBinding

class Reminders : Fragment() {

    lateinit var bind:FragmentRemindersBinding
    lateinit var shareVM:SharedModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_reminders,container,false)
        shareVM = ViewModelProvider(requireActivity())[SharedModel::class.java]

        val listAdapter = ReminderListAdapter()
        bind.incompleteReminders.adapter = listAdapter
        bind.incompleteReminders.layoutManager = GridLayoutManager(requireContext(),2)

        //
        val completedListAdapter = completedListAdapter()
        bind.completeReminders.adapter = completedListAdapter
        bind.completeReminders.layoutManager = LinearLayoutManager(requireContext())
        bind.txtCompleted.visibility = View.VISIBLE
        //
        //share's ViewModel to list
        listAdapter.getData(shareVM)
        completedListAdapter.getData(shareVM)

        shareVM.readReminder().observe(viewLifecycleOwner){reminders->
           listAdapter.submitList(checkIncompletion(reminders))
           completedListAdapter.submitList(checkCompletion(reminders))
        }

        // Inflate the layout for this fragment
        return bind.root
    }

    private fun checkCompletion(data:List<DataOO>): List<DataOO> {
        var list:List<DataOO> = emptyList()
        for (i in data){
            if(i.Condition.toBoolean()){
                list = list+i
            }
        }
        return list
    }

    private fun checkIncompletion(data:List<DataOO>): List<DataOO> {
        var list:List<DataOO> = emptyList()
        for (i in data){
            if(!i.Condition.toBoolean()){
                list = list+i
            }
        }
        return list
    }

}