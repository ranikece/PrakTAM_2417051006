package com.example.praktam_2417051006

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.praktam_2417051006.model.Kamus_Binggris
import com.example.praktam_2417051006.fitur.DashboardScreen
import com.example.praktam_2417051006.ui.theme.PrakTAM_2417051006Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM_2417051006Theme {
                var currentScreen by remember { mutableStateOf("dashboard") }
                var kamusList by remember { mutableStateOf<List<Kamus_Binggris>>(emptyList()) }
                var selectedIndex by remember { mutableStateOf(0) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "dashboard" -> {
                            DashboardScreen(
                                innerPadding = innerPadding,
                                onKamusLoaded = { fetchedList -> kamusList = fetchedList },
                                onDetailClick = { index ->
                                    selectedIndex = index
                                    currentScreen = "detail"
                                }
                            )
                        }
                        "detail" -> {
                            if (kamusList.isNotEmpty() && selectedIndex < kamusList.size) {
                                DetailScreen(
                                    kamus = kamusList[selectedIndex],
                                    onBack = { currentScreen = "dashboard" }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(kamus: Kamus_Binggris, onBack: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val placeholderRes = when (kamus.category.lowercase()) {
        "animal" -> R.drawable.animal
        "fruit" -> R.drawable.fruit
        "object" -> R.drawable.`object`
        "stationary" -> R.drawable.stationary
        "vehicle" -> R.drawable.vehicle
        else -> R.drawable.animal
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kata", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    AsyncImage(
                        model = kamus.imageUrl,
                        contentDescription = null,
                        placeholder = painterResource(id = placeholderRes),
                        error = painterResource(id = placeholderRes),
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = kamus.kataInggris,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = kamus.artiIndonesia,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Contoh Kalimat:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = kamus.contohKalimat,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(2000)
                        snackbarHostState.showSnackbar("Berhasil mempelajari kata '${kamus.kataInggris}'!")
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Memproses...")
                } else {
                    Text("Mulai Belajar Kata Ini")
                }
            }
        }
    }
}
