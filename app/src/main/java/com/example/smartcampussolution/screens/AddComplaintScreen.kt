package com.example.smartcampussolution.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartcampussolution.data.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AddComplaintScreen(navController: NavController) {
    AddComplaintScreenContent(onComplaintSubmitted = { navController.navigate("myComplaints") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddComplaintScreenContent(onComplaintSubmitted: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    suspend fun submitComplaint() {
        if (title.isBlank() || description.isBlank()) {
            snackbarHostState.showSnackbar("Please fill in title and description")
            return
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            snackbarHostState.showSnackbar("Please log in to submit complaints")
            return
        }

        isLoading = true
        try {
            val complaint =
                    Complaint(
                            title = title.trim(),
                            description = description.trim(),
                            category = category.trim(),
                            location = location.trim(),
                            contact = contact.trim(),
                            userId = currentUser.uid,
                            userEmail = currentUser.email ?: "",
                            timestamp = Date()
                    )

            firestore.collection("complaints").add(complaint).await()

            snackbarHostState.showSnackbar("Complaint submitted successfully!")
            onComplaintSubmitted()
        } catch (e: Exception) {
            snackbarHostState.showSnackbar("Error: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
            topBar = { TopAppBar(title = { Text(text = "Submit Complaint") }) },
            snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .padding(innerPadding)
                                .padding(20.dp)
                                .imePadding()
                                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                    text = "Tell us what needs attention",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    text = "Provide clear details so the campus team can respond quickly.",
                    style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
            )
            OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g., Facilities, Safety)") },
                    modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    label = { Text("Preferred contact") },
                    modifier = Modifier.fillMaxWidth()
            )
            Button(
                    onClick = { scope.launch { submitComplaint() } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.height(20.dp))
                } else {
                    Text(text = "Submit Complaint")
                }
            }
        }
    }
}
