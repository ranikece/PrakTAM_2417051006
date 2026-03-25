package com.example.praktam_2417051006.fitur

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.sp
import com.example.praktam_2417051006.Model.Isi_kamus
import com.example.praktam_2417051006.Model.Kamus_Binggris

@Composable
fun DashboardScreen(innerPadding: PaddingValues) {
    val cardColors = listOf(
        Color(0xFFFFD1DC), // Soft Pink
        Color(0xFFE0BBE4), // Lilac
        Color(0xFFB3E5FC), // Soft Blue
        Color(0xFFFFF9C4), // Soft Yellow
        Color(0xFFC8E6C9)  // Soft Green
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Hi, Putri Maharani 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Mau belajar apa hari ini?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }

        item {
            Column {
                Text(
                    text = "Rekomendasi Populer",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    itemsIndexed(Isi_kamus.listKamus) { index, kamus ->
                        KamusRowCard(kamus, cardColors[index % cardColors.size])
                    }
                }
            }
        }

        item {
            Text(
                text = "Daftar Kamus Lengkap",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }

        itemsIndexed(Isi_kamus.listKamus) { index, kamus ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                KamusCard(kamus, cardColors[index % cardColors.size])
            }
        }
    }
}

@Composable
fun KamusRowCard(kamus: Kamus_Binggris, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = kamus.gambarResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = kamus.kataInggris,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    text = kamus.artiIndonesia,
                    fontSize = 12.sp,
                    color = Color.DarkGray.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.extraSmall,
                    color = Color(0xFFFFFDD0),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Lihat Detail",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun KamusCard(kamus: Kamus_Binggris, backgroundColor: Color) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = kamus.gambarResId),
                    contentDescription = kamus.kataInggris,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = kamus.kataInggris,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = kamus.artiIndonesia,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFFFFFDD0), 
                        disabledContentColor = Color.DarkGray
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(text = "Lihat Detail", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
