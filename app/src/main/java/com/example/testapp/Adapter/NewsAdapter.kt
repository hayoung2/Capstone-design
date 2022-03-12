package com.example.testapp.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.data.News

class NewsAdapter(val context: Context,val NewsList:ArrayList<News>):BaseAdapter() {

    override fun getCount(): Int {
        return NewsList.size
    }

    override fun getItem(position: Int): Any {
       return NewsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view:View =LayoutInflater.from(context).inflate(R.layout.list_item_news,null)

        val layout=view.findViewById<ListView>(R.id.news_list)
        val num=view.findViewById<TextView>(R.id.list_news_number)
        val title=view.findViewById<TextView>(R.id.lits_news_title)
        val contents=view.findViewById<TextView>(R.id.list_news_contents)
        val img=view.findViewById<ImageView>(R.id.list_news_img)
        var url=""
        val news=NewsList[position]
        num.text=news.num
        title.text=news.title
        contents.text=news.contents
        url=news.url

//        layout.setOnClickListener {
//            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            view.context.startActivity(intent)
//        }

        Log.d("아아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ몇번 나와니 ","나오니 해봐라")

        return view
    }


}