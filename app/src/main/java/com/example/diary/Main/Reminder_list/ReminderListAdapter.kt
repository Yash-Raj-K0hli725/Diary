package com.example.diary.Main.Reminder_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.Fragments.Home.MainFrameListDirections
import com.example.diary.Main.ModelV.SharedModel
import com.example.diary.databinding.ReminderItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class ReminderListAdapter : ListAdapter<DataOO, ReminderListAdapter.listVH>(diffUtil()) {

    lateinit var shareVM: SharedModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listVH {
        return listVH(
            ReminderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: listVH, position: Int) {
        val currentItem = getItem(position)
        holder.bind.apply {
            checkBox.isChecked = currentItem.Condition.toBoolean()
            checkBox.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    updateCheckBox(currentItem)
                }
            }
            reminderCard.setOnClickListener {
                holder.itemView.findNavController()
                    .navigate(MainFrameListDirections.actionMainFrameListToAddReminder(currentItem))
            }
            reminderText.text = currentItem.Title
        }
    }

    //updates_TaskCompletion
    fun updateCheckBox(currentItem: DataOO) {
        val condi: Boolean = !currentItem.Condition.toBoolean()
        val reminder = DataOO(
            currentItem.id,
            currentItem.Title,
            condi.toString(),
            Time(System.currentTimeMillis())
        )
        shareVM.updateReminder(reminder)
    }

    fun getData(vm: SharedModel) {
        this.shareVM = vm
    }

    //ViewHoldeClass_forListAdapter
    inner class listVH(val bind: ReminderItemBinding) : RecyclerView.ViewHolder(bind.root)
}

private class diffUtil : DiffUtil.ItemCallback<DataOO>() {
    override fun areItemsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem.Title == newItem.Title
    }
}