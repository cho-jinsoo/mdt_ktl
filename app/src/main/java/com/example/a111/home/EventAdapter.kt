package com.example.a111.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.BoardDetailActivityRe
import com.example.a111.GlobalApplication
import com.example.a111.R
import com.example.a111.model.EventBoardList
import com.example.a111.model.UserBoardList
import kotlinx.android.synthetic.main.fragment_board_cardview.view.*
import kotlinx.android.synthetic.main.fragment_home_event_cardview.view.*

class EventAdapter: RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var eventBoardList = ArrayList<EventBoardList>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_event_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.eventBoardList[position])
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 300
        holder.itemView.requestLayout()


    }

    override fun getItemCount(): Int {
        return this.eventBoardList.size
    }

    fun submitList(listreceive: ArrayList<EventBoardList>) {
        this.eventBoardList = listreceive
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ebtitle: TextView = itemView.ebtitle
        private val ebcontent: TextView = itemView.ebcontent
        private val ebdate: TextView = itemView.ebdate

        fun bind(item: EventBoardList) {

            ebtitle.text = item.b_Title
            ebcontent.text = item.b_Content
            ebdate.text = item.b_date

        }


    }
}

