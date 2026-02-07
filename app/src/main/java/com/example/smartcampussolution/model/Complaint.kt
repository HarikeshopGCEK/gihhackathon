package com.example.smartcampussolution.model

data class Complaint(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val location: String = "",
    val status: String = "Pending",
    val studentName: String = "",
    val date: String = ""
)
