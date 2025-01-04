package com.example.diary.Main.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.DataEntries.Edit
import com.example.diary.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Recycleadapter : RecyclerView.Adapter<Recycleadapter.RecyclerVHolder>() {
    //Variable
    var entriesList = emptyList<DataCC>()
    lateinit var SFM: FragmentManager
    lateinit var EDB: EdataBase
    lateinit var context: Context
    lateinit var bind:View
    //Overrides
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVHolder {
        val gView = LayoutInflater.from(parent.context).inflate(R.layout.recyclelay, parent, false)
        return RecyclerVHolder(gView)
    }

    override fun onBindViewHolder(holder: RecyclerVHolder, position: Int) {
        val currentItem = entriesList[position]
        bind = holder.itemView
        holder.itemView.apply {
            findViewById<TextView>(R.id.Title).text = currentItem.Title
            findViewById<TextView>(R.id.Date).text = currentItem.Date.toString()
        }
        if (currentItem.Text.isNotEmpty()) {
            holder.itemView.findViewById<TextView>(R.id.Data).text = currentItem.Text
        }
        holder.itemView.findViewById<CardView>(R.id.card).setOnClickListener {
            UpdateItem(position)
        }
    }

    override fun getItemCount(): Int {
        return entriesList.size
    }

    fun DeleteItem(postion: Int) {
        val CI = entriesList[postion]
        alert(CI)
    }

    fun UpdateItem(postion: Int) {
        val CI = entriesList[postion]
        val frag = Edit()
        frag.fetchData(CI)
        SFM.beginTransaction().replace(R.id.MFrameHolder, frag)
            .addToBackStack(null)
            .commit()
    }

    private fun alert(CI: DataCC) {
        val alertDialog0 = AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are You Sure you want to delete \"${CI.Title}\"")
            .setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch{
                    EDB.EDBDao().DeleteData(CI)
                }
                Snackbar.make(bind,"Item Successfully Deleted",Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }.show()
            notifyDataSetChanged()
    }

    fun setData(lis: List<DataCC>, sfm: FragmentManager, edb: EdataBase, cc: Context) {
        this.entriesList = lis
        this.SFM = sfm
        this.EDB = edb
        this.context = cc
        notifyDataSetChanged()
    }

    inner class RecyclerVHolder(view: View) : RecyclerView.ViewHolder(view)
}