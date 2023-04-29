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
import kotlinx.android.synthetic.main.activity_my_big_club_cardview.view.*
import kotlinx.android.synthetic.main.bg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class bgDetailAdapter : RecyclerView.Adapter<bgDetailAdapter.ViewHolder>(){

    var modelList = ArrayList<GroupList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_my_big_club_cardview,parent,false))
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

        private val mst_name=itemView.bd_master
        private val date_name=itemView.bd_date
        private val group_name =itemView.bd_name
        private val group_exp = itemView.bd_exp
        private val group_lev=itemView.bd_lev
        private val group_intro=itemView.bd_intro


        fun bind(Mymodel: GroupList){


            group_lev.text=Mymodel.bg_level.toString()
            group_exp.text=Mymodel.bg_experience.toString()
            Mymodel.bg_id.toString()
            mst_name.text=Mymodel.u_id
            date_name.text=Mymodel.bg_date.toString()
            group_name.text=Mymodel.bg_name
            group_intro.text=Mymodel.bg_intro.toString()


            }


        }

}
