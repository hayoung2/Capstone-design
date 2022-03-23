package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Adapter.Alarmadapter
import com.example.testapp.Adapter.MedicineAdapter
import com.example.testapp.data.AlarmData
import com.example.testapp.data.Medicine
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response

class DiseaseSearch : AppCompatActivity() {
    lateinit var api :API
    lateinit var tv1:TextInputEditText
    lateinit var ry_m_list:RecyclerView
    var datas = mutableListOf<Medicine>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_search)
        val retrofit = RetrofitClient.client
        api = retrofit.create(API::class.java)



        tv1=findViewById(R.id.tv1)
        ry_m_list=findViewById(R.id.ry_d_medicine)

        tv1.setOnEditorActionListener { v, actionId, event ->
            var tmp=false
            if(actionId== EditorInfo.IME_ACTION_DONE){
                search()
                tmp=true
            }
            tmp
        }

    }

    fun search(){

        var doseSearch=api.getDrbEasyDrugList(serviceKey ="mftadouy+o9ROaRw7EADk02FYgh06+F/Xs1rAnkzfifweeV+hg22qr3lLY93MJTTtczz/G33BnlimtbBPwur8Q==",efcyQesitm=tv1.text.toString(),type = "json")

        doseSearch.enqueue(object : retrofit2.Callback<Responsedose?> {
            override fun onResponse(call: Call<Responsedose?>, response: Response<Responsedose?>) {
                val result = response.body()

                Log.d("TAG","result : ${result}")
                Log.d("TAG","response : ${response}")

                Log.d("TAG","response : ${result?.header}")



                runOnUiThread(){
                    if (result != null) {

                        for(i in result.body.items!!){
                            var tmp=i.atpnQesitm?.replace("<p>","")?.replace("</p>","")
                            if (tmp.toString()==""){
                                tmp=i.atpnWarnQesitm?.replace("<p>","")?.replace("</p>","")
                            }
                            datas.add(
                                Medicine(
                                i.entpName?.replace("<p>","")?.replace("</p>",""),
                                i.itemName?.replace("<p>","")?.replace("</p>",""),
                                i.efcyQesitm?.replace("<p>","")?.replace("</p>",""),
                                i.useMethodQesitm?.replace("<p>","")?.replace("</p>",""),
                                tmp,
                                i.depositMethodQesitm?.replace("<p>","")?.replace("</p>","")
                            )
                            )
                            Log.d("확인 하자","rkqt ghkrdls 값 확인 "+i.toString())
                        }

                        val medicineAdapter= MedicineAdapter(applicationContext)
                        medicineAdapter.datas=datas
                        Log.d("확인해봐",datas.size.toString()+" 호가인 ")
                        medicineAdapter.notifyDataSetChanged()
                        ry_m_list.layoutManager= LinearLayoutManager(applicationContext)
                        ry_m_list.adapter=medicineAdapter
                    }

                }

            }
            override fun onFailure(call: Call<Responsedose?>, t: Throwable) {
                Log.d("TAG", "실패 : $t")



            }
        })
    }
}