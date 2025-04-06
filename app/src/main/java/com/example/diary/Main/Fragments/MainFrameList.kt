package com.example.diary.Main.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.diary.Main.DiaryEntries.RListItems
import com.example.diary.Main.Fragments.SwipeGestures.VPadapter
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.Reminder_list.Reminders
import com.example.diary.R
import com.example.diary.databinding.FragmentMainFrameListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MainFrameList : Fragment() {
    lateinit var bind: FragmentMainFrameListBinding
    lateinit var sharedVM: MainVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_frame_list, container, false
        )
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]

        sharedVM.liveSkip.observe(viewLifecycleOwner) {
            updateLockTint(it)
        }

        //ViewPager-->
        val vp2FragList = listOf(RListItems(), Reminders())
        val vp2Adapter = VPadapter(this, vp2FragList)
        bind.MFVP2.adapter = vp2Adapter

        bind.Licon.setOnClickListener {
            bind.MFVP2.currentItem = 0
        }
        bind.Ricon.setOnClickListener {
            bind.MFVP2.currentItem = 1
        }
        TabLayoutMediator(bind.tab, bind.MFVP2) { _, _ ->
        }.attach()
        bind.tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
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
        //<--

        return bind.root
    }

    override fun onResume() {
        if (sharedVM.addinPermission) {
            sharedVM.createNotes(sharedVM.addinItem!!)
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

        bind.setLock.setOnClickListener {
            lifecycleScope.launch {
                if (sharedVM.isSkipped()) {
                    view.findNavController()
                        .navigate(MainFrameListDirections.actionMainFrameListToSetPassword())
                } else {
                    view.findNavController().navigate(R.id.action_mainFrameList_to_popUp)
                }
            }
        }
    }

    private fun updateLockTint(bool: Boolean) {
        val lock = bind.setLock
        if (!bool) {
            lock.setColorFilter(Color.argb(200, 24, 133, 246))
        } else {
            lock.setColorFilter(Color.argb(200, 100, 98, 98))
        }
    }
}