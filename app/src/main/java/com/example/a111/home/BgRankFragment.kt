package com.example.a111.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a111.MainActivity
import com.example.a111.R
import com.example.a111.model.GroupRankList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_bg_list.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BgRankFragment : Fragment() {
    lateinit var  mainActivity: MainActivity
    private lateinit var  mra: MRA
    private lateinit var gra: GroupRankAdapter //레트로핏
    companion object {
        fun newInstance(): BgRankFragment = BgRankFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitManager.instance.iRetrofit.bgranklist().enqueue(object : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if(response.isSuccessful){
                    var result:JsonElement? = response.body()
                    Log.d(TAG, "onResponse: ${result.toString()}")
                    getrank(result)
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getrank(result: JsonElement?) {
        var rankList =ArrayList<GroupRankList>()
        try {
            val list =JSONObject(result.toString()).keys()
            var ite = list.iterator()
            while(ite.hasNext()){
                val list1 = JSONObject(result.toString()).getJSONObject(ite.next())
                val bg_name =list1.getString("bg_name")
                val bg_experience= list1.getDouble("bg_experience")
                val bg_level=list1.getInt("bg_level")
                val Mymodel=GroupRankList(

                    bg_name=bg_name,
                    bg_experience = bg_experience,
                    bg_level=bg_level)
                rankList.add(Mymodel)
            }
            gra = GroupRankAdapter()
            gra.submitList(rankList)
            my_recycle.apply {
                layoutManager=LinearLayoutManager(this@BgRankFragment.context,LinearLayoutManager.VERTICAL,false)
                adapter=gra
            }
       }catch(e: Exception){
          e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bg_rank, container, false)
    }
}