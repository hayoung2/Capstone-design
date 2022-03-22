package com.example.testapp


import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.*


object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/"
    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null

    val client: Retrofit

        get() {

            var gson = GsonBuilder().setLenient().create()

            while (retrofit == null) {
                retrofit= Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                    GsonConverterFactory.create(gson)).build();
            }
            return retrofit!!
        }
    fun getXMLInstance() : API{

        val xmlclient = OkHttpClient.Builder() .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(xmlclient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(API::class.java)
    }

}

@Root(name="response", strict = false)
class HosInfo @JvmOverloads constructor(
    @field:Element(name = "header",required = false)
    var header: Header?=null
    ,
    @field:Element(name = "body",required = false)
    var body: Body?=null
)

@Root(name = "header", strict = false)
class Header@JvmOverloads constructor(
    @field:Element(name ="resultCode",required = false)
    var resultCode: Int?=null,
    @field:Element(name = "resultMsg",required = false)
    var resultMsg: String?=null
)

@Root(name = "body", strict = false)
data class Body@JvmOverloads constructor(
    @field:Element(name="items",required = false)
    var items: Items?=null,
    @field:Element(name = "numOfRows",required = false) var numOfRows: Int?=null,
    @field:Element(name = "pageNo",required = false) var pageNo: Int?=null,
    @field:Element(name = "totalCount",required = false) var totalCount: Int?=null,

    )

@Root(name= "items", strict = false)
data class Items@JvmOverloads constructor(
    @field:ElementList(name="item",required = false,inline = true)
    var item: List<Item>?=null

)

@Root
class Item@JvmOverloads constructor(
    @field:Element(name = "addr",required = false) var addr: String?=null,
    @field:Element(name = "clCd",required = false) var clCd: String?=null,
    @field:Element(name = "clCdNm",required = false) var clCdNm: String?=null,
    @field:Element(name = "cmdcGdrCnt",required = false) var cmdcGdrCnt: String?=null,
    @field:Element(name = "cmdcIntnCnt",required = false) var cmdcIntnCnt: String?=null,
    @field:Element(name = "cmdcResdntCnt",required = false) var cmdcResdntCnt: String?=null,
    @field:Element(name = "cmdcSdrCnt",required = false) var cmdcSdrCnt: String?=null,
    @field:Element(name = "detyGdrCnt",required = false) var detyGdrCnt: String?=null,
    @field:Element(name = "detyIntnCnt",required = false) var detyIntnCnt: String?=null,
    @field:Element(name = "detyResdntCnt",required = false) var detyResdntCnt: String?=null,
    @field:Element(name = "detySdrCnt",required = false) var detySdrCnt: String?=null,
    @field:Element(name = "distance",required = false) var distance: Double?=null,
    @field:Element(name = "drTotCnt",required = false) var drTotCnt: Int?=null,
    @field:Element(name = "estbDd",required = false) var estbDd: String?=null,
    @field:Element(name = "mdeptGdrCnt",required = false) var mdeptGdrCnt: String?=null,
    @field:Element(name = "mdeptIntnCnt",required = false) var mdeptIntnCnt: String?=null,
    @field:Element(name = "mdeptResdntCnt",required = false) var mdeptResdntCnt: String?=null,
    @field:Element(name = "mdeptSdrCnt",required = false) var mdeptSdrCnt: String?=null,
    @field:Element(name = "postNo",required = false) var postNo: String?=null,
    @field:Element(name = "sgguCd",required = false) var sgguCd: String?=null,

    @field:Element(name = "sgguCdNm",required = false) var sgguCdNm: String?=null,
    @field:Element(name = "emdongNm",required = false) var emdongNm: String?=null,
    @field:Element(name = "sidoCd",required = false) var sidoCd: String?=null,
    @field:Element(name = "sidoCdNm",required = false) var sidoCdNm: String?=null,
    @field:Element(name = "telno",required = false) var telno: String?=null,
    @field:Element(name = "XPos",required = false) var XPos: String?=null,
    @field:Element(name = "YPos",required = false) var YPos: String?=null,
   @field:Element(name = "hospUrl",required = false) var hospUrl: String?=null,

    @field:Element(name="yadmNm",required = false) var yadmNm: String?=null,
    @field:Element(name="ykiho",required = false) var ykiho: String?=null,



    )

data class Responsedose(
    @SerializedName("header") val header:dos_header,
    @SerializedName("body") val body:dos_body,
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
        @Query("pageNo") pageNo:String?="1",
        @Query ("numOfRows") numOfRows:String?="10",
        @Query("sidoCd") sidoCd:String?=null,
        @Query("sgguCd") sgguCd:String?=null,
        @Query("emdongNm") emdongNm:String?=null,
        @Query("yarmNm") yarmNm:String?=null,
        @Query("zipCd") zipCd:String?=null,
        @Query("clCd") clCd: String? =null,
        @Query("dgsbjtCd") dgsbjtCd:String?=null,
        @Query("xPos") xPos:Double?=null,
        @Query("yPos") yPos:Double?=null,
        @Query("radius") radius:Int?=null,


        ):Call<HosInfo>



    @GET("1471000/MdcinGrnldntfcInfoService01")
    fun getMdcinGrnldntfclnfoList(
        @Query("serviceKey") serviceKey:String,
        @Query("item_name") itemName:String?=null,
        @Query("entp_name") entpName: String?=null,
        @Query("item_seq") itemSeq: String?=null,
        @Query("img_regist_ts") imgRegisterTs:String?=null,
        @Query("pageNo") pageNo: Int=1,
        @Query("numOfRows") numOfRows: Int=3,
        @Query("edi_code") ediCode:String?=null,
        @Query("type") type:String="josn"
        ):Call<HosInfo>
}