package com.example.a111.login

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.a111.R
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_join.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)


        // 0)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        // 1) 회원가입

        userid
        userpw

/*
        RetrofitManager.instance.iRetrofit.ubwrite_Request(u_id, b_title, b_content)?.enqueue(object : Callback<String> {
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
            }*/
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

}