package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun MyComplaintsScreen(navController: NavController) {
    MyComplaintsScreenContent()
}

private data class ComplaintItem(
    val title: String,
    val status: String,
    val location: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyComplaintsScreenContent() {
    val complaints = listOf(
        ComplaintItem("Broken AC in Library", "In Review", "Central Library", "Jan 28"),
        ComplaintItem("Water leakage in Hostel B", "Resolved", "Hostel B", "Jan 20"),
        ComplaintItem("Streetlight outage", "Pending", "Main Gate", "Jan 18")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "My Complaints") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(complaints) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = item.location, style = MaterialTheme.typography.bodySmall)
                            Text(text = item.date, style = MaterialTheme.typography.bodySmall)
                        }
                        Text(
                            text = "Status: ${item.status}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
