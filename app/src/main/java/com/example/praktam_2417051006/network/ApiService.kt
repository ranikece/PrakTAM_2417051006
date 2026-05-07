package com.example.praktam_2417051006.network

import com.example.praktam_2417051006.model.Kamus_Binggris
import retrofit2.http.GET

interface ApiService {
    @GET("kamus_binggris.json")
    suspend fun getKamus(): List<Kamus_Binggris>
}
