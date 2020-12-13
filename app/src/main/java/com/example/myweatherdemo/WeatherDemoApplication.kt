package com.example.myweatherdemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherDemoApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        //彩云天气的API令牌
        const val TOKEN = "bdKp4RE8E3iU7QrX"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}