package com.example.diary.Main.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.DataEntries.Edit
import com.example.diary.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class listAdapter : ListAdapter<DataCC, listAdapter.RecyclerVHolder>(utilclass()) {
    //Variable
    lateinit var SFM: FragmentManager
    lateinit var EDB: EdataBase
    lateinit var context: Context
    lateinit var bind: View
    lateinit var animation:Animation
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
            anim(findViewById(R.id.card))
        }
        if (currentItem.Text.isNotEmpty()) {
            holder.itemView.findViewById<TextView>(R.id.Data).text = currentItem.Text
        }
        holder.itemView.findViewById<CardView>(R.id.card).setOnClickListener {
            UpdateItem(position)
        }
    }
    //Functions

    fun DeleteItem(postion: Int) {
        val CI = getItem(postion)
        alert(CI)
    }

    fun UpdateItem(postion: Int) {
        val CI = getItem(postion)
        val frag = Edit()
        frag.fetchData(CI)
        SFM.beginTransaction().replace(R.id.MFrameHolder, frag)
            .addToBackStack(null)
            .commit()
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

    private fun anim(item:CardView){
        animation = android.view.animation.AnimationUtils.loadAnimation(context,R.anim.slide_up)
        item.startAnimation(animation)
    }

    fun setData( sfm: FragmentManager, edb: EdataBase, cc: Context) {
        this.SFM = sfm
        this.EDB = edb
        this.context = cc
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