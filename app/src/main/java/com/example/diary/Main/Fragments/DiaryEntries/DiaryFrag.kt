package com.example.diary.Main.Fragments.DiaryEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        val entriesAdapter = EntriesListAdapter(sharedVM)
        //RecycleView
        val manager = LinearLayoutManager(requireContext())
        bind.notes.adapter = entriesAdapter
        bind.notes.layoutManager = manager


        sharedVM.readNotes().observe(viewLifecycleOwner) {
            entriesAdapter.submitList(it)
        }
        return bind.root
    }


}