package com.example.a111.home

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.GlobalApplication
import com.example.a111.MainActivity
import com.example.a111.R
import com.example.a111.model.GroupList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.bg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MRA : RecyclerView.Adapter<MRA.ViewHolder>(){

    var modelList = ArrayList<GroupList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bg_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=700
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList : ArrayList<GroupList>){
        Log.d(TAG, "submitList: 4")
        this.modelList=modelList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var mainActivity: MainActivity
        lateinit var context: Context

        private val num_name=itemView.bg_num
        private val mst_name=itemView.master_test
        private val date_name=itemView.date_test
        private val group_name =itemView.group_name
        private val group_exp = itemView.group_exp
        private val group_lev=itemView.group_lev
        private val group_intro=itemView.group_intro


        fun bind(Mymodel: GroupList){


            group_lev.text=Mymodel.bg_level.toString()
            group_exp.text=Mymodel.bg_experience.toString()
            num_name.text=Mymodel.bg_id.toString()
            mst_name.text=Mymodel.u_id
            date_name.text=Mymodel.bg_date.toString()
            group_name.text=Mymodel.bg_name
            group_intro.text=Mymodel.bg_intro.toString()

            val joinplz = itemView.findViewById<Button>(R.id.joingroup)

            joinplz.setOnClickListener {
                Log.d(TAG, "bind: ")
                val builder= AlertDialog.Builder(itemView.context)
                builder.setTitle("${group_name.text}동호회가입신청하기")
                    .setMessage("가입하시겠습니까?")
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener{ dialog, id->


                        }
                    )
                    .setPositiveButton("신청",
                        DialogInterface.OnClickListener{dialog,id ->
                            RetrofitManager.instance.iRetrofit.bgjoin(
                                GlobalApplication.prefs.getString("u_id","").toString(),
                                num_name.text.toString(),group_name.text.toString()).enqueue(object :
                                Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if(response.isSuccessful){   Log.d(TAG, "onResponse: ")
                                    }else{   Log.d(TAG, "onResponse: ")
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                                }

                            })

                            Toast.makeText(
                                GlobalApplication.ApplicationContext(),
                                "가입신청 완료",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                builder.show()



            }


        }

    }
}