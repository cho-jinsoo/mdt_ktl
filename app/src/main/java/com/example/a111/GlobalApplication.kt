package com.example.a111

import android.app.Application
import android.content.Context
import com.example.a111.preference.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ApplicationContextInfo

class GlobalApplication : Application() {

    init{
        instance = this
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var instance: GlobalApplication

        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }


    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()

        KakaoSdk.init(this ,"ae5d6352d802d1d0f44df15efc807186" )
    }

}