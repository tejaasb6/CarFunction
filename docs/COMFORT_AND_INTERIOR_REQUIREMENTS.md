# Comfort & Interior Screen — Software Requirements Specification (SRS)
## CarFunction — Audi MVI Application

**Version:** 1.0  
**Date:** 2026-07-29  
**Module:** `app` (presentation layer)  
**UI Libraries:** `audi-compose-ui`, `common-core-ui`  
**Architecture:** MVI + Clean Architecture  
**Platform:** SDV / CL8min  

---

## 1. Executive Summary

The **Comfort & Interior (C&I)** screen is a primary navigation destination within the CarFunction application, accessible from the top navigation bar. It provides the user with full control over vehicle comfort features including seat massage programs, seat/loading functions, ambient lighting, panoramic roof control, display brightness management, favorites (quick-access interior zones), and safety/privacy settings.

The screen follows the same **3-zone layout architecture** as the existing MyCar screen:
- **Left Sidebar** — Sub-category vertical navigation menu
- **Center Content Area** — Settings controls (toggles, sliders, segmented selectors, list items)
- **Right Visualization Area** — Contextual 3D interior renderings

---

## 2. Constraints & Compliance Assumptions

| Standard | Applicability |
|----------|---------------|
| **ISO 26262 (ASIL-B)** | Safety-critical toggles (Passenger Airbag, Child Presence Detection) require state confirmation |
| **ISO/SAE 21434** | Glovebox PIN storage must use encrypted secure storage; PIN entry UI must mask input |
| **MISRA C++/Kotlin** | All generated code adheres to MISRA-aligned defensive patterns per project conventions |
| **ASPICE SWE.1-3** | Full requirements traceability to design artifacts and test cases |
| **CERT Secure Coding** | PIN handling, toggle state persistence, secure data flow |
| **Detekt/ktlint** | Kotlin code style enforcement as per `config/detekt/detekt.yml` |

---

## 3. Screen Layout Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ [🔍] [MyCar] [Charging] [Driving Assistance] [Driving & Exterior] [C&I●]  │ Top Nav Bar
├──────────────────┬──────────────────────────────────────────────────────────┤
│                  │                                                          │
│  Seat Massage ●  │   ┌─────────────────────┐  ┌─────────────────────────┐  │
│  Seat- & Loading │   │  Settings Controls  │  │  3D Interior            │  │
│  Ambient Light   │   │  (toggles, sliders, │  │  Visualization          │  │
│  Panorama Roof   │   │   segmented bars,   │  │  (contextual per        │  │
│  Display         │   │   list items)       │  │   sidebar selection)    │  │
│  Favorites       │   │                     │  │                         │  │
│  Safety          │   └─────────────────────┘  └─────────────────────────┘  │
│                  │                                                          │
│                  │  ┌───────────────────────────────────────────────┐       │
│                  │  │  Bottom Action Bar (mode selector / presets) │       │
│                  │  └───────────────────────────────────────────────┘       │
├──────────────────┴──────────────────────────────────────────────────────────┤
│  [👤] [📞] [🎵]  [⬇] [🏠] [⚠] [🚗] [🔀] [⊞] [▶]  [⚙] 07:45  ☁ 21°   │ System Bar
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 4. Navigation & Routing Requirements

### REQ-CI-NAV-001: Top Navigation Tab
- **Description:** The "Comfort & Interior" tab SHALL be displayed as the 6th item in the top navigation bar (after "Driving & Exterior").
- **Active State:** When selected, the tab SHALL render as a **black rounded pill/capsule** with **white text** (using `AudiSegmentedButton` or equivalent from `audi-compose-ui`).
- **Inactive State:** Plain black text on the light gray background.
- **UI Component:** `TopNavBar` from `common-core-ui`, themed via `audi-compose-ui`.

### REQ-CI-NAV-002: Left Sidebar Navigation
- **Description:** The left sidebar SHALL display the following sub-category menu items in vertical order:
  1. Seat Massage
  2. Seat- & Loading
  3. Ambient Light
  4. Panorama Roof
  5. Display
  6. Favorites
  7. Safety
- **Active State:** Selected item SHALL render as a **black rounded pill/capsule** with **white text**.
- **Inactive State:** Plain black text on white/light background.
- **Default Selection:** "Seat Massage" SHALL be selected by default when the C&I screen is first opened.
- **Behavior:** Tapping a sidebar item SHALL update the center content area and right visualization area to show the corresponding sub-section content.
- **UI Component:** `SidebarMenu` / `VerticalNavList` from `common-core-ui`.

### REQ-CI-NAV-003: Platform-Conditional Sidebar Items
- **Description:** Sidebar items SHALL be conditionally rendered based on `PlatformCapabilities`:
  - `supportsMassage` → Seat Massage
  - `supportsAmbientLight` → Ambient Light
  - `supportsPanoramaRoof` → Panorama Roof (new capability)
  - Seat- & Loading, Display, Favorites, Safety → always visible.

---

## 5. Sub-Section Requirements

---

### 5.1 Seat Massage (REQ-CI-SM-xxx)

#### REQ-CI-SM-001: 3D Seat Visualization
- **Description:** When "Seat Massage" is selected, the right visualization area SHALL display a **3D rendered interior view** of the vehicle cabin showing both front seats.
- **Viewing Angle:** Slightly elevated, looking from the front-left toward the passenger side.
- **Vehicle Body:** Rendered in **translucent/ghosted light gray** (wireframe-like).
- **Seats:** Rendered in **solid black** with detailed headrests, bolsters, and cushions.
- **Seat Labels:** Each seat SHALL display a **white rounded pill label** ("Driver" / "Passenger") indicating which seat is being configured.

#### REQ-CI-SM-002: Seat Selection
- **Description:** The user SHALL be able to select which seat (Driver / Passenger) to configure for massage.
- **Interaction:** Tapping the seat label or the 3D seat model SHALL switch the active massage configuration to that seat.
- **Default:** Passenger seat SHALL be selected by default.

#### REQ-CI-SM-003: Massage Mode Selector
- **Description:** A **horizontal segmented control bar** SHALL be displayed at the bottom of the content area with the following massage program options:
  1. **OFF** (default selected)
  2. **Balance**
  3. **Active**
  4. **Mobility**
  5. **Relax**
  6. **Stretch**
- **Active State:** The selected mode SHALL display as a **black pill/capsule** with **white text**.
- **Inactive State:** Plain black text, separated by thin vertical dividers.
- **Container:** The entire selector SHALL be wrapped in a **white rounded container/card**.
- **UI Component:** `SegmentedButton` / `PillSelector` from `audi-compose-ui`.
- **Behavior:** Selecting a mode SHALL dispatch a `SetMassageModeIntent` to the ViewModel, updating the massage state for the selected seat.

#### REQ-CI-SM-004: Independent Seat Massage State
- **Description:** Each seat (Driver, Passenger) SHALL maintain its own independent massage mode state.
- **Constraint:** Switching between seats SHALL preserve the previously selected massage mode for each seat.

---

### 5.2 Seat- & Loading Functions (REQ-CI-SL-xxx)

#### REQ-CI-SL-001: Section Layout
- **Description:** The center content area SHALL be divided into two labeled sections:
  1. **"Seat Functions"** — section header in gray text
  2. **"Loading Functions"** — section header in gray text

#### REQ-CI-SL-002: Seat Functions List
- **Description:** Under "Seat Functions", the following items SHALL be displayed as a vertically scrollable list:
  1. **Entry aid for 3rd seat row**
  2. **Passenger seat adjustment from the driver's seat**
  3. **Passenger seat adjustment from the rear seat**
- **Selection Style:** The selected item SHALL display with a **black rounded rectangle background** and **white text**.
- **Default:** "Entry aid for 3rd seat row" SHALL be selected by default.
- **UI Component:** `SelectableListItem` from `common-core-ui`.

#### REQ-CI-SL-003: Loading Functions List
- **Description:** Under "Loading Functions", the following item SHALL be displayed:
  1. **Cargo**
- **Selection Style:** Same as Seat Functions — black pill with white text when selected.

#### REQ-CI-SL-004: 3D Seat/Cargo Visualization
- **Description:** Depending on the selected function:
  - **Entry aid for 3rd seat row:** The right visualization SHALL show a **top-down 3D view** of the vehicle's 2nd and 3rd row seating with **interactive seat markers** (white circular dots on each seat). Text overlay: *"Please select at least one seat"*.
  - **Passenger seat adjustment from driver's seat / rear seat:** Visualization SHALL show corresponding seat perspective.
  - **Cargo:** Visualization SHALL show cargo/loading area perspective.

#### REQ-CI-SL-005: Seat Selection Interaction (Entry Aid)
- **Description:** When "Entry aid for 3rd seat row" is active, the user SHALL be able to tap on individual 2nd-row seats to select them.
- **Visual Feedback:** Selected seats SHALL show a **white selection border/outline**.
- **Play Buttons:** Each selectable seat SHALL have a **circular play/arrow button** (▷) beneath it for triggering the entry aid animation/action.
- **Constraint:** At least one seat MUST be selected before the action can be triggered.

---

### 5.3 Ambient Light (REQ-CI-AL-xxx)

#### REQ-CI-AL-001: Master Toggle
- **Description:** An **"Ambient Light"** master toggle switch SHALL be displayed at the top of the content area.
- **States:** ON (green with white checkmark) / OFF (gray/neutral).
- **Behavior:** When OFF, all ambient light sub-controls SHALL be disabled/dimmed.
- **UI Component:** `AudiToggleSwitch` from `audi-compose-ui`.

#### REQ-CI-AL-002: Ambient Light Brightness Section
- **Description:** A section labeled **"Ambientlight Brightness"** SHALL contain:
  1. **Theme Selector** — Horizontal segmented control with 4 options:
     - **Sky** (default selected)
     - **Horizon**
     - **Hearth**
     - **Sync**
  2. **Brightness Slider** — Horizontal slider with:
     - **Small sun icon** (dim) on the left
     - **Large sun icon** (bright) on the right
     - **Segmented tick marks** along the track
     - **Vertical black line indicator** (slider thumb) for current brightness level
- **UI Components:**
  - Theme Selector: `SegmentedButton` from `audi-compose-ui`
  - Brightness Slider: `TickMarkSlider` from `common-core-ui`

#### REQ-CI-AL-003: Ambient Light Settings Section
- **Description:** A section labeled **"Ambient Light Settings"** SHALL contain:
  1. **Footwell Lighting** — Toggle switch (ON/OFF)
  2. **Indicator/Hazard warning lights** — Toggle switch (ON/OFF)
  3. **Charging** — Toggle switch (ON/OFF)
  4. **Digital Assistant** — Toggle switch (ON/OFF)
  5. **Navigation** — Toggle switch (ON/OFF)
  6. **Phone** — Toggle switch (ON/OFF)
- **Default State:** All toggles SHALL default to OFF.
- **Behavior:** Each toggle dispatches an individual `SetAmbientLightSettingIntent` with the specific setting ID and boolean state.
- **UI Component:** `AudiToggleSwitch` with label from `audi-compose-ui`.

#### REQ-CI-AL-004: 3D Ambient Light Preview
- **Description:** The right visualization area SHALL display a **3D rendered dashboard/cockpit view** showing ambient lighting effects.
- **Purple/Magenta Glow:** The ambient light strips along the dashboard, door panels, and lower trim SHALL glow in the currently selected theme color.
- **Preview Buttons:** A **white rounded pill container** with **three play/forward triangle buttons** (▷ ▷ ▷) SHALL allow navigation through different 3D preview angles of the ambient lighting.

#### REQ-CI-AL-005: Temperature Slider Bar (Contextual)
- **Description:** When Ambient Light sub-settings are scrolled (advanced view), a **horizontal temperature control slider** MAY appear at the top of the content area with:
  - **Left chevron (▲)** — Driver temperature adjustment
  - **Right chevron (▲)** — Passenger temperature adjustment
  - **Center divider** — Bold black vertical line separating driver/passenger zones
  - **Graduated tick marks** along the slider track

---

### 5.4 Panorama Roof (REQ-CI-PR-xxx)

#### REQ-CI-PR-001: 3D Roof Visualization
- **Description:** When "Panorama Roof" is selected, the right visualization area SHALL display a **top-down 3D view** of the vehicle's panoramic glass roof.
- **Roof Segments:** The roof glass SHALL be divided into approximately **10 vertical segments/zones** separated by **dashed vertical lines**.

#### REQ-CI-PR-002: Segment Opacity Controls
- **Description:** Each roof segment SHALL have a **circular toggle control point** (dot) along a horizontal center line.
- **States:**
  - **Filled/solid white circle** — Segment is opaque/closed
  - **Hollow/outline circle** — Segment is transparent/open
- **Interaction:** Tapping a segment control SHALL toggle its opacity state between opaque and transparent.

#### REQ-CI-PR-003: Preset Pattern Selector
- **Description:** A **white rounded pill-shaped toolbar** SHALL be displayed at the bottom center containing approximately **8 preset pattern icons**.
- **Icon Style:** Each icon SHALL be a **stylized rectangular pattern** showing different combinations of open/closed segments (e.g., all closed, half-open, alternating, fully open).
- **Active State:** The currently selected preset SHALL display in **bold black/filled** state.
- **Inactive State:** Outlined/gray style.
- **Behavior:** Selecting a preset SHALL automatically set all segment states to match the chosen pattern.

---

### 5.5 Display (REQ-CI-DI-xxx)

#### REQ-CI-DI-001: 3D Cockpit Visualization
- **Description:** When "Display" is selected, the right visualization area SHALL display a **3D rendered cockpit view** from the driver's perspective showing the steering wheel, dashboard, and all displays.

#### REQ-CI-DI-002: Interactive Display Callouts
- **Description:** Three **white rounded pill-shaped labels** SHALL be overlaid on the 3D cockpit, pointing to specific display areas:
  1. **Head-Up** — Pointing to the head-up display projection area on the upper windshield
  2. **Virtual Cockpit** — Pointing to the digital instrument cluster behind the steering wheel
  3. **MMI** — Pointing to the central infotainment/MMI touchscreen
- **Interaction:** Tapping a callout label SHALL select that display for brightness adjustment.
- **UI Component:** `CalloutLabel` / `AnnotationBubble` from `common-core-ui`.

#### REQ-CI-DI-003: Brightness Slider
- **Description:** A **horizontal brightness slider** SHALL be displayed at the bottom of the content area:
  - **Left icon:** Small sun icon (dim) ☀
  - **Right icon:** Large sun icon (bright) ☀
  - **Slider track:** Horizontal bar with tick marks/notches
  - **Slider thumb:** Vertical black line indicator positioned at current brightness level
- **Behavior:** Adjusting the slider SHALL dispatch a `SetDisplayBrightnessIntent` with the target display ID and brightness value (0.0–1.0).
- **UI Component:** `TickMarkSlider` from `common-core-ui`.

---

### 5.6 Favorites (REQ-CI-FAV-xxx)

#### REQ-CI-FAV-001: 3D Cockpit Overview
- **Description:** When "Favorites" is selected, the visualization area SHALL display a **3D rendered cockpit view** with interactive zone labels.

#### REQ-CI-FAV-002: Interactive Zone Labels
- **Description:** Four **white rounded pill-shaped labels** SHALL be overlaid on the 3D cockpit:
  1. **Left Satellite** — Pointing to the left steering wheel spoke controls
  2. **Right Satellite** — Pointing to the right steering wheel spoke controls
  3. **Climate Favorite** — Pointing to the passenger-side dashboard area
  4. **Center Control Unit** — Pointing to the center console area
- **Interaction:** Tapping a zone label SHALL navigate to the detailed configuration for that specific hardware control zone.
- **UI Component:** `CalloutLabel` / `AnnotationBubble` from `common-core-ui`.

---

### 5.7 Safety (REQ-CI-SAF-xxx)

#### REQ-CI-SAF-001: Safety Section
- **Description:** A section labeled **"Safety"** (gray text header) SHALL contain the following toggle controls:
  1. **Passenger Airbag** — Toggle switch, default ON (green with checkmark)
  2. **Fond Information tone** — Toggle switch, default OFF
  3. **Child presence detection** — Toggle switch, default OFF
- **Safety Criticality:** Passenger Airbag toggle changes SHALL require a **confirmation dialog** before applying (ISO 26262 ASIL-B requirement).
- **UI Component:** `AudiToggleSwitch` with safety confirmation from `audi-compose-ui`.

#### REQ-CI-SAF-002: Privacy Section
- **Description:** A section labeled **"Privacy"** (gray text header) SHALL contain:
  1. **Glovebox PIN** — Toggle switch (ON/OFF) with an adjacent **pencil/edit icon** (✏) separated by a vertical divider line.
- **Behavior:**
  - Enabling the toggle SHALL trigger the PIN setup modal (REQ-CI-SAF-003).
  - The edit icon SHALL allow changing an existing PIN.

#### REQ-CI-SAF-003: Glovebox PIN Entry Modal
- **Description:** A **white modal dialog** SHALL be displayed for PIN creation/editing:
  - **Title:** "Set Glovebox PIN"
  - **PIN Input Display:** 4 dot indicators showing progress:
    - **Filled black dots** — Digits entered
    - **Empty circles** — Remaining digits
  - **Backspace Button:** ⌫ icon to the right of the dots for deleting last entered digit
  - **Numeric Keypad:** 3×4 grid of rounded rectangular buttons:
    ```
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
    |   | 0 |   |
    ```
  - **Button Style:** Light gray with dark numerals, rounded corners.
- **Security:** PIN SHALL be stored using encrypted secure storage (ISO/SAE 21434 compliance). PIN input SHALL be masked (dots, not digits).
- **Behavior:** After 4th digit entry, the modal SHALL auto-confirm and close, storing the encrypted PIN.
- **Dismissal:** Tapping outside the modal or pressing a cancel action SHALL dismiss without saving.
- **UI Component:** `ModalDialog` / `BottomSheet` from `common-core-ui`, `NumericKeypad` custom component.

#### REQ-CI-SAF-004: 3D Safety Visualization
- **Description:** The right visualization area SHALL display a **3D interior/cockpit view** with subtle purple/pink ambient lighting to provide visual context for the safety settings.

---

## 6. MVI Contract Definition

### REQ-CI-MVI-001: ComfortInteriorContract

```kotlin
object ComfortInteriorContract {

    sealed interface Intent : MviIntent {
        // Navigation
        data class SelectSubSection(val section: SubSection) : Intent

        // Seat Massage
        data class SelectMassageSeat(val seat: SeatPosition) : Intent
        data class SetMassageMode(val seat: SeatPosition, val mode: MassageMode) : Intent

        // Seat & Loading
        data class SelectSeatFunction(val function: SeatFunction) : Intent
        data class SelectLoadingFunction(val function: LoadingFunction) : Intent
        data class ToggleSeatSelection(val seatIndex: Int) : Intent
        data class TriggerEntryAid(val seatIndex: Int) : Intent

        // Ambient Light
        data class ToggleAmbientLight(val enabled: Boolean) : Intent
        data class SetAmbientTheme(val theme: AmbientTheme) : Intent
        data class SetAmbientBrightness(val brightness: Float) : Intent
        data class ToggleFootwellLighting(val enabled: Boolean) : Intent
        data class ToggleAmbientSetting(val settingId: String, val enabled: Boolean) : Intent

        // Panorama Roof
        data class ToggleRoofSegment(val segmentIndex: Int) : Intent
        data class SelectRoofPreset(val presetIndex: Int) : Intent

        // Display
        data class SelectDisplay(val display: DisplayTarget) : Intent
        data class SetDisplayBrightness(val display: DisplayTarget, val brightness: Float) : Intent

        // Safety
        data class TogglePassengerAirbag(val enabled: Boolean) : Intent
        data class ToggleFondInfoTone(val enabled: Boolean) : Intent
        data class ToggleChildPresenceDetection(val enabled: Boolean) : Intent
        data class ToggleGloveboxPin(val enabled: Boolean) : Intent
        data class SetGloveboxPin(val pin: String) : Intent
        object ConfirmAirbagChange : Intent
        object DismissAirbagConfirmation : Intent
        object OpenPinModal : Intent
        object DismissPinModal : Intent
    }

    data class State(
        // Navigation
        val selectedSubSection: SubSection = SubSection.SEAT_MASSAGE,

        // Seat Massage
        val selectedMassageSeat: SeatPosition = SeatPosition.PASSENGER,
        val driverMassageMode: MassageMode = MassageMode.OFF,
        val passengerMassageMode: MassageMode = MassageMode.OFF,

        // Seat & Loading
        val selectedSeatFunction: SeatFunction = SeatFunction.ENTRY_AID_3RD_ROW,
        val selectedLoadingFunction: LoadingFunction? = null,
        val selectedSeats: Set<Int> = emptySet(),

        // Ambient Light
        val ambientLightEnabled: Boolean = false,
        val ambientTheme: AmbientTheme = AmbientTheme.SKY,
        val ambientBrightness: Float = 0.3f,
        val footwellLightingEnabled: Boolean = false,
        val ambientSettings: Map<String, Boolean> = mapOf(
            "hazard_lights" to false,
            "charging" to false,
            "digital_assistant" to false,
            "navigation" to false,
            "phone" to false
        ),

        // Panorama Roof
        val roofSegments: List<Boolean> = List(10) { false },
        val selectedRoofPreset: Int = -1,

        // Display
        val selectedDisplay: DisplayTarget = DisplayTarget.VIRTUAL_COCKPIT,
        val displayBrightness: Map<DisplayTarget, Float> = mapOf(
            DisplayTarget.HEAD_UP to 0.5f,
            DisplayTarget.VIRTUAL_COCKPIT to 0.5f,
            DisplayTarget.MMI to 0.5f
        ),

        // Safety
        val passengerAirbagEnabled: Boolean = true,
        val fondInfoToneEnabled: Boolean = false,
        val childPresenceDetectionEnabled: Boolean = false,
        val gloveboxPinEnabled: Boolean = false,
        val showPinModal: Boolean = false,
        val showAirbagConfirmation: Boolean = false,
        val pinEntryProgress: Int = 0,

        // General
        val isLoading: Boolean = false,
        val error: String? = null
    ) : MviState

    sealed interface Effect : MviEffect {
        data class ShowError(val message: String) : Effect
        object PinSetSuccessfully : Effect
        object AirbagStateChanged : Effect
        data class EntryAidTriggered(val seatIndex: Int) : Effect
        object NavigateBack : Effect
    }
}
```

---

## 7. Domain Model Definitions

### REQ-CI-DOM-001: Enumerations

```kotlin
enum class SubSection {
    SEAT_MASSAGE,
    SEAT_AND_LOADING,
    AMBIENT_LIGHT,
    PANORAMA_ROOF,
    DISPLAY,
    FAVORITES,
    SAFETY
}

enum class MassageMode {
    OFF, BALANCE, ACTIVE, MOBILITY, RELAX, STRETCH
}

enum class SeatPosition {
    DRIVER, PASSENGER
}

enum class AmbientTheme {
    SKY, HORIZON, HEARTH, SYNC
}

enum class DisplayTarget {
    HEAD_UP, VIRTUAL_COCKPIT, MMI
}

enum class SeatFunction {
    ENTRY_AID_3RD_ROW,
    PASSENGER_FROM_DRIVER,
    PASSENGER_FROM_REAR
}

enum class LoadingFunction {
    CARGO
}

data class MassageState(
    val driverMode: MassageMode = MassageMode.OFF,
    val passengerMode: MassageMode = MassageMode.OFF
)

data class AmbientLightState(
    val enabled: Boolean = false,
    val theme: AmbientTheme = AmbientTheme.SKY,
    val brightness: Float = 0.3f,
    val footwellEnabled: Boolean = false,
    val settings: Map<String, Boolean> = emptyMap()
)

data class PanoramaRoofState(
    val segments: List<Boolean> = List(10) { false },
    val selectedPreset: Int = -1
)

data class DisplayState(
    val selectedTarget: DisplayTarget = DisplayTarget.VIRTUAL_COCKPIT,
    val brightness: Map<DisplayTarget, Float> = emptyMap()
)

data class SafetyState(
    val passengerAirbag: Boolean = true,
    val fondInfoTone: Boolean = false,
    val childPresenceDetection: Boolean = false,
    val gloveboxPinEnabled: Boolean = false
)
```

---

## 8. Use Case Definitions

### REQ-CI-UC-001: Use Cases

| Use Case | Input | Output | Description |
|----------|-------|--------|-------------|
| `GetMassageStateUseCase` | `SeatPosition` | `MassageMode` | Retrieves current massage mode for a seat |
| `SetMassageModeUseCase` | `SeatPosition, MassageMode` | `Unit` | Sets massage program for specified seat |
| `GetAmbientLightStateUseCase` | — | `AmbientLightState` | Retrieves full ambient light configuration |
| `SetAmbientLightUseCase` | `AmbientLightState` | `Unit` | Updates ambient light settings |
| `GetPanoramaRoofStateUseCase` | — | `PanoramaRoofState` | Retrieves roof segment states |
| `SetRoofSegmentUseCase` | `Int, Boolean` | `Unit` | Toggles individual roof segment |
| `SetRoofPresetUseCase` | `Int` | `PanoramaRoofState` | Applies a roof preset pattern |
| `GetDisplayBrightnessUseCase` | `DisplayTarget` | `Float` | Gets brightness for a display |
| `SetDisplayBrightnessUseCase` | `DisplayTarget, Float` | `Unit` | Sets brightness for a display |
| `GetSafetyStateUseCase` | — | `SafetyState` | Retrieves safety toggle states |
| `SetSafetySettingUseCase` | `String, Boolean` | `Unit` | Updates a safety toggle |
| `SetGloveboxPinUseCase` | `String` | `Result<Unit>` | Encrypts and stores PIN |
| `ValidateGloveboxPinUseCase` | `String` | `Result<Boolean>` | Validates entered PIN |

---

## 9. UI Component Mapping

### 9.1 Components from `audi-compose-ui`

| Component | Usage | Screen Section |
|-----------|-------|----------------|
| `AudiSegmentedButton` | Top nav active tab, massage mode selector, ambient theme selector | All sections |
| `AudiToggleSwitch` | Ambient light master toggle, footwell lighting, safety toggles | Ambient Light, Safety |
| `AudiTopNavBar` | Top navigation bar with tab items | Global |
| `AudiSidebarMenu` | Left sidebar with sub-section items | Global |
| `AudiCard` | Settings group containers | All sections |
| `AudiTheme` | Design tokens (colors, typography, spacing, shadows) | Global |
| `AudiTextField` | Section headers with gray text style | All sections |

### 9.2 Components from `common-core-ui`

| Component | Usage | Screen Section |
|-----------|-------|----------------|
| `TickMarkSlider` | Brightness slider with sun icons and tick marks | Ambient Light, Display |
| `SelectableListItem` | Seat/Loading function list items with pill selection | Seat- & Loading |
| `CalloutLabel` / `AnnotationBubble` | Interactive 3D overlay labels (Head-Up, Virtual Cockpit, MMI, etc.) | Display, Favorites |
| `ModalDialog` | Glovebox PIN entry modal overlay | Safety |
| `NumericKeypad` | 3×4 numeric grid for PIN input | Safety (PIN Modal) |
| `PinDotIndicator` | 4-dot progress indicator for PIN entry | Safety (PIN Modal) |
| `SectionHeader` | Gray text section dividers ("Seat Functions", "Safety", etc.) | All sections |
| `ToggleWithLabel` | Toggle switch with adjacent text label | Ambient Light, Safety |
| `IconButton` | Play/arrow buttons, edit pencil icon, backspace icon | Seat- & Loading, Safety |
| `PresetSelector` | Horizontal icon bar for panorama roof presets | Panorama Roof |
| `InteractiveSeatMap` | Top-down seat layout with selectable markers | Seat- & Loading |

---

## 10. Platform Capabilities Extension

### REQ-CI-PLAT-001: New Capabilities

```kotlin
interface PlatformCapabilities {
    // ... existing capabilities ...
    val supportsPanoramaRoof: Boolean
    val supportsGloveboxPin: Boolean
    val supportsChildPresenceDetection: Boolean
    val supportsHeadUpDisplay: Boolean
    val supportsFavorites: Boolean
}
```

| Capability | SDV | CL8min |
|-----------|-----|--------|
| Panorama Roof | Yes | No |
| Glovebox PIN | Yes | Yes |
| Child Presence Detection | Yes | Yes |
| Head-Up Display | Yes | No |
| Favorites | Yes | Yes |
| Seat Massage | Yes | Yes |
| Ambient Light | Yes | Yes |

---

## 11. Data Source Requirements

### REQ-CI-DS-001: Mock Data Source
- **Description:** `MockCarFunctionDataSource` SHALL be extended with mock data for all Comfort & Interior states.
- **Default Values:** As specified in the State data class defaults.
- **Delay Simulation:** Mock responses SHALL include configurable delay (50–200ms) to simulate real vehicle bus communication latency.

### REQ-CI-DS-002: Prod Data Source
- **Description:** `ProdCarFunctionDataSource` SHALL interface with the vehicle's CAN/CAN-FD bus for real-time seat massage, ambient light, roof, display, and safety state management.
- **Protocol:** Vehicle signals via Android Automotive VHAL (Vehicle Hardware Abstraction Layer) or OEM-specific signal interface.

### REQ-CI-DS-003: Repository Extension

```kotlin
interface CarFunctionRepository {
    // ... existing methods ...

    // Comfort & Interior
    fun getMassageState(): Flow<MassageState>
    suspend fun setMassageMode(seat: SeatPosition, mode: MassageMode)

    fun getAmbientLightState(): Flow<AmbientLightState>
    suspend fun updateAmbientLight(state: AmbientLightState)

    fun getPanoramaRoofState(): Flow<PanoramaRoofState>
    suspend fun setRoofSegment(index: Int, opaque: Boolean)
    suspend fun setRoofPreset(presetIndex: Int): PanoramaRoofState

    fun getDisplayBrightness(): Flow<DisplayState>
    suspend fun setDisplayBrightness(target: DisplayTarget, brightness: Float)

    fun getSafetyState(): Flow<SafetyState>
    suspend fun setSafetySetting(settingId: String, enabled: Boolean)
    suspend fun setGloveboxPin(pin: String): Result<Unit>
    suspend fun validateGloveboxPin(pin: String): Result<Boolean>
}
```

---

## 12. 3D Visualization Requirements

### REQ-CI-3D-001: Contextual Rendering
- **Description:** The 3D visualization SHALL change based on the selected sidebar sub-section:

| Sub-Section | 3D View | Key Visual Elements |
|-------------|---------|---------------------|
| Seat Massage | Interior cabin (elevated angle) | Translucent body, solid black seats, seat labels |
| Seat- & Loading | Top-down cabin view | Interactive seat markers, play buttons |
| Ambient Light | Dashboard/cockpit view | Glowing ambient light strips (theme-colored) |
| Panorama Roof | Top-down roof view | Segmented glass panel with opacity controls |
| Display | Driver perspective cockpit | Steering wheel, displays with callout labels |
| Favorites | Cockpit overview | Zone callout labels (satellites, climate, center) |
| Safety | Interior/cockpit view | Subtle ambient lighting, dashboard context |

### REQ-CI-3D-002: 3D Preview Navigation
- **Description:** Where applicable (Ambient Light), a set of **play/forward triangle buttons** (▷) in a white pill container SHALL allow cycling through different 3D preview angles.

---

## 13. Validation & Test Strategy

### REQ-CI-TEST-001: Unit Tests
- All use cases SHALL have 100% unit test coverage.
- ViewModel intent handling SHALL be tested for every intent type in `ComfortInteriorContract.Intent`.
- State transitions SHALL be verified for edge cases (e.g., switching seats preserves massage mode).

### REQ-CI-TEST-002: UI Tests
- Each sub-section composable SHALL have Compose UI tests verifying:
  - Correct rendering of active/inactive states
  - Toggle interaction behavior
  - Slider value changes
  - Modal display/dismissal
  - PIN entry flow (4-digit completion, backspace)

### REQ-CI-TEST-003: Integration Tests
- End-to-end flow from Intent dispatch → State update → UI render for:
  - Massage mode selection per seat
  - Ambient light theme + brightness change
  - Roof segment toggle + preset application
  - Display brightness adjustment
  - Safety toggle with airbag confirmation dialog
  - PIN entry modal lifecycle

### REQ-CI-TEST-004: Safety-Critical Tests
- Passenger Airbag toggle SHALL be tested for:
  - Confirmation dialog appearance before state change
  - State NOT changing if confirmation is dismissed
  - State changing ONLY after explicit confirmation
- Glovebox PIN SHALL be tested for:
  - Encrypted storage (no plaintext PIN in memory/logs)
  - PIN validation success/failure paths
  - Modal dismissal without save

---

## 14. Accessibility Requirements

### REQ-CI-ACC-001: Content Descriptions
- All interactive elements SHALL have meaningful `contentDescription` values for screen readers.
- 3D visualization labels SHALL be accessible via TalkBack.

### REQ-CI-ACC-002: Touch Targets
- All interactive elements SHALL meet minimum **48dp × 48dp** touch target size (Android automotive guidelines).

### REQ-CI-ACC-003: Color Contrast
- All text SHALL meet **WCAG 2.1 AA** contrast ratio (4.5:1 for normal text, 3:1 for large text) against their background.

---

## 15. File Structure (New Files)

```
app/src/main/java/com/example/carfunction/
├── domain/
│   ├── model/
│   │   ├── MassageState.kt
│   │   ├── AmbientLightState.kt
│   │   ├── PanoramaRoofState.kt
│   │   ├── DisplayState.kt
│   │   ├── SafetyState.kt
│   │   └── ComfortEnums.kt          (SubSection, MassageMode, SeatPosition, etc.)
│   └── usecase/
│       ├── GetMassageStateUseCase.kt
│       ├── SetMassageModeUseCase.kt
│       ├── GetAmbientLightStateUseCase.kt
│       ├── SetAmbientLightUseCase.kt
│       ├── GetPanoramaRoofStateUseCase.kt
│       ├── SetRoofSegmentUseCase.kt
│       ├── SetRoofPresetUseCase.kt
│       ├── GetDisplayBrightnessUseCase.kt
│       ├── SetDisplayBrightnessUseCase.kt
│       ├── GetSafetyStateUseCase.kt
│       ├── SetSafetySettingUseCase.kt
│       ├── SetGloveboxPinUseCase.kt
│       └── ValidateGloveboxPinUseCase.kt
├── presentation/
│   ├── comfortinterior/
│   │   ├── ComfortInteriorContract.kt
│   │   ├── ComfortInteriorViewModel.kt
│   │   ├── ComfortInteriorScreen.kt
│   │   └── components/
│   │       ├── SeatMassageContent.kt
│   │       ├── SeatAndLoadingContent.kt
│   │       ├── AmbientLightContent.kt
│   │       ├── PanoramaRoofContent.kt
│   │       ├── DisplayContent.kt
│   │       ├── FavoritesContent.kt
│   │       ├── SafetyContent.kt
│   │       ├── MassageModeSelector.kt
│   │       ├── BrightnessSlider.kt
│   │       ├── RoofSegmentControl.kt
│   │       ├── RoofPresetBar.kt
│   │       ├── PinEntryModal.kt
│   │       ├── NumericKeypad.kt
│   │       ├── InteractiveSeatMap.kt
│   │       └── DisplayCalloutOverlay.kt
```

---

## 16. Deployment & Next Steps

1. **Implement Domain Models** — Create all enums and data classes in `domain/model/`.
2. **Extend Repository Interface** — Add Comfort & Interior methods to `CarFunctionRepository`.
3. **Implement Use Cases** — Create all use cases in `domain/usecase/`.
4. **Extend Mock/Prod Data Sources** — Add C&I data handling to both source sets.
5. **Create MVI Contract** — Implement `ComfortInteriorContract.kt`.
6. **Create ViewModel** — Implement `ComfortInteriorViewModel.kt` extending `MviViewModel`.
7. **Build UI Components** — Implement all composable components using `audi-compose-ui` and `common-core-ui`.
8. **Compose Screen** — Assemble `ComfortInteriorScreen.kt` with sidebar navigation and content switching.
9. **Add Route** — Register C&I route in `AppRoutes` and `CarFunctionNavHost`.
10. **Write Tests** — Unit, UI, and integration tests per REQ-CI-TEST-xxx.
11. **Code Review** — Detekt/ktlint validation, MISRA alignment check.

---

> **Disclaimer:** This requirements specification is generated by KGPT Code Assist (Beacon CLI). All artifacts MUST undergo human review before integration into production systems. Safety-critical features (Passenger Airbag, Child Presence Detection) require additional FMEA and hazard analysis per ISO 26262 before deployment.
