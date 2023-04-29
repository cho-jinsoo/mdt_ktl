package com.example.a111.model

import java.io.Serializable

data class CommentList (
    var u_name:String,
    var b_id:Int,
    var comment_contents:String,
    var comment_date:String,
    var u_level:Int
        ): Serializable
