package com.example.a111.model

import java.io.Serializable

data class UsersList(
    var u_id: String,
    var u_address: String,
    var u_pw: String,
    var u_name: String,
    var u_level:Int,
    var u_experience: String
): Serializable