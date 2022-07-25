package com.example.testapp.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.LoginActivity
import com.example.testapp.MapActivity
import com.example.testapp.R
import com.example.testapp.data.AlarmData
import com.example.testapp.data.Hospital
import com.example.testapp.data.board
import com.google.firebase.database.*

class Alarmadapter(var context: Context) : RecyclerView.Adapter<Alarmadapter.ViewHolder>() {

    var datas = mutableListOf<AlarmData>()
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ry_alarm, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val alarmName: TextView = itemView.findViewById(R.id.item_alarmName)
        private val alarmM:TextView=itemView.findViewById(R.id.item_alarmM)
        private val alarmUserName:TextView=itemView.findViewById(R.id.item_userName)
        private val alarmDate:TextView=itemView.findViewById(R.id.item_date)
        private val alarmTime:TextView=itemView.findViewById(R.id.item_alarm_time)
        private val alarmNum:TextView=itemView.findViewById(R.id.item_alarm_num)
//        private val alarmDelete:ImageView=itemView.findViewById(R.id.delete_alarm)

        fun bind(item: AlarmData) {
            alarmName.text = item.alarmName
            alarmM.text = item.alarmMName + "   "
            alarmTime.text = item.alarmTime
            alarmDate.text = item.alarmStart + "~" + item.alarmEnd
            alarmNum.text = item.alarmNum + " 정 "
            alarmUserName.text = " By " + item.userName + " 님"

//            alarmDelete.setOnClickListener {
//                myRef.child("User").child(LoginActivity.currentUser.toString()).child("alarm")
//            }
        }
    }


}