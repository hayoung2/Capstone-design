package com.example.testapp.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.testapp.*
import com.example.testapp.data.AlarmData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class PagerTimeFragment : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.reference
    lateinit var timePicker: TimePicker
    lateinit var save: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pager_time, container, false)
        timePicker = view.findViewById(R.id.al_time)
        save=view.findViewById(R.id.save)
        save.setOnClickListener {
            saveAlarmData()

        }

        return view
    }

    fun saveAlarmData() {
        Log.d("호가인 ", InsertAlarmActivity.alarmName.toString()+
            InsertAlarmActivity.alarmMName.toString()+
            InsertAlarmActivity.alarmStart.toString()+
            InsertAlarmActivity.alarmEnd.toString()+"ghkrdls 확ㅌ인 ")
        myRef.child("User")
            .child(LoginActivity.currentUser.toString()).child("alarm").push()
            .setValue(
                AlarmData(
                    InsertAlarmActivity.alarmName.toString(),
                    InsertAlarmActivity.alarmMName.toString(),
                    InsertAlarmActivity.mNum.toString(),
                    InsertAlarmActivity.alarmStart.toString(),//시작 시간 나중에 설정
                    InsertAlarmActivity.alarmEnd.toString(),//끝나는 시간 나중ㅇ ㅔ 설정
                    timePicker.hour.toString()+":"+timePicker.minute.toString(),
                    LoginActivity.currentName.toString()
                )
            )

        val alarmManager = context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("name",InsertAlarmActivity.alarmName.toString())// 1
        val pendingIntent = PendingIntent.getBroadcast(
            context, AlarmReceiver.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        var calendar = Calendar.getInstance()
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

        activity?.finish()
    }
    companion object {

        //
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerTimeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}