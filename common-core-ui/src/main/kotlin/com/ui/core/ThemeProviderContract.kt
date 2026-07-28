package com.ui.core

import android.net.Uri

object ThemeProviderContract {
    const val AUTHORITY = "com.ui.themeprovider"

    /**
     * URI for the raw design-token JSON map.
     *
     * Returns a cursor with one row per JSON file.  Each row has two columns:
     *  - `filename`  – relative path inside the theme APK assets (e.g. "Sem/Colors-Dark.json")
     *  - `json`      – the raw JSON content string
     *
     * Consumers call [DesignTokenManager.initFromJsonMap] with the resulting map.
     */
    val RAW_TOKENS_URI: Uri = Uri.parse("content://$AUTHORITY/raw_tokens")

    /** Column name for the filename in the raw-tokens cursor. */
    const val COL_FILENAME = "filename"

    /** Column name for the JSON content in the raw-tokens cursor. */
    const val COL_JSON = "json"

    const val ACTION_THEME_CHANGED = "com.ui.themeprovider.THEME_CHANGED"
    const val EXTRA_BRAND = "brand"
    const val EXTRA_THEME = "theme_name"

    const val META_BRAND = "com.ui.theme.brand"
    const val META_THEME_NAME = "com.ui.theme.name"

    const val PROVIDER_PACKAGE = "com.ui.themeprovider"
    const val SERVICE_CLASS = "com.ui.themeprovider.ThemeProviderService"

    data class ThemeInfo(
        val brand: String,
        val name: String,
    )
}
