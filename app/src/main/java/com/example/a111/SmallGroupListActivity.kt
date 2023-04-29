package com.example.a111

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.home.SgMRA
import com.example.a111.model.SgList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_small_group_list.*
import kotlinx.android.synthetic.main.fragment_bg_list.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class SmallGroupListActivity : AppCompatActivity() {
    private lateinit var sgmra: SgMRA


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_small_group_list)



        // 0)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 1)소모임 리스트.
        RetrofitManager.instance.iRetrofit.sglist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(Constants.TAG, "onResponse: ${result.toString()}")
                    getsgListtwo(result)
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

    // 2-1) 소모임 펑션
    private fun getsgListtwo(result: JsonElement?) {
        var modelList = ArrayList<SgList>()

        try {
            Log.d(Constants.TAG, "getsgList: ")
            val list = JSONObject(result.toString()).keys()

            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val sg_id = list1.getInt("sg_id")
                val u_id = list1.getString("u_id")
                val sg_name = list1.getString("sg_name")
                val sg_intro = list1.getString("sg_intro")
                val sg_date = list1.getLong("sg_date")
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(sg_date)
                val Mymodel =
                    SgList(sg_id = sg_id, u_id = u_id, sg_name = sg_name,
                        sg_date = outputdate,sg_intro=sg_intro)
                modelList.add(Mymodel)
            }
            sgmra = SgMRA()
            sgmra.submitList(modelList)



            smalllist.apply {

                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = sgmra


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}