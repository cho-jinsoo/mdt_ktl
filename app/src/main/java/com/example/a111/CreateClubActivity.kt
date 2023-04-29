package com.example.a111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants
import kotlinx.android.synthetic.main.fragment_bg_make.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateClubActivity : AppCompatActivity() {

    var g_choice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_club)


        // 1) 모임 선택 라디오 버튼
        radio_group.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.bigroup -> g_choice = 1

                R.id.smallgroup -> g_choice = 2

            }
        }



        // 2) 클럽 만들기 값 보내기
        val u_id=bg_u_id.text.toString()
        val g_name=bg_name.text.toString()
        val g_intro =bg_itro.text.toString()
        Log.d(Constants.TAG, "onCreate: ${g_intro}")
        Log.d(Constants.TAG, "onCreate: ${g_name}")
        Log.d(Constants.TAG, "onCreate: ${u_id}")


        RetrofitManager.instance.iRetrofit.createbg(g_choice, u_id, g_name, g_intro)?.enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    var result: String? = response.body()
                    Log.d(Constants.TAG, "onResponse: " + result)
                } else {
                    Log.d(Constants.TAG, "onResponse: " + response.body() + response)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(Constants.TAG, "onFailure: " + t.message.toString())
            }

        })

    }
}