package com.example.testapp.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testapp.*
import com.example.testapp.Adapter.NewsAdapter
import com.example.testapp.data.News
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class HomeFragment : Fragment() {


    val url="https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=103&sid2=241"
    var NewsList = arrayListOf<News>()
    lateinit var hosArr:Array<LinearLayout>
    var arr= arrayOf<String>("05","08","52","10","12","14","11","13","03","80")
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        val userName=view.findViewById<TextView>(R.id.userName)
        val medicineSearch=view.findViewById<LinearLayout>(R.id.medicine_search)


        medicineSearch.setOnClickListener {
            val intent = Intent(activity, MedicineSearch::class.java)
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
            com.example.testapp.MainActivity.selectedNum=arr[4]
            val nextIntent =
                android.content.Intent(context, com.example.testapp.HospitalActivity::class.java)
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





        MyAsyncTask().execute(url)
        val listview=view.findViewById<ListView>(R.id.news_list)
        val adapter= NewsAdapter(view.context, NewsList)

        userName.text=userName.text.toString() + getName()

        return view
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

    fun getName():String {
        myRef.child("User").child(LoginActivity.currentUser.toString()).get().addOnSuccessListener {
            Log.d("아확인헤ㅐ유 ",it.value.toString())

        }

        return ""
    }
}