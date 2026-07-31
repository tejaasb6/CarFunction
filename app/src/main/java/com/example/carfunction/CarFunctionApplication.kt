/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction

import android.app.Application
import android.content.ComponentCallbacks2
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point for the CarFunction app.
 *
 * Responsibilities:
 * - Hilt dependency injection root
 * - Memory management via [onTrimMemory]
 */
@HiltAndroidApp
class CarFunctionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Platform capabilities are configured via AppContainer.configurePlatform()
        // which should be called here before any dependency is accessed.
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL,
            -> {
                // Release non-essential caches and resources
                // Future: clear image caches, release bitmap pools
            }
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                // App moved to background — release UI-related resources
            }
        }
    }
}
