package com.example.myweatherdemo.logic.network


import com.example.myweatherdemo.WeatherDemoApplication
import com.example.myweatherdemo.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${WeatherDemoApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}