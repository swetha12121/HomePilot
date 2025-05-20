package com.example.homepilot.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homepilot.AuthViewModel
import androidx.compose.foundation.clickable


@Composable
fun SettingsPage(modifier: Modifier = Modifier, navController: NavController,authViewModel: AuthViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Settings", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        SettingsOption("Edit Profile") { println("Navigating to Edit Profile") }
        SettingsOption("Notifications") { println("Opening Notification Settings") }
        SettingsOption("Help & Support") { println("Accessing Help & Support") }
        SettingsOption("Privacy Policy") { println("Viewing Privacy Policy") }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                authViewModel.signout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

@Composable
fun SettingsOption(title: String, onClick: @Composable () -> Unit) {
    Text(
        text = title,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick }
            .padding(vertical = 12.dp)
    )
}
