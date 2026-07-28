package com.example.carfunction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.carfunction.presentation.navigation.CarFunctionNavHost
import com.ui.audi.AudiTheme

/**
 * Single Activity entry point for the CarFunction app.
 * Hosts Jetpack Navigation with Compose.
 *
 * Wraps the content tree in [AudiTheme] so that all design-system widgets
 * (NavigationBar, ToggleSwitch, Icon, Text, Divider, etc.) from
 * audi-compose-ui receive their brand-specific token styles.
 *
 * No Material3 theme is used — all UI is rendered via Audi design-system
 * widgets exclusively.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AudiTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    val navController = rememberNavController()
                    CarFunctionNavHost(navController = navController)
                }
            }
        }
    }
}
