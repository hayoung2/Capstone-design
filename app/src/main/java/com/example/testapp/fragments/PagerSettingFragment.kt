package com.example.testapp.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.example.testapp.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.googlecode.tesseract.android.TessBaseAPI
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class PagerSettingFragment : Fragment() {

    lateinit var name: TextInputEditText
    lateinit var title: TextInputEditText
    lateinit var mNum:EditText
    lateinit var btnPhoto:ImageView
    lateinit var api: ServiceKakaoApi
    lateinit var boxarray:ArrayList<box>
    var UserUid=-1
    val REQUEST_IMAGE_CAPTURE=1
    lateinit var imageBitmap:Bitmap
    lateinit var tess : TessBaseAPI


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_pager_setting, container, false)
        // Inflate the layout for this fragment
        name = view.findViewById(R.id.al_m_name)
       title = view.findViewById(R.id.al_title)
        mNum=view.findViewById(R.id.mNum)
        btnPhoto=view.findViewById(R.id.btn_photo)
        val retrofit = RetrofitClientkakao.client


        // kakao api 초기화 코드 처음에 한번실행해서 설정해야함
        KakaoSdk.init(requireContext(), "{e8712179e2dea93762d44b45786da674}") // 여기안에 네이티브 키 들어가면됨
        // kakao api 초기화 코드 처음에 한번실행해서 설정해야함

        // kakao hash key 가져오는 코드 - 작업환경에따라 달라져서 한번 실행해서 키값을 카카오 디벨로퍼에 넣어야함
       // val keyHash: String = com.kakao.util.helper.Utility.getKeyHash(this /* context */)
        //Log.d("TAG","-----:${keyHash}")
        // kakao hash key 가져오는 코드 - 작업환경에따라 달라져서 한번 실행해서 키값을 카카오 디벨로퍼에 넣어야함




        api = retrofit.create(ServiceKakaoApi::class.java)
        
        name.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InsertAlarmActivity.alarmMName=name.text.toString()
                handled = true
            }
            handled
        }

        title.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InsertAlarmActivity.alarmName=title.text.toString()
                handled = true
            }
            handled
        }
        mNum.setOnEditorActionListener { v, actionId, event ->
            var handled=false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InsertAlarmActivity.mNum=mNum.text.toString()
                handled = true
            }
            handled
        }

        btnPhoto.setOnClickListener {
            //dispatchTakePictureIntent()
           openGallery()
//            view.findViewById<EditText>(R.id.item_alarmM).text=imgtoText(imageToBitmap(binding.ivOcr))
        }

        return view
    }
//
////    fun dispatchTakePictureIntent() {
////        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent->
////            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
////                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
////            }
////        }
////
////    }
////
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////
////        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
////            val imageBitmap = data!!.extras?.get("data") as Bitmap
////            val image = FirebaseVisionImage.fromBitmap(imageBitmap)
////            val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
////                .setLanguageHints(listOf("ko", "안녕"))
////                .build()
////
////        }
////    }
//
//
    private  val OPEN_GALLERY=1
    private fun openGallery(){
        val intent: Intent = Intent()
        intent.setDataAndType(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        );
        intent.action=Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Load Picture"), OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if(requestCode==OPEN_GALLERY){
                var currentImageUrl: Uri? = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, currentImageUrl)
                   // binding.ivOcr.setImageBitmap(bitmap) // 비트맵으로 표현
                    imgToText(bitmap)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

        }
    }
//
//    private fun imageToBitmap(image: ImageView): Bitmap {
//        val bitmap = (image.drawable as BitmapDrawable).bitmap
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 40, stream)
//
//        return bitmap
//    }
//
    private fun imgToText(bitmap: Bitmap): String{
        var resulttext=""
        Log.d("TAG", "img to text 실행")

        var filepath= requireActivity().getExternalFilesDir(null).toString() +"/wordbook"
        var dir= File(filepath)
        if(!dir.exists())
            dir.mkdirs()

        var fileName="temp.png"
        var file = File(dir, fileName)
        filepath=file.absolutePath

        file.writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 50)
        //var file = File(filepath+"/"+fileName)
        Log.d("DEBUG", "$filepath")
        file= File(filepath)
        Log.d("TAG", "파일 저장 완료")

        //file = File(filepath+"/"+fileName)

        CoroutineScope(IO).launch {
            boxarray= arrayListOf()
            var isfinish=false

            val requestBody: RequestBody
            val body: MultipartBody.Part
            val mapRequestBody =
                LinkedHashMap<String, RequestBody>()
            val arrBody: ArrayList<MultipartBody.Part> = ArrayList()

            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            mapRequestBody["file\"; filename=\"" + file.name] = requestBody
            mapRequestBody["test"] = RequestBody.create(
                MediaType.parse("text/plain"),
                "gogogogogogogog"
            )

            body = MultipartBody.Part.createFormData("image", file.name, requestBody)
            arrBody.add(body)

            api.imagetoText(mapRequestBody, arrBody).enqueue(object : Callback<responseimgtotxt?> {
                override fun onResponse(
                    call: Call<responseimgtotxt?>,
                    response: Response<responseimgtotxt?>
                ) {
                    val result = response.body()
                    Log.d(
                        "TAG",
                        "code : ${
                            response.code().toString()
                        } ,message : ${response.message()}, ${response.errorBody()} "
                    )
                    if (result != null) {
                        var resultarr = result.result
                        var lastboxint = -1
                        for (i in resultarr) {
                            var temp = ""
                            for (j in i.recognition_words!!) {
                                temp += "$j"
                            }
                            if (lastboxint != -1 && lastboxint < ((i.boxes.get(0)
                                    .get(1) + i.boxes.get(
                                    2
                                ).get(1)) / 2)
                            )
                                temp = "\n" + temp
                            else if (lastboxint != -1)
                                temp = " " + temp
                            lastboxint = i.boxes.get(2).get(1)

                            boxarray.add(
                                box(
                                    i.boxes.get(0).get(1),
                                    i.boxes.get(0).get(0),
                                    temp
                                )
                            )
                            Log.d("TAG", "box 생성 $temp")

                        }
                        //tv_wordfind_result.text=resulttext
                        Log.d("TAG", "-----------------------성공! ")
                        isfinish = true
                    }
                }

                override fun onFailure(call: Call<responseimgtotxt?>, t: Throwable) {

                    Log.d("TAG", "-----------------------실패 : 이미지 -> text $t")
                    isfinish = true
                }
            })
            withContext(Main) {
                while (!isfinish){
                    delay(100)
                }
                Log.d("TAG", "-------------MAIN 박스업데이트 수행중")
                //boxarray.sort() //sort 안하고 카카오에서 준대로 하는게 더 효과가 좋은듯
                var text=""
                for ( i in boxarray){
                    text +="${i.text}"
                    Log.d("TAG", "텍스트 출력--------" + i.text)
                }
                name.setText(text)


            }
        }


        return resulttext
    }
//
    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
            out.close()
        }
    }



    companion object {
       @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PagerSettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}