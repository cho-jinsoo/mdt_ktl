package com.example.a111.Mountain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.BoardDetailActivityRe
import com.example.a111.MountainListAdapter
import com.example.a111.MapActivity
import com.example.a111.R

import com.example.a111.model.MountainList
import com.example.a111.retrofit.RetrofitManager
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_mountain_recommend.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MountainRecommendFragment : Fragment() {

    private lateinit var binding : BoardDetailActivityRe
    private lateinit var mountainListAdapter: MountainListAdapter
    companion object {
        fun newInstance(): MountainRecommendFragment = MountainRecommendFragment()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        RetrofitManager.instance.iRetrofit.mountainlist_retrofit().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    var result: JsonElement? = response.body()
                    Log.d("로그","onResponse 성공: " + result?.toString())
                    getlist(result)
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("로그", "onResponse 실패${response.body().toString()}")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("로그", "onFailure 에러: " + t.message.toString())
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_mountain_recommend, container, false)
    }

    private fun getlist(result: JsonElement?) {
        var modellist = ArrayList<MountainList>()
        try {
            Log.d("로그", "getlist: on")

            val list = JSONObject(result.toString()).keys()
            var ite=list.iterator()

            while(ite.hasNext()){
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val m_id =list1.getInt("m_id")
                val m_name= list1.getString("m_name")
                val m_level=list1.getString("m_level")
                val area=list1.getString("area")
                val m_explain=list1.getString("m_explain")

                var Mymodel =MountainList(m_id=m_id,m_name=m_name,
                    m_level=m_level,area=area,m_explain=m_explain)

                modellist.add(Mymodel)
                Log.d("로그", "getlist: ${m_id},${m_name},${m_level},${area}")
            }
            mountainListAdapter= MountainListAdapter()
            mountainListAdapter.submitList(modellist)

            mountain_recycler.apply {
                layoutManager=
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
                adapter=mountainListAdapter
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

}