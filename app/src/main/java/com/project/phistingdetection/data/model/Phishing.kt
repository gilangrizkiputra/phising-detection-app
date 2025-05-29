package com.project.phistingdetection.data.model

data class PhishingResponse(
    val url: String,
    val status: String,
    val message: String,
    val prediction: Int,
    val probability: Probability
)

data class Probability(
    val phishing: Double,
    val non_phishing: Double
)

data class UrlRequest(
    val url: String
)
