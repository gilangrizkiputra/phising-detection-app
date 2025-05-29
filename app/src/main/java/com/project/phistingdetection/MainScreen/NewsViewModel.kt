package com.project.phistingdetection.MainScreen

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.phistingdetection.data.Retrofit.NewsRetrofitClient
import com.project.phistingdetection.data.model.NewsItem
import kotlinx.coroutines.launch
import com.project.phistingdetection.BuildConfig

class NewsViewModel : ViewModel() {

    var newsList by mutableStateOf<List<NewsItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = NewsRetrofitClient.instance.getLatestNews(
                    apiKey = BuildConfig.NEWS_API_KEY,
                    query = "phishing",
                    language = "id"
                )
                newsList = response.results
            } catch (e: Exception) {
                Log.e("NewsFetch", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
