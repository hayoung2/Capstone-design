package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HospitalActivity : AppCompatActivity() {
    lateinit var api :API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        val retrofit = RetrofitClient.client
        api = retrofit.create(API::class.java)

search()
    }

    fun getRetrofitBuild(client: OkHttpClient) = Retrofit.Builder().run {
        baseUrl("http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1")
        client(client)
        //addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        //addConverterFactory(GsonConverterFactory.create(gson))
        addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
        build()
    }

    fun search(){
            Log.d("한번 확인 ",MainActivity.selectedNum)
        var hosSearch = api.getHospBasisList(serviceKey = "mftadouy+o9ROaRw7EADk02FYgh06+F/Xs1rAnkzfifweeV+hg22qr3lLY93MJTTtczz/G33BnlimtbBPwur8Q=="
            ,dgsbjtCd="01")


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




