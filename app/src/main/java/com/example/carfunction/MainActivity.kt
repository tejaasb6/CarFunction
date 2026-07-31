/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.carfunction.presentation.components.TopNavigationBar
import com.example.carfunction.presentation.navigation.AppRoute
import com.example.carfunction.presentation.navigation.AppRoutes
import com.example.carfunction.presentation.navigation.CarFunctionNavHost
import com.ui.audi.AudiTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single Activity entry point for the CarFunction app.
 * Hosts Jetpack Navigation with Compose.
 *
 * The [TopNavigationBar] is lifted above the [CarFunctionNavHost] so that
 * tapping a tab triggers real Jetpack Navigation between screens (MyCar,
 * Charging, Driving Assistance, Driving & Exterior, Comfort & Interior).
 *
 * Wraps the content tree in [AudiTheme] so that all design-system widgets
 * (NavigationBar, ToggleSwitch, Icon, Text, Divider, etc.) from
 * audi-compose-ui receive their brand-specific token styles.
 *
 * No Material3 theme is used — all UI is rendered via Audi design-system
 * widgets exclusively.
 */
@AndroidEntryPoint
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

                    // Observe current route to highlight the correct tab
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = backStackEntry?.destination?.route
                    val selectedTab = AppRoutes.tabForRoute(currentRoute)

                    Column(modifier = Modifier.fillMaxSize()) {
                        // ── Shared Top Navigation Bar ──────────────────────
                        TopNavigationBar(
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                val route = AppRoutes.routeForTab(tab)
                                if (route != currentRoute) {
                                    navController.navigate(route) {
                                        // Pop up to start to avoid stacking screens
                                        popUpTo(AppRoute.MyCar.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            onSearchClick = { /* Search not yet implemented */ },
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ── Screen content via NavHost ─────────────────────
                        CarFunctionNavHost(
                            navController = navController,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
