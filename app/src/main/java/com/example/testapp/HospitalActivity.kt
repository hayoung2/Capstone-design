package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class HospitalActivity : AppCompatActivity() {
    lateinit var api :API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        val retrofit = RetrofitClient.client
        api = retrofit.create(API::class.java)

search()
    }

    fun search(){
            Log.d("한번 확인 ",MainActivity.selectedNum.toString())
        var hosSearch = api.getHospBasisList(serviceKey = "mftadouy+o9ROaRw7EADk02FYgh06+F/Xs1rAnkzfifweeV+hg22qr3lLY93MJTTtczz/G33BnlimtbBPwur8Q=="
            ,dgsbjtCd=MainActivity.selectedNum.toInt()
            ,type="json")


            hosSearch.enqueue(object : retrofit2.Callback<ResponseHos?> {
                override fun onResponse(
                    call: Call<ResponseHos?>,
                    response: Response<ResponseHos?>
                ) {
                    val result = response.body()

                    Log.d("TAG", "result : ${result}")
                    Log.d("TAG", "response : ${response}")
                    Log.d("TAG", "response : ${result?.header}")



                    runOnUiThread() {
                        if (result != null) {
                            //tv2.text= result.body.items?.get(0).toString() ?: "dd"
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseHos?>, t: Throwable) {
                   Log.d("실패했으여 ",t.message.toString())
                }
            })
        }
}




