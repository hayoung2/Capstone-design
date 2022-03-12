package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.testapp.databinding.ActivityMedicineSearchBinding
import retrofit2.Call
import retrofit2.Response

class MedicineSearch : AppCompatActivity() {

    lateinit var api :API

    lateinit var tv1 :TextView
    lateinit var tv2 :TextView
    lateinit var spinner :Spinner
    lateinit var btn :Button
    var curpos:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_search)

        tv1=findViewById(R.id.tv1)
        tv2=findViewById(R.id.tv2)
        spinner=findViewById(R.id.spinner)
        btn=findViewById(R.id.button)

        val retrofit = RetrofitClient.client
        api = retrofit.create(API::class.java)

        curpos=0
        spinner.selectedItem

        spinner.onItemSelectedListener =object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long) {
                curpos = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        btn.setOnClickListener {
            search()
        }

    }


    fun search(){
        if(tv1.text.toString()=="")
            return
        var doseSearch = when(curpos){
            0->api.getDrbEasyDrugList(serviceKey = "mftadouy+o9ROaRw7EADk02FYgh06+F/Xs1rAnkzfifweeV+hg22qr3lLY93MJTTtczz/G33BnlimtbBPwur8Q==",itemName=tv1.text.toString(),type = "json")
            1->api.getDrbEasyDrugList(serviceKey ="mftadouy+o9ROaRw7EADk02FYgh06+F/Xs1rAnkzfifweeV+hg22qr3lLY93MJTTtczz/G33BnlimtbBPwur8Q==",efcyQesitm=tv1.text.toString(),type = "json")

            else ->return
        }
        doseSearch.enqueue(object : retrofit2.Callback<Responsedose?> {
            override fun onResponse(call: Call<Responsedose?>, response: Response<Responsedose?>) {
                val result = response.body()

                Log.d("TAG","result : ${result}")
                Log.d("TAG","response : ${response}")

                Log.d("TAG","response : ${result?.header}")



                runOnUiThread(){
                    if (result != null) {
                        tv2.text= result.body.items?.get(0).toString() ?: "dd"
                    }
                }

            }
            override fun onFailure(call: Call<Responsedose?>, t: Throwable) {
                Log.d("TAG", "실패 : $t")



            }
        })
    }
}