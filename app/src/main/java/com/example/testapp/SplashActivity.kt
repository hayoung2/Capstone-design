package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    lateinit var img_splash: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        img_splash=findViewById(R.id.img_splash)
        Glide.with(this).load(R.drawable.firework).into(img_splash)

        var handler= Handler()
        handler.postDelayed({
            var intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        },1700)


    }
}