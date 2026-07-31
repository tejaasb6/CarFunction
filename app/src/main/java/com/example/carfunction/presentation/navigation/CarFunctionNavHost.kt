/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

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
        startDestination = AppRoutes.startDestination,
        modifier = modifier,
    ) {
        composable(AppRoute.MyCar.route) {
            MyCarScreen()
        }
        composable(AppRoute.Charging.route) {
            ChargingScreen()
        }
        composable(AppRoute.DrivingAssistance.route) {
            DrivingAssistanceScreen()
        }
        composable(AppRoute.DrivingExterior.route) {
            DrivingExteriorScreen()
        }
        composable(AppRoute.ComfortInterior.route) {
            ComfortInteriorScreen()
        }
    }
}
