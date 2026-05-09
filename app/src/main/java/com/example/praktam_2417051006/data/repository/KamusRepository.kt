package com.example.praktam_2417051006.data.repository

import com.example.praktam_2417051006.data.api.RetrofitClient
import com.example.praktam_2417051006.model.Kamus_Binggris

class KamusRepository {

    suspend fun getKamus(): List<Kamus_Binggris> {
        return try {
            RetrofitClient.instance.getKamus()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getKamus_Binggris(): List<Kamus_Binggris> {
        return getKamus()
    }
}
