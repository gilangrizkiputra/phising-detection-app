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

    var isNotSafe by mutableStateOf(false)
        private set

    // Fungsi untuk memeriksa URL
    fun checkUrl(url: String) {
        if (url.isBlank()) {
            urlStatus = "URL tidak boleh kosong"
            isUrlChecked = true
            isSafe = false
            isNotSafe = false
            return
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            urlStatus = "Input form harus berupa tautan website (http/https)"
            isUrlChecked = true
            isSafe = false
            isNotSafe = false
            return
        }

        isLoading = true
        isUrlChecked = false

        viewModelScope.launch {
            try {

                val request = UrlRequest(url = url)

                val response = RetrofitClient.instance.checkUrl(request)

                isUrlChecked = true
                isSafe = response.status.equals("Aman", ignoreCase = true)
                isNotSafe = response.status.equals("Tidak Aman", ignoreCase = true)

                val safePercentage = if (response.prediction == 1) {
                    (response.probability.non_phishing * 100).roundToInt()
                } else {
                    (response.probability.phishing * 100).roundToInt()
                }

                urlStatus = "Link $safePercentage% ${response.status.uppercase()} digunakan"

            } catch (e: Exception) {
                urlStatus = "Error: ${e.message}"
                isUrlChecked = true
                isSafe = false
            } finally {
                isLoading = false
            }
        }
    }

    fun reset() {
        urlStatus = ""
        urlSafePercentage = ""
        isLoading = false
        isUrlChecked = false
        isSafe = false
        isNotSafe = false
    }
}
