package com.example.a111

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.model.UserBoardList
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_board_detail_re.*
import kotlinx.android.synthetic.main.fragment_board.*
import kotlinx.android.synthetic.main.fragment_board_cardview.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class BoardFragment : Fragment() {
    companion object {
        fun newInstance() :BoardFragment= BoardFragment()
    }

    private lateinit var BoardAadapterConnect: BoardAdapter //레트로핏

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MainActivity.TAG, "[매인액티비티 ---> 보드프래그먼트] 접속!")



        fragmentManager?.let { refreshFragment(this, it) }


        RetrofitManager.instance.iRetrofit.userboardlist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그", "자유게시판 보드 프래그먼트 레트로핏 onResponse 성공: " + result?.toString())
                    getlist(result)
                    Log.d(ContentValues.TAG, "${result} - onResponse() called")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "자유게시판 보드 프래그먼트 레트로핏 onResponse 실패" + response.body().toString())
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "자유게시판 보드 프래그먼트 레트로핏 onFailure 에러: " + t.message.toString())
            }
        })
/*
        pullToRefresh.setOnRefreshListener {
            pullToRefresh.isRefreshing = false
        }*/
    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userboardwritebt.setOnClickListener {
            activity?.let {
                val intent = Intent(context, BoardWriteActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)
    }


    private fun getlist(result: JsonElement?) {
        var bf_userboardlist = ArrayList<UserBoardList>()
    Log.d("로그", "하하하하하 - getlist() called")
        try {
            val list = JSONObject(result.toString()).keys()
            var ite = list.iterator()
            Log.d("로그", "하하하하하1111 - getlist() called")

            while (ite.hasNext()) {
                Log.d("로그", "하하하하하123 - getlist() called")

                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                Log.d("로그", "하하하하하456 - getlist() called")

                val b_id = list1.getInt("b_id")

                Log.d("로그", "하하하하하789 - getlist() called")

                val u_id = list1.getString("u_id")
                val b_Title = list1.getString("b_title")
                val b_Content = list1.getString("b_content")
                val b_date = list1.getLong("b_date")
                val parser = SimpleDateFormat("yyyy년 MM월 dd일")
                val ubtime = parser.format(b_date)
                val bcomment_count = list1.getInt("bcomment_count")
                val u_name =  list1.getString("u_name")
                val u_level =  list1.getInt("u_level")
                val b_like = list1.getInt("b_like")
                Log.d("로그", "하하하하하2222 - getlist() called")


                var Mymodel = UserBoardList(
                    b_id = b_id,
                    u_id = u_id,
                    b_Title = b_Title,
                    b_Content = b_Content,
                    b_date = ubtime,
                    bcomment_count = bcomment_count,
                    u_name=u_name,
                    u_level=u_level,
                    blike_count = b_like
                )
                Log.d("로그", "하하하하하3333 - getlist() called")


                Mymodel
                bf_userboardlist.add(Mymodel)
                bf_userboardlist
                Log.d("로그", "검은콩우유 ${bf_userboardlist} - getlist() called")

                //  프로필 설정하기
                /*

                }*/

            }

            BoardAadapterConnect = BoardAdapter()
            BoardAadapterConnect.submitList(bf_userboardlist)

            boardRecycler.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = BoardAadapterConnect
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}

