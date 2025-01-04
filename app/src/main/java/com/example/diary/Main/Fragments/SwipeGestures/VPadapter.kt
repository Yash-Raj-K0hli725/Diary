package com.example.diary.Main.Fragments.SwipeGestures

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPadapter(act: Fragment, val lis: List<Fragment>) : FragmentStateAdapter(act) {

    override fun getItemCount(): Int {
        return lis.size
    }

    override fun createFragment(position: Int): Fragment {
        return lis[position]
    }
}