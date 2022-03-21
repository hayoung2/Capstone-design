package com.example.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.example.testapp.InsertAlarmActivity
import com.example.testapp.R


class PagerEndFragment : Fragment() {

    lateinit var calendar: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pager_end, container, false)
        calendar=view.findViewById(R.id.calendarView_end)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            InsertAlarmActivity.alarmEnd=year.toString().substring(2)+"."+(month+1).toString()+"."+dayOfMonth.toString()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerEndFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}