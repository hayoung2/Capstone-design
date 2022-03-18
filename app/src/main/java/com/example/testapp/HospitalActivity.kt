package com.example.testapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Adapter.HospitalAdapter
import com.example.testapp.data.Hospital
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Response

class HospitalActivity : AppCompatActivity() {
    lateinit var api :API
    lateinit var tv2:TextView
    lateinit var recyclerView: RecyclerView
    lateinit var hospitalAdapter: HospitalAdapter
    var x:Double? = null
    var y:Double? =null

    var hospitalList=arrayListOf<Hospital>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        tv2=findViewById(R.id.tv2)
        val retrofit = RetrofitClient.client
        api=RetrofitClient.getXMLInstance()
        recyclerView=findViewById(R.id.ry_hos)
        hospitalAdapter= HospitalAdapter(this)
        recyclerView.adapter=hospitalAdapter

        search()
    }


    fun search(){

            Log.d("한번 확인 ",MainActivity.selectedNum.toString())
        var hosSearch =api.getHospBasisList(
            serviceKey = "X9NXpvBf0n/7m+qsUT2YBcRruNImwkzffWh1Jk4qwgkp1N35Z1FbJ6mLPd/b81datUmDuBACuJFTZEakx8vKSw==",
            dgsbjtCd =MainActivity.selectedNum.toString(), xPos = MainActivity.uLongitude,yPos = MainActivity.uLatitude,radius = 3000
        )


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
                                Log.d("값 ",i.yadmNm.toString() +"  "+i.telno.toString())
                                hospitalList!!.add(Hospital(i.yadmNm.toString(),i.telno.toString(),i.addr.toString(),i.XPos.toString(),i.YPos.toString()))
                            }
                            hospitalAdapter.datas=hospitalList
                            hospitalAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<HosInfo?>, t: Throwable) {
                   Log.d("실패했으여 ",t.message.toString())
                }
            })
        }
}




