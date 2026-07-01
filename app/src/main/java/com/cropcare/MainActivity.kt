package com.cropcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cropcare.core.ui.theme.CropCareTheme
import com.cropcare.feature.notifications.permission.ExactAlarmPermissionEffect
import com.cropcare.feature.notifications.permission.NotificationPermissionEffect
import com.cropcare.navigation.CropCareNavHost
import com.cropcare.navigation.NotificationDeepLinkHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CropCareTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                NotificationPermissionEffect(snackbarHostState)
                ExactAlarmPermissionEffect(snackbarHostState)
                NotificationDeepLinkHandler(navController)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { padding ->
                    CropCareNavHost(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                }
            }
        }
    }
}
