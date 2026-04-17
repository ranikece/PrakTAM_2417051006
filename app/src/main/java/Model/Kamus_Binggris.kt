package com.example.praktam_2417051006.Model
import androidx.annotation.DrawableRes

data class Kamus_Binggris(
    val kataInggris: String,
    val artiIndonesia: String,
    val contohKalimat: String,
    @DrawableRes val gambarResId: Int
)
