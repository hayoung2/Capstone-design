package com.example.testapp.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.testapp.Adapter.Alarmadapter
import com.example.testapp.Adapter.BoardAdapter
import com.example.testapp.Adapter.FragmentAdapter
import com.example.testapp.Adapter.HospitalAdapter
import com.example.testapp.InsertAlarmActivity
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.data.AlarmData
import com.example.testapp.data.board
import com.example.testapp.data.comment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.HashMap


class ScheduleFragment : Fragment() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    var datas = mutableListOf<AlarmData>()
    lateinit var rv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_schedule, container, false)
        rv=view.findViewById(R.id.ry_alarm)

       // rv.adapter=alarmAdapter

        view.findViewById<Button>(R.id.insert_medicine_btn).setOnClickListener {
            val intent= Intent(context,InsertAlarmActivity::class.java )
            startActivity(intent)
        }

        loadData()

        return view
    }
    fun loadData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                for (i in dataSnapshot.children) {

                    Log.d("TAG", "datasnapshot.i ${i.value.toString()}")
                    var key = i.key
                    var map = i.value as Map<String, Any>
                    var user = map["userName"].toString()
                    var alarmTitle = map["alarmName"].toString()
                    var alarmStart = map["alarmStart"].toString()
                    var alarmEnd=map["alarmEnd"].toString()
                    var alarmTime = map["alarmTime"].toString()
                    var alarmM=map["alarmMName"].toString()
                    var alarmNum=map["alarmNum"].toString()

                    datas.add(AlarmData(alarmTitle,alarmM,alarmNum,alarmStart,alarmEnd,alarmTime,user))

                }

                val alarmAdapter= Alarmadapter(context as MainActivity)
                alarmAdapter.datas=datas
                Log.d("확인해봐",datas.size.toString()+" 호가인 ")
                alarmAdapter.notifyDataSetChanged()
                rv.layoutManager=LinearLayoutManager(context)
                rv.adapter=alarmAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }

        }
        databaseReference.child("User").child(LoginActivity.currentUser.toString())
            .child("alarm").addValueEventListener(postListener)


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