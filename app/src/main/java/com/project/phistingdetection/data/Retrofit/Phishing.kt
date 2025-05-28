package com.project.phistingdetection.data.Retrofit

import com.project.phistingdetection.model.PhishingResponse
import com.project.phistingdetection.model.UrlRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PhishingApi {
    @POST("predict")
    suspend fun checkUrl(@Body request: UrlRequest): PhishingResponse
}
