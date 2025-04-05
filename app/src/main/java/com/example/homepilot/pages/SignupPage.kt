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

@Composable
fun SignupPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authState by authViewModel.authstate.observeAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmFocusRequester = remember { FocusRequester() }

    authState?.let { state ->
        if (state is AuthState.Error) {
            LaunchedEffect(state.id) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create Account", fontSize = 35.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.Black) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
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
                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+[A-Za-z]{2,6}$")
                    when {
                        email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                        !email.matches(emailRegex) -> {
                            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                        }
                        password.length < 6 -> {
                            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                        }
                        password != confirmPassword -> {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            authViewModel.signup(email, password)
                        }
                    }
                },
                modifier = Modifier.padding(top = 18.dp)
            ) {
                Text("Signup")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account? Login",
                color = Color.Black,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}
