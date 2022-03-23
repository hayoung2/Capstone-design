package com.example.testapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.viewpager.widget.ViewPager
import com.example.testapp.Adapter.FragmentAdapter
import com.example.testapp.fragments.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class InsertAlarmActivity : AppCompatActivity() {

    companion object{
        var alarmName:String?=null
        var alarmMName:String?=null
        var alarmStart:String?=null
        var alarmEnd:String?=null
        var mNum:String?=null
        var alarmTime:String?=null
    }
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.reference
    lateinit var am: AlarmManager
    lateinit var save: Button
    lateinit var timePicker: TimePicker
    lateinit var pi: PendingIntent
    lateinit var myIntent: Intent

    var hour: Int = 0
    var min: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_alarm)

//        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        name = findViewById(R.id.al_m_name)
//        title = findViewById(R.id.al_title)
//        save = findViewById(R.id.al_save)
//        timePicker = findViewById(R.id.al_time)
//        myIntent = Intent(this, AlarmReceiver::class.java)
//        save.setOnClickListener {
//            saveAlarmData()
//
//        }

        var viewPager=findViewById<ViewPager>(R.id.viewPager)
        var tabLayout=findViewById<TabLayout>(R.id.tabLayout)

        val fragmentAdapter= FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(PagerSettingFragment(),"알림")
        fragmentAdapter.addFragment(PagerStartFragment(),"시작일")
        fragmentAdapter.addFragment(PagerEndFragment(),"마지막일")
        fragmentAdapter.addFragment(PagerTimeFragment(),"시간")
        viewPager.adapter=fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

    }



}


data class alarmData(
    var title:String,
    var name:String,
    var time:String,
    var startDate:String,
    var endDate:String
)