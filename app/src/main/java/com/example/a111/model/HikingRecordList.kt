package com.example.a111.model

import java.io.Serializable


data class HikingRecordList(
    var u_id:String,
    var start_location:String,
    var finish_location:String,
    var record_time:String,
    var hiking_name:String,
    var hiking_level:String,
    var hiking_exp:String,
    var hiking_date:String
): Serializable
