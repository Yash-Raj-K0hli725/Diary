package com.example.diary.Main.Reminder_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.Reminder
import com.example.diary.Main.Fragments.Home.HomeDirections
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.databinding.ReminderItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class ReminderListAdapter :
    ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder>(DIFF_UTIL) {
    private lateinit var shareVM: SharedModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            ReminderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
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
                    .navigate(HomeDirections.actionMainFrameListToAddReminder(currentItem))
            }
            reminderText.text = currentItem.Title
        }
    }

    //updates_TaskCompletion
    fun updateCheckBox(currentItem: Reminder) {
        val condi: Boolean = !currentItem.Condition.toBoolean()
        val reminder = Reminder(
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

    inner class ReminderViewHolder(val bind: ReminderItemBinding) :
        RecyclerView.ViewHolder(bind.root)
}

private val DIFF_UTIL: DiffUtil.ItemCallback<Reminder> =
    object : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.Title == newItem.Title
        }
    }