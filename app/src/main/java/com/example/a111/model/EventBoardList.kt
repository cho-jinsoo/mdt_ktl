package com.example.a111.model

import java.io.Serializable

data class EventBoardList(
    var b_id: Int,
    var u_id: String,
    var b_Title:String,
    var b_Content:String,
    var b_date: String
): Serializable