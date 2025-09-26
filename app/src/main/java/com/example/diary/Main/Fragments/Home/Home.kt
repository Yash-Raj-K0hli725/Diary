package com.example.diary.Main.Fragments.Home

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.diary.Main.Fragments.DiaryEntries.DiaryFrag
import com.example.diary.Main.Reminder_list.Reminders
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentMainFrameListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class Home : Fragment() {
    lateinit var bind: FragmentMainFrameListBinding
    private val sharedModel: SharedModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentMainFrameListBinding.inflate(inflater, container, false)
        //<--sets viewCompat according to screen size-->
        ViewCompat.setOnApplyWindowInsetsListener(bind.headerFlow) { v, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val mParams = v.layoutParams as ViewGroup.MarginLayoutParams
            mParams.setMargins(0, statusBar.top, 0, 0)
            v.layoutParams = mParams
            insets
        }
        val vp2Adapter = VPAdapter(this@Home, listOf(DiaryFrag(), Reminders()))
        bind.viewpager.adapter = vp2Adapter
        setGreetings()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_notification)
        TabLayoutMediator(bind.tab, bind.viewpager) { tabs, position ->
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

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        bind.add.setOnClickListener {
            if (bind.viewpager.currentItem == 0) {
                findNavController().navigate(HomeDirections.actionMainFrameListToAddin())
            } else {
                findNavController().navigate(
                    HomeDirections.actionMainFrameListToAddReminder(null)
                )
            }
        }
    }

    private fun setGreetings() {
        val greetings = bind.greetings
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour > 20 || hour < 4)
            greetings.setText(R.string.good_night)
        else if (hour > 16)
            greetings.setText(R.string.good_evening)
        else if (hour > 12)
            greetings.setText(R.string.good_afternoon)
        else
            greetings.setText(R.string.good_morning)
    }
}