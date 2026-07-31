/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carfunction.domain.model.ComfortSubSection
import com.example.carfunction.presentation.comfortinterior.components.AirbagConfirmationDialog
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
 * Comfort & Interior screen layout per the reference Audi MMI design.
 *
 * Two distinct layout modes depending on the selected sub-section:
 *
 * **Seat Massage** (visualization mode):
 * ┌──────────┬────────────────────────────────────────────┐
 * │ Sidebar  │            Visualization Area              │
 * │  (~20%)  │                                            │
 * │          │                                            │
 * │          │    ┌──────────────────────────────────┐    │
 * │          │    │ OFF│Balance│Active│Mobility│...  │    │  ← floating bottom
 * │          │    └──────────────────────────────────┘    │
 * └──────────┴────────────────────────────────────────────┘
 *
 * **Other sections** (settings mode):
 * ┌──────────┬────────────────────────────────────────────┐
 * │ Sidebar  │         Settings Content                   │
 * │  (~20%)  │  (toggles, sliders, lists — scrollable)    │
 * │          │                                            │
 * └──────────┴────────────────────────────────────────────┘
 */
@Composable
fun ComfortInteriorScreen(
    viewModel: ComfortInteriorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Trigger initial data load once per composition lifetime
    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    // Collect one-shot effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ComfortInteriorContract.Effect.ShowToast -> { /* Handle toast */ }
                is ComfortInteriorContract.Effect.PinSetSuccessfully -> { /* PIN success */ }
                is ComfortInteriorContract.Effect.AirbagStateChanged -> { /* Airbag changed */ }
                is ComfortInteriorContract.Effect.OpenSearch -> { /* Open search */ }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            // ── Left Sidebar ───────────────────────────────────────────────
            ComfortSidebar(
                selectedSection = state.selectedSubSection,
                visibleSections = state.visibleSubSections,
                onSectionSelected = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.SelectSubSection(it))
                },
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight(),
            )

            // ── Content Area (remaining ~80%) ──────────────────────────────
            when (state.selectedSubSection) {
                ComfortSubSection.SEAT_MASSAGE -> {
                    // Seat Massage: full visualization area with floating bar at bottom
                    SeatMassageLayout(
                        state = state,
                        dispatch = viewModel::dispatch,
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxHeight(),
                    )
                }
                ComfortSubSection.SEAT_AND_LOADING,
                ComfortSubSection.AMBIENT_LIGHT,
                ComfortSubSection.PANORAMA_ROOF,
                ComfortSubSection.DISPLAY,
                ComfortSubSection.FAVORITES,
                ComfortSubSection.SAFETY,
                -> {
                    // All other sections: scrollable settings content
                    Column(
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                    ) {
                        SubSectionContent(state = state, dispatch = viewModel::dispatch)
                    }
                }
            }
        }

        // ── PIN Entry Modal Overlay ────────────────────────────────────────
        if (state.showPinModal) {
            PinEntryModal(
                enteredDigitCount = state.pinEntryDigitCount,
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

        // ── Airbag Confirmation Dialog Overlay ─────────────────────────────
        if (state.showAirbagConfirmation) {
            AirbagConfirmationDialog(
                pendingEnabled = state.pendingAirbagState ?: true,
                onConfirm = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.ConfirmAirbagChange)
                },
                onDismiss = {
                    viewModel.dispatch(ComfortInteriorContract.Intent.DismissAirbagConfirmation)
                },
            )
        }
    }
}

/**
 * Seat Massage layout: gradient visualization area with the massage mode
 * selector bar floating at the bottom center (matching the reference design).
 */
@Composable
private fun SeatMassageLayout(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
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
        // Massage mode selector floating at bottom center
        SeatMassageContent(
            state = state,
            dispatch = dispatch,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        )
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
        ComfortSubSection.SEAT_MASSAGE -> {
            // Handled separately in SeatMassageLayout
        }
        ComfortSubSection.SEAT_AND_LOADING -> SeatAndLoadingContent(state = state, dispatch = dispatch)
        ComfortSubSection.AMBIENT_LIGHT -> AmbientLightContent(state = state, dispatch = dispatch)
        ComfortSubSection.PANORAMA_ROOF -> PanoramaRoofContent(state = state, dispatch = dispatch)
        ComfortSubSection.DISPLAY -> DisplayContent(state = state, dispatch = dispatch)
        ComfortSubSection.FAVORITES -> FavoritesContent()
        ComfortSubSection.SAFETY -> SafetyContent(state = state, dispatch = dispatch)
    }
}
