package com.example.a111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.home.MRA
import com.example.a111.model.GroupList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_big_group_list.*
import kotlinx.android.synthetic.main.fragment_bg_list.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class BigGroupListActivity : AppCompatActivity() {
    private lateinit var mra: MRA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_group_list)


        // 0)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 1)동호회 리스트
        RetrofitManager.instance.iRetrofit.searchPhotos().enqueue(object : Callback<JsonElement> {
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

    // 1-1) 동호회 펑션
    private fun getList(result: JsonElement?) {
        var modelList = ArrayList<GroupList>()
        try {
            Log.d(Constants.TAG, "getList: on")
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val bg_id = list1.getInt("bg_id")
                val u_id = list1.getString("u_id")
                val bg_name = list1.getString("bg_name")
                val bg_date = list1.getLong("bg_date")
                val bg_experience = list1.getDouble("bg_experience")
                val bg_intro = list1.getString("bg_intro")
                val bg_level = 0
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(bg_date)


                val Mymodel = GroupList(
                    bg_id = bg_id, u_id = u_id, bg_name = bg_name,
                    bg_date = outputdate, bg_experience = bg_experience,
                    bg_level = bg_level, bg_intro = bg_intro
                )

                modelList.add(Mymodel)
                Log.d(
                    Constants.TAG,
                    "getlist: ${bg_id},${u_id},${bg_name},${bg_date},${bg_experience},${bg_level}"
                )
            }
            mra = MRA()

            mra.submitList(modelList)


            biglist.apply {

                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = mra


            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}