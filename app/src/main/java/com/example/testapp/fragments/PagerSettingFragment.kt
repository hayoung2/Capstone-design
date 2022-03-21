package com.example.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.example.testapp.InsertAlarmActivity
import com.example.testapp.R
import com.google.android.material.textfield.TextInputEditText

class PagerSettingFragment : Fragment() {

    lateinit var name: TextInputEditText
    lateinit var title: TextInputEditText
    lateinit var mNum:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_pager_setting, container, false)
        // Inflate the layout for this fragment
        name = view.findViewById(R.id.al_m_name)
       title = view.findViewById(R.id.al_title)
        mNum=view.findViewById(R.id.mNum)
        
        name.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InsertAlarmActivity.alarmMName=name.text.toString()
                handled = true
            }
            handled
        }

        title.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InsertAlarmActivity.alarmName=title.text.toString()
                handled = true
            }
            handled
        }
        mNum.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InsertAlarmActivity.mNum=mNum.text.toString()
                handled = true
            }
            handled
        }

        return view
    }

    companion object {
       @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerSettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}