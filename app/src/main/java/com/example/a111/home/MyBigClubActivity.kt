package com.example.a111.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.GlobalApplication
import com.example.a111.MainActivity.Companion.loginid
import com.example.a111.MainActivity.Companion.share_username
import com.example.a111.MyClubAdimnActivity
import com.example.a111.R
import com.example.a111.model.BgmList
import com.example.a111.model.GroupList
import com.example.a111.model.MyClubBoardList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_board_write.*
import kotlinx.android.synthetic.main.activity_my_big_club.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MyBigClubActivity : AppCompatActivity() {

    lateinit var datas : BgmList
    private lateinit var bda: bgDetailAdapter
    private lateinit var mcba : MyClubBoardAdapter
    var myclubname :String=""
    var myclubid : Int = 0
    var chekindid : Int = 0
    var checkingmaster : String=""

    companion object {
        var myclubidcheck : Int = 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_big_club)

        // 1)뒤로가기 툴바
        val toolbar = findViewById<Toolbar>(R.id.bdtoolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 2) 유저보드리스트  데이터 받아오기
        datas = intent.getSerializableExtra("data") as BgmList
        myclubname = datas.bg_name
        chekindid = datas.bg_id
        datas.bgm_date


        // 2-1)동호회 리스트
        RetrofitManager.instance.iRetrofit.myclubdetail().enqueue(object : Callback<JsonElement> {
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

        // 3) 내 클럽 코멘트 달기
        myclubboarwirte.setOnClickListener {

            val bg_id = myclubid
            val myclub_contents = mcbcontents.text.toString()
            val u_name = share_username

            RetrofitManager.instance.iRetrofit.myclubboardwrite(bg_id, myclub_contents, u_name)
                .enqueue(object : Callback<String> {
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
            startActivity(intent)

        }



        // 4)내 클럽 코멘트 출력
        RetrofitManager.instance.iRetrofit.myclubboardlist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(Constants.TAG, "onResponse:${response.body().toString()} ")
                    getListboard(result)
                } else {
                    Log.d(Constants.TAG, "onResponse: fail")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "onFailure: ${t.message.toString()}")
            }
        })


        // 5) 코멘트 작성자값 주기
        myclubmyname.text = share_username

        // 6)내클럽 관리 페이지 들어가기
        myclubamdin.setOnClickListener {
            if(checkingmaster == loginid){
                val intent = Intent(this, MyClubAdimnActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "죄송합니다. 열람권한이 없습니다.", Toast.LENGTH_SHORT).show()
            }

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
                val bg_level =  list1.getInt("bg_level")
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(bg_date)

                Log.d("로그", "성시경" +
                        "${myclubname}")

                if(myclubname == bg_name) {
                    val Mymodel = GroupList(
                        bg_id = bg_id, u_id = u_id, bg_name = bg_name,
                        bg_date = outputdate, bg_experience = bg_experience,
                        bg_level = bg_level, bg_intro = bg_intro
                    )

                    modelList.add(Mymodel)
                    myclubid = bg_id
                    checkingmaster = u_id

                }
            }
            bda = bgDetailAdapter()

            bda.submitList(modelList)


            mycclubdetail.apply {

                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = bda


            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // 1-1) 동호회 펑션
    private fun getListboard(result: JsonElement?) {
        var modelList = ArrayList<MyClubBoardList>()
        try {
            Log.d(Constants.TAG, "getList: on")
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val myclub_num = list1.getInt("myclub_num")
                val bg_id = list1.getInt("bg_id")
                val myclub_contents = list1.getString("myclub_contents")
                val myclub_date = list1.getLong("myclub_date")
                val u_name = list1.getString("u_name")
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(myclub_date)

                Log.d("로그", "내클럽아이디" +
                        "${chekindid}")

                if(bg_id == chekindid) {
                    val Mymodel = MyClubBoardList(
                        bg_id = bg_id, myclub_num = myclub_num, myclub_contents = myclub_contents,
                        myclub_date = outputdate, u_name = u_name
                    )

                    modelList.add(Mymodel)
                    myclubid = bg_id
                    myclubidcheck = bg_id
                }
            }
            mcba = MyClubBoardAdapter()

            mcba.submitList(modelList)


            myclubboard.apply {

                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = mcba


            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}