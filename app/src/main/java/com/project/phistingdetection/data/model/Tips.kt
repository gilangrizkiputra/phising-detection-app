package com.project.phistingdetection.data.model

import com.project.phistingdetection.R

data class Tips(
    val iconRes: Int,
    val title: String,
    val description: String
)

fun getDummyTips(): List<Tips> = listOf(
    Tips(
        R.drawable.ic_response,
        "Respon",
        "Jangan pernah menanggapi email yang meminta informasi probadi."
    ),
    Tips(
        R.drawable.ic_spy,
        "Jadilah Curiga",
        "Waspadalah email yang tidak menyebutkan nama Anda, salah mengeja atau tidak terlihat profesional."
    ),
    Tips(
        R.drawable.ic_navigasi,
        "Navigasi",
        "Selalu navigasikan ke situs web bank, media sosial atau lembaga keuanga dengan mengetikkan alamat web, daripada mengeklik tautan url."
    ),
    Tips(
        R.drawable.ic_dukungan,
        "Dukungan",
        "Jangan pernah mengeklik tautan di email, sosial media dll yang terlihat mecurigakan."
    )
)
