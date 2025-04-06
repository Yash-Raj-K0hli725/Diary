package com.example.diary.Main.Reminder_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.Fragments.MainFrameListDirections
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class R_listAdapter : ListAdapter<DataOO, R_listAdapter.listVH>(diffUtil()) {

    lateinit var shareVM: MainVM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listVH {
        val oncreateview = LayoutInflater.from(parent.context)
            .inflate(R.layout.reminder_items, parent, false)
        return listVH(oncreateview)
    }

    override fun onBindViewHolder(holder: listVH, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            checkBox.isChecked = currentItem.Condition.toBoolean()
            findViewById<CheckBox>(R.id.checkBox).setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    updateCheckBox(currentItem)
                }
            }
            findViewById<CardView>(R.id.reminderCard).setOnClickListener {
                    holder.itemView.findNavController()
                        .navigate(MainFrameListDirections.actionMainFrameListToAddReminder(currentItem))
            }

            findViewById<TextView>(R.id.reminderText).text = currentItem.Title
        }
    }
    //updates_TaskCompletion
    fun updateCheckBox(currentItem:DataOO){
        val condi:Boolean = !currentItem.Condition.toBoolean()
        val reminder = DataOO(
            currentItem.id,
            currentItem.Title,
            condi.toString(),
            Time(System.currentTimeMillis())
        )
        shareVM.updateReminder(reminder)
    }

    fun getData(vm: MainVM) {
        this.shareVM = vm
    }

    //ViewHoldeClass_forListAdapter
    inner class listVH(view: View) : RecyclerView.ViewHolder(view)
}

private class diffUtil : DiffUtil.ItemCallback<DataOO>() {
    override fun areItemsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem.Title == newItem.Title
    }
}