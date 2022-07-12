package com.example.testapp.fragments

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapp.*
import com.example.testapp.Adapter.NewsAdapter
import com.example.testapp.data.News
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment : Fragment() {

    val url="https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=103&sid2=241"
    var NewsList = arrayListOf<News>()
    lateinit var hosArr:Array<LinearLayout>
    var arr= arrayOf<String>("05", "08", "52", "10", "12", "14", "11", "13", "03", "80","04","01","24")
    var strArr=arrayOf<String>("좋은 아침이에요", "좋은 오후 보내세요", "좋은 꿈 꾸세요")
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference
    var num=0
    lateinit var userName:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        userName=view.findViewById<TextView>(R.id.userName)
        val medicineSearch=view.findViewById<LinearLayout>(R.id.medicine_search)


        medicineSearch.setOnClickListener {
            val intent = Intent(activity, MedicineSearch::class.java)
            startActivity(intent)
        }

        view.findViewById<LinearLayout>(R.id.disease_search).setOnClickListener {
            val intent = Intent(activity, DiseaseSearch::class.java)
            startActivity(intent)
        }

        view.findViewById<LinearLayout>(R.id.item1).setOnClickListener {
            MainActivity.selectedNum=arr[0]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item2).setOnClickListener {
            MainActivity.selectedNum=arr[1]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item3).setOnClickListener {
            MainActivity.selectedNum=arr[2]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item4).setOnClickListener {
            MainActivity.selectedNum=arr[3]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item5).setOnClickListener {
            MainActivity.selectedNum=arr[4]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item6).setOnClickListener {
            MainActivity.selectedNum=arr[5]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item7).setOnClickListener {
            MainActivity.selectedNum=arr[6]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item8).setOnClickListener {
            MainActivity.selectedNum=arr[7]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item9).setOnClickListener {
            MainActivity.selectedNum=arr[8]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }
        view.findViewById<LinearLayout>(R.id.item10).setOnClickListener {
            MainActivity.selectedNum=arr[9]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }

        view.findViewById<LinearLayout>(R.id.item11).setOnClickListener {
            MainActivity.selectedNum=arr[10]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }

        view.findViewById<LinearLayout>(R.id.item12).setOnClickListener {
            MainActivity.selectedNum=arr[11]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }

        view.findViewById<LinearLayout>(R.id.item13).setOnClickListener {
            MainActivity.selectedNum=arr[12]
            val nextIntent = Intent(context, HospitalActivity::class.java)
            startActivity(nextIntent)
        }





        MyAsyncTask().execute(url)
        val listview=view.findViewById<ListView>(R.id.news_list)
        val adapter= NewsAdapter(view.context, NewsList)


        setTextView()


        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setTextView() {
        val currentTime=LocalDateTime.now()
        val formatType =DateTimeFormatter.ISO_LOCAL_TIME
        var time=currentTime.format(formatType).substring(0 until 2).toInt()
        if(5<time && time < 12){
            num=0
        }else if(12<= time&& time <19){
            num=1
        }else{
            num=2
        }

        userName.text=strArr[num]+"\n"+ LoginActivity.currentName+" 님"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
    }

    fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            context as MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            val mgr = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            Log.d("여기 확인", mgr.toString())

            var isGPSEnabled = mgr?.isProviderEnabled(LocationManager.GPS_PROVIDER)
            // getting network status
            var isNetworkEnabled = mgr?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            try {
                var userNowLocation: Location? = mgr?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                if(isGPSEnabled == true){
                    userNowLocation=mgr?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }else if(isNetworkEnabled==true){
                    userNowLocation=mgr?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }else{
                    Log.d("둘다 ","둘다 불가 ")
                }

               Log.d("확인 다 ", userNowLocation.toString())

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                Log.d("확인 다 ", userNowLocation.toString())

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.EXTRA_PROVIDER_ENABLED)
                Log.d("확인 다 ", userNowLocation.toString())

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                Log.d("확인 다 ", userNowLocation.toString())

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.FUSED_PROVIDER)
                Log.d("확인 다 ", userNowLocation.toString())

                if(userNowLocation==null)
                    userNowLocation= mgr?.getLastKnownLocation(LocationManager.EXTRA_LOCATION_ENABLED)
                Log.d("확인 다 ", userNowLocation.toString())



                MainActivity.uLatitude = userNowLocation!!.latitude
                Log.d("확인", "확인해ㅐ해ㅐ해해해해해햏000000000000000000000000000000000000000")
                Log.d("여기다 여기가 안된다", userNowLocation.toString())
                MainActivity.uLongitude=userNowLocation!!.longitude
                Log.d(
                    "값을 가져와라 제발",
                    MainActivity.uLatitude.toString() + "   " + MainActivity.uLongitude.toString()
                )
                Log.d("들어가니? ", "들어감")
            } catch (e: NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())
                Log.d("값 가져와ㅏ라 ", "에러남")
            }
        } else {
            Toast.makeText(context, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            Log.d("실패", "완전실패")
        }


    }

    inner class MyAsyncTask: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg p0: String?): String {
            val doc: Document =Jsoup.connect("$url").get()
            var elements = doc.select("ul.type06_headline li")

            elements.forEachIndexed { index, element ->

                var title=""
                title=element.select("a").text().toString()
                var url=""
                url=element.select("a").attr("href")
                var contents=""
                contents=element.select("span").text()
                var img=""
                img=element.select("img").attr("src")

                Log.d("확인하자 확인하자 ", title + "   " + url + "   " + contents + "   " + img)

                NewsList.add(News(index.toString(), title, url, contents, img))
            }
            activity?.runOnUiThread(){

                val listview=view?.findViewById<ListView>(R.id.news_list)
                val adapter= NewsAdapter(context as MainActivity, NewsList)
                listview?.adapter=adapter

                var totalHeight = 0
                for (i in 0 until listview?.adapter?.count!!) {
                    val listItem: View? = listview?.adapter?.getView(i, null, listview) //어댑터에 세팅된 아이템정보 가지고 오기.
                    listItem?.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    totalHeight += listItem?.measuredHeight!! //현재 측정된 아이템 높이 만큼 총높이를 증가
                }
                val params: ViewGroup.LayoutParams = listview.getLayoutParams() //현재 리스트뷰의 파라미터값들

                params.height =
                    totalHeight + (listview?.getDividerHeight()?.times((listview?.adapter.count - 1))
                        ?: 1) //현재 리스트뷰의 높이를 측정한 총높이(ListView 자체의 height)에 아이템 수만큼 +

                listview?.setLayoutParams(params) //리스트뷰에 파라미터 세팅

                listview?.requestLayout()

            }
            return doc.title()
        }

    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }

    }


}