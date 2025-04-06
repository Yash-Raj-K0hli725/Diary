package com.example.diary.Main.DiaryEntries

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
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.SwipeGestures.SGestures
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentRListItemsBinding


class RListItems : Fragment() {
    private lateinit var bind:FragmentRListItemsBinding
    private lateinit var sharedVM:MainVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_r_list_items,container,false)

        //RecycleView
        val Adapter = listAdapter()
        //swipeTouch
        val swipeGesture = object :SGestures(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction)
                {
                    ItemTouchHelper.RIGHT ->{
                        Adapter.DeleteItem(viewHolder.position)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(bind.RecycleList)

        bind.RecycleList.adapter = Adapter
        bind.RecycleList.layoutManager = LinearLayoutManager(requireContext())

        sharedVM.readNotes().observe(viewLifecycleOwner){
            Adapter.submitList(it)
            Adapter.setData(sharedVM,requireContext())
        }

        return bind.root
    }

}