package com.example.homepilot.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homepilot.AuthState
import com.example.homepilot.AuthViewModel
import androidx.compose.material3.Text

@Composable
fun HomePage (modifier:Modifier = Modifier, navController : NavController, authViewModel: AuthViewModel){
    val authState = authViewModel.authstate.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.UnAuthenticated->navController.navigate("login")
            else->Unit
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text (text = "Home Page", fontSize = 35.sp)
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text (text="Sign Out")
        }
    }

}