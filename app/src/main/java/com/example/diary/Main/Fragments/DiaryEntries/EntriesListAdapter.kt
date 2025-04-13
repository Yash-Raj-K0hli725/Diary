package com.example.diary.Main.Fragments.DiaryEntries

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.Fragments.Home.MainFrameListDirections
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.RecyclelayBinding
import com.google.android.material.snackbar.Snackbar

class EntriesListAdapter : ListAdapter<DataCC, EntriesListAdapter.RecyclerVHolder>(UtilClass()) {
    //Variable
    lateinit var sharedVM: MainVM
    lateinit var context: Context
    lateinit var bind: View

    //Overrides
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVHolder {
        val gView = RecyclelayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerVHolder(gView)
    }

    override fun onBindViewHolder(holder: RecyclerVHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind.apply {
            Title.text = currentItem.Title
            Date.text = currentItem.Date.toString()
            notesThumbnail.setBackgroundResource(randomThumbBackgroundColor())
            notesThumbnail.setImageResource(randomNotesThumb())
            card.setOnClickListener {
                holder.itemView.findNavController()
                    .navigate(MainFrameListDirections.actionMainFrameListToEdit(currentItem))
            }
        }
        if (currentItem.Text.isNotEmpty()) {
            holder.bind.Data.text = currentItem.Text
        }
    }
    //Functions

    fun DeleteItem(postion: Int) {
        alert(getItem(postion))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun alert(selectedItem: DataCC) {
        AlertDialog.Builder(context).setTitle("Delete")
            .setMessage("Are You Sure you want to delete \"${selectedItem.Title}\"")
            .setPositiveButton("Yes") { _, _ ->
                sharedVM.deleteNotes(selectedItem)
                Snackbar.make(bind, "Item Successfully Deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }.show()
        notifyDataSetChanged()
    }


    fun setData(vM: MainVM, cc: Context) {
        this.sharedVM = vM
        this.context = cc
    }

    private fun randomThumbBackgroundColor(): Int {
        return listOf(
            R.color.card1,
            R.color.card2,
            R.color.card3,
            R.color.card4,
        ).random()
    }

    private fun randomNotesThumb(): Int {
        return listOf(
            R.drawable.notes1,
            R.drawable.notes2,
            R.drawable.notes3,
            R.drawable.notes4,
            R.drawable.notes5,
            R.drawable.notes6,
            R.drawable.notes7,
            R.drawable.notes8,
            R.drawable.notes9,
            R.drawable.notes10,
            R.drawable.notes11
        ).random()
    }

    inner class RecyclerVHolder(val bind: RecyclelayBinding) : RecyclerView.ViewHolder(bind.root)
}

//Comparator class
private class UtilClass : DiffUtil.ItemCallback<DataCC>() {
    override fun areItemsTheSame(oldItem: DataCC, newItem: DataCC): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataCC, newItem: DataCC): Boolean {
        return oldItem.Text == newItem.Text
    }
}