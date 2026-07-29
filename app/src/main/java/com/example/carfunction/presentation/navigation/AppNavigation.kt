package com.example.carfunction.presentation.navigation

import com.example.carfunction.domain.model.NavigationTab

/**
 * Navigation route definitions for the app.
 */
object AppRoutes {
    const val MY_CAR = "my_car"
    const val CHARGING = "charging"
    const val DRIVING_ASSISTANCE = "driving_assistance"
    const val DRIVING_EXTERIOR = "driving_exterior"
    const val COMFORT_INTERIOR = "comfort_interior"

    /**
     * Maps a [NavigationTab] to its route string.
     */
    fun routeForTab(tab: NavigationTab): String = when (tab) {
        NavigationTab.MY_CAR -> MY_CAR
        NavigationTab.CHARGING -> CHARGING
        NavigationTab.DRIVING_ASSISTANCE -> DRIVING_ASSISTANCE
        NavigationTab.DRIVING_EXTERIOR -> DRIVING_EXTERIOR
        NavigationTab.COMFORT_INTERIOR -> COMFORT_INTERIOR
    }

    /**
     * Maps a route string back to its [NavigationTab].
     */
    fun tabForRoute(route: String?): NavigationTab = when (route) {
        MY_CAR -> NavigationTab.MY_CAR
        CHARGING -> NavigationTab.CHARGING
        DRIVING_ASSISTANCE -> NavigationTab.DRIVING_ASSISTANCE
        DRIVING_EXTERIOR -> NavigationTab.DRIVING_EXTERIOR
        COMFORT_INTERIOR -> NavigationTab.COMFORT_INTERIOR
        else -> NavigationTab.MY_CAR
    }
}
