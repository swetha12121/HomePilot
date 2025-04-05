package com.example.homepilot.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homepilot.AuthState
import com.example.homepilot.AuthViewModel
import com.example.homepilot.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val authState = authViewModel.authstate.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 40.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.White,
                    unfocusedContainerColor = Color.Gray.copy(alpha = 0.2f),
                    focusedContainerColor = Color.Gray.copy(alpha = 0.2f)
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.White,
                    unfocusedContainerColor = Color.Gray.copy(alpha = 0.2f),
                    focusedContainerColor = Color.Gray.copy(alpha = 0.2f)
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Checkbox(
                    checked = showPassword,
                    onCheckedChange = { showPassword = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Blue,
                        uncheckedColor = Color.White
                    )
                )
                Text("Show Password", color = Color.Black)
            }

            Button(
                onClick = {
                    val auth = FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("home")
                            } else {
                                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                modifier = Modifier.padding(top = 18.dp)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account? Signup",
                color = Color.Black,
                modifier = Modifier.clickable {
                    navController.navigate("signup")
                }
            )
        }
    }
}

private fun authenticate(email: String, password: String): Boolean {
    if (email.isBlank() || password.isBlank()) return false

    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    val validEmail = "test@example.com"
    val validPassword = "password123"

    return email.matches(emailPattern) && password == validPassword
}