package com.example.diary.Main.Reminder_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataOO
import com.example.diary.Main.ModelV.SharedModel
import com.example.diary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class completedListAdapter:ListAdapter<DataOO,completedListAdapter.completedListVH>(completeddiffUtil()) {
    lateinit var shareVM: SharedModel
    inner class completedListVH(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): completedListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item,parent,false)
        return completedListVH(view)
    }

    override fun onBindViewHolder(holder: completedListVH, position: Int) {
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

     fun updateCheckBox(currentItem:DataOO){
        val condi:Boolean = !currentItem.Condition.toBoolean()
        val reminder = DataOO(currentItem.id,currentItem.Title,condi.toString(),
            Time(System.currentTimeMillis())
        )
        shareVM.updateReminder(reminder)
    }

    fun getData(vm: SharedModel) {
        this.shareVM = vm

    }

}
class completeddiffUtil: DiffUtil.ItemCallback<DataOO>(){
    override fun areItemsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataOO, newItem: DataOO): Boolean {
        return oldItem.Condition == newItem.Condition
    }
}