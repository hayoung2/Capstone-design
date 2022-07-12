package com.example.testapp.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Adapter.BoardAdapter
import com.example.testapp.LoginActivity
import com.example.testapp.MainActivity
import com.example.testapp.PostingActivity
import com.example.testapp.R
import com.example.testapp.data.board
import com.example.testapp.data.comment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.ArrayList
import java.util.HashMap


class SearchFragment : Fragment() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference

    lateinit var rv: RecyclerView
    lateinit var contents:EditText

    companion object{
        var boardlist: ArrayList<board> = ArrayList()
        var currentuser:String=""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_search, container, false)

        rv=view.findViewById(R.id.rv_board)
        currentuser=LoginActivity.currentUser.toString()
        loaddata()

        view.findViewById<ImageButton>(R.id.btn_post).setOnClickListener {
            val intent= Intent(context,PostingActivity::class.java)
            startActivity(intent)
        }
        contents=view.findViewById<EditText>(R.id.search_community)
        view.findViewById<ImageButton>(R.id.btn_community_search).setOnClickListener {
            loaddata()
        }
        return view
    }

    fun searchData(contents : String) {
        databaseReference.child("BOARD").orderByChild("content").startAt(contents).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val result=snapshot.value
                Log.d("확인 ",result.toString())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    fun loaddata() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                boardlist = ArrayList()

                for (i in dataSnapshot.children) {

                    Log.d("TAG", "datasnapshot.i ${i.toString()}")
                    var key = i.key
                    var map = i.value as Map<String, Any>
                    var user = map["user"].toString()
                    var title = map["title"].toString()

                    if(contents.text.toString()!="" && !title.contains(contents.text.toString())){
                        continue
                    }

                    var datetime = map["datetime"].toString()
                    var content = map["content"].toString()
                    var arr: ArrayList<comment> = ArrayList()
                    if(!map["comment"].toString().equals("null")){
                        var comment = map["comment"] as HashMap<String, Any>

                        for (key in comment.keys) {
                            val i = comment[key]

                            i as HashMap<String, Any>
                            Log.d("TAG", i.toString())
                            arr.add(
                                comment(
                                    key = key,
                                    user = i["user"] as String,
                                    content = i["content"] as String,
                                    datetime = i["datetime"] as String
                                )
                            )
                        }
                    }

                    if (key != null)
                        boardlist.add(board(key, user, title, datetime, content, arr))

                }

                var boardAdapter = BoardAdapter(context as MainActivity, boardlist)
                rv.layoutManager = LinearLayoutManager(context as MainActivity)
                rv.adapter = boardAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }

        }
        databaseReference.child("BOARD").addValueEventListener(postListener)
    }



}