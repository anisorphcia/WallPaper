package com.asakao.wallpaper.logic.network

import android.content.Context
import android.content.Intent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "http://service.picasso.adesk.com/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    // 泛型实化，借助 reified 以及 inline来实现
    // 可以获取当前泛型的类型
    // 可用在 intent 地方，需要注明当前的类
    inline fun <reified T> create(): T = create(T::class.java)


}