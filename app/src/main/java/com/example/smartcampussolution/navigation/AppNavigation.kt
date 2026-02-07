package com.example.smartcampussolution.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartcampussolution.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("addComplaint") {
            AddComplaintScreen(navController)
        }

        composable("myComplaints") {
            MyComplaintsScreen(navController)
        }

        composable("admin") {
            AdminScreen(navController)
        }

        composable("about") {
            AboutUsScreen()
        }
    }
}
