package com.example.a111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.a111.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.activity_board_write.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardWriteActivity : AppCompatActivity() {
    private lateinit var boardFragment: BoardFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bwtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        // 2) 글 보내기
        button_sendac.setOnClickListener {


            val b_title = boardfreetitleac.text.toString()
            Log.d("로그", "onCreate: $b_title")
            val b_content = boardfreecontentac.text.toString()
            Log.d("로그", "onCreate: $b_content")
            val u_id = GlobalApplication.prefs.getString("u_id","")
            Log.d("로그", "onCreate: $u_id")

            RetrofitManager.instance.iRetrofit.ubwrite_Request(u_id, b_title, b_content).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성고된 경우
                        var result :String? = response.body()
                        Log.d("로그", "onResponse 성공: " + result?.toString())
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("로그", "onResponse 실패"+response.body() + response)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("로그", "onFailure 에러: " + t.message.toString())
                }
            })

           finish()
        }

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

}