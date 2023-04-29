package com.example.a111.home

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.GlobalApplication
import com.example.a111.MainActivity.Companion.TAG
import com.example.a111.R
import com.example.a111.model.SgList
import com.example.a111.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.sg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SgMRA : RecyclerView.Adapter<SgMRA.ViewHolder>(){

    var modelList=ArrayList<SgList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sg_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams= holder.itemView.layoutParams
        layoutParams.height=700
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList: ArrayList<SgList>){
        this.modelList=modelList
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val num_name = itemView.sg_id
        private val mst_name =itemView.sg_master
        private val sg_name=itemView.sg_name
        private val sg_date =itemView.sg_date
        private val sg_intro =itemView.sg_intro





        fun bind(sgList: SgList) {
            num_name.text=sgList.sg_id.toString()
            mst_name.text=sgList.u_id
            sg_name.text=sgList.sg_name
            sg_date.text=sgList.sg_date
            sg_intro.text=sgList.sg_intro


            itemView.setOnClickListener {
                val builder= AlertDialog.Builder(itemView.context)
                Log.d("로그", "bind: ${num_name.text}")
                Log.d("로그", "bind: ${sg_name.text},${GlobalApplication.prefs.getString("u_id","")}")

                builder.setTitle("${sg_name.text}모임가입하기")
                    .setMessage("가입하시겠습니까?")
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener{ dialog, id->

                        }
                    )
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{dialog,id->
                            Log.d(TAG, "bind:")
                            RetrofitManager.instance.iRetrofit.sgjoin(GlobalApplication.prefs.getString("u_id",""),num_name.text.toString(),sg_name.text.toString()).enqueue(object :
                                Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if(response.isSuccessful){
                                        Log.d(TAG, "onResponse:성공 ")

                                    }else{
                                        Log.d(TAG, "onResponse:실패 ")

                                    }

                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Log.d(TAG, "onFailure: ${t.message.toString()}")

                                }

                            })

                        })
                builder.show()
            }

        }

    }
}
