package com.example.golush.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.golush.AppData
import com.example.golush.ui.StepFragment

class StepsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return StepFragment(position)
    }

    override fun getCount(): Int = AppData.steps.size
}