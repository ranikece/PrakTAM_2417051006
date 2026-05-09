package com.example.praktam_2417051006.fitur

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.praktam_2417051006.model.Kamus_Binggris
import com.example.praktam_2417051006.data.api.RetrofitClient
import com.example.praktam_2417051006.R
import com.example.praktam_2417051006.data.repository.KamusRepository

@Composable
fun DashboardScreen(
    innerPadding: PaddingValues,
    onKamusLoaded: (List<Kamus_Binggris>) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    var kamusList by remember { mutableStateOf<List<Kamus_Binggris>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var repository = KamusRepository()

    LaunchedEffect(Unit) {
        try {
            var isloading = true
            var kamus = repository.getKamus()
            val response = RetrofitClient.instance.getKamus()
            kamusList = response
            onKamusLoaded(response)
            isLoading = false
            isError = false
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (isError) {
            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gagal Memuat Data", style = MaterialTheme.typography.titleLarge, color = Color.Red, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pastikan koneksi internet Anda menyala", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Hi, Putri Maharani 👋", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = "Mau belajar apa hari ini?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                    }
                }

                item {
                    Column {
                        Text(text = "Rekomendasi Populer", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            itemsIndexed(kamusList) { index, kamus ->
                                val bgColor = if (index % 2 == 0) Color(0xFFE0BBE4) else Color(0xFFB3E5FC)
                                KamusCard(kamus, bgColor, isRow = true, onClick = { onDetailClick(index) })
                            }
                        }
                    }
                }

                item {
                    Text(text = "Daftar Kamus Lengkap", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }

                itemsIndexed(kamusList) { index, kamus ->
                    val bgColor = if (index % 2 == 0) Color(0xFFE0BBE4) else Color(0xFFB3E5FC)
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        KamusCard(kamus, bgColor, isRow = false, onClick = { onDetailClick(index) })
                    }
                }
            }
        }
    }
}

@Composable
fun KamusCard(kamus: Kamus_Binggris, containerColor: Color, isRow: Boolean, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    
    // Pilih gambar default berdasarkan kategori dari JSON
    val placeholderRes = when (kamus.category.lowercase()) {
        "animal" -> R.drawable.animal
        "fruit" -> R.drawable.fruit
        "object" -> R.drawable.`object`
        "stationary" -> R.drawable.stationary
        "vehicle" -> R.drawable.vehicle
        else -> R.drawable.animal
    }

    Card(
        modifier = if (isRow) Modifier.width(180.dp) else Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = kamus.imageUrl,
                    contentDescription = kamus.kataInggris,
                    placeholder = painterResource(id = placeholderRes),
                    error = painterResource(id = placeholderRes),
                    modifier = Modifier.fillMaxWidth().height(if (isRow) 130.dp else 200.dp),
                    contentScale = ContentScale.Crop
                )
                if (!isRow) {
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = kamus.kataInggris, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = kamus.artiIndonesia, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFFFF80AB)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(text = "Lihat Detail", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
