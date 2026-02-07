package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun DashboardScreen(navController: NavController) {
    DashboardScreenContent(
        onAddComplaint = { navController.navigate("addComplaint") },
        onMyComplaints = { navController.navigate("myComplaints") },
        onAdmin = { navController.navigate("admin") },
        onAbout = { navController.navigate("about") },
        onLogout = { navController.navigate("login") }
    )
}

private data class StatusSummary(val label: String, val value: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    onAddComplaint: () -> Unit,
    onMyComplaints: () -> Unit,
    onAdmin: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit
) {
    val summaries = listOf(
        StatusSummary("Open", "8"),
        StatusSummary("In Review", "3"),
        StatusSummary("Resolved", "15")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Dashboard") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Campus Complaint & Request System",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Track issues, submit requests, and follow progress in one place.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    summaries.forEach { summary ->
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = summary.label, style = MaterialTheme.typography.labelLarge)
                                Text(
                                    text = summary.value,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            item {
                Text(text = "Quick Actions", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = onAddComplaint, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Submit a Complaint")
                    }
                    Button(onClick = onMyComplaints, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "My Complaints")
                    }
                    Button(onClick = onAdmin, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Admin Console")
                    }
                    Button(onClick = onAbout, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "About the System")
                    }
                    Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Logout")
                    }
                }
            }
            item {
                Text(text = "Recent Updates", style = MaterialTheme.typography.titleMedium)
            }
            items(
                listOf(
                    "Library AC reported as faulty - under review",
                    "Hostel water leak resolved",
                    "New request submitted for parking lights"
                )
            ) { update ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = update,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
