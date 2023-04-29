package com.example.a111.ViewHolder

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.a111.GlobalApplication
import com.example.a111.model.SgList
import com.example.a111.retrofit.RetrofitManager
import com.example.a111.utils.Constants.TAG
import kotlinx.android.synthetic.main.sg_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySgViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
