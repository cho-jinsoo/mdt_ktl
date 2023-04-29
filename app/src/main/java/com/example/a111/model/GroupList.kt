package com.example.a111.model

import java.io.Serializable

data class GroupList(
                    var bg_id:Int,
                     var u_id:String,
                     var bg_name:String,
                     var bg_date:String,
                    var bg_experience:Double,
                    var bg_level:Int,
                    var bg_intro:String
                     ): Serializable