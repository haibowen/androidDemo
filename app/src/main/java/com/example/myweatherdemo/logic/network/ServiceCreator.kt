package com.example.myweatherdemo.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(servicesClass: Class<T>): T = retrofit.create(servicesClass)
    inline fun <reified T> create(): T = create(T::class.java)
    //解释 inline的声明意义   想当与把  被 inline声明的 方法体代码粘贴到函数的调用处

}