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
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class completedListAdapter:ListAdapter<DataOO,completedListAdapter.completedListVH>(completeddiffUtil()) {
    lateinit var shareVM: MainVM
    inner class completedListVH(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): completedListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_items,parent,false)
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

    suspend fun updateCheckBox(currentItem:DataOO){
        val condi:Boolean = !currentItem.Condition.toBoolean()
        val tempData = DataOO(currentItem.id,currentItem.Title,condi.toString(),
            Time(System.currentTimeMillis())
        )
        shareVM.database.EDBDao().updateReminders(tempData)
    }

    fun getData(vm: MainVM) {
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