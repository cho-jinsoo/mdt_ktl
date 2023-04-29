package com.example.a111.model

import java.io.Serializable

data class BgmList(
    var bgm_id:Int,
    var bg_id:Int,
    var bg_name:String,
    var u_id:String,
    var u_experience: Long,
    var u_level:Int,
    var bgm_date:String
                     ): Serializable