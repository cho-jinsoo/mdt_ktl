package com.example.a111.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.R
import com.example.a111.model.GroupRankList
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.bg_list_item.view.*


class GroupRankAdapter : RecyclerView.Adapter<GroupRankAdapter.ViewHolder>(){

    var modelList = ArrayList<GroupRankList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.group_rank_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=200
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList : ArrayList<GroupRankList>){
        Log.d(TAG, "submitList: 4")
        this.modelList=modelList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val group_name =itemView.group_name
        private val group_exp = itemView.group_exp
        private val group_lev=itemView.group_lev


        fun bind(Mymodel: GroupRankList){
            group_lev.text=Mymodel.bg_level.toString()
            group_exp.text=Mymodel.bg_experience.toString()
            group_name.text=Mymodel.bg_name
        }
    }
}