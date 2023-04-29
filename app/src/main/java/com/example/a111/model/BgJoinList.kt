package com.example.a111.model

import java.io.Serializable

data class BgJoinList(
    var bgj_id:Int,
                    var bg_id:Int,
                     var bg_name:String,
                     var u_id:String,
                     var u_experience:Double,
                    var u_level:Int,
                    var bgj_date:String,
                    var y_n:String,
                    var note:String

): Serializable