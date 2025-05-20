package com.example.homepilot.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homepilot.AuthViewModel
import com.example.homepilot.R

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedTab) { selectedTab = it }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> DashboardSection()
                1 -> ObjectDetectionSection()
                2 -> ARDIYSection()
                3 -> VideoCallSection()
                4 -> BookingSection()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        val items = listOf("Home", "Detect", "AR DIY", "Video", "Booking")
        val icons = listOf(
            R.drawable.ic_home, R.drawable.ic_detect, R.drawable.ic_ar,
            R.drawable.ic_video, R.drawable.ic_booking
        )
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = label,
                        modifier = Modifier.size(26.dp),
                        tint = if (selectedTab == index) MaterialTheme.colorScheme.primary else Color.Black
                    )
                },
                label = { Text(label, fontSize = 10.sp) }
            )
        }
    }
}

@Composable
fun DashboardSection() {
    FeatureContent("Dashboard", "Overview of your smart assistant controls and recent activity.")
}

@Composable
fun ObjectDetectionSection() {
    FeatureContent("Object Detection", "Scan items using your camera to identify and suggest tools.")
}

@Composable
fun ARDIYSection() {
    FeatureContent("AR DIY", "Get step-by-step AR-based DIY assistance using Sceneform and ARCore.")
}

@Composable
fun VideoCallSection() {
    FeatureContent("Video Call", "Connect with service professionals through secure video chat.")
}

@Composable
fun BookingSection() {
    FeatureContent("Book a Pro", "Browse local pros, view availability, and book services.")
}

@Composable
fun FeatureContent(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = description, fontSize = 16.sp, color = Color.Gray)
    }
}
