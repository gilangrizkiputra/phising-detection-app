package com.project.phistingdetection.data.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsdata.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}