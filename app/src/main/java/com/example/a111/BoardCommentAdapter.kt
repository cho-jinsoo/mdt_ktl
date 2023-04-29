package com.example.a111

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.Mountain.MountainRcmDetailActivity
import com.example.a111.model.CommentList
import com.example.a111.model.MountainList
import com.example.a111.model.UserBoardList
import com.example.a111.utils.Constants
import kotlinx.android.synthetic.main.activity_board_detail_cardview.view.*
import kotlinx.android.synthetic.main.mountain_list_layout.view.*

class BoardCommentAdapter : RecyclerView.Adapter<BoardCommentAdapter.ViewHolder>(){

    var commentlist = ArrayList<CommentList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardCommentAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_board_detail_cardview,parent,false))
    }

    override fun onBindViewHolder(holder: BoardCommentAdapter.ViewHolder, position: Int) {
        holder.bind(this.commentlist[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=300
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return this.commentlist.size
    }
    fun submitList(commentlistarray : ArrayList<CommentList>){
        Log.d(Constants.TAG, "submitList: 4")
        this.commentlist=commentlistarray
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val u_id : TextView = itemView.ubcmt_u_id
        private val c_contents : TextView = itemView.ubcmt_b_contents
        private val c_date : TextView = itemView.ubcmt_b_date


        fun bind(item : CommentList) {
            u_id.text = item.u_name.toString()
            c_contents.text = item.comment_contents.toString()
            c_date.text = item.comment_date.toString()

            // 프로필 설정하기
            val imageView = itemView.findViewById<ImageView>(R.id.uid1)
            if( item.u_level == 1) {
                imageView.setImageResource(R.drawable.level_1_squirrel_80x80)
            }
            else if( item.u_level == 2){
                imageView.setImageResource(R.drawable.level_2_rabbit_80x80)

            }
            else if( item.u_level == 3){
                imageView.setImageResource(R.drawable.level_3_owl_80x80)
            }
            else if( item.u_level == 4){
                imageView.setImageResource(R.drawable.level_4_eagle_80x80)

            }
            else if( item.u_level == 5) {
                imageView.setImageResource(R.drawable.level_5_bear_80x80)
            }
            else if( item.u_level == 6){
                imageView.setImageResource(R.drawable.level_6_eagle_80x80)
            }

        }
    }
}