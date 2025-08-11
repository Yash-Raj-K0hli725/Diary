package com.example.diary.Main.Fragments.DiaryEntries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.Main.ModelV.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryEntriesBinding

class DiaryFrag : Fragment() {
    private lateinit var bind: FragmentDiaryEntriesBinding
    private  val sharedVM: SharedModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        bind = FragmentDiaryEntriesBinding.inflate(inflater,container,false)
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