package com.example.a111.model

import java.io.Serializable

data class UserBoardList(
    var b_id: Int,
    var u_id: String,
    var b_Title:String,
    var b_Content:String,
    var b_date: String,
    var bcomment_count: Int,
    var u_name:String,
    var u_level:Int,
    var blike_count:Int
): Serializable