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
import com.example.a111.model.MyClubBoardList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.activity_my_big_club_cardview.view.*
import kotlinx.android.synthetic.main.activity_myclub_board_cardview.view.*
import kotlinx.android.synthetic.main.bg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyClubBoardAdapter : RecyclerView.Adapter<MyClubBoardAdapter.ViewHolder>(){

    var modelList = ArrayList<MyClubBoardList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_myclub_board_cardview,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.modelList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=250
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return this.modelList.size
    }
    fun submitList(modelList : ArrayList<MyClubBoardList>){
        Log.d(TAG, "submitList: 4")
        this.modelList=modelList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var mainActivity: MainActivity
        lateinit var context: Context

        private val myclub_contents = itemView.myclub_contents
        private val myclub_date=itemView.myclub_date
        private val u_name=itemView.u_name


        fun bind(Mymodel: MyClubBoardList){


            Mymodel.myclub_num.toString()
            Mymodel.bg_id.toString()
            myclub_contents.text=Mymodel.myclub_contents.toString()
            myclub_date.text=Mymodel.myclub_date.toString()
            u_name.text=Mymodel.u_name.toString()


            }


        }

}
