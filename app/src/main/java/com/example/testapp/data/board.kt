package com.example.testapp.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


public class board{
    public lateinit var user:String
    public lateinit var title :String
    public lateinit var datetime: String
    public lateinit var content :String
    lateinit var key :String
    var comment :MutableList<comment>?=null






    constructor(key :String, user: String, title:String, datetime: String, content:String, comment: ArrayList<comment>?=null){
        this.user=user
        this.key=key

        this.title=title
        this.datetime=datetime
        this.content=content
        this.comment=comment

    }
    fun toMap():HashMap<String,Any>{

        var result :HashMap<String,Any>  = HashMap()

        result.put("content", content);
        result.put("user", user);
        result.put("title", title);
        result.put("datetime", datetime.toString());
        result.put("comment", comment?:"null")




        return result
    }

}

class comment : Comparable<Any> {
    lateinit var user: String
    lateinit var content: String
    lateinit var datetime: String
    var key :String?=null


    constructor(key: String, user :String,content: String,datetime: String){
        this.user =user
        this.key=key
        this.content=content
        this.datetime=datetime
    }
    constructor(user :String,content: String,datetime: String){
        this.user =user
        this.content=content
        this.datetime=datetime
    }

    fun toMap():HashMap<String,Any>{

        val now = LocalDateTime.now()


        var result :HashMap<String,Any>  = HashMap()
        result.put("content", content);
        result.put("user", user);
        result.put("datetime", datetime);

        return result
    }

    override fun compareTo(other: Any): Int {
        return datetime.compareTo((other as comment).datetime)
    }


}
