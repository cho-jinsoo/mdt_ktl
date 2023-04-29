package com.example.a111

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.a111.home.EventAdapter
import com.example.a111.home.HomeMonthActivity
import com.example.a111.home.NoticeAdapter
import com.example.a111.model.EventBoardList
import com.example.a111.model.NoticeBoardList
import com.example.a111.retrofit.RetrofitManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mountain_recommend.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class HomeFragment : Fragment() {



    private lateinit var NoticeAadapterConnect: NoticeAdapter //레트로핏
    private lateinit var EventAadapterConnect: EventAdapter //레트로핏

    companion object {
        const val TAG: String = "로그"
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")



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


        // 2) 이벤트 ^ㅅ^..
        RetrofitManager.instance.iRetrofit.eventboard().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "보드 프래그먼트 레트로핏 onResponse 성공: " + result?.toString())
                    geteventlist(result)
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 0) 이달의 추천 산
        monthmountain_image.setOnClickListener {
            activity?.let {
                val intent = Intent(context, HomeMonthActivity::class.java)
                startActivity(intent)
            }
        }

        // 1) 공지 리스트 창 가기
        noticebutton.setOnClickListener {
            activity?.let {
                val intent = Intent(context, NoticeActivity::class.java)
                startActivity(intent)
            }
        }


        // 2) 이벤트 리스트 창 가기
        eventbutton.setOnClickListener {
            activity?.let {
                val intent = Intent(context, EventActivity::class.java)
                startActivity(intent)
            }
        }

    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

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

            notice.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = NoticeAadapterConnect
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    // 2-1) 이벤트 펑션 ^_^...
    private fun geteventlist(result: JsonElement?) {
        var hef_event = ArrayList<EventBoardList>()

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


                var model = EventBoardList(
                    b_id = b_id,
                    u_id = u_id,
                    b_Title = b_Title,
                    b_Content = b_Content,
                    b_date = ubtime
                )
                hef_event.add(model)
            }

            EventAadapterConnect = EventAdapter()
            EventAadapterConnect.submitList(hef_event)

            event.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = EventAadapterConnect
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}