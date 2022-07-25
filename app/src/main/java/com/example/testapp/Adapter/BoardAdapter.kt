package com.example.testapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.PostActivity
import com.example.testapp.R
import com.example.testapp.data.board
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class BoardAdapter(var _context : Context, val boardList: MutableList<board>) : RecyclerView.Adapter<BoardAdapter.CustomViewHolder>() {

    var lastPostion:Int=1
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef = firebaseDatabase.reference

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(_context).inflate(R.layout.item_board,parent,false)
        return CustomViewHolder(view) // inflater -> 부착
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.title.text=boardList.get(position).title
        holder.content.text=boardList.get(position).content
        myRef.child("User").child(boardList.get(position).user)
            .child("name").get().addOnSuccessListener {
                holder.user.text="작성자 : "+it.value.toString()+" 님"
            }

        holder.itemView.setOnClickListener{
            if(LoginActivity.currentUser=="")
                return@setOnClickListener
            val intent=Intent(_context, PostActivity::class.java)
            intent.putExtra("curuser",LoginActivity.currentName)
            intent.putExtra("postkey",boardList.get(position).key)
            intent.putExtra("title",boardList.get(position).title)
            intent.putExtra("content",boardList.get(position).content)
            intent.putExtra("datetime",boardList.get(position).datetime)
            intent.putExtra("postuser",boardList.get(position).user)

            _context.startActivity(intent)

        }

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title =itemView.findViewById<TextView>(R.id.tv_title)
        val content =itemView.findViewById<TextView>(R.id.tv_content)
        val user=itemView.findViewById<TextView>(R.id.tv_user)



    }

}