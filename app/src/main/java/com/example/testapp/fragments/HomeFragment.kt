package com.example.testapp.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.data.News
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HomeFragment : Fragment() {

    val url="https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=103&sid2=241"
    lateinit var NewsData:ArrayList<News>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        val userName=view.findViewById<TextView>(R.id.userName)
        userName.text=userName.text.toString() + LoginActivity.currentUser?.kakaoAccount?.profile?.nickname
        NewsData=ArrayList<News>()

        MyAsyncTask().execute(url)

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
                Log.d("확인하자 확인하자 ", title+"   "+url+"   "+contents+"   "+img)

            NewsData.add(News(index,title,url,contents,img))
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