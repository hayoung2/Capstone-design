package com.example.testapp

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Adapter.CommentAdapter
import com.example.testapp.data.comment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PostActivity : AppCompatActivity() {

    lateinit var postkey:String
    lateinit var curuser:String
    lateinit var content:String
    lateinit var datetime:String
    lateinit var title:String
    lateinit var postuser:String
    lateinit var tv_datetimeuser:TextView
    lateinit var tv_title:TextView
    lateinit var tv_content:TextView
    lateinit var rv_comment:RecyclerView
    lateinit var constraintLayout:ConstraintLayout
    lateinit var tv_addcomment:EditText
    lateinit var btn_addcomment: Button
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference
    lateinit var rvcomment: RecyclerView

    var commentlist: MutableList<comment> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        postkey=intent.getStringExtra("postkey").toString()
        curuser= intent.getStringExtra("curuser").toString()
        postuser= intent.getStringExtra("postuser").toString()
        title= intent.getStringExtra("title").toString()
        content= intent.getStringExtra("content").toString()
        datetime= intent.getStringExtra("datetime").toString()


        tv_datetimeuser=findViewById(R.id.tv_datetimeuser)
        tv_title=findViewById(R.id.tv_title)
        tv_content=findViewById(R.id.tv_content)
        tv_addcomment=findViewById(R.id.tv_addcomment)
        rv_comment=findViewById(R.id.rv_comment)
        constraintLayout=findViewById(R.id.constraintLayout)
        btn_addcomment=findViewById(R.id.btn_addcomment)

        tv_datetimeuser.text=datetime+"     "+postuser
        tv_title.text=title
        tv_content.text=content


        loadData()

        constraintLayout.bringToFront()
        tv_addcomment.bringToFront()
        btn_addcomment.bringToFront()

        rvcomment=rv_comment

        btn_addcomment.setOnClickListener {
            var str = tv_addcomment.text.toString()
            if(str=="")
                return@setOnClickListener

            var curdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            var newcomment=comment(curuser,str,curdate)

            databaseReference.child("BOARD").child(postkey).child("comment").push().setValue(newcomment)

            tv_addcomment.setText("")

        }

    }


    fun loadData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                var map = dataSnapshot.value as Map<String,Any>
                commentlist = ArrayList()
                if(!map["comment"].toString().equals("null")){
                    var comment = map["comment"] as HashMap<String, Any>

                    for (key in comment.keys){
                        val i =comment[key]

                        i as HashMap<String, Any>
                        Log.d("TAG",i.toString())
                        commentlist.add(comment(key= key , user= i["user"] as String, content= i["content"] as String, datetime= i["datetime"] as String ))
                    }

                    Collections.sort(commentlist)
                }


                var commentadapter = CommentAdapter(this@PostActivity,commentlist = commentlist )
                rv_comment.layoutManager= LinearLayoutManager(this@PostActivity)
                rv_comment.adapter = commentadapter

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.child("BOARD").child(postkey).addValueEventListener(postListener)
    }

}