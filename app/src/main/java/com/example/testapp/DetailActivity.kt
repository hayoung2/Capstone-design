package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.Adapter.MedicineAdapter
import com.example.testapp.data.Medicine
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    lateinit var itemName: TextView
    lateinit var entpName: TextView
    lateinit var Llayout: LinearLayout
    lateinit var userMethod: TextView
    lateinit var efcyQesitm: TextView
    lateinit var warnQesItm: TextView
    lateinit var atpnQesItm: TextView
    lateinit var depositMethod: TextView
    lateinit var imgPill:ImageView
    lateinit var api :API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val retrofit = RetrofitClient.client
        api = retrofit.create(API::class.java)

        itemName=findViewById(R.id.d_itemName)
        entpName=findViewById(R.id.d_entpName)
        Llayout=findViewById(R.id.d_layout)
        userMethod=findViewById(R.id.d_useMethod)
        efcyQesitm=findViewById(R.id.d_efcyQesitem)
        warnQesItm=findViewById(R.id.d_warnQesitem)
        atpnQesItm=findViewById(R.id.d_atpnQesitm)
        depositMethod=findViewById(R.id.d_depositMethod)
        imgPill=findViewById(R.id.img_pill)

        itemName.text=intent.getStringExtra("약이름")
        entpName.text=intent.getStringExtra("제조")
        userMethod.text=intent.getStringExtra("사용법")
        efcyQesitm.text=intent.getStringExtra("효능")
        atpnQesItm.text=intent.getStringExtra("부작용")
        depositMethod.text=intent.getStringExtra("보관법")
        Llayout.visibility=View.VISIBLE
        searchImage()


    }

    fun searchImage(){


        var doseSearch=api.getMdcinGrnldntfclnfoList(serviceKey = resources.getString(R.string.pill_api_encoding),itemName=itemName.text.toString(),type = "json")

        doseSearch.enqueue(object : retrofit2.Callback<Responsedose?> {

            override fun onResponse(call: Call<Responsedose?>, response: Response<Responsedose?>) {
                val result = response.body()
                Log.d("값 나옴 ", result.toString())

            }
            override fun onFailure(call: Call<Responsedose?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
