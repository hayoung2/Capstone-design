package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Response

class HospitalActivity : AppCompatActivity() {
    lateinit var api :API
    lateinit var tv2:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        tv2=findViewById(R.id.tv2)
        val retrofit = RetrofitClient.client
        api=RetrofitClient.getXMLInstance()

        search()
    }

    fun search(){
            Log.d("한번 확인 ",MainActivity.selectedNum.toString())
        var hosSearch =api.getHospBasisList(
            serviceKey = "X9NXpvBf0n/7m+qsUT2YBcRruNImwkzffWh1Jk4qwgkp1N35Z1FbJ6mLPd/b81datUmDuBACuJFTZEakx8vKSw==",
            dgsbjtCd =MainActivity.selectedNum.toString())


            hosSearch.enqueue(object : retrofit2.Callback<HosInfo?> {
                override fun onResponse(
                    call: Call<HosInfo?>,
                    response: Response<HosInfo?>
                ) {
                    val result = response.body()

                    Log.d("TAG", "result : ${result}")
                    Log.d("TAG", "response : ${response}")
                    Log.d("TAG", "response : ${result?.header}")
                    Log.d("TAG", "response : ${result?.body}")


                    runOnUiThread() {
                        if (result != null) {
                            for (i in result.body?.items?.item!!){
                                tv2.text ="병원 이름 : "+i.yadmNm+"전화번호 : "+i.telno+"\n"
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<HosInfo?>, t: Throwable) {
                   Log.d("실패했으여 ",t.message.toString())
                }
            })
        }
}




