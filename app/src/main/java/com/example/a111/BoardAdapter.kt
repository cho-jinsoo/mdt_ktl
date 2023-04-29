package com.example.a111

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.model.UserBoardList
import kotlinx.android.synthetic.main.fragment_board_cardview.view.*

class BoardAdapter: RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var userboardlist = ArrayList<UserBoardList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_board_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.userboardlist[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 650
        holder.itemView.requestLayout()


    }

    override fun getItemCount(): Int {
        return this.userboardlist.size
    }

    fun submitList(listreceive: ArrayList<UserBoardList>) {
        this.userboardlist = listreceive
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ubid: TextView = itemView.ubid
        private val ubtitle: TextView = itemView.ubtitle
        private val ubcontent: TextView = itemView.ubcontent
        private val ubdate: TextView = itemView.ubdate
        private val ubcommentcount: TextView = itemView.comentcount
        private val ublikecount : TextView = itemView.likecount

        fun bind(item: UserBoardList) {


            ubid.text = item.u_name
            ubtitle.text = item.b_Title
            ubcontent.text = item.b_Content
            ubdate.text = item.b_date
            ubcommentcount.text = item.bcomment_count.toString()
            ublikecount.text = item.blike_count.toString()
            Log.d("로그", "강낭콩 bind ${item.u_name}")


            val imageView = itemView.findViewById<ImageView>(R.id.fbc_profile)
            imageView.setImageResource(R.drawable.aaa)

            if( item.u_level == 1) {
                imageView.setImageResource(R.drawable.aaa)
            }
            else if(item.u_level == 2){
                imageView.setImageResource(R.drawable.level_2_rabbit_80x80)

            }
            else if( item.u_level == 3) {
                imageView.setImageResource(R.drawable.level_3_owl_80x80)
            }
            else if(item.u_level == 4){
                imageView.setImageResource(R.drawable.level_4_eagle_50x50)
            }
            else if( item.u_level == 5) {
                imageView.setImageResource(R.drawable.level_5_bear_80x80)
            }
            else if( item.u_level == 6){
                imageView.setImageResource(R.drawable.level_6_eagle_80x80)
            }




                itemView.setOnClickListener {
                Intent(GlobalApplication.ApplicationContext(), BoardDetailActivityRe::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { GlobalApplication.ApplicationContext().startActivity(this) }
            }

        }


    }
}

