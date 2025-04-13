package com.example.diary.Main.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.DataCC
import com.example.diary.Main.Fragments.DiaryEntries.DiaryFrag
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.Main.Reminder_list.Reminders
import com.example.diary.R
import com.example.diary.databinding.FragmentMainFrameListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.sql.Time
import java.util.Calendar

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

        //ViewPager-->
        setUpViewPager2()
        //<--
        setGreetings()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
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

    private fun setData() {
        val navController = requireActivity().findNavController(R.id.hoster)
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DataCC>("addin")
            ?.observe(
                viewLifecycleOwner
            ) {
                it?.let {
                    sharedVM.createNotes(it)
                }
            }

    }

    private fun setGreetings() {
        val greetings = bind.txtGoodMorning
        val calendar = Calendar.getInstance()
        val time = Time(calendar.timeInMillis).hours
        if (time > 20)
            greetings.setText(R.string.good_night)
        else if (time > 16)
            greetings.setText(R.string.good_evening)
        else if (time > 12)
            greetings.setText(R.string.good_afternoon)
        else
            greetings.setText(R.string.good_morning)
    }

    private fun setUpViewPager2() {
        val vp2Adapter = VPadapter(this, listOf(DiaryFrag(), Reminders()))
        bind.MFVP2.adapter = vp2Adapter

        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_notification)
        TabLayoutMediator(bind.tab, bind.MFVP2) { tabs, position ->
            tabs.icon = ContextCompat.getDrawable(requireContext(), tabIcons[position])
        }.attach()
        bind.tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> bind.add.setImageResource(R.drawable.ic_feather)
                    1 -> bind.add.setImageResource(R.drawable.ic_add)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}