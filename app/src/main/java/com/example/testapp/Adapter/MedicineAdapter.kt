package com.example.testapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.DetailActivity
import com.example.testapp.R
import com.example.testapp.data.Medicine


class MedicineAdapter (var context: Context) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {
    var datas = mutableListOf<Medicine>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val mEntpName: TextView = itemView.findViewById(R.id.item_d_entpName)
        private val mItemName: TextView = itemView.findViewById(R.id.item_d_itemName)
        private val medicineLayout: LinearLayout = itemView.findViewById(R.id.container_m)


        fun bind(item: Medicine) {
           mEntpName.text=item.entpName
            mItemName.text=item.itemName
            medicineLayout.setOnClickListener {
                val intent= Intent(context,DetailActivity::class.java)
                intent.putExtra("부작용",item.atpnQesitm)
                intent.putExtra("보관법",item.depositMethod)
                intent.putExtra("효능",item.efcyQesitm)
                intent.putExtra("사용법",item.useMethodQesitm)
                intent.putExtra("약이름",item.itemName)
                intent.putExtra("제조",item.entpName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                context.startActivity(intent)
            }

        }
    }
}