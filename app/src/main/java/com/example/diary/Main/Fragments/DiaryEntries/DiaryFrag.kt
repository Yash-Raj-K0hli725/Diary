package com.example.diary.Main.Fragments.DiaryEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.DataBase.DiaryEntry
import com.example.diary.Main.Fragments.Home.MainFrameListDirections
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.databinding.FragmentDiaryEntriesBinding
import java.util.Date

class DiaryFrag : Fragment() {
    private lateinit var bind: FragmentDiaryEntriesBinding
    private val sharedVM: SharedModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = FragmentDiaryEntriesBinding.inflate(inflater, container, false)
        val entriesAdapter = EntriesListAdapter(sharedVM) { sItem->
            findNavController().navigate(MainFrameListDirections.actionMainFrameListToEdit(sItem))
        }
        //RecycleView
        val manager = LinearLayoutManager(requireContext())
        bind.notes.adapter = entriesAdapter
        bind.notes.layoutManager = manager


        sharedVM.readNotes().observe(viewLifecycleOwner) {
            val entries = mutableListOf(DiaryEntry("none", "none", Date()))
            entries.addAll(it)
            entriesAdapter.submitList(entries)
        }
        return bind.root
    }


}