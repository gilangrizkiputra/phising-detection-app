package com.project.phistingdetection.data.model

import com.project.phistingdetection.R


data class DataNewsDummy(
    val imageUrl: Int,
    val title: String,
    val content: String
)

fun getDummyNews(): List<DataNewsDummy> = listOf(
    DataNewsDummy(
        imageUrl = R.drawable.img_news1,
        title = "Hati-Hati dengan Phishing di Email Kantor",
        content = "Phishing kini menyasar email perusahaan. Waspadalah terhadap link mencurigakan."
    ),
    DataNewsDummy(
        imageUrl = R.drawable.img_news1,
        title = "Peretasan Akun Instagram Semakin Canggih",
        content = "Metode baru phishing lewat DM Instagram kini marak terjadi."
    ),
    DataNewsDummy(
        imageUrl = R.drawable.img_news1,
        title = "Cara Aman Mengakses Wi-Fi Publik",
        content = "Tips dan trik agar tidak terkena sniffing di jaringan umum."
    ),
    DataNewsDummy(
        imageUrl = R.drawable.img_news1,
        title = "Cara Aman Mengakses Wi-Fi Publik",
        content = "Tips dan trik agar tidak terkena sniffing di jaringan umum."
    ),
    DataNewsDummy(
        imageUrl = R.drawable.img_news1,
        title = "Cara Aman Mengakses Wi-Fi Publik",
        content = "Tips dan trik agar tidak terkena sniffing di jaringan umum."
    )

)