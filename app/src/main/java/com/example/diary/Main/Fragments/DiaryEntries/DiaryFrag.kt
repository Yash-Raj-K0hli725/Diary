package com.example.diary.Main.Fragments.DiaryEntries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.DataBase.Table_Diary
import com.example.diary.Main.Fragments.Home.HomeDirections
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.databinding.FragmentDiaryEntriesBinding
import java.util.Date

class DiaryFrag : Fragment() {
    private lateinit var bind: FragmentDiaryEntriesBinding
    private val sharedVM: SharedModel by viewModels()
    private val mAdapter: EntriesListAdapter by lazy {
        EntriesListAdapter(sharedVM) { sItem ->
            findNavController().navigate(HomeDirections.actionMainFrameListToEdit(sItem))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = FragmentDiaryEntriesBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.notes.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        sharedVM.readNotes().observe(viewLifecycleOwner) {
            val entries = mutableListOf(Table_Diary("none", "none", Date()))
            entries.addAll(it)
            mAdapter.submitList(entries)
        }
    }
}