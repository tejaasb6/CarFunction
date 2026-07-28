package com.ui.audi.engine.fallback

import android.content.Context
import android.util.Log
import java.io.IOException

/**
 * Loads bundled NX-1 fallback design token JSON files from the module's
 * `assets/fallback/` directory.
 *
 * This is the last-resort token source when the ThemeContentProvider is
 * unreachable (no theme-provider APK installed) or returns an empty result
 * (no theme APK installed). The bundled JSONs follow the same folder
 * structure as a real theme APK:
 *
 * ```
 * assets/fallback/
 * ├── Core/
 * │   ├── Colors.json
 * │   └── Dimensions.json
 * ├── Base/
 * │   ├── Sem/
 * │   │   ├── Colors-Light.json
 * │   │   ├── Colors-Dark.json
 * │   │   ├── Dimensions.json
 * │   │   ├── Typography.json
 * │   │   ├── TextStyles.json
 * │   │   ├── ShadowsStyles.json
 * │   │   └── GradientStyles.json
 * │   └── Cmp/
 * │       ├── Action/
 * │       │   ├── Button.json
 * │       │   ├── ButtonGroup.json
 * │       │   ├── Chip.json
 * │       │   ├── ComponentButton.json
 * │       │   ├── IconButton.json
 * │       │   ├── Keypad.json
 * │       │   ├── MultiToggleButton.json
 * │       │   ├── SegmentedControl.json
 * │       │   └── Tile.json
 * │       ├── DataDisplay/
 * │       │   ├── BarGraph.json
 * │       │   ├── DividerWithText.json
 * │       │   ├── Icon.json
 * │       │   ├── Table.json
 * │       │   ├── Tag.json
 * │       │   ├── TextList.json
 * │       │   └── ValueWithUnit.json
 * │       ├── Feedback/
 * │       │   ├── Badge.json
 * │       │   ├── LoadingLayer.json
 * │       │   ├── ProgressIndicator.json
 * │       │   ├── ProgressTracker.json
 * │       │   └── SemanticShape.json
 * │       ├── Forms/
 * │       │   ├── DataPicker.json
 * │       │   ├── FormControls.json
 * │       │   ├── FormFields.json
 * │       │   ├── ListItem.json
 * │       │   ├── PinDisplay.json
 * │       │   ├── SearchField.json
 * │       │   ├── Select.json
 * │       │   ├── Slider.json
 * │       │   ├── Stepper.json
 * │       │   ├── TimeBar.json
 * │       │   └── ToggleSwitch.json
 * │       ├── Global/
 * │       │   ├── Divider.json
 * │       │   ├── Fadeout.json
 * │       │   ├── MediaCover.json
 * │       │   ├── Scrim.json
 * │       │   ├── Scrollbar.json
 * │       │   └── Skeleton.json
 * │       ├── Group/
 * │       │   ├── Carousel.json
 * │       │   └── ContentGroup.json
 * │       ├── Layer/
 * │       │   ├── Dialog.json
 * │       │   ├── Notification.json
 * │       │   ├── Popover.json
 * │       │   ├── Popup.json
 * │       │   └── Snackbar.json
 * │       └── Navigation/
 * │           ├── Accordion.json
 * │           ├── Avatar.json
 * │           ├── BottomNavigation.json
 * │           ├── Disabled.json
 * │           ├── NavigationBar.json
 * │           ├── PageIndicator.json
 * │           ├── Pagination.json
 * │           ├── Tabs.json
 * │           ├── TextLink.json
 * │           └── TopBar.json
 * ```
 *
 * Figma metadata files (`$metadata.json`, `$themes.json`) are present
 * alongside the token files but are skipped during loading (entries
 * prefixed with `$` are filtered out).
 *
 * The returned `Map<String, String>` (relative path to JSON content) is
 * identical in shape to what [com.ui.core.ThemeProviderContract] delivers
 * via ContentProvider, so the existing [com.ui.audi.engine.parser.DesignTokenParser]
 * can consume it without any changes. Map keys use the full relative path
 * (e.g. `Base/Sem/Colors-Dark.json`, `Base/Cmp/Action/Button.json`).
 */
internal object FallbackTokenLoader {
    private const val TAG = "FallbackTokenLoader"
    internal const val FALLBACK_ASSETS_PATH = "fallback"

    /**
     * Reads all `.json` files under `assets/fallback/` and returns them as a
     * filename-to-content map.
     *
     * Keys are stripped of the `fallback/` prefix so they match the format
     * returned by the ThemeContentProvider (e.g. `Base/Sem/Colors-Dark.json`
     * instead of `fallback/Base/Sem/Colors-Dark.json`). This ensures the
     * [com.ui.audi.engine.parser.DesignTokenParser] light/dark file detection
     * and all downstream logic works identically to the live-theme path.
     *
     * @param context Application context used to access the asset manager.
     * @return A map of relative file paths to their JSON content, or an empty
     *         map if the fallback directory is missing or unreadable.
     */
    fun load(context: Context): Map<String, String> {
        val result = mutableMapOf<String, String>()
        try {
            collectJsonAssets(context, FALLBACK_ASSETS_PATH, result)
            if (result.isNotEmpty()) {
                Log.i(TAG, "Loaded ${result.size} fallback token files from assets/$FALLBACK_ASSETS_PATH/")
            } else {
                Log.w(TAG, "No fallback token files found in assets/$FALLBACK_ASSETS_PATH/")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to load fallback tokens from assets", e)
        }
        return result
    }

    /**
     * Recursively collects all `.json` files from the given asset path,
     * skipping Figma metadata files (prefixed with `$`).
     *
     * Map keys are stored relative to the [FALLBACK_ASSETS_PATH] root,
     * stripping the `fallback/` prefix to mirror ContentProvider output.
     */
    private fun collectJsonAssets(
        context: Context,
        path: String,
        out: MutableMap<String, String>,
    ) {
        val prefix = "$FALLBACK_ASSETS_PATH/"
        val entries = context.assets.list(path) ?: return
        for (entry in entries) {
            if (entry.startsWith("$")) continue
            val fullPath = "$path/$entry"
            if (entry.endsWith(".json")) {
                runCatching {
                    val key = fullPath.removePrefix(prefix)
                    out[key] =
                        context.assets
                            .open(fullPath)
                            .bufferedReader()
                            .use { it.readText() }
                }.onFailure {
                    Log.w(TAG, "Failed to read asset: $fullPath", it)
                }
            } else {
                collectJsonAssets(context, fullPath, out)
            }
        }
    }
}
