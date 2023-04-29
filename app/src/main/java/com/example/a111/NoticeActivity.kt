package com.example.a111

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.home.NoticeAdapter
import com.example.a111.model.NoticeBoardList
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class NoticeActivity : AppCompatActivity() {
    private lateinit var NoticeAadapterConnect: NoticeAdapter //레트로핏

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        // 0)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        // 1) 공지사항 ^_^..
        RetrofitManager.instance.iRetrofit.noticeboard().enqueue(object : Callback<JsonElement> {
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


    // 0-1)뒤로가기 툴바
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


    // 1-1) 공지사항 펑션 ^_^... 부들부들.. 조민규 개자슥..
    private fun getlist(result: JsonElement?) {
        var nba_notice = ArrayList<NoticeBoardList>()

        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val b_id = list1.getInt("b_id")
                val u_id = list1.getString("u_id")
                val b_Title = list1.getString("b_title")
                val b_Content = list1.getString("b_content")
                val b_date = list1.getLong("b_date")
                val parser = SimpleDateFormat("MM월 dd일")
                val ubtime = parser.format(b_date)


                var model = NoticeBoardList(
                    b_id = b_id,
                    u_id = u_id,
                    b_Title = b_Title,
                    b_Content = b_Content,
                    b_date = ubtime
                )
                nba_notice.add(model)
            }

            NoticeAadapterConnect = NoticeAdapter()
            NoticeAadapterConnect.submitList(nba_notice)

            noticeac.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = NoticeAadapterConnect
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}