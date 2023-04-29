package com.example.a111

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.model.HikingRecordList
import com.example.a111.utils.Constants
import kotlinx.android.synthetic.main.activity_top_my_cardview.view.*
import kotlinx.android.synthetic.main.mountain_list_layout.view.*

class MyHikingRecordAdapter : RecyclerView.Adapter<MyHikingRecordAdapter.ViewHolder>(){

    var hikingrecordlist = ArrayList<HikingRecordList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_top_my_cardview,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.hikingrecordlist[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=500
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return this.hikingrecordlist.size
    }
    fun submitList(modelList3 : ArrayList<HikingRecordList>){
        Log.d(Constants.TAG, "submitList: 4")
        this.hikingrecordlist=modelList3
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val start_location : TextView = itemView.hk_start_cv
        private val finish_location : TextView = itemView.hk_finish_cv
        private val record_time : TextView = itemView.hk_recordtime
        private val hiking_name : TextView = itemView.hk_name_cv
        private val hiking_level : TextView = itemView.hk_level_cv
        private val hiking_exp : TextView = itemView.hk_exp_cv
        private val hiking_date : TextView = itemView.hk_date_cv


        fun bind(item: HikingRecordList) {
            start_location.text = item.start_location.toString()
            finish_location.text = item.finish_location.toString()
            record_time.text = item.record_time.toString()
            hiking_name.text = item.hiking_name.toString()
            hiking_level.text = item.hiking_level.toString()
            hiking_exp.text = item.hiking_exp.toString()
            hiking_date.text = item.hiking_date.toString()


        }
    }
}