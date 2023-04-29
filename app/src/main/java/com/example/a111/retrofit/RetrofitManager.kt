package com.example.a111.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }
    val retrofit = Retrofit.Builder().baseUrl("http://54.87.120.41:8080/com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()

     val iRetrofit:IRetrofit =retrofit.create(IRetrofit::class.java)




}