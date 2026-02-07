package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartcampussolution.data.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun DashboardScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    val isAdmin = currentUser?.email == "opharikesh2005@gmail.com"

    var pendingCount by remember { mutableIntStateOf(0) }
    var inProgressCount by remember { mutableIntStateOf(0) }
    var resolvedCount by remember { mutableIntStateOf(0) }
    var recentComplaints by remember { mutableStateOf(emptyList<Complaint>()) }

    LaunchedEffect(Unit) {
        // Listen for status counts
        firestore.collection("complaints").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val docs = snapshot.documents
                pendingCount = docs.count { it.getString("status") == "Pending" }
                inProgressCount = docs.count { it.getString("status") == "In Progress" }
                resolvedCount = docs.count { it.getString("status") == "Resolved" }
            }
        }

        // Listen for recent updates (top 5)
        firestore.collection("complaints")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(5)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    recentComplaints = snapshot.toObjects(Complaint::class.java)
                }
            }
    }

    DashboardScreenContent(
        pendingCount = pendingCount,
        inProgressCount = inProgressCount,
        resolvedCount = resolvedCount,
        recentComplaints = recentComplaints,
        isAdmin = isAdmin,
        onAddComplaint = { navController.navigate("addComplaint") },
        onMyComplaints = { navController.navigate("myComplaints") },
        onAdmin = { navController.navigate("admin") },
        onAbout = { navController.navigate("about") },
        onLogout = {
            auth.signOut()
            navController.navigate("login") {
                popUpTo("dashboard") { inclusive = true }
            }
        }
    )
}

private data class StatusSummary(val label: String, val value: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    pendingCount: Int,
    inProgressCount: Int,
    resolvedCount: Int,
    recentComplaints: List<Complaint>,
    isAdmin: Boolean,
    onAddComplaint: () -> Unit,
    onMyComplaints: () -> Unit,
    onAdmin: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit
) {
    val summaries = listOf(
        StatusSummary("Pending", pendingCount),
        StatusSummary("In Progress", inProgressCount),
        StatusSummary("Resolved", resolvedCount)
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
                                    text = summary.value.toString(),
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
                    if (isAdmin) {
                        Button(
                            onClick = onAdmin,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text(text = "Admin Console")
                        }
                    }
                    Button(onClick = onAbout, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "About the System")
                    }
                    OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Logout")
                    }
                }
            }
            item {
                Text(text = "Recent Updates", style = MaterialTheme.typography.titleMedium)
            }
            if (recentComplaints.isEmpty()) {
                item {
                    Text(
                        text = "No recent activity found.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            items(recentComplaints) { complaint ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = complaint.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            StatusBadge(status = complaint.status)
                        }
                        Text(
                            text = "Category: ${complaint.category}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
