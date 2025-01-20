package com.example.diary.Main.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.Fragments.SwipeGestures.VPadapter
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.Reminder_list.RListItems
import com.example.diary.Main.Reminder_list.Reminders
import com.example.diary.R
import com.example.diary.databinding.FragmentMainFrameListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFrameList : Fragment() {
    lateinit var bind: FragmentMainFrameListBinding
    lateinit var sharedVM: MainVM
    lateinit var database: EdataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_frame_list, container, false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        database = EdataBase.getData(requireContext())
        //
        CoroutineScope(Dispatchers.Main).launch {
            if (!sharedVM.isSkipped()) {
                bind.setLock.setImageResource(R.drawable.locked)
                val loggedDetail = sharedVM.database.EDBDao().fetchPassword()
                bind.setLock.setOnClickListener {
                    bind.setLock.setImageResource(R.drawable.unlocked)
                    bindUnlock()
                    CoroutineScope(Dispatchers.IO).launch {
                        sharedVM.database.EDBDao().removeLogin(loggedDetail)
                    }
                }
            }
            else {
                    bindUnlock()
            }
        }
        //ViewPager
        val lis = listOf(RListItems(), Reminders())
        val Vpadapter = VPadapter(this, lis)
        bind.MFVP2.adapter = Vpadapter

        bind.Licon.setOnClickListener {
            bind.MFVP2.currentItem = 0
        }
        bind.Ricon.setOnClickListener {
            bind.MFVP2.currentItem = 1
        }
        //
        //TabLayout
        TabLayoutMediator(bind.tab, bind.MFVP2) { _, _ ->
        }.attach()
        bind.tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        // Toast.makeText(requireContext(),"${tab.position}",Toast.LENGTH_SHORT).show()
                        bind.Licon.setImageResource(R.drawable.ic_notes_filled)
                    }

                    1 -> {
                        bind.Ricon.setImageResource(R.drawable.ic_check_filled)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        bind.Licon.setImageResource(R.drawable.ic_notes_out)
                    }

                    1 -> {
                        bind.Ricon.setImageResource(R.drawable.ic_check_oout)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        //end
        return bind.root
    }

    fun bindUnlock(){
        bind.setLock.setImageResource(R.drawable.unlocked)
        bind.setLock.setOnClickListener {
            view?.findNavController()
                ?.navigate(MainFrameListDirections.actionMainFrameListToSetPassword())
        }

    }

    override fun onResume() {
        if (sharedVM.addinPermission) {
            CoroutineScope(Dispatchers.IO).launch {
                database.EDBDao().InsertData(sharedVM.addinItem!!)
            }
            sharedVM.addinPermission = false
        }
        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.add.setOnClickListener {
            if (bind.MFVP2.currentItem == 0) {
                view.findNavController()
                    .navigate(MainFrameListDirections.actionMainFrameListToAddin())
            } else {
                view.findNavController()
                    .navigate(MainFrameListDirections.actionMainFrameListToAddReminder(null))
            }
        }
    }
}