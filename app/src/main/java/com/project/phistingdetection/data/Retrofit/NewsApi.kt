package com.project.phistingdetection.data.Retrofit

import com.project.phistingdetection.data.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("api/1/latest")
    suspend fun getLatestNews(
        @Query("apikey") apiKey: String,
        @Query("q") query: String = "phishing",
        @Query("language") language: String = "id"
    ): News
}
