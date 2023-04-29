package com.example.a111.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.*
import com.example.a111.MainActivity.Companion.loginid

import com.example.a111.model.BgmList
import com.example.a111.model.GroupList
import com.example.a111.model.SgList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_bg_list.*
import kotlinx.android.synthetic.main.fragment_board.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class BgListFragment : Fragment() {
    lateinit var mainActivity: MainActivity
    private lateinit var mra: MRA
    private lateinit var sgmra: SgMRA
    private lateinit var bg: bgMemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 0)내 클럽 리스트
        RetrofitManager.instance.iRetrofit.myclubinfo().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(TAG, "onResponse:${response.body().toString()} ")
                    getListmyclub(result)
                } else {
                    Log.d(TAG, "onResponse: fail")
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        // 1)동호회 리스트
        RetrofitManager.instance.iRetrofit.searchPhotos().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(TAG, "onResponse:${response.body().toString()} ")
                    getList(result)
                } else {
                    Log.d(TAG, "onResponse: fail")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        // 2)소모임 리스트.
        RetrofitManager.instance.iRetrofit.sglist().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    var result: JsonElement? = response.body()
                    Log.d(TAG, "onResponse: ${result.toString()}")
                    getsgListtwo(result)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })


    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bgmove.setOnClickListener {
            activity?.let {
                val intent = Intent(context, BigGroupListActivity::class.java)
                startActivity(intent)
            }
        }

        sgmove.setOnClickListener {
            activity?.let {
                val intent = Intent(context, SmallGroupListActivity::class.java)
                startActivity(intent)
            }
        }

    }

    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity

        Log.d(TAG, "MountainFragment - onAttach() called")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bg_list, container, false)
    }

    companion object {

        fun newInstance(): BgListFragment = BgListFragment()

    }

    // 0-1) 동호회 펑션
    private fun getListmyclub(result: JsonElement?) {
        var modelList = ArrayList<BgmList>()

        try {
            Log.d(TAG, "getsgList: ")
            val list = JSONObject(result.toString()).keys()

            var ite = list.iterator()

            while (ite.hasNext()) {
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val bgm_id = list1.getInt("bgm_id")
                val bg_id = list1.getInt("bg_id")
                val bg_name = list1.getString("bg_name")
                val u_id = list1.getString("u_id")
                val u_experience = list1.getLong("u_experience")
                val u_level = list1.getInt("u_level")
                val bgm_date = list1.getLong("bgm_date")
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val outputdate = parser.format(bgm_date)

                Log.d("로그", "아이디 체크 ${loginid}")

                if(loginid == u_id) {
                    val Mymodel =
                        BgmList(
                            bgm_id = bgm_id, bg_id = bg_id, bg_name = bg_name, u_level =u_level,
                            u_id = u_id, u_experience = u_experience, bgm_date = outputdate
                        )
                    modelList.add(Mymodel)
                    Log.d("로그", "모델리스트값체킹${modelList}")
                }
            }
            bg = bgMemberAdapter()
            bg.submitList(modelList)
            myclub.apply {

                layoutManager = LinearLayoutManager(
                    this@BgListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = bg


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 1-1) 동호회 펑션
    private fun getList(result: JsonElement?) {
        var modelList = ArrayList<GroupList>()
        try {
            Log.d(TAG, "getList: on")
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
                    TAG,
                    "getlist: ${bg_id},${u_id},${bg_name},${bg_date},${bg_experience},${bg_level}"
                )
            }
            mra = MRA()

            mra.submitList(modelList)


            my_recycle.apply {

                layoutManager = LinearLayoutManager(
                    this@BgListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = mra


            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // 2-1) 소모임 펑션
    private fun getsgListtwo(result: JsonElement?) {
        var modelList = ArrayList<SgList>()

        try {
            Log.d(TAG, "getsgList: ")
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



            sgrecycle.apply {

                layoutManager = LinearLayoutManager(
                    this@BgListFragment.context,
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