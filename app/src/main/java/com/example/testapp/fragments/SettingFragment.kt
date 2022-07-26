package com.example.testapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.MyPostingListActivity
import com.example.testapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.NullPointerException


class SettingFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference
    lateinit var userName :TextView
    lateinit var userPostList:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_setting, container, false)
        userName=view.findViewById(R.id.setting_userName)
        mAuth = FirebaseAuth.getInstance()
        userPostList=view.findViewById(R.id.txt_post_list)

        view.findViewById<TextView>(R.id.logout).setOnClickListener {
            logout()

        }

        view.findViewById<TextView>(R.id.delete).setOnClickListener {
            delete()
        }

        userPostList.setOnClickListener {
            val intent=Intent(context,MyPostingListActivity::class.java)
            startActivity(intent)
        }

        userName.text=" \" "+LoginActivity.currentName.toString()+" \" 님 "
        Log.d("유형",LoginActivity.type.toString())
        Log.d(" 값 ",LoginActivity.profileImg.toString())

        if(LoginActivity.type=="g"){

                Glide.with(view).load(Uri.parse(LoginActivity.profileImg)).into(view.findViewById(R.id.user_img))
        }
        //view.findViewById<CircleImageView>(R.id.user_img).setImageResource(R.drawable.user_icon)
        return view
    }


    fun logout(){
        when(LoginActivity.type){
            "k" -> {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e("확인", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    }
                    else {

                        Log.d("확인", "로그아웃 성공. SDK에서 토큰 삭제됨")
                        moveActivity()
                    }
                }
            }
            "g" -> {
                Toast.makeText(context, "로그아웃 완료", Toast.LENGTH_SHORT).show()
                mAuth.signOut()
                moveActivity()
            }
        }
    }

    fun delete() {
        when(LoginActivity.type){
            "k" -> {
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.d("오류남 ", error.toString())
                        Toast.makeText(context, "탈퇴 실패 하셨습니다", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context, "탈퇴 완료 하셨습니다", Toast.LENGTH_SHORT).show()
                        moveActivity()
                    }
                }

            }
            "g" -> {
                myRef.child("User").child(LoginActivity.currentUser.toString()).removeValue()
                mAuth.currentUser?.delete()
                Toast.makeText(context, "탈퇴 완료 하셨습니다", Toast.LENGTH_SHORT).show()
                moveActivity()
            }
        }
    }

    fun moveActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}