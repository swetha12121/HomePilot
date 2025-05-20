package com.example.homepilot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homepilot.pages.HomePage
import com.example.homepilot.pages.LoginPage
import com.example.homepilot.pages.SignupPage

@Composable
fun Navigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
   val navController: NavHostController = rememberNavController()

   NavHost(navController = navController, startDestination = "login") {
      composable("login") {
         LoginPage(modifier, navController, authViewModel)
      }
      composable("signup") {
         SignupPage(modifier, navController, authViewModel)
      }
      composable("home") {
         HomePage(modifier, navController, authViewModel)
      }
      composable("scan") {
         ObjectDetectionPage(modifier, navController,authViewModel)
      }
      composable("diy-ar-guide") {
         ARDIYGuidePage(modifier, navController,authViewModel)
      }
      composable("video-support") {
         VideoSupportPage(modifier, navController,authViewModel)
      }
      composable("booking") {
         BookingPage(modifier, navController,authViewModel)
      }
      composable("payment") {
         PaymentPage(modifier, navController,authViewModel)
      }
      composable("experts-map") {
         ExpertsMapPage(modifier, navController,authViewModel)
      }
      composable("my-bookings") {
         MyBookingsPage(modifier, navController,authViewModel)
      }
      composable("pro-profile/{id}") { backStackEntry ->
         val proId = backStackEntry.arguments?.getString("id") ?: ""
         ProProfilePage(modifier, navController, proId,authViewModel)
      }
      composable("settings") {
         SettingsPage(modifier, navController,authViewModel)
      }
   }
}