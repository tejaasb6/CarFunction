/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.navigation

import com.example.carfunction.domain.model.NavigationTab

/**
 * Type-safe navigation route definitions for the app.
 * Each route is a sealed-class member with a unique [route] string,
 * eliminating raw-string routing throughout the codebase.
 */
sealed class AppRoute(val route: String) {
    data object MyCar : AppRoute("my_car")
    data object Charging : AppRoute("charging")
    data object DrivingAssistance : AppRoute("driving_assistance")
    data object DrivingExterior : AppRoute("driving_exterior")
    data object ComfortInterior : AppRoute("comfort_interior")
}

/**
 * Companion utilities for route ↔ [NavigationTab] mapping.
 */
object AppRoutes {
    /** Start destination route string for NavHost. */
    val startDestination: String = AppRoute.MyCar.route

    /**
     * Maps a [NavigationTab] to its [AppRoute].
     */
    fun routeForTab(tab: NavigationTab): String = when (tab) {
        NavigationTab.MY_CAR -> AppRoute.MyCar.route
        NavigationTab.CHARGING -> AppRoute.Charging.route
        NavigationTab.DRIVING_ASSISTANCE -> AppRoute.DrivingAssistance.route
        NavigationTab.DRIVING_EXTERIOR -> AppRoute.DrivingExterior.route
        NavigationTab.COMFORT_INTERIOR -> AppRoute.ComfortInterior.route
    }

    /**
     * Maps a route string back to its [NavigationTab].
     */
    fun tabForRoute(route: String?): NavigationTab = when (route) {
        AppRoute.MyCar.route -> NavigationTab.MY_CAR
        AppRoute.Charging.route -> NavigationTab.CHARGING
        AppRoute.DrivingAssistance.route -> NavigationTab.DRIVING_ASSISTANCE
        AppRoute.DrivingExterior.route -> NavigationTab.DRIVING_EXTERIOR
        AppRoute.ComfortInterior.route -> NavigationTab.COMFORT_INTERIOR
        else -> NavigationTab.MY_CAR
    }
}
