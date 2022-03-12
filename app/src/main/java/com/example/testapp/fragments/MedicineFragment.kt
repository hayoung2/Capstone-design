package com.example.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MedicineFragment : Fragment() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_medicine, container, false)

        view.findViewById<Button>(R.id.insert_medicine_btn).setOnClickListener {

        }

        view.findViewById<Button>(R.id.insert_btn).setOnClickListener {

        }

        return view
    }

    fun getUserAlarmData() {
        myRef.child("User").child(LoginActivity.currentUser.toString())
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