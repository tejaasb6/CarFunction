package com.example.carfunction.presentation.comfortinterior

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carfunction.domain.model.ComfortSubSection
import com.example.carfunction.presentation.comfortinterior.components.AmbientLightContent
import com.example.carfunction.presentation.comfortinterior.components.ComfortSidebar
import com.example.carfunction.presentation.comfortinterior.components.DisplayContent
import com.example.carfunction.presentation.comfortinterior.components.FavoritesContent
import com.example.carfunction.presentation.comfortinterior.components.PanoramaRoofContent
import com.example.carfunction.presentation.comfortinterior.components.PinEntryModal
import com.example.carfunction.presentation.comfortinterior.components.SafetyContent
import com.example.carfunction.presentation.comfortinterior.components.SeatAndLoadingContent
import com.example.carfunction.presentation.comfortinterior.components.SeatMassageContent

/**
 * Comfort & Interior screen — three core layout elements:
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  [Search] [MyCar] [Charging] [Driving...] [C&I●]               │ ← Nav Bar
 * ├──────────────┬──────────────────────────────────────────────────┤
 * │              │                                                  │
 * │  Seat        │  Settings Controls    │  3D Interior             │
 * │  Massage  ●  │  (toggles, sliders,  │  Visualization           │
 * │  Seat- &     │   segmented bars,    │  (contextual per         │
 * │  Loading     │   list items)        │   sidebar selection)     │
 * │  Ambient     │                      │                           │
 * │  Light       │                      │                           │
 * │  Panorama    │──────────────────────┘                           │
 * │  Roof        │                                                  │
 * │  Display     │                                                  │
 * │  Favorites   │                                                  │
 * │  Safety      │                                                  │
 * │              │                                                  │
 * └──────────────┴──────────────────────────────────────────────────┘
 */
@Composable
fun ComfortInteriorScreen(
    viewModel: ComfortInteriorViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ── Main Content: Sidebar + Content Area ───────────────────────
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                // ── 2. Left Sidebar ────────────────────────────────────────
                ComfortSidebar(
                    selectedSection = state.selectedSubSection,
                    onSectionSelected = {
                        viewModel.dispatch(ComfortInteriorContract.Intent.SelectSubSection(it))
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight(),
                )

                // ── 3. Center Content + Right Visualization ────────────────
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    // ── Settings controls (scrollable) ─────────────────────
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                    ) {
                        SubSectionContent(state = state, dispatch = viewModel::dispatch)
                    }

                    // ── Right visualization area ───────────────────────────
                    Box(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFF5F5F5),
                                        Color(0xFFE8E8E8),
                                        Color(0xFFF0F0F0),
                                    ),
                                ),
                            ),
                    ) {
                        // 3D visualization placeholder — contextual per sub-section
                    }
                }
            }
        }

        // ── PIN Entry Modal Overlay ────────────────────────────────────────
        if (state.showPinModal) {
            PinEntryModal(
                enteredDigits = state.pinEntryDigits,
                onDigitEntered = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.PinDigitEntered(it))
                },
                onBackspace = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.PinBackspace)
                },
                onDismiss = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.DismissPinModal)
                },
            )
        }
    }
}

/**
 * Routes to the correct sub-section content based on the selected sidebar item.
 */
@Composable
private fun SubSectionContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
) {
    when (state.selectedSubSection) {
        ComfortSubSection.SEAT_MASSAGE -> SeatMassageContent(state = state, dispatch = dispatch)
        ComfortSubSection.SEAT_AND_LOADING -> SeatAndLoadingContent(state = state, dispatch = dispatch)
        ComfortSubSection.AMBIENT_LIGHT -> AmbientLightContent(state = state, dispatch = dispatch)
        ComfortSubSection.PANORAMA_ROOF -> PanoramaRoofContent(state = state, dispatch = dispatch)
        ComfortSubSection.DISPLAY -> DisplayContent(state = state, dispatch = dispatch)
        ComfortSubSection.FAVORITES -> FavoritesContent()
        ComfortSubSection.SAFETY -> SafetyContent(state = state, dispatch = dispatch)
    }
}
