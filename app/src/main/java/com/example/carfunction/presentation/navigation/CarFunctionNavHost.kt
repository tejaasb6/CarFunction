package com.example.carfunction.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.carfunction.presentation.mycar.MyCarScreen
import com.example.carfunction.presentation.screens.ChargingScreen
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorScreen
import com.example.carfunction.presentation.screens.DrivingAssistanceScreen
import com.example.carfunction.presentation.screens.DrivingExteriorScreen

/**
 * Central NavHost wiring all screens.
 */
@Composable
fun CarFunctionNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.MY_CAR,
        modifier = modifier,
    ) {
        composable(AppRoutes.MY_CAR) {
            MyCarScreen()
        }
        composable(AppRoutes.CHARGING) {
            ChargingScreen()
        }
        composable(AppRoutes.DRIVING_ASSISTANCE) {
            DrivingAssistanceScreen()
        }
        composable(AppRoutes.DRIVING_EXTERIOR) {
            DrivingExteriorScreen()
        }
        composable(AppRoutes.COMFORT_INTERIOR) {
            ComfortInteriorScreen()
        }
    }
}
