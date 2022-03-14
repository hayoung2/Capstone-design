package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testapp.InsertAlarmActivity
import com.example.testapp.MedicineSearch
import com.example.testapp.R


class MedicineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_medicine, container, false)

        view.findViewById<Button>(R.id.insert_medicine_btn).setOnClickListener {
            val intent = Intent(activity, InsertAlarmActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.insert_btn).setOnClickListener {

        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MedicineFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}