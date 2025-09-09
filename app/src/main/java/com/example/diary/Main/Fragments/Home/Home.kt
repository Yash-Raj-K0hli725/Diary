package com.example.diary.Main.Fragments.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import java.sql.Time

class MainFrameList : Fragment() {
    lateinit var bind: FragmentMainFrameListBinding
    private val sharedModel: SharedModel by viewModels()
    private val viewPager: ViewPager2 by lazy {
        bind.MFVP2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_frame_list, container, false
        )
        //ViewPager-->
        //<--
        setGreetings()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(bind.headerFlow) { v, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val mParams = v.layoutParams as ViewGroup.MarginLayoutParams
            mParams.setMargins(0, statusBar.top, 0, 0)
            v.layoutParams = mParams
            insets
        }
        bind.headerImage.post {
            val headerHeight = bind.headerImage.height + 37
            Log.e("Yash","height at post: $headerHeight")
            sharedModel.setHeaderHeight(headerHeight)
        }

        val vp2Adapter = VPAdapter(this@MainFrameList, listOf(DiaryFrag(), Reminders()))
        viewPager.adapter = vp2Adapter

        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_notification)

        TabLayoutMediator(bind.tab, viewPager) { tabs, position ->
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

        bind.add.setOnClickListener {
            if (viewPager.currentItem == 0) {
                findNavController().navigate(MainFrameListDirections.actionMainFrameListToAddin())
            } else {
                findNavController().navigate(
                    MainFrameListDirections.actionMainFrameListToAddReminder(null)
                )
            }
        }
    }

    private fun setGreetings() {
        val greetings = bind.greetings
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

const val HEADER_HEIGHT = "HEADER_HEIGHT"