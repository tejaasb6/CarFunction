/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.core.oem

import com.example.carfunction.core.platform.PlatformCapabilities

/**
 * Binds an OEM to its platform capabilities.
 * Single point of truth for OEM + platform config.
 */
data class OemConfig(
    val oem: OemType,
    val capabilities: PlatformCapabilities,
)
