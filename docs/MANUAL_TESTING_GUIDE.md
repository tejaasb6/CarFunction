# CarFunction App — Manual Testing Guide

**Application:** CarFunction (Audi Car Function HMI)  
**Version:** 1.0  
**Document Date:** 2026-07-31  
**Screens Covered:** MyCar Screen, Comfort & Interior Screen  
**Build Variant:** `prodDebug`  
**Target:** Android Emulator (API 26+) / Physical Device

---

## Table of Contents

1. [Prerequisites](#1-prerequisites)
2. [Emulator Setup](#2-emulator-setup)
3. [Building the APK](#3-building-the-apk)
4. [Installing and Launching the App](#4-installing-and-launching-the-app)
5. [App Overview & Navigation](#5-app-overview--navigation)
6. [MyCar Screen — Test Cases](#6-mycar-screen--test-cases)
7. [Comfort & Interior Screen — Test Cases](#7-comfort--interior-screen--test-cases)
8. [Cross-Screen & Integration Tests](#8-cross-screen--integration-tests)
9. [Performance & Stability Tests](#9-performance--stability-tests)
10. [Known Limitations & Notes](#10-known-limitations--notes)
11. [Bug Report Template](#11-bug-report-template)

---

## 1. Prerequisites

### 1.1 Software Requirements

| Tool | Minimum Version | Notes |
|------|----------------|-------|
| **Android Studio** | Hedgehog 2024.1+ (or later) | Must support AGP 9.3.1 |
| **JDK** | 17 | Project uses Java 17 toolchain |
| **Android SDK** | API 36 (compileSdk) | Ensure SDK 36 is installed |
| **Kotlin** | 2.2.10 | Bundled with project |
| **Gradle** | Wrapper included | Use `./gradlew` from project root |
| **ADB** | Latest via SDK Platform-Tools | For CLI install/debug |

### 1.2 SDK Components to Install (via SDK Manager)

Open Android Studio → **Settings** → **SDK Manager** and install:

- **SDK Platforms:** Android 14 (API 34) or Android 15 (API 36)
- **SDK Tools:** Android SDK Build-Tools, Android Emulator, Android SDK Platform-Tools
- **System Images:** `google_apis` or `google_apis_playstore` for your target API level (x86_64 or arm64)

### 1.3 Hardware Requirements

| Resource | Minimum | Recommended |
|----------|---------|-------------|
| RAM | 8 GB | 16 GB+ |
| Disk | 10 GB free | 20 GB+ |
| CPU | x86_64 with VT-x/AMD-V | Multi-core with hardware virtualization |
| GPU | OpenGL 3.0+ | Hardware GPU acceleration enabled |

---

## 2. Emulator Setup

### 2.1 Option A: Android Studio Device Manager (GUI)

1. Open Android Studio.
2. Go to **Tools → Device Manager** (or click the device icon in the toolbar).
3. Click **Create Virtual Device**.
4. **Choose a Hardware Profile:**
   - **Recommended for Automotive HMI:** Select **Automotive** category → `Automotive (1024p landscape)` profile.
   - **Alternative (Generic Tablet):** Select **Tablet** category → any landscape tablet profile.
   - The app is designed for **landscape orientation** with a 3904×1320 reference resolution.
5. **Select a System Image:**
   - Tab: **Recommended**
   - Select **API 33** (Android 13) or **API 34** (Android 14) — `google_apis | x86_64`.
   - Click **Download** if the image is not yet installed, then select it.
6. **Configure AVD:**
   - Name: `CarFunction_Test`
   - Orientation: **Landscape**
   - Advanced Settings:
     - RAM: `2048 MB` (or higher)
     - Internal Storage: `2048 MB`
     - Enable **Hardware GPU** acceleration.
7. Click **Finish**.

### 2.2 Option B: Command Line (avdmanager + emulator)

```bash
# Step 1: Install a system image (if not already installed)
sdkmanager "system-images;android-34;google_apis;x86_64"

# Step 2: Create the AVD
avdmanager create avd \
  -n "CarFunction_Test" \
  -k "system-images;android-34;google_apis;x86_64" \
  -d "automotive_1024p_landscape"

# Step 3: Launch the emulator
emulator -avd CarFunction_Test -gpu host

# Step 4: Verify the device is online
adb devices
# Expected output: emulator-5554   device
```

### 2.3 Emulator Tips

- **Force Landscape:** If the emulator opens in portrait, rotate it using `Ctrl + Left/Right Arrow` (or the rotation button in the emulator toolbar).
- **Multi-touch simulation:** Hold `Ctrl` while clicking to simulate pinch gestures.
- **If multiple devices are connected:** Prefix all `adb` commands with `-s emulator-5554`.

---

## 3. Building the APK

### 3.1 Build Variants

The project has two product flavors under the `environment` dimension:

| Variant | Application ID | Data Source | Use For |
|---------|---------------|-------------|---------|
| **`mockDebug`** | `com.example.carfunction.mock.debug` | `MockCarFunctionDataSource` (hardcoded test data) | Development / quick UI iteration |
| **`mockRelease`** | `com.example.carfunction.mock` | `MockCarFunctionDataSource` | Release testing with ProGuard |
| **`prodDebug`** | `com.example.carfunction.debug` | `ProdCarFunctionDataSource` (vehicle HAL stubs) | **Primary testing — use this** |
| **`prodRelease`** | `com.example.carfunction` | `ProdCarFunctionDataSource` | Production deployment |

> **For this guide, use `prodDebug`.** This variant uses `ProdCarFunctionDataSource`, which is the production data source wired via Hilt. On emulator (without a real vehicle HAL), it provides in-memory stub data identical in structure to mock but routed through the production DI graph — ensuring the full production dependency injection, service binding paths, and repository wiring are exercised.
>
> **Important `prodDebug` behavioral differences from `mockDebug`:**
> - `setDriveMode()` is a **no-op** (TODO: wire to `CarPropertyManager`). Drive mode selection will dispatch the intent and update the UI carousel highlight, but no downstream vehicle signal is sent.
> - `selectAmbientLightPreset()` is a **no-op** (TODO: wire to ambient light controller). Preset taps register visually but do not persist a "selected" state in the data source.
> - Application ID is `com.example.carfunction.debug` (no `.mock` segment).
> - `BuildConfig.ENVIRONMENT` = `"PROD"`.

### 3.2 Build via Android Studio

1. Open the project in Android Studio.
2. Go to **Build → Select Build Variant** (bottom-left panel).
3. Set the `:app` module variant to **`prodDebug`**.
4. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
5. APK output: `app/build/outputs/apk/prod/debug/app-prod-debug.apk`

### 3.3 Build via Command Line

```bash
# Navigate to project root
cd /path/to/CarFunction

# Clean and build the prodDebug APK
./gradlew clean assembleProdDebug

# APK location after build:
# app/build/outputs/apk/prod/debug/app-prod-debug.apk
```

### 3.4 Verify Build Success

```bash
# Check the APK exists
ls -la app/build/outputs/apk/prod/debug/app-prod-debug.apk

# Verify APK info
aapt dump badging app/build/outputs/apk/prod/debug/app-prod-debug.apk | grep -E "package:|sdkVersion:|targetSdkVersion:"
# Expected: package: name='com.example.carfunction.debug'
```

---

## 4. Installing and Launching the App

### 4.1 Install via Android Studio

1. Ensure the emulator is running (green dot in Device Manager).
2. Select the `CarFunction_Test` emulator as the deployment target (top toolbar dropdown).
3. Click **Run ▶** (or `Shift + F10`).
4. Android Studio will build, install, and launch the app automatically.

### 4.2 Install via ADB (Command Line)

```bash
# Install the APK
adb install -r app/build/outputs/apk/prod/debug/app-prod-debug.apk

# Verify installation
adb shell pm list packages | grep carfunction
# Expected: package:com.example.carfunction.debug

# Launch the app
adb shell am start -n com.example.carfunction.debug/com.example.carfunction.MainActivity
```

### 4.3 Uninstall (for clean re-testing)

```bash
adb uninstall com.example.carfunction.debug
```

### 4.4 Post-Install Verification Checklist

- [ ] App launches without crash
- [ ] Dark-themed UI renders correctly
- [ ] Top navigation bar displays all 5 tabs
- [ ] Default screen is **MyCar**
- [ ] No ANR dialog appears within 5 seconds of launch

---

## 5. App Overview & Navigation

### 5.1 Top Navigation Bar

The app has a **horizontal top navigation bar** with 5 tabs:

| Tab Index | Label | Screen | Status |
|-----------|-------|--------|--------|
| 0 | **MyCar** | `MyCarScreen` | ✅ Fully Implemented |
| 1 | **Charging** | `PlaceholderScreen` | ⬜ Placeholder |
| 2 | **Driving Assistance** | `PlaceholderScreen` | ⬜ Placeholder |
| 3 | **Driving & Exterior** | `PlaceholderScreen` | ⬜ Placeholder |
| 4 | **Comfort & Interior** | `ComfortInteriorScreen` | ✅ Fully Implemented |

### 5.2 Navigation Architecture

- Navigation is powered by **Jetpack Compose Navigation** (`NavHost`).
- Routes: `my_car`, `charging`, `driving_assistance`, `driving_exterior`, `comfort_interior`.
- The selected tab is highlighted with a distinct visual indicator.
- Tab switching re-creates the destination screen (no back-stack retention for tabs).

---

## 6. MyCar Screen — Test Cases

### 6.1 Screen Layout Overview

```
┌──────────────────────────────────────────────────────────────────┐
│                     Top Navigation Bar (5 tabs)                  │
├─────────────────┬────────────────────────────────────────────────┤
│   Left Pane     │              Right Pane                        │
│   (320dp fixed) │          (remaining space)                     │
│                 │                                                │
│  Quick Access   │     Marble Gradient Background                 │
│  Drive Select   │                                                │
│  Massage        │                                                │
│  Ambient Light  │                                                │
│  Dynamic Content│              Exterior/Interior Toggle          │
│   (scrollable)  │                    (bottom-right)              │
└─────────────────┴────────────────────────────────────────────────┘
```

---

### 6.2 TC-MYCAR-001: Screen Initial Load

| Field | Value |
|-------|-------|
| **Precondition** | App installed, launch via MyCar tab (default) |
| **Steps** | 1. Launch the app. |
| **Expected** | MyCar tab is selected (highlighted). Left pane shows all sections: Quick Access tiles, Drive Select carousel, Massage Control, Ambient Light presets, Dynamic Content toggle. Right pane shows marble gradient background. No loading spinner persists beyond 2 seconds. |
| **Priority** | Critical |

---

### 6.3 TC-MYCAR-002: Quick Access Tiles Display

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Observe the top section of the left pane ("Quick Access" area). |
| **Expected** | Multiple quick access tiles are displayed in a grid/row layout. Each tile shows an icon and a label. Tiles with `hasSettings = true` show a gear/settings indicator. All tiles have proper icon rendering (no missing drawables). |
| **Priority** | High |

---

### 6.4 TC-MYCAR-003: Quick Access Tile Tap Interaction

| Field | Value |
|-------|-------|
| **Precondition** | Quick Access tiles visible |
| **Steps** | 1. Tap on any Quick Access tile. 2. Observe behavior. |
| **Expected** | Tile shows a visual tap ripple/feedback. The `QuickAccessClicked` event is dispatched. *Note: Current implementation has placeholder handlers — verify no crash occurs.* |
| **Priority** | Medium |

---

### 6.5 TC-MYCAR-004: Drive Select Carousel Display

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Scroll down in the left pane to the "Drive Select" section. 2. Observe the carousel. |
| **Expected** | Horizontal carousel displays all 4 drive modes: **Comfort**, **Balanced**, **Sport**, **Dynamic**. One mode is visually highlighted as the currently selected mode. Carousel items show mode labels clearly. |
| **Priority** | High |

---

### 6.6 TC-MYCAR-005: Drive Select Mode Switching

| Field | Value |
|-------|-------|
| **Precondition** | Drive Select carousel visible |
| **Steps** | 1. Tap on "Sport" mode in the carousel. 2. Observe the selection indicator. 3. Tap on "Dynamic" mode. 4. Tap on "Comfort" mode. |
| **Expected** | Each tap visually selects the tapped mode with a highlight/animation. The previously selected mode is deselected. The `SelectDriveMode` intent is dispatched for each selection. State updates to reflect `selectedDriveMode` change. *`prodDebug` note: `setDriveMode()` in `ProdCarFunctionDataSource` is a no-op — UI selection updates but no downstream vehicle signal is sent.* |
| **Priority** | High |

---

### 6.7 TC-MYCAR-006: Drive Select Carousel Horizontal Scroll

| Field | Value |
|-------|-------|
| **Precondition** | Drive Select carousel visible |
| **Steps** | 1. Swipe left on the carousel. 2. Swipe right on the carousel. |
| **Expected** | Carousel scrolls smoothly in both directions. All 4 modes (Comfort, Balanced, Sport, Dynamic) are accessible. Scroll does not overshoot or bounce excessively. |
| **Priority** | Medium |

---

### 6.8 TC-MYCAR-007: Massage Control Display

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Scroll to the "Massage" section in the left pane. |
| **Expected** | Massage control shows two zones: **Driver** and **Passenger**. Each zone displays the current massage mode (OFF / ACTIVE / MOBILITY). Mode selector buttons/controls are visible for each zone. |
| **Priority** | High |

---

### 6.9 TC-MYCAR-008: Massage Mode Switching (Driver)

| Field | Value |
|-------|-------|
| **Precondition** | Massage control visible |
| **Steps** | 1. Tap the "Active" mode for the Driver seat. 2. Observe the change. 3. Tap "Mobility" mode. 4. Tap "OFF" to deactivate. |
| **Expected** | Each mode selection is reflected visually. Driver mode state changes (OFF → ACTIVE → MOBILITY → OFF). Mode label updates accordingly. `SetMassageMode` intent is dispatched for each change. |
| **Priority** | High |

---

### 6.10 TC-MYCAR-009: Massage Mode Switching (Passenger)

| Field | Value |
|-------|-------|
| **Precondition** | Massage control visible |
| **Steps** | 1. Repeat massage mode switching for the Passenger seat. |
| **Expected** | Same behavior as Driver seat massage switching. Driver and Passenger modes are independent — changing one does not affect the other. |
| **Priority** | High |

---

### 6.11 TC-MYCAR-010: Ambient Light Presets Display

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Scroll to the "Ambient Light" section. |
| **Expected** | A list/row of ambient light preset items is displayed. Each preset shows a **label** and a **color indicator** (colored circle/swatch). Presets are loaded from mock data and rendered correctly. One preset may be highlighted as active. |
| **Priority** | High |

---

### 6.12 TC-MYCAR-011: Ambient Light Preset Selection

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light presets visible |
| **Steps** | 1. Tap on different ambient light presets. 2. Observe visual feedback. |
| **Expected** | Tapped preset gets visually selected/highlighted. Previously selected preset is deselected. Color indicator matches the preset's `colorArgb` value. No crash on rapid tapping. *`prodDebug` note: `selectAmbientLightPreset()` is a no-op in `ProdCarFunctionDataSource` — visual selection works via ViewModel state but the data source does not track the selected preset ID (unlike `mockDebug`).* |
| **Priority** | Medium |

---

### 6.13 TC-MYCAR-012: Dynamic Content Toggle

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Scroll to the "Dynamic Content" section. 2. Observe the toggle switch. 3. Tap the toggle to enable/disable. |
| **Expected** | Toggle switch renders in the correct on/off state. Tapping toggles the state (on ↔ off). The `ToggleDynamicContent` intent is dispatched. Visual state immediately reflects the change. |
| **Priority** | Medium |

---

### 6.14 TC-MYCAR-013: Exterior/Interior Toggle (Right Pane)

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Observe the bottom-right corner of the right pane. 2. Tap the Exterior/Interior toggle. |
| **Expected** | The toggle displays two options: "Exterior" and "Interior". The `ToggleCarView` event/intent is available. *Note: This toggle is currently non-functional (placeholder handler) — verify no crash on tap.* |
| **Priority** | Low |

---

### 6.15 TC-MYCAR-014: Left Pane Vertical Scroll

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Scroll up and down in the left pane. 2. Verify all sections are reachable. |
| **Expected** | Left pane scrolls smoothly through all sections: Quick Access → Drive Select → Massage → Ambient Light → Dynamic Content. Scroll does not clip content at the top or bottom. Scroll velocity feels natural (no janky frames). |
| **Priority** | High |

---

### 6.16 TC-MYCAR-015: Right Pane Gradient Background

| Field | Value |
|-------|-------|
| **Precondition** | MyCar screen loaded |
| **Steps** | 1. Observe the right pane. |
| **Expected** | A marble gradient background renders across the full right pane area. No visual artifacts, tearing, or solid color blocks. Background fills the available space without gaps. |
| **Priority** | Medium |

---

## 7. Comfort & Interior Screen — Test Cases

### 7.1 Screen Layout Overview

```
┌──────────────────────────────────────────────────────────────────┐
│                     Top Navigation Bar (5 tabs)                  │
├───────────┬──────────────────────────────────────────────────────┤
│  Sidebar  │              Content Area (80%)                      │
│   (20%)   │                                                      │
│           │  [Content varies by selected sidebar tab]            │
│ Seat      │                                                      │
│ Massage   │  - Seat Massage: visualization + floating bar        │
│           │  - Seat & Loading: toggle list                       │
│ Seat &    │  - Ambient Light: master toggle, themes, brightness  │
│ Loading   │  - Panorama Roof: segment controls                   │
│           │  - Display: brightness sliders per target             │
│ Ambient   │  - Favorites: 3D cockpit overlay zones               │
│ Light     │  - Safety: airbag, PIN, toggles                      │
│           │                                                      │
│ Panorama  │                                                      │
│ Roof      │                                                      │
│           │                                                      │
│ Display   │                                                      │
│ Favorites │                                                      │
│ Safety    │                                                      │
└───────────┴──────────────────────────────────────────────────────┘
```

---

### 7.2 TC-CI-001: Navigate to Comfort & Interior Screen

| Field | Value |
|-------|-------|
| **Precondition** | App launched, on MyCar screen |
| **Steps** | 1. Tap the **"Comfort & Interior"** tab in the top navigation bar (5th tab). |
| **Expected** | Screen transitions to the Comfort & Interior screen. Left sidebar shows available sub-sections. Default sub-section (Seat Massage) is selected and its content is displayed. "Comfort & Interior" tab is visually highlighted. |
| **Priority** | Critical |

---

### 7.3 TC-CI-002: Sidebar Navigation — All Tabs

| Field | Value |
|-------|-------|
| **Precondition** | Comfort & Interior screen loaded |
| **Steps** | 1. Tap each sidebar tab in order: Seat Massage → Seat & Loading → Ambient Light → Panorama Roof → Display → Favorites → Safety. 2. Observe content area changes. |
| **Expected** | Each tab click highlights the selected tab in the sidebar. Content area switches to the corresponding section's content. Previously selected tab is deselected. Transition is smooth with no flicker. *Note: Some tabs may be hidden based on platform capabilities (e.g., Seat Massage requires `supportsMassage`).* |
| **Priority** | Critical |

---

### 7.4 TC-CI-003: Sidebar Tab Visibility (Platform Capabilities)

| Field | Value |
|-------|-------|
| **Precondition** | Comfort & Interior screen loaded |
| **Steps** | 1. Count the visible sidebar tabs. |
| **Expected** | Tabs are filtered by platform capabilities: **Seat Massage** — visible only if `supportsMassage = true`. **Ambient Light** — visible only if `supportsAmbientLight = true`. **Panorama Roof** — visible only if `supportsPanoramaRoof = true`. **Display** — visible only if `supportsDisplay = true`. **Seat & Loading, Favorites, Safety** — always visible. In `prodDebug` build, all capabilities are `true` (all tabs visible). |
| **Priority** | High |

---

### 7.5 Seat Massage Sub-Section

#### TC-CI-004: Seat Massage Initial Display

| Field | Value |
|-------|-------|
| **Precondition** | Seat Massage tab selected |
| **Steps** | 1. Observe the content area. |
| **Expected** | Full gradient visualization area is displayed (replaces standard settings layout). A floating bottom bar appears with massage mode controls. Seat position selector shows **Driver** and **Passenger** options. Current massage mode is displayed for the selected seat. |
| **Priority** | High |

#### TC-CI-005: Seat Massage — Driver/Passenger Seat Toggle

| Field | Value |
|-------|-------|
| **Precondition** | Seat Massage tab selected |
| **Steps** | 1. Tap the **Passenger** seat selector. 2. Observe mode display. 3. Tap back to **Driver**. |
| **Expected** | Seat position switches between Driver and Passenger. Massage mode display updates to reflect the selected seat's current mode. Each seat maintains independent state. |
| **Priority** | High |

#### TC-CI-006: Seat Massage — Mode Selection

| Field | Value |
|-------|-------|
| **Precondition** | Seat Massage tab selected, Driver seat selected |
| **Steps** | 1. Select each massage mode in order: **OFF** → **Balance** → **Active** → **Mobility** → **Relax** → **Stretch**. 2. Observe visual feedback for each mode. |
| **Expected** | Each mode is selectable and visually highlighted when active. Mode label updates. `SetComfortMassageMode` intent is dispatched. Previous mode is deselected. Modes available: OFF, Balance, Active, Mobility, Relax, Stretch. |
| **Priority** | High |

#### TC-CI-007: Seat Massage — Independent Seat State

| Field | Value |
|-------|-------|
| **Precondition** | Seat Massage tab selected |
| **Steps** | 1. Set Driver to "Active". 2. Switch to Passenger. 3. Set Passenger to "Relax". 4. Switch back to Driver. |
| **Expected** | Driver still shows "Active". Passenger shows "Relax". Each seat's massage mode is independently maintained. |
| **Priority** | High |

---

### 7.6 Seat & Loading Sub-Section

#### TC-CI-008: Seat & Loading Display

| Field | Value |
|-------|-------|
| **Precondition** | Seat & Loading tab selected |
| **Steps** | 1. Observe the content area. |
| **Expected** | Two sections are displayed: **Seat Functions** with toggles for: Entry aid for 3rd seat row, Passenger seat adjustment from the driver's seat, Passenger seat adjustment from the rear seat. **Loading Functions** with toggle for: Cargo. Each function has a label and an on/off toggle switch. |
| **Priority** | High |

#### TC-CI-009: Seat & Loading Toggle Interaction

| Field | Value |
|-------|-------|
| **Precondition** | Seat & Loading tab selected |
| **Steps** | 1. Toggle "Entry aid for 3rd seat row" ON. 2. Toggle "Passenger seat adjustment from the driver's seat" ON. 3. Toggle "Cargo" ON. 4. Toggle each back OFF. |
| **Expected** | Each toggle switch changes state on tap. Visual state (on/off) matches the logical state. Toggles are independent — changing one does not affect others. |
| **Priority** | High |

---

### 7.7 Ambient Light Sub-Section

#### TC-CI-010: Ambient Light Master Toggle

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light tab selected |
| **Steps** | 1. Observe the master toggle at the top. 2. Toggle it ON. 3. Toggle it OFF. |
| **Expected** | Master toggle enables/disables all ambient light controls below it. When OFF, theme selector, brightness slider, and individual settings should be disabled/grayed out. When ON, all sub-controls become interactive. |
| **Priority** | Critical |

#### TC-CI-011: Ambient Light Theme Selector

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light master toggle ON |
| **Steps** | 1. Select each theme: **Sky** → **Horizon** → **Hearth** → **Sync**. |
| **Expected** | Each theme is selectable. Selected theme is visually highlighted. `SelectAmbientTheme` intent is dispatched. Theme name is displayed. |
| **Priority** | High |

#### TC-CI-012: Ambient Light Brightness Slider

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light master toggle ON |
| **Steps** | 1. Drag the brightness slider to minimum (left). 2. Drag to maximum (right). 3. Set to approximately 50%. |
| **Expected** | Slider moves smoothly. Value updates in real-time. Slider range is 0.0 to 1.0 (visual min to max). No jumpiness or lag during drag. |
| **Priority** | High |

#### TC-CI-013: Ambient Light Settings Toggles

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light master toggle ON |
| **Steps** | 1. Toggle each setting individually: Footwell Lighting, Roofline Lighting, Panoramic Roof Lighting. 2. Toggle each ON, then OFF. |
| **Expected** | Each setting toggle is independent. Visual state matches logical state. `ToggleAmbientSetting` intent is dispatched for each change. |
| **Priority** | High |

#### TC-CI-014: Interaction Light Controls

| Field | Value |
|-------|-------|
| **Precondition** | Ambient Light master toggle ON |
| **Steps** | 1. Toggle the **Interaction Light** master toggle. 2. Adjust the Interaction Light brightness slider. 3. Toggle individual interaction light features: Indicator/Hazard warning lights, Charging, Digital Assistant, Navigation, Phone. |
| **Expected** | Interaction Light master toggle enables/disables its sub-controls. Brightness slider is functional when Interaction Light is ON. Each feature toggle works independently. All 5 features are listed and toggleable. |
| **Priority** | High |

---

### 7.8 Panorama Roof Sub-Section

#### TC-CI-015: Panorama Roof Segment Display

| Field | Value |
|-------|-------|
| **Precondition** | Panorama Roof tab selected |
| **Steps** | 1. Observe the content area. |
| **Expected** | 10 roof segments are displayed (visual representation). Each segment shows its current state (open/closed). Segment controls are interactive. Up to 8 preset positions are available. |
| **Priority** | High |

#### TC-CI-016: Panorama Roof Segment Toggle

| Field | Value |
|-------|-------|
| **Precondition** | Panorama Roof tab selected |
| **Steps** | 1. Tap on individual roof segments to toggle them open/closed. 2. Toggle multiple segments. |
| **Expected** | Each segment toggles independently. Visual state changes (e.g., color/opacity to indicate open vs. closed). `ToggleRoofSegment` intent dispatched with segment index. |
| **Priority** | High |

#### TC-CI-017: Panorama Roof Preset Selection

| Field | Value |
|-------|-------|
| **Precondition** | Panorama Roof tab selected |
| **Steps** | 1. Tap on different preset positions (up to 8 available). |
| **Expected** | Selecting a preset updates all 10 segments to the preset configuration. Selected preset index is highlighted. `SelectRoofPreset` intent dispatched with preset index. |
| **Priority** | Medium |

---

### 7.9 Display Sub-Section

#### TC-CI-018: Display Brightness Targets

| Field | Value |
|-------|-------|
| **Precondition** | Display tab selected |
| **Steps** | 1. Observe available display targets. |
| **Expected** | Three display targets are shown: **Head-Up**, **Virtual Cockpit**, **MMI**. Each has an individual brightness slider. *Note: Display targets may be filtered by platform capabilities.* |
| **Priority** | High |

#### TC-CI-019: Display Brightness Slider Interaction

| Field | Value |
|-------|-------|
| **Precondition** | Display tab selected |
| **Steps** | 1. Adjust the brightness slider for "Head-Up" display. 2. Adjust for "Virtual Cockpit". 3. Adjust for "MMI". |
| **Expected** | Each slider moves smoothly and independently. Brightness value updates in real-time. Sliders maintain their individual values (changing one doesn't affect others). `SetDisplayBrightness` intent dispatched with target and value. |
| **Priority** | High |

---

### 7.10 Favorites Sub-Section

#### TC-CI-020: Favorites Zone Display

| Field | Value |
|-------|-------|
| **Precondition** | Favorites tab selected |
| **Steps** | 1. Observe the content area. |
| **Expected** | A 3D cockpit overlay visualization is displayed showing labeled zones: **Left Satellite**, **Right Satellite**, **Climate Favorite**, **Center Control Unit**. Each zone is clearly labeled and tappable. |
| **Priority** | High |

#### TC-CI-021: Favorites Zone Interaction

| Field | Value |
|-------|-------|
| **Precondition** | Favorites tab selected |
| **Steps** | 1. Tap on "Left Satellite" zone. 2. Tap on "Right Satellite" zone. 3. Tap on "Climate Favorite" zone. 4. Tap on "Center Control Unit" zone. |
| **Expected** | Each zone tap provides visual feedback. `SelectFavoriteZone` intent dispatched with zone identifier. Selected zone is visually highlighted. No crash on any zone tap. |
| **Priority** | Medium |

---

### 7.11 Safety Sub-Section

#### TC-CI-022: Safety Section Display

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected |
| **Steps** | 1. Observe the safety settings content area. |
| **Expected** | Four settings are displayed with toggle switches: **Passenger Airbag** (default: ON), **Fond Info Tone** (default: OFF), **Child Presence Detection** (default: OFF), **Glovebox PIN** (default: OFF). An "Edit PIN" option is visible (with edit icon). |
| **Priority** | Critical |

#### TC-CI-023: Passenger Airbag Toggle — Confirmation Dialog

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected, Passenger Airbag is ON |
| **Steps** | 1. Tap the Passenger Airbag toggle to turn it OFF. 2. Observe the confirmation dialog. |
| **Expected** | An **Airbag Confirmation Dialog** appears (safety-critical action). Dialog shows: A **warning banner** with warning icon (`ic_warning`), A close button (`ic_close`), A clear warning message about disabling the airbag, **Confirm** and **Cancel** buttons. This is an ISO 26262 ASIL-B rated safety control — state must NOT change without explicit user confirmation. |
| **Priority** | Critical |

#### TC-CI-024: Airbag Dialog — Confirm Action

| Field | Value |
|-------|-------|
| **Precondition** | Airbag confirmation dialog visible |
| **Steps** | 1. Tap **Confirm**. |
| **Expected** | Dialog dismisses. Passenger Airbag toggle switches to OFF. State is updated via `ConfirmAirbagChange` intent. |
| **Priority** | Critical |

#### TC-CI-025: Airbag Dialog — Cancel Action

| Field | Value |
|-------|-------|
| **Precondition** | Airbag confirmation dialog visible |
| **Steps** | 1. Tap **Cancel** (or close button). |
| **Expected** | Dialog dismisses. Passenger Airbag toggle remains in its previous state (ON). No state change occurs. |
| **Priority** | Critical |

#### TC-CI-026: Airbag Dialog — Dismiss via Close Button

| Field | Value |
|-------|-------|
| **Precondition** | Airbag confirmation dialog visible |
| **Steps** | 1. Tap the **X (close)** button on the dialog. |
| **Expected** | Dialog dismisses without changing the airbag state. Same behavior as Cancel. |
| **Priority** | High |

#### TC-CI-027: Safety — Fond Info Tone Toggle

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected |
| **Steps** | 1. Toggle "Fond Info Tone" ON. 2. Toggle it OFF. |
| **Expected** | Toggle switches state. No confirmation dialog required (non-safety-critical). |
| **Priority** | Medium |

#### TC-CI-028: Safety — Child Presence Detection Toggle

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected |
| **Steps** | 1. Toggle "Child Presence Detection" ON. 2. Toggle it OFF. |
| **Expected** | Toggle switches state. No confirmation dialog required. |
| **Priority** | Medium |

#### TC-CI-029: Safety — Glovebox PIN Toggle & PIN Entry Modal

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected |
| **Steps** | 1. Toggle "Glovebox PIN" ON. 2. Observe the PIN entry modal. |
| **Expected** | A **PIN Entry Modal** appears requesting the user to set a PIN. Modal shows: Numeric keypad (0-9), Backspace button (`ic_backspace`), PIN input display (dots/digits), Confirm/Submit button. |
| **Priority** | High |

#### TC-CI-030: PIN Entry Modal — PIN Input

| Field | Value |
|-------|-------|
| **Precondition** | PIN entry modal visible |
| **Steps** | 1. Tap digits 1, 2, 3, 4 on the keypad. 2. Observe the PIN display. 3. Tap backspace to delete the last digit. 4. Re-enter the digit. 5. Tap Confirm. |
| **Expected** | Each digit tap appends to the PIN display. PIN display shows dots/masked characters for each entered digit. Backspace removes the last entered digit. Confirm submits the PIN and dismisses the modal. `SubmitPin` intent dispatched with the entered PIN. |
| **Priority** | High |

#### TC-CI-031: PIN Entry Modal — Dismiss Without Entry

| Field | Value |
|-------|-------|
| **Precondition** | PIN entry modal visible |
| **Steps** | 1. Tap outside the modal (or press Back). |
| **Expected** | Modal dismisses. Glovebox PIN toggle reverts to its previous state. No PIN is set. |
| **Priority** | High |

#### TC-CI-032: Safety — Edit PIN

| Field | Value |
|-------|-------|
| **Precondition** | Safety tab selected, Glovebox PIN is ON |
| **Steps** | 1. Tap the **Edit PIN** button (edit icon `ic_edit`). |
| **Expected** | PIN Entry Modal opens for editing the existing PIN. Same keypad interface as initial PIN setup. |
| **Priority** | Medium |

---

## 8. Cross-Screen & Integration Tests

### TC-INT-001: Tab Switching — MyCar ↔ Comfort & Interior

| Field | Value |
|-------|-------|
| **Steps** | 1. Start on MyCar screen. 2. Switch to Comfort & Interior. 3. Interact with some settings. 4. Switch back to MyCar. 5. Switch to Comfort & Interior again. |
| **Expected** | Both screens render correctly on each switch. No crash during rapid tab switching. Screen state may or may not persist across tab switches (navigation re-creates destinations). |
| **Priority** | Critical |

### TC-INT-002: Rapid Tab Switching Stress Test

| Field | Value |
|-------|-------|
| **Steps** | 1. Rapidly tap between all 5 navigation tabs 20+ times in quick succession. |
| **Expected** | No ANR. No crash. No visual glitch. UI remains responsive. |
| **Priority** | High |

### TC-INT-003: Placeholder Screen Navigation

| Field | Value |
|-------|-------|
| **Steps** | 1. Tap Charging tab. 2. Tap Driving Assistance tab. 3. Tap Driving & Exterior tab. |
| **Expected** | Each tab shows a placeholder screen (centered text: "Coming soon" or similar). No crash. Navigation highlight updates correctly. |
| **Priority** | Medium |

### TC-INT-004: App Backgrounding and Resume

| Field | Value |
|-------|-------|
| **Steps** | 1. Navigate to Comfort & Interior → Ambient Light. 2. Enable master toggle and set theme to "Horizon". 3. Press Home button (background the app). 4. Re-open the app from recent apps. |
| **Expected** | App resumes on the same screen and tab. State may reset (ViewModel lifecycle behavior) or persist. No crash on resume. |
| **Priority** | High |

### TC-INT-005: Screen Rotation (if applicable)

| Field | Value |
|-------|-------|
| **Steps** | 1. On MyCar screen, rotate device/emulator. 2. On Comfort & Interior screen, rotate device/emulator. |
| **Expected** | App is designed for **landscape only** (`screenOrientation="landscape"` in manifest). If rotation is forced, the app should maintain landscape or handle rotation gracefully without crash. |
| **Priority** | Medium |

---

## 9. Performance & Stability Tests

### TC-PERF-001: Cold Start Time

| Field | Value |
|-------|-------|
| **Steps** | 1. Force-stop the app: `adb shell am force-stop com.example.carfunction.debug`. 2. Launch the app: `adb shell am start -W com.example.carfunction.debug/com.example.carfunction.MainActivity`. 3. Note the `TotalTime` in the output. |
| **Expected** | Cold start time < 3 seconds. No blank/white screen longer than 1 second. |
| **Priority** | High |

### TC-PERF-002: Memory Usage

| Field | Value |
|-------|-------|
| **Steps** | 1. Launch the app. 2. Navigate through all screens and interact with controls. 3. Check memory: `adb shell dumpsys meminfo com.example.carfunction.debug`. |
| **Expected** | Total PSS < 150 MB during normal usage. No continuous memory growth (potential leak). |
| **Priority** | Medium |

### TC-PERF-003: Frame Rate / Jank Detection

| Field | Value |
|-------|-------|
| **Steps** | 1. Enable GPU rendering profiling: `adb shell setprop debug.hwui.profile true`. 2. Navigate between screens and scroll through content. 3. Observe frame rendering bars. |
| **Expected** | Most frames render within 16ms (60fps target). No sustained dropped frames during scrolling or tab switching. |
| **Priority** | Medium |

### TC-PERF-004: Process Death Recovery

| Field | Value |
|-------|-------|
| **Steps** | 1. Navigate to Comfort & Interior → Safety. 2. Toggle some settings. 3. Kill the process: `adb shell am kill com.example.carfunction.debug`. 4. Re-open the app from recent apps. |
| **Expected** | App relaunches without crash. State may reset to defaults (acceptable). No data corruption. |
| **Priority** | High |

### TC-PERF-005: Low Memory Simulation

| Field | Value |
|-------|-------|
| **Steps** | 1. Launch the app. 2. Send trim memory signal: `adb shell am send-trim-memory com.example.carfunction.debug RUNNING_LOW`. 3. Interact with the app. |
| **Expected** | App continues functioning. No crash or ANR. App may release non-critical resources gracefully. |
| **Priority** | Medium |

---

## 10. Known Limitations & Notes

### 10.1 Current Placeholder Functionality

| Feature | Status | Notes |
|---------|--------|-------|
| Exterior/Interior toggle (MyCar right pane) | Non-functional | Event dispatched but handler is a no-op |
| Vehicle Visualization (3D car model) | Placeholder | Shows gradient background only |
| Hotspot tap on vehicle | Non-functional | `HotspotClicked` intent defined but not wired |
| Search button | Non-functional | `SearchClicked` intent defined but not wired |
| Charging, Driving Assistance, Driving & Exterior tabs | Placeholder screens | Display "Coming Soon" text only |
| Effects (ShowToast, NavigateTo, etc.) | Placeholder | Effect handlers are no-op lambdas |

### 10.2 `prodDebug`-Specific Behavioral Notes

The `prodDebug` variant uses `ProdCarFunctionDataSource` injected via the production Hilt module (`DataSourceModule`). Key behavioral differences on emulator (no real vehicle HAL):

| Data Source Method | Behavior in `prodDebug` |
|--------------------|------------------------|
| `setDriveMode()` | **No-op** — UI carousel updates visually, but no vehicle signal sent. TODO in source: "Send to vehicle HAL via CarPropertyManager" |
| `selectAmbientLightPreset()` | **No-op** — Preset tap registers but selected state is not tracked in the data source (unlike `mockDebug` which tracks `selectedPresetId`) |
| `setMassageDriverMode()` / `setMassagePassengerMode()` | ✅ Functional — updates in-memory `MutableStateFlow` |
| `setDynamicContentEnabled()` | ✅ Functional — updates in-memory `MutableStateFlow` |
| All `get*()` / `is*()` flows | ✅ Functional — returns in-memory stub data |

### 10.3 Platform Capabilities in `prodDebug` Build

In the `prodDebug` build, all platform capabilities are enabled by default:
- `supportsMassage = true`
- `supportsAmbientLight = true`
- `supportsPanoramaRoof = true`
- `supportsDisplay = true`

This means all sidebar tabs in Comfort & Interior are visible.

### 10.4 Safety-Critical Controls

The **Passenger Airbag** toggle is classified as **ISO 26262 ASIL-B**. It requires a confirmation dialog before state changes. This is the only safety-gated control in the current implementation.

### 10.5 Dark Theme Only

The app uses a dark theme exclusively. There is no light theme variant. All testing should expect dark backgrounds with light text and accent colors.

---

## 11. Bug Report Template

When filing bugs discovered during testing, use this template:

```markdown
### Bug Title
[Brief, descriptive title]

### Environment
- **Build Variant:** prodDebug
- **Device:** [Emulator name or physical device]
- **API Level:** [e.g., 34]
- **App Version:** 1.0 (versionCode 1)
- **Resolution:** [e.g., 3904×1320]

### Test Case Reference
[e.g., TC-CI-023]

### Steps to Reproduce
1. [Step 1]
2. [Step 2]
3. [Step 3]

### Expected Result
[What should happen]

### Actual Result
[What actually happened]

### Severity
- [ ] Critical (crash / data loss / safety issue)
- [ ] High (feature broken / major UI issue)
- [ ] Medium (minor UI issue / edge case)
- [ ] Low (cosmetic / enhancement)

### Screenshots / Logs
[Attach screenshots, screen recordings, or logcat output]

### Logcat Command
```bash
adb logcat -s "CarFunction" --pid=$(adb shell pidof com.example.carfunction.debug) > bug_log.txt
```
```

---

## Test Execution Summary Template

| Test Case | Status | Tester | Date | Notes |
|-----------|--------|--------|------|-------|
| TC-MYCAR-001 | ⬜ | | | |
| TC-MYCAR-002 | ⬜ | | | |
| TC-MYCAR-003 | ⬜ | | | |
| TC-MYCAR-004 | ⬜ | | | |
| TC-MYCAR-005 | ⬜ | | | |
| TC-MYCAR-006 | ⬜ | | | |
| TC-MYCAR-007 | ⬜ | | | |
| TC-MYCAR-008 | ⬜ | | | |
| TC-MYCAR-009 | ⬜ | | | |
| TC-MYCAR-010 | ⬜ | | | |
| TC-MYCAR-011 | ⬜ | | | |
| TC-MYCAR-012 | ⬜ | | | |
| TC-MYCAR-013 | ⬜ | | | |
| TC-MYCAR-014 | ⬜ | | | |
| TC-MYCAR-015 | ⬜ | | | |
| TC-CI-001 | ⬜ | | | |
| TC-CI-002 | ⬜ | | | |
| TC-CI-003 | ⬜ | | | |
| TC-CI-004 | ⬜ | | | |
| TC-CI-005 | ⬜ | | | |
| TC-CI-006 | ⬜ | | | |
| TC-CI-007 | ⬜ | | | |
| TC-CI-008 | ⬜ | | | |
| TC-CI-009 | ⬜ | | | |
| TC-CI-010 | ⬜ | | | |
| TC-CI-011 | ⬜ | | | |
| TC-CI-012 | ⬜ | | | |
| TC-CI-013 | ⬜ | | | |
| TC-CI-014 | ⬜ | | | |
| TC-CI-015 | ⬜ | | | |
| TC-CI-016 | ⬜ | | | |
| TC-CI-017 | ⬜ | | | |
| TC-CI-018 | ⬜ | | | |
| TC-CI-019 | ⬜ | | | |
| TC-CI-020 | ⬜ | | | |
| TC-CI-021 | ⬜ | | | |
| TC-CI-022 | ⬜ | | | |
| TC-CI-023 | ⬜ | | | |
| TC-CI-024 | ⬜ | | | |
| TC-CI-025 | ⬜ | | | |
| TC-CI-026 | ⬜ | | | |
| TC-CI-027 | ⬜ | | | |
| TC-CI-028 | ⬜ | | | |
| TC-CI-029 | ⬜ | | | |
| TC-CI-030 | ⬜ | | | |
| TC-CI-031 | ⬜ | | | |
| TC-CI-032 | ⬜ | | | |
| TC-INT-001 | ⬜ | | | |
| TC-INT-002 | ⬜ | | | |
| TC-INT-003 | ⬜ | | | |
| TC-INT-004 | ⬜ | | | |
| TC-INT-005 | ⬜ | | | |
| TC-PERF-001 | ⬜ | | | |
| TC-PERF-002 | ⬜ | | | |
| TC-PERF-003 | ⬜ | | | |
| TC-PERF-004 | ⬜ | | | |
| TC-PERF-005 | ⬜ | | | |

---

*Generated with BEACON*
