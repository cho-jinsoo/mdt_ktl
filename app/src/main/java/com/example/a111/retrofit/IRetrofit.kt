package com.example.a111.retrofit

import com.example.a111.utils.API
import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {


    //빅그룹 리스트 출력
    @GET("appbglist")
    fun searchPhotos() : Call<JsonElement>

    @GET("api2")
    fun creategroup(@Query("u_id") u_id:String , @Query("g_name") g_name:String ,@Query("g_intro") g_intro:String ): Call<String>?

    //빅그룹 만들기
    @FormUrlEncoded
    @POST("appbgadd")
    fun createbg(
        @Field("g_choice") g_choice: Int,
        @Field("u_id") u_id: String,
        @Field("g_name") g_name: String,
        @Field("g_intro") g_intro: String
    ) : Call<String>?


    @GET("userboardapp")
    fun searchPhotos1() : Call<JsonElement>

    @FormUrlEncoded
    @POST("applogin")
    fun login(@Field("u_id") u_id:String,@Field("u_pw") u_pw:String) :Call<JsonElement>?


    @POST("appbgranklist")
    fun bgranklist(): Call<JsonElement>

    @POST("appsglist")
    fun sglist(): Call<JsonElement>

    @POST("appe_list")
    fun eventlist():Call<JsonElement>
    @FormUrlEncoded
    @POST("news")
    fun newslist():Call<JsonElement>
    @FormUrlEncoded
    @POST("appsg_addCommand")
    fun sgmake(@Field("u_id") u_id: String,@Field("sg_name") sg_name:String, @Field("sg_intro") sg_intro:String):Call<Void>
    @FormUrlEncoded
    @POST("join")
    fun join(@Field("u_id") u_id: String,@Field("u_pw")u_pw: String,@Field("u_email") u_email:String,@Field("u_name") u_name:String, @Field("u_address") u_address:String):Call<Void>

    //빅그룹 가입신청
    @FormUrlEncoded
    @POST("appbgjoin")
    fun bgjoin(@Field("u_id")u_id:String,
               @Field("bg_id")bg_id:String,
               @Field("bg_name")bg_name:String
    ):Call<Void>


    @POST("appbgjoinlist")
    fun bgjoinlist(@Field("u_id")u_id: String):Call<JsonElement>
    @FormUrlEncoded
    @POST("appsgjoin")
    fun sgjoin(@Field("u_id")u_id: String,@Field("sg_id")sg_id:String,@Field("sg_name")sg_name: String):Call<Void>



    // 게시글 작성하기
    @FormUrlEncoded
    @POST("userboardwriteapp")
    fun ubwrite_Request(
        @Field("u_id") u_id: String,
        @Field("b_title") b_title: String,
        @Field("b_content") b_content: String): Call<String>

    // 게시글 출력하기
    @GET("userboardapp")
    fun userboardlist() : Call<JsonElement>


    // 등산 기록 보내기
    @GET("recordadd")
    fun recordAdd(@Query("u_id") u_id: String,
                  @Query("start_location") start_location: String,
                  @Query("finish_location") finish_location: String,
                  @Query("record_time") record_time: String,
                  @Query("hiking_name") hiking_name: String,
                  @Query("hiking_level") hiking_level: String,
                  @Query("hiking_exp") hiking_exp: String,
                  @Query("mttype") mttype: Int,
                  @Query("m_id") m_id: Int



    ) : Call<String>?


    // 산 출력하기
    @GET("app")
    fun mountainlist_retrofit() : Call<JsonElement>



    // 댓글 출력하기
    @GET("userboardcomentapp")
    fun userboardcommentlist() : Call<JsonElement>

    // 덧글 작성하기
    @FormUrlEncoded
    @POST("ubcommentwriteapp")
    fun userboardcommentapp(
        @Field("comment_contents") comment_contents: String,
        @Field("u_name") u_name: String,
        @Field("b_id") b_id: String,
        @Field("u_level") u_level: Int
        ): Call<String>

    // 내정보 출력하기
    @GET("myinfolist")
    fun myinfolist() : Call<JsonElement>






    // 산행기록 출력하기
    @GET("hikingrecord")
    fun hikingrecord() : Call<JsonElement>


    // 공지사항 출력하기
    @GET("noticeboard")
    fun noticeboard() : Call<JsonElement>

    // 이벤트 출력하기
    @GET("eventboard")
    fun eventboard() : Call<JsonElement>

    // 내클럽정보 출력하기
    @GET("myclub")
    fun myclubinfo() : Call<JsonElement>

    // 내클럽 상세정보 출력하기
    @GET("appbglist")
    fun myclubdetail() : Call<JsonElement>


    // 클럽 코멘트 작성하기
    @FormUrlEncoded
    @POST("clubboardwrite")
    fun myclubboardwrite(
        @Field("bg_id") bg_id : Int,
        @Field("myclub_contents") myclub_contents: String,
        @Field("u_name") u_name : String): Call<String>

    // 내클럽 상세정보 출력하기
    @GET("myclubboardlist")
    fun myclubboardlist() : Call<JsonElement>



    // 내클럽 상세정보 출력하기
    @GET("myclubadminjoin")
    fun myclubadminjoin() : Call<JsonElement>

    // 조인승인 작성하기
    @FormUrlEncoded
    @POST("appbgjoin_yes")
    fun joinok(
        @Field("bgj_id") bgj_id : Int,
        @Field("u_id") u_id: String,
        @Field("bg_id") bg_id : String): Call<String>


    // 하트보내기
    @FormUrlEncoded
    @POST("likeanddelike")
    fun like(
        @Field("b_id") b_id : Int,
        @Field("likechoice") likechoice : Int,
        @Field("u_id") u_id : String

    ): Call<String>

    // 하트보내기
    @FormUrlEncoded
    @POST("likeanddelike")
    fun delike(
        @Field("b_id") b_id : Int,
        @Field("likechoice") likechoice : Int,
        @Field("u_id") u_id : String

    ): Call<String>

    // 내클럽 상세정보 출력하기
    @GET("likedo")
    fun likedo() : Call<JsonElement>

}