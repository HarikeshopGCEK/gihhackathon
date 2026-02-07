package com.example.smartcampussolution.data

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Complaint(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val location: String = "",
    val contact: String = "",
    val status: String = "Pending", // Pending, In Progress, Resolved
    val userId: String = "",
    val userEmail: String = "",
    val timestamp: Date = Date(),
    val priority: String = "Medium", // Low, Medium, High
    val feedback: String = "" // Additional user notes or student feedback
)
