package com.example.testapp.Adapter

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.MainActivity
import com.example.testapp.MapActivity
import com.example.testapp.R
import com.example.testapp.data.Hospital

class HospitalAdapter(private val context: Context) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {
    var datas = mutableListOf<Hospital>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hospital, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = itemView.findViewById(R.id.hos_name)
        private val address: TextView = itemView.findViewById(R.id.hos_address)
        //private val tel: TextView = itemView.findViewById(R.id.hos_tel)
        private val btn: Button = itemView.findViewById(R.id.btn_tel)
        private val layout:LinearLayout=itemView.findViewById(R.id.layout_hos)

        fun bind(item: Hospital) {
            name.text = item.name
            address.text = item.address
           // tel.text = item.tel
            layout.setOnClickListener {
                val intent = Intent(context, MapActivity::class.java)
                intent.putExtra("xPos",item.xPos.toString())
                intent.putExtra("yPos",item.yPos.toString())
                intent.putExtra("hosName",item.name.toString())
                context.startActivity(intent)
            }
            btn.setOnClickListener {
                var intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.tel))
                context.startActivity(intent)
            }

        }
    }


}