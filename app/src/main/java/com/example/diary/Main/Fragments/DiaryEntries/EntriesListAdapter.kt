package com.example.diary.Main.Fragments.DiaryEntries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DiaryEntry
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.EntriesImageHeaderBinding
import com.example.diary.databinding.EntryItemsBinding
import com.google.android.material.snackbar.Snackbar

class EntriesListAdapter(viewModel: SharedModel, private val onEdit: (DiaryEntry) -> Unit) :
    ListAdapter<DiaryEntry, RecyclerView.ViewHolder>(UtilClass()) {
    lateinit var view: View
    private val sharedModel = viewModel

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
            (holder as ItemsViewHolder).initView(currentItem)
        }

    }

    inner class ImageHeaderViewHolder(private val bind: EntriesImageHeaderBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind() {
            bind.punchLines.text = setRandomPunchLines()
            bind.imagePanel.setImageDrawable(sharedModel.thumbnail)
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
        fun initView(cItem: DiaryEntry) {
            bind.apply {
                Title.text = cItem.title
                Date.text = cItem.date.toString()
                notesThumbnail.setBackgroundResource(randomThumbBackgroundColor())
                notesThumbnail.setImageResource(randomNotesThumb())
                if (cItem.text.isNotEmpty()) {
                    bind.Data.text = cItem.text
                }
                card.setOnClickListener {
                    options.apply {
                        visibility = if (visibility == View.GONE) {
                            menu.rotation = 270f
                            View.VISIBLE
                        } else {
                            menu.rotation = 0f
                            View.GONE
                        }
                    }
                }
                delete.setOnClickListener { confirmDelete(cItem) }
                edit.setOnClickListener {onEdit.invoke(cItem)}
            }
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

        private fun confirmDelete(sItem: DiaryEntry) {
            val title = sItem.title.ifEmpty { sItem.text }
            AlertDialog.Builder(bind.root.context).setTitle("Delete")
                .setMessage("Are You Sure you want to delete \"$title\"")
                .setPositiveButton("Yes") { _, _ ->
                    sharedModel.deleteNotes(sItem)
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
private class UtilClass : DiffUtil.ItemCallback<DiaryEntry>() {
    override fun areItemsTheSame(oldItem: DiaryEntry, newItem: DiaryEntry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiaryEntry, newItem: DiaryEntry): Boolean {
        return oldItem.text == newItem.text
    }
}