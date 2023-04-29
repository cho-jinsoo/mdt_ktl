package com.example.a111

import android.app.Application
import com.example.a111.preference.PreferenceUtil

class app : Application() {

    companion object{
        lateinit var instance: app
            private set


    }

    override fun onCreate() {

        super.onCreate()
        instance = this
    }




}