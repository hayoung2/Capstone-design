package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.testapp.data.board
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat

class PostingActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef = firebaseDatabase.reference
    lateinit var post_title:TextView
    lateinit var post_contents:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        post_title=findViewById(R.id.post_title)
        post_contents=findViewById(R.id.post_contents)
        findViewById<ImageView>(R.id.post_exit).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.post_done).setOnClickListener {
            saveData()
            finish()
        }
    }

    fun saveData(){
        var currentTime =System.currentTimeMillis()
        val dateType=SimpleDateFormat("yyyyMMddHHmmss")

       myRef.child("BOARD").push().setValue(board(
            LoginActivity.currentUser.toString(),post_title.text.toString(),dateType.format(currentTime).toString(),post_contents.text.toString()
        ).toMap())


    }
}