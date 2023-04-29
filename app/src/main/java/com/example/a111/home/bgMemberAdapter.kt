package com.example.a111.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.*
import com.example.a111.model.BgmList
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.fragment_bg_myclub.view.*


class bgMemberAdapter : RecyclerView.Adapter<bgMemberAdapter.ViewHolder>(){

    var modelList = ArrayList<BgmList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_bg_myclub,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=600
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList : ArrayList<BgmList>){
        Log.d(TAG, "submitList: 4")
        this.modelList=modelList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var mainActivity: MainActivity
        lateinit var context: Context

        private val bg_name=itemView.bg_name
        private val bgm_date=itemView.bgm_date


        fun bind(Mymodel: BgmList){


         Mymodel.bgm_id.toString()
         Mymodel.bg_id.toString()
         bg_name.text=Mymodel.bg_name
         Mymodel.u_level.toString()
         Mymodel.u_id
         Mymodel.u_experience.toString()
         bgm_date.text= Mymodel.bgm_date




            itemView.setOnClickListener {
                Intent(GlobalApplication.ApplicationContext(), MyBigClubActivity::class.java).apply {
                    putExtra("data", Mymodel)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { GlobalApplication.ApplicationContext().startActivity(this) }
            }
        }

    }
}