package com.example.a111

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.model.HikingRecordList
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_top_my_hiking_record.*
import kotlinx.android.synthetic.main.fragment_board.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class TopMyHikingRecord : AppCompatActivity() {


    private lateinit var MyHikingRecordconnect: MyHikingRecordAdapter //레트로핏

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_my_hiking_record)

        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.myinfotoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        // 2)나의 산행기록 보기
        RetrofitManager.instance.iRetrofit.hikingrecord().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "보드 프래그먼트 레트로핏 onResponse 성공: " + result?.toString())
                    getlist(result)
                    Log.d(ContentValues.TAG, "${result} - onResponse() called")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "보드 프래그먼트 레트로핏 onResponse 실패" + response.body().toString())
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "보드 프래그먼트 레트로핏 onFailure 에러: " + t.message.toString())
            }
        })


    }

    // 1_1)뒤로가기 툴바
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    // 2-1) 나의 산행 기록 보기
    private fun getlist(result: JsonElement?) {
        var mkr_hikingrecord= ArrayList<HikingRecordList>()

        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val u_id = list1.getString("u_id")
                val start_location = list1.getString("start_location")
                val finish_location = list1.getString("finish_location")
                val record_time = list1.getString("cleartime")
                val hiking_name = list1.getString("m_id")
                val hiking_level = list1.getString("hiking_level")
                val hiking_exp = list1.getString("u_experience")
                val hk_date = list1.getLong("hk_date")
                val parser = SimpleDateFormat("MM.dd")
                val hk_date_format = parser.format(hk_date)


                var Mymodel = HikingRecordList(
                    u_id = u_id,
                    start_location = start_location,
                    finish_location = finish_location,
                    record_time = record_time,
                    hiking_name = hiking_name,
                    hiking_level = hiking_level,
                    hiking_exp = hiking_exp,
                    hiking_date = hk_date_format



                    )



                mkr_hikingrecord.add(Mymodel)

            }

            MyHikingRecordconnect = MyHikingRecordAdapter()
            MyHikingRecordconnect.submitList(mkr_hikingrecord)

            atmhr1.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = MyHikingRecordconnect
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}