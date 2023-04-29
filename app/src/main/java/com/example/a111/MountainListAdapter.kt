
package com.example.a111

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.Mountain.MountainRcmDetailActivity

import com.example.a111.model.MountainList
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.mountain_list_layout.view.*


class MountainListAdapter : RecyclerView.Adapter<MountainListAdapter.ViewHolder>(){

    var mrecomanlist = ArrayList<MountainList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mountain_list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.mrecomanlist[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=200
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return this.mrecomanlist.size
    }
    fun submitList(modelList3 : ArrayList<MountainList>){
        Log.d(TAG, "submitList: 4")
        this.mrecomanlist=modelList3
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val m_name : TextView = itemView.m_name
        private val m_level : TextView = itemView.m_level
        private val area : TextView = itemView.area


        fun bind(item : MountainList) {
            item.m_id.toString()
            m_name.text = item.m_name.toString()
            m_level.text = item.m_level.toString()
            area.text = item.area.toString()
           item.m_explain


            itemView.setOnClickListener {
                Intent(GlobalApplication.ApplicationContext(), MountainRcmDetailActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { GlobalApplication.ApplicationContext().startActivity(this) }
            }

        }
    }
}