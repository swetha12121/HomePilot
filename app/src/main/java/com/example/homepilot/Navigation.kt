package com.example.homepilot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.homepilot.pages.HomePage
import com.example.homepilot.pages.LoginPage
import com.example.homepilot.pages.SignupPage

@Composable
fun Navigation(modifier: Modifier = Modifier) {
   val navController = rememberNavController()

   NavHost(navController = navController, startDestination = "login",builer =
   {
      composable("login") {
         LoginPage(modifier,navController)
      }
      composable("signup") {
         SignupPage(navController)
      }
      composable("home") {
         HomePage(navController)
    )
   }
}
