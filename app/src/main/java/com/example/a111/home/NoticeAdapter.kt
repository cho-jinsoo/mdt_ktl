package com.example.a111.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.BoardDetailActivityRe
import com.example.a111.GlobalApplication
import com.example.a111.R
import com.example.a111.model.NoticeBoardList
import kotlinx.android.synthetic.main.activity_notice_board_cardview.view.*

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    var nbl = ArrayList<NoticeBoardList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_notice_board_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.nbl[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 300
        holder.itemView.requestLayout()


    }

    override fun getItemCount(): Int {
        return this.nbl.size
    }

    fun submitList(listreceive: ArrayList<NoticeBoardList>) {
        this.nbl = listreceive
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nbtitle: TextView = itemView.nbtitle
        private val nbcontent: TextView = itemView.nbcontent
        private val nbdate: TextView = itemView.nbdate

        fun bind(item: NoticeBoardList) {

            nbtitle.text = item.b_Title
            nbcontent.text = item.b_Content
            nbdate.text = item.b_date


        }


    }
}

