package com.example.a111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.home.BgJoinAdapter
import com.example.a111.home.MyBigClubActivity.Companion.myclubidcheck
import com.example.a111.home.bgDetailAdapter
import com.example.a111.model.BgJoinList
import com.example.a111.model.GroupList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_my_big_club.*
import kotlinx.android.synthetic.main.activity_my_club_adimn.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MyClubAdimnActivity : AppCompatActivity() {
    private lateinit var bja: BgJoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_club_adimn)


        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 2) 내 클럽 가입신청자 출력
        RetrofitManager.instance.iRetrofit.myclubadminjoin().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(Constants.TAG, "onResponse:${response.body().toString()} ")
                    getList(result)
                } else {
                    Log.d(Constants.TAG, "onResponse: fail")
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "onFailure: ${t.message.toString()}")
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

    //2-1) 내 클럽 가입신청자 출력
    private fun getList(result: JsonElement?) {
        var modelList = ArrayList<BgJoinList>()
        try {
            Log.d(Constants.TAG, "getList: on")
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()
            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val bgj_id = list1.getInt("bgj_id")
                val bg_id = list1.getInt("bg_id")
                val bg_name = list1.getString("bg_name")
                val u_id = list1.getString("u_id")
                val u_experience = list1.getDouble("u_experience")
                val u_level = list1.getInt("u_level")
                val bgj_date = list1.getLong("bgj_date")
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(bgj_date)
                val y_n = list1.getString("y_n")
                val note = list1.getString("note")

                if(myclubidcheck == bg_id) {
                    val Mymodel = BgJoinList(
                        bgj_id = bgj_id,
                        bg_id = bg_id, u_id = u_id, bg_name = bg_name,
                        u_experience = u_experience, u_level = u_level,
                        bgj_date = outputdate, y_n = y_n, note=note
                    )
                    modelList.add(Mymodel)
                }
            }
            bja = BgJoinAdapter()
            bja.submitList(modelList)
            adminclub.apply {
                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = bja
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}