package com.example.homepilot.pages

import androidx.compose.runtime.Composable
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.homepilot.AuthViewModel

@Composable
fun ObjectDetectionPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }//Used to handle permission requests
    var isCameraReady by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(//to request permission
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted // Saves the result to permissionGranted


    }

    LaunchedEffect(Unit) {//Automatically launches permission check on first composition
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED//CAMERAX Used for showing camera feed

        if (!granted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            permissionGranted = true
        }
    }

    if (permissionGranted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Scan the Issue",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                AndroidView(factory = { ctx ->//Sets up a Preview pipeline for back camera
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                ctx as LifecycleOwner, cameraSelector, preview
                            )//Binds camera to lifecycle so it automatically handles pause/resume
                            isCameraReady = true//Once it binds successfully, shows the action button
                        } catch (exc: Exception) {
                            Toast.makeText(ctx, "Camera error: ${exc.message}", Toast.LENGTH_SHORT).show()
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                    previewView
                })
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!isCameraReady) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        println("Running object detection...")
                        navController.navigate("diy-ar-guide")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Run Detection and Continue")
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission is required to use this feature")
        }
    }
}