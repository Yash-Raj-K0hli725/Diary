package com.example.diary.Main.Fragments.DiaryEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.Main.Fragments.SwipeGestures.SGestures
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryEntriesBinding


class DiaryFrag : Fragment() {
    private lateinit var bind: FragmentDiaryEntriesBinding
    private lateinit var sharedVM: MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_entries, container, false)

        //RecycleView
        val Adapter = EntriesListAdapter()
        //swipeTouch
        val swipeGesture = object : SGestures(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        Adapter.DeleteItem(viewHolder.position)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(bind.notes)
        val manager = LinearLayoutManager(requireContext())
        bind.notes.adapter = Adapter
        bind.notes.layoutManager = manager

        sharedVM.readNotes().observe(viewLifecycleOwner) {
            Adapter.submitList(it)
            Adapter.setData(sharedVM, requireContext())
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.imagePanel.setImageResource(setRandomImages())
        bind.punchLines.text = setRandomPunchLines()
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