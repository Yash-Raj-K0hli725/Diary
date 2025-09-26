package com.example.diary.Main.Reminder_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.Reminder
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class CTaskAdapter :
    ListAdapter<Reminder, CTaskAdapter.CReminderViewHolder>(DIFF_UTIL) {
    private lateinit var shareVM: SharedModel

    inner class CReminderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CReminderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return CReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CReminderViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.apply {
            val checkBox = findViewById<CheckBox>(R.id.checkBox)
            checkBox.isChecked = currentItem.Condition.toBoolean()
            findViewById<CheckBox>(R.id.checkBox).setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    updateCheckBox(currentItem)
                }
            }
            findViewById<TextView>(R.id.reminderText).text = currentItem.Title
        }
    }

    fun updateCheckBox(currentItem: Reminder) {
        val condi: Boolean = !currentItem.Condition.toBoolean()
        val reminder = Reminder(
            currentItem.id, currentItem.Title, condi.toString(),
            Time(System.currentTimeMillis())
        )
        shareVM.updateReminder(reminder)
    }

    fun getData(vm: SharedModel) {
        this.shareVM = vm
    }

}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.Condition == newItem.Condition
    }
}