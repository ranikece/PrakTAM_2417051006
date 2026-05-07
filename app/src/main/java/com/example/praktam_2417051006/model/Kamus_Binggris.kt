package com.example.praktam_2417051006.model

import com.google.gson.annotations.SerializedName

data class Kamus_Binggris(
    @SerializedName("kata_inggris")
    val kataInggris: String,
    @SerializedName("arti_indonesia")
    val artiIndonesia: String,
    @SerializedName("contoh_kalimat")
    val contohKalimat: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("category")
    val category: String
)
