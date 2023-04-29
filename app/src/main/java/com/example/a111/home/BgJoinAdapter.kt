package com.example.a111.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.GlobalApplication
import com.example.a111.MainActivity
import com.example.a111.R
import com.example.a111.model.BgJoinList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.activity_my_big_club_cardview.view.*
import kotlinx.android.synthetic.main.activity_my_big_club_join.view.*
import kotlinx.android.synthetic.main.bg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BgJoinAdapter : RecyclerView.Adapter<BgJoinAdapter.ViewHolder>(){

    var modelList = ArrayList<BgJoinList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_my_big_club_join,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=300
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList : ArrayList<BgJoinList>){
        Log.d(TAG, "submitList: 4")
        this.modelList=modelList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var mainActivity: MainActivity
        lateinit var context: Context

        private val u_id=itemView.u_id
        private val u_experience=itemView.u_experience
        private val u_level =itemView.u_level
        private val bgj_date = itemView.bgj_date


        fun bind(Mymodel: BgJoinList){


            Mymodel.bgj_id.toString()
            Mymodel.bg_id.toString()
            Mymodel.bg_name
            u_id.text=Mymodel.u_id
            u_experience.text=Mymodel.u_experience.toString()
            u_level.text=Mymodel.u_level.toString()
            bgj_date.text=Mymodel.bgj_date
            Mymodel.y_n
            Mymodel.note

            val join = itemView.findViewById<Button>(R.id.joinbutton)
            join.setOnClickListener {

                var bg_id = Mymodel.bg_id.toString()
                var u_id = Mymodel.u_id
                var bgj_id = Mymodel.bgj_id


                RetrofitManager.instance.iRetrofit.joinok(bgj_id, u_id, bg_id).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성고된 경우
                            var result :String? = response.body()
                            Log.d("로그", "onResponse 성공: " + result?.toString())
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Toast.makeText( GlobalApplication.ApplicationContext(), "통신실패", Toast.LENGTH_SHORT).show()

                            Log.d("로그", "onResponse 실패"+response.body() + response)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        Log.d("로그", "onFailure 에러: " + t.message.toString())
                        Toast.makeText( GlobalApplication.ApplicationContext(), "통신실패", Toast.LENGTH_SHORT).show()

                    }
                })

                Toast.makeText( GlobalApplication.ApplicationContext(), "가입승인 완료", Toast.LENGTH_SHORT).show()
                join.isEnabled = false

            }
        }


        }

}
