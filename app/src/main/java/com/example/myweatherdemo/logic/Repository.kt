package com.example.myweatherdemo.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myweatherdemo.logic.model.Place
import com.example.myweatherdemo.logic.model.Weather
import com.example.myweatherdemo.logic.network.WeatherDemoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlace(query: String) = liveData(Dispatchers.IO) {

        val result = try {
            val placeResponse = WeatherDemoNetwork.searchPlaces(query)
            if (placeResponse.status == "OK") {
                val place = placeResponse.places
                Result.success(place)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {

        coroutineScope {
            val deferredRealtime = async {
                WeatherDemoNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                WeatherDemoNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" + "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }

    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}