package com.example.testapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.example.testapp.InsertAlarmActivity
import com.example.testapp.R


class PagerStartFragment : Fragment() {

    lateinit var calendar:CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view=inflater.inflate(R.layout.fragment_pager_start, container, false)
        calendar=view.findViewById(R.id.calendarView_Start)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d("화긴해 ",(month+1).toString()+"  "+dayOfMonth.toString())
            InsertAlarmActivity.alarmStart=year.toString().substring(2)+"."+(month+1).toString()+"."+dayOfMonth.toString()
        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerStartFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}