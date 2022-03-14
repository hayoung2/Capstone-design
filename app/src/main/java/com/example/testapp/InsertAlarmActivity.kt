package com.example.testapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class InsertAlarmActivity : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.reference
    lateinit var am: AlarmManager
    lateinit var name: TextInputEditText
    lateinit var title: TextInputEditText
    lateinit var save: Button
    lateinit var timePicker: TimePicker
    lateinit var pi: PendingIntent
    lateinit var myIntent: Intent

    var hour: Int = 0
    var min: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_alarm)

        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        name = findViewById(R.id.al_m_name)
        title = findViewById(R.id.al_title)
        save = findViewById(R.id.al_save)
        timePicker = findViewById(R.id.al_time)
        myIntent = Intent(this, AlarmReceiver::class.java)
        save.setOnClickListener {
            saveAlarmData()
        }

    }

    fun saveAlarmData() {
        myRef.child("User")
            .child(LoginActivity.currentUser.toString()).child("dose")
            .setValue(
                alarmData(
                    title.text.toString(),
                    name.text.toString(),
                    name.text.toString(),
                    name.text.toString(),
                    name.text.toString()
                )
            )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)  // 1
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmReceiver.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


       var calendar =Calendar.getInstance()
       // calendar.set(Calendar.YEAR,2022)
        //calendar.set(Calendar.MONTH,3)
        //calendar.set(Calendar.DAY_OF_MONTH,14)
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.hour)
        calendar.set(Calendar.MINUTE,timePicker.minute)
        calendar.set(Calendar.SECOND,0)



//        alarmManager.setExact(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, pendingIntent);


    }
}


data class alarmData(
    var title:String,
    var name:String,
    var time:String,
    var startDate:String,
    var endDate:String
)