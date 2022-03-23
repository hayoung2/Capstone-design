package com.example.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.testapp.Adapter.FragmentAdapter
import com.example.testapp.R
import com.google.android.material.tabs.TabLayout


class ScheduleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_schedule, container, false)

        var viewPager=view.findViewById<ViewPager>(R.id.viewPager)
        var tabLayout=view.findViewById<TabLayout>(R.id.tabLayout)

        val fragmentAdapter=FragmentAdapter(parentFragmentManager)
        fragmentAdapter.addFragment(MedicineFragment(),"오늘의 약")
        fragmentAdapter.addFragment(CalendarFragment(),"일정 달력")
        viewPager.adapter=fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)



        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}