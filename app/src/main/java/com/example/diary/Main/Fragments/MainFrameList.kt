package com.example.diary.Main.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFrameList : Fragment(), onDialogDismiss {
    lateinit var bind: FragmentMainFrameListBinding
    lateinit var sharedVM: MainVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_frame_list, container, false)
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        //
        CoroutineScope(Dispatchers.IO).launch {
            updateSwitchesCondition()
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

    override fun onResume() {
        if (sharedVM.addinPermission) {
            CoroutineScope(Dispatchers.IO).launch {
                sharedVM.database.EDBDao().InsertData(sharedVM.addinItem!!)
            }
            sharedVM.addinPermission = false
        }
        Toast.makeText(requireContext(),"Yash",Toast.LENGTH_SHORT).show()
        Log.d("Yash","onResume")
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

        bind.setLock.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.Main).launch {
                if(isChecked && sharedVM.isSkipped()){
                    view.findNavController()
                        .navigate(MainFrameListDirections.actionMainFrameListToSetPassword())
                }
                else{
                    view.findNavController().navigate(R.id.action_mainFrameList_to_popUp)
                }
            }
        }
    }

    override fun onDismissDialog() {
        Log.d("Yash","Hello There")
    }

    suspend fun updateSwitchesCondition(){
        if(sharedVM.isSkipped()){
            bind.setLock.isChecked = false
        }
        else{
            bind.setLock.isChecked = true
        }
    }

}