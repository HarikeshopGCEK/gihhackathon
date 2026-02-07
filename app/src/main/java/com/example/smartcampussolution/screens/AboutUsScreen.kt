package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AboutUsScreen() {
    AboutUsScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutUsScreenContent() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "About Smart Campus") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Campus Complaint & Request System",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "This platform helps students and staff report campus issues, track progress, and improve service delivery.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Key Features",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "- Submit complaints and requests in minutes")
            Text(text = "- Track status updates in real time")
            Text(text = "- Admin console for faster resolution")
            Text(
                text = "Built to deliver a safer, smarter, and more responsive campus experience.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
