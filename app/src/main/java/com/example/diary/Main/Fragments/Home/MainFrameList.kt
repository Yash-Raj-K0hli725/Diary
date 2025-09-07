package com.example.diary.Main.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.Main.Fragments.DiaryEntries.DiaryFrag
import com.example.diary.Main.Reminder_list.Reminders
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentMainFrameListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.sql.Time

class MainFrameList : Fragment() {
    lateinit var bind: FragmentMainFrameListBinding
    private val sharedVM: SharedModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_frame_list, container, false
        )

        //ViewPager-->
        val vp2Adapter = VPAdapter(this, listOf(DiaryFrag(), Reminders()))
        bind.MFVP2.adapter = vp2Adapter

        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_notification)

        TabLayoutMediator(bind.tab, bind.MFVP2) { tabs, position ->
            tabs.icon = ContextCompat.getDrawable(requireContext(), tabIcons[position])
        }.attach()
        bind.tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        bind.add.setImageResource(R.drawable.ic_feather)
                    }

                    1 -> {
                        bind.add.setImageResource(R.drawable.ic_add)
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        //<--
        setGreetings()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.add.setOnClickListener {
            if (bind.MFVP2.currentItem == 0) {
                findNavController().navigate(MainFrameListDirections.actionMainFrameListToAddin())
            } else {
                findNavController().navigate(
                    MainFrameListDirections.actionMainFrameListToAddReminder(null)
                )
            }
        }
    }

    private fun setGreetings() {
        val greetings = bind.txtGoodMorning
        val time = Time(System.currentTimeMillis()).hours
        if (time > 20 || time < 4)
            greetings.setText(R.string.good_night)
        else if (time > 16)
            greetings.setText(R.string.good_evening)
        else if (time > 12)
            greetings.setText(R.string.good_afternoon)
        else
            greetings.setText(R.string.good_morning)
    }

}