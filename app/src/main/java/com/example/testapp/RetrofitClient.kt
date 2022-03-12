package com.example.testapp


import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/"
    private var retrofit: Retrofit? = null
    val client: Retrofit
        get() {
            while (retrofit == null) {
                retrofit= Retrofit.Builder().baseUrl(BASE_URL) .addConverterFactory(
                    GsonConverterFactory.create()).build();
            }
            return retrofit!!
        }


}

data class Responsedose(
    @SerializedName("header") val header:dos_header,
    @SerializedName("body") val body:dos_body,
    )
data class ResponseHos(
    @SerializedName("header") val header:hos_header,
    @SerializedName("body") val body:hos_body
)

data class dos_header(
    @SerializedName("resultCode") val resultCode:Int,
    @SerializedName("resultMsg") val resultMsg:String,
)
data class dos_body(
    @SerializedName("pageNo") val pageNo:String?,
    @SerializedName("totalCount") val totalCount:String?,
    @SerializedName("numOfRows") val numOfRows:String?,

    @SerializedName("items") val items: List<doseitem>?
)

data class hos_header(
    @SerializedName("resultCode") val resultCode:Int,
    @SerializedName("resultMsg") val resultMsg:String,
)

data class hos_body(
    @SerializedName("pageNo") val pageNo:String?,
    @SerializedName("totalCount") val totalCount:String?,
    @SerializedName("numOfRows") val numOfRows:String?,

    @SerializedName("items") val items: List<hosItem>?
)

data class doseitem(
    @SerializedName("entpName") val entpName:String?,
    @SerializedName("itemName") val itemName:String?,
    @SerializedName("itemSeq") val itemSeq:String?,
    @SerializedName("efcyQesitm") val efcyQesitm:String?,
    @SerializedName("useMethodQesitm") val useMethodQesitm:String?,

    @SerializedName("atpnWarnQesitm") val atpnWarnQesitm:String?,
    @SerializedName("atpnQesitm") val atpnQesitm:String?,
    @SerializedName("intrcQesitm") val intrcQesitm:String?,

    @SerializedName("seQesitm") val seQesitm:String?,
    @SerializedName("depositMethodQesitm") val depositMethodQesitm:String?,

    @SerializedName("openDe") val openDe:String?,
    @SerializedName("updateDe") val updateDe:String?,

    @SerializedName("itemImage") val type:String?
)


//병원 데이터
data class hosItem(
    @SerializedName("yadmNm") val yadmNm:String?,
    @SerializedName("addr") val addr:String?,
    @SerializedName("telno") val telno:String?,
    @SerializedName("hospUrl") val hospUrl:String?=null,
    @SerializedName("XPos") val atpnWarnQesitm:String?,
    @SerializedName("YPos") val atpnQesitm:String?
)



interface API {
    @GET("1471000/DrbEasyDrugInfoService/getDrbEasyDrugList")
    fun getDrbEasyDrugList(
        @Query("serviceKey") serviceKey:String,
        @Query("pageNo") pageNo:Int?=1,
        @Query ("numOfRows")  numOfRows:Int?=10,
        @Query ("entpName")  entpName:String?=null,
        @Query ("itemName")  itemName:String?=null,
        @Query ("itemSeq")  itemSeq:String?=null,
        @Query ("efcyQesitm")  efcyQesitm:String?=null,
        @Query ("useMethodQesitm")  useMethodQesitm:String?=null,

        @Query ("atpnWarnQesitm")  atpnWarnQesitm:String?=null,
        @Query ("atpnQesitm")  atpnQesitm:String?=null,
        @Query ("intrcQesitm")  intrcQesitm:String?=null,

        @Query ("seQesitm")  seQesitm:String?=null,
        @Query ("depositMethodQesitm")  depositMethodQesitm:String?=null,

        @Query ("openDe")  openDe:String?=null,
        @Query ("updateDe")  updateDe:String?=null,

        @Query ("type")  type:String="json"
    ): Call<Responsedose?>

    @GET("B551182/hospInfoService1/getHospBasisList1")
    fun getHospBasisList(
        @Query("serviceKey") serviceKey:String,
        @Query("pageNo") pageNo:Int?=1,
        @Query ("numOfRows")  numOfRows:Int?=10,
        @Query("sidoCd") sidoCd:Int?=null,
        @Query("sgguCd") sgguCd:Int?=null,
        @Query("emdongNm") emdongNm:String?=null,
        @Query("yarmNm") yarmNm:String?=null,
        @Query("zipCd") zipCd:Int?=null,
        @Query("clCd") clCd:Int?=null,
        @Query("dgsbjtCd") dgsbjtCd:Int?=null,
        @Query("xPos") xPos:Double?=null,
        @Query("yPos") yPos:Double?=null,
        @Query("radius") radius:Int?=null,
        @Query ("type")  type:String="json"

    ):Call<ResponseHos>

}