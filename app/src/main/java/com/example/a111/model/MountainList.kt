package com.example.a111.model

import java.io.Serializable

data class MountainList(
    var m_id:Int,
    var m_name:String,
    var m_level:String,
//    var m_img:String,
    var area:String,
    var m_explain:String
//    var parking:String,
//    var m_address:String,
//    var items_name:String,
//    var items_img:String
): Serializable

