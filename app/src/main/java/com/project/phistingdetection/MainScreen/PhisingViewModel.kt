package com.project.phistingdetection.MainScreen

import com.project.phistingdetection.data.Retrofit.RetrofitClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.phistingdetection.model.UrlRequest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class PhishingViewModel : ViewModel() {

    var urlStatus by mutableStateOf("")
        private set

    var urlSafePercentage by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isUrlChecked by mutableStateOf(false)
        private set

    var isSafe by mutableStateOf(false)
        private set

    // Fungsi untuk memeriksa URL
    fun checkUrl(url: String) {
        if (url.isBlank()) {
            urlStatus = "URL tidak boleh kosong"
            return
        }

        isLoading = true
        isUrlChecked = false

        viewModelScope.launch {
            try {

                val request = UrlRequest(url = url)

                val response = RetrofitClient.instance.checkUrl(request)

                isUrlChecked = true
                isSafe = response.status == "Aman"

                urlStatus = if (isSafe) {
                    "Link 100% AMAN digunakan"
                } else {
                    "Link 100% TIDAK AMAN digunakan"
                }

                val safePercentage = if (isSafe) {
                    (response.probability.non_phishing * 100).roundToInt()
                } else {
                    (response.probability.phishing * 100).roundToInt()
                }

                urlSafePercentage = "Tingkat keyakinan: $safePercentage%"

            } catch (e: Exception) {
                urlStatus = "Error: ${e.message}"
                isUrlChecked = true
                isSafe = false
            } finally {
                isLoading = false
            }
        }
    }
}
