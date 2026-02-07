package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminScreen(navController: NavController) {
    AdminScreenContent()
}

private data class AdminComplaint(
    val title: String,
    val reporter: String,
    val status: String,
    val location: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminScreenContent() {
    val queue = listOf(
        AdminComplaint("Electrical outage in Lab 3", "Amina K.", "Pending", "Engineering Block"),
        AdminComplaint("Broken benches near cafeteria", "Samuel T.", "In Review", "Cafeteria"),
        AdminComplaint("Wi-Fi down in Hostel C", "Lerato M.", "Pending", "Hostel C")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Admin Console") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(queue) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Reported by ${item.reporter}")
                        Text(text = "Location: ${item.location}")
                        Text(text = "Status: ${item.status}")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = {}) { Text(text = "Assign") }
                            Button(onClick = {}) { Text(text = "Resolve") }
                        }
                    }
                }
            }
        }
    }
}
