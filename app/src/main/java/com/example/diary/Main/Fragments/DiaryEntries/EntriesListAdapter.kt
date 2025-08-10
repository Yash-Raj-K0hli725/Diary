package com.example.diary.Main.Fragments.DiaryEntries

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.Fragments.Home.MainFrameListDirections
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.EntriesImageHeaderBinding
import com.example.diary.databinding.EntryItemsBinding
import com.google.android.material.snackbar.Snackbar

class EntriesListAdapter(private val sharedVM: MainVM) :
    ListAdapter<DataCC, RecyclerView.ViewHolder>(UtilClass()) {
        lateinit var view: View
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> IMAGEHEADER
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == IMAGEHEADER) {
            ImageHeaderViewHolder(
                EntriesImageHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ItemsViewHolder(
                EntryItemsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == IMAGEHEADER) {
            (holder as ImageHeaderViewHolder).bind()
        } else {
            view = holder.itemView
            val currentItem = getItem(position)
            (holder as ItemsViewHolder).bind(currentItem, sharedVM)
        }

    }

    class ImageHeaderViewHolder(private val bind: EntriesImageHeaderBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind() {
            bind.punchLines.text = setRandomPunchLines()
            bind.imagePanel.setImageResource(setRandomImages())
        }

        private fun setRandomImages(): Int {
            return listOf(
                R.drawable.thumb1,
                R.drawable.thumb2,
                R.drawable.thumb3,
                R.drawable.thumb4,
                R.drawable.thumb5,
                R.drawable.thumb6
            ).random()
        }

        private fun setRandomPunchLines(): String {
            return listOf(
                "Your cozy space to capture life's moments",
                "Capture the moments that matter",
                "Where your stories feel at home",
                "Spill the tea (digitally, of course!)",
                "Discover yourself, one page at a time",
                "A cozy corner for your mind"
            ).random()
        }
    }

    inner class ItemsViewHolder(private val bind: EntryItemsBinding) :
        RecyclerView.ViewHolder(bind.root) {
        lateinit var vModel: MainVM
        fun bind(currentItem: DataCC, vModel: MainVM) {
            this.vModel = vModel
            bind.apply {
                Title.text = currentItem.Title
                Date.text = currentItem.Date.toString()
                notesThumbnail.setBackgroundResource(randomThumbBackgroundColor())
                notesThumbnail.setImageResource(randomNotesThumb())
                card.setOnClickListener {
                    view.findNavController()
                        .navigate(MainFrameListDirections.actionMainFrameListToEdit(currentItem))

                }
                if (currentItem.Text.isNotEmpty()) {
                    bind.Data.text = currentItem.Text
                }
            }
            setUpMenu(bind, currentItem)
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

        private fun randomThumbBackgroundColor(): Int {
            return listOf(
                R.color.card1,
                R.color.card2,
                R.color.card3,
                R.color.card4,
            ).random()
        }

        private fun setUpMenu(bind: EntryItemsBinding, currentItem: DataCC) {
            bind.menu.setOnClickListener { m ->
                val menuPopup = PopupMenu(m.context, m, Gravity.END, 0, R.style.customOptionMenu)
                menuPopup.menuInflater.inflate(R.menu.option_menu, menuPopup.menu)
                menuPopup.show()
                menuPopup.setOnMenuItemClickListener { selectedItem ->
                    when (selectedItem.itemId) {
                        R.id.delete -> {
                            confirmDelete(currentItem)
                            true
                        }

                        R.id.edit -> {

                            true
                        }

                        else ->
                            false
                    }
                }

            }
        }

        private fun confirmDelete(selectedItem: DataCC) {
            AlertDialog.Builder(bind.root.context).setTitle("Delete")
                .setMessage("Are You Sure you want to delete \"${selectedItem.Title}\"")
                .setPositiveButton("Yes") { _, _ ->
                    vModel.deleteNotes(selectedItem)
                    Snackbar.make(view, "Item Successfully Deleted", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ ->
                }.show()
        }

    }

    companion object {
        private const val IMAGEHEADER = 0
    }
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