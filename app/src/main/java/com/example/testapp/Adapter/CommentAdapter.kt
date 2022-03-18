package com.example.testapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.comment
import java.util.*

class CommentAdapter(var _context : Context, val commentlist: MutableList<comment>) : RecyclerView.Adapter<CommentAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(_context).inflate(R.layout.item_comment,parent,false)
        return CustomViewHolder(view) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.user.text=commentlist.get(position).user
        holder.content.text=commentlist.get(position).content
        holder.date.text=commentlist.get(position).datetime

        holder.itemView.setOnClickListener{



        }

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val user =itemView.findViewById<TextView>(R.id.tv_comment_user)
        val date =itemView.findViewById<TextView>(R.id.tv_comment_date)
        val content =itemView.findViewById<TextView>(R.id.tv_comment_content)



    }

}