package com.example.a111

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk

class SoketApplicaion: Application() {
    companion object {
        var appContext : Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this,"ae5d6352d802d1d0f44df15efc807186")
    }
}