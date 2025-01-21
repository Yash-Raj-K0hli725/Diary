package com.example.diary.Main.DiaryEntries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.MainFrameListDirections
import com.example.diary.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class listAdapter : ListAdapter<DataCC, listAdapter.RecyclerVHolder>(utilclass()) {
    //Variable
    lateinit var EDB: EdataBase
    lateinit var context: Context
    lateinit var bind: View
    lateinit var SFM: FragmentManager

    //Overrides
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVHolder {
        val gView = LayoutInflater.from(parent.context).inflate(R.layout.recyclelay, parent, false)
        return RecyclerVHolder(gView)
    }

    override fun onBindViewHolder(holder: RecyclerVHolder, position: Int) {
        val currentItem = getItem(position)
        bind = holder.itemView
        holder.itemView.apply {
            findViewById<TextView>(R.id.Title).text = currentItem.Title
            findViewById<TextView>(R.id.Date).text = currentItem.Date.toString()
            holder.itemView.findViewById<CardView>(R.id.card).setOnClickListener {

                holder.itemView.findNavController()
                    .navigate(MainFrameListDirections.actionMainFrameListToEdit(currentItem))
            }
        }
        if (currentItem.Text.isNotEmpty()) {
            holder.itemView.findViewById<TextView>(R.id.Data).text = currentItem.Text
        }
    }
    //Functions

    fun DeleteItem(postion: Int) {
        val CI = getItem(postion)
        alert(CI)
    }

    private fun alert(CI: DataCC) {
        val alertDialog0 = AlertDialog.Builder(context).setTitle("Delete")
            .setMessage("Are You Sure you want to delete \"${CI.Title}\"")
            .setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    EDB.EDBDao().DeleteData(CI)
                }
                Snackbar.make(bind, "Item Successfully Deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }.show()
        notifyDataSetChanged()
    }

    fun setData(edb: EdataBase, cc: Context, sfm: FragmentManager) {
        this.EDB = edb
        this.context = cc
        this.SFM = sfm
    }

    inner class RecyclerVHolder(view: View) : RecyclerView.ViewHolder(view)
}

//Comparator class
class utilclass : DiffUtil.ItemCallback<DataCC>() {
    override fun areItemsTheSame(oldItem: DataCC, newItem: DataCC): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataCC, newItem: DataCC): Boolean {
        return oldItem.Text == newItem.Text
    }
}