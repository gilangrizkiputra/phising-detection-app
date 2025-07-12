package com.project.phistingdetection.data.model

import java.io.Serializable

data class News(
    val results: List<NewsItem>
)

data class NewsItem(
    val title: String,
    val link: String,
    val description: String?,
    val image_url: String?,
    val pubDate: String,
    val source_name: String
) : Serializable
