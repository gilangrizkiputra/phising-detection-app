package com.project.phistingdetection.model

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
