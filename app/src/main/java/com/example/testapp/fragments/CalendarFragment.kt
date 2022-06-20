package com.example.testapp.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.testapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.FileInputStream
import java.io.FileOutputStream


class CalendarFragment : Fragment() {
    var userID: String = "userID"
    lateinit var fname: String
    lateinit var str: String
    lateinit var calendarView: CalendarView
    lateinit var updateBtn: Button
    lateinit var deleteBtn:Button
    lateinit var saveBtn:Button
    lateinit var diaryTextView: TextView
    lateinit var diaryContent:TextView
    lateinit var contextEditText: EditText
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView=view.findViewById(R.id.calendarView)
        diaryTextView=view.findViewById(R.id.diaryTextView)
        saveBtn=view.findViewById(R.id.saveBtn)
        deleteBtn=view.findViewById(R.id.deleteBtn)
        updateBtn=view.findViewById(R.id.updateBtn)
        diaryContent=view.findViewById(R.id.diaryContent)
        contextEditText=view.findViewById(R.id.contextEditText)



        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            diaryTextView.visibility = View.VISIBLE
            saveBtn.visibility = View.VISIBLE
            contextEditText.visibility = View.VISIBLE
            diaryContent.visibility = View.INVISIBLE
            updateBtn.visibility = View.INVISIBLE
            deleteBtn.visibility = View.INVISIBLE
            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            contextEditText.setText("")
            checkDay(year, month, dayOfMonth, userID)
        }

        saveBtn.setOnClickListener {
            saveDiary(fname)
            contextEditText.visibility = View.INVISIBLE
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            str = contextEditText.text.toString()
            diaryContent.text = str
            diaryContent.visibility = View.VISIBLE
        }

        return view
    }

    fun checkDay(cYear: Int, cMonth: Int, cDay: Int, userID: String) {
        //저장할 파일 이름설정
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        var fileInputStream: FileInputStream
        try {
            fileInputStream = requireActivity().openFileInput(fname)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
            contextEditText.visibility = View.INVISIBLE
            diaryContent.visibility = View.VISIBLE
            diaryContent.text = str
            saveBtn.visibility = View.INVISIBLE
            updateBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            updateBtn.setOnClickListener {
                contextEditText.visibility = View.VISIBLE
                diaryContent.visibility = View.INVISIBLE
                contextEditText.setText(str)
                saveBtn.visibility = View.VISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryContent.text = contextEditText.text
            }
            deleteBtn.setOnClickListener {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                removeDiary(fname)
            }
            if (diaryContent.text == null) {
                diaryContent.visibility = View.INVISIBLE
                updateBtn.visibility = View.INVISIBLE
                deleteBtn.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                saveBtn.visibility = View.VISIBLE
                contextEditText.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // 달력 내용 제거
    @SuppressLint("WrongConstant")
    fun removeDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    // 달력 내용 추가
    @SuppressLint("WrongConstant")
    fun saveDiary(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = requireActivity().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = contextEditText.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}