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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AdminScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Check if user is admin
    if (currentUser?.email != "opharikesh2005@gmail.com") {
        LaunchedEffect(Unit) {
            navController.navigate("dashboard") {
                popUpTo("admin") { inclusive = true }
            }
        }
        return
    }

    AdminScreenContent(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminScreenContent(navController: NavController) {
    val firestore = FirebaseFirestore.getInstance()
    var complaints by remember { mutableStateOf(emptyList<Complaint>()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        firestore.collection("complaints")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    complaints = snapshot.toObjects(Complaint::class.java)
                }
                isLoading = false
            }
    }

    suspend fun updateStatus(complaintId: String, newStatus: String) {
        try {
            firestore.collection("complaints").document(complaintId)
                .update("status", newStatus).await()
            snackbarHostState.showSnackbar("Status updated to $newStatus")
        } catch (e: Exception) {
            snackbarHostState.showSnackbar("Error: ${e.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Admin Console") },
                navigationIcon = {
                    TextButton(onClick = { navController.navigateUp() }) {
                        Text("Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (complaints.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No complaints found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(complaints) { complaint ->
                    ComplaintAdminCard(
                        complaint = complaint,
                        onStatusUpdate = { status ->
                            scope.launch { updateStatus(complaint.id, status) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ComplaintAdminCard(
    complaint: Complaint,
    onStatusUpdate: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = complaint.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(status = complaint.status)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Reporter: ${complaint.userEmail}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Location: ${complaint.location}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = complaint.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (complaint.status == "Pending") {
                    Button(
                        onClick = { onStatusUpdate("In Progress") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Review")
                    }
                }
                if (complaint.status != "Resolved") {
                    Button(
                        onClick = { onStatusUpdate("Resolved") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Resolve")
                    }
                    OutlinedButton(
                        onClick = { onStatusUpdate("Rejected") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Reject")
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Pending" -> MaterialTheme.colorScheme.error
        "In Progress" -> MaterialTheme.colorScheme.tertiary
        "Resolved" -> androidx.compose.ui.graphics.Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.outline
    }
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.extraSmall,
        border = androidx.compose.foundation.BorderStroke(1.dp, color)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}
