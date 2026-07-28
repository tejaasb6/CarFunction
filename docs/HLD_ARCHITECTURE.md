# High-Level Design (HLD) Architecture Document
## CarFunction — Multi-OEM, Multi-Platform MVI Application

**Version:** 1.0  
**OEM:** Audi (extensible to future OEMs)  
**Platforms:** SDV (Software-Defined Vehicle), CL8min (Cockpit)  
**Architecture:** MVI + Clean Architecture  

---

## 1. Executive Summary

CarFunction is a multi-screen Android automotive application built with **Jetpack Compose**, 
implementing **MVI (Model-View-Intent)** pattern atop **Clean Architecture** principles. 
The architecture is designed to support **Audi** across **SDV** and **CL8min** platforms,
with native extensibility for onboarding future OEMs (BMW, Mercedes, etc.) and platforms
with **minimal code changes**.

### Key Design Goals
- **Scalability** — Add new OEMs/platforms via configuration, not refactoring
- **Reusability** — Shared domain logic, OEM-agnostic UI components
- **Maintainability** — Clean separation of concerns across layers
- **Testability** — Pure domain layer, isolated data sources, mock/prod variants

---

## 2. Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        PRESENTATION LAYER                       │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Screens (MyCarScreen, ChargingScreen, etc.)            │   │
│  │  Components (TopNavBar, ExteriorInteriorToggle, etc.)   │   │
│  │  ViewModels (MyCarViewModel : MviViewModel<I,S,E>)      │   │
│  │  Contracts (Intent, State, Effect)                      │   │
│  └─────────────────────────────────────────────────────────┘   │
├─────────────────────────────────────────────────────────────────┤
│                         DOMAIN LAYER                            │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Use Cases (GetDriveModesUseCase, SetMassageModeUseCase)│   │
│  │  Repository Interfaces (CarFunctionRepository)          │   │
│  │  Domain Models (DriveMode, MassageState, CarViewMode)   │   │
│  └─────────────────────────────────────────────────────────┘   │
├─────────────────────────────────────────────────────────────────┤
│                          DATA LAYER                             │
│  ┌──────────────────────┐  ┌──────────────────────┐           │
│  │  src/mock/           │  │  src/prod/            │           │
│  │  MockDataSource      │  │  ProdDataSource       │           │
│  │  MockDI Provider     │  │  ProdDI Provider      │           │
│  └──────────────────────┘  └──────────────────────┘           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  src/main/ — Shared interfaces & RepositoryImpl         │   │
│  └─────────────────────────────────────────────────────────┘   │
├─────────────────────────────────────────────────────────────────┤
│                       CORE / PLATFORM LAYER                     │
│  ┌────────────┐  ┌────────────┐  ┌──────────────────┐         │
│  │  MVI Base  │  │  OEM       │  │  Platform         │         │
│  │  Classes   │  │  Config    │  │  Capabilities     │         │
│  └────────────┘  └────────────┘  └──────────────────┘         │
├─────────────────────────────────────────────────────────────────┤
│                     UI LIBRARY MODULES                          │
│  ┌────────────────────┐  ┌───────────────────────┐            │
│  │  common-core-ui    │  │  audi-compose-ui      │            │
│  │  (OEM-agnostic)    │  │  (Audi brand theme)   │            │
│  └────────────────────┘  └───────────────────────┘            │
└─────────────────────────────────────────────────────────────────┘
```

---

## 3. MVI (Model-View-Intent) Architecture

### 3.1 Unidirectional Data Flow

```
User Action → Intent → ViewModel → State Update → Composable re-render
                          ↓
                     Side Effect → Toast / Navigation / etc.
```

### 3.2 Base Classes

| Class | Purpose |
|-------|---------|
| `MviIntent` | Marker interface for user actions |
| `MviState` | Marker interface for UI state |
| `MviEffect` | Marker interface for one-shot side effects |
| `MviViewModel<I,S,E>` | Base ViewModel with `dispatch()`, `handleIntent()`, `updateState()`, `sendEffect()` |

### 3.3 Contract Pattern

Each screen defines a `Contract` object grouping:
- **Intent** — Sealed interface of user actions
- **State** — Data class representing the full UI state
- **Effect** — Sealed interface of one-shot events

```kotlin
object MyCarContract {
    sealed interface Intent : MviIntent { ... }
    data class State(...) : MviState
    sealed interface Effect : MviEffect { ... }
}
```

---

## 4. Multi-OEM & Multi-Platform Strategy

### 4.1 OEM Abstraction

```kotlin
enum class OemType { AUDI /*, BMW, MERCEDES, ... */ }

data class OemConfig(
    val oem: OemType,
    val capabilities: PlatformCapabilities,
)
```

**Adding a new OEM:**
1. Add entry to `OemType` enum
2. Create `<oem>-compose-ui` module (e.g., `bmw-compose-ui`) — or reuse shared
3. Configure `OemConfig` in `AppContainer`

### 4.2 Platform Abstraction

```kotlin
interface PlatformCapabilities {
    val platformType: PlatformType      // SDV or CL8MIN
    val supports3DModel: Boolean
    val supportsExteriorInterior: Boolean
    val supportsMassage: Boolean
    val supportsAmbientLight: Boolean
    val supportsDriveSelect: Boolean
    val maxQuickAccessSlots: Int
}
```

| Capability | SDV | CL8min |
|-----------|-----|--------|
| 3D Model | Yes | No |
| Exterior/Interior | Yes | Yes |
| Massage | Yes | Yes |
| Ambient Light | Yes | Yes |
| Drive Select | Yes | Yes |
| Max Quick Access | 4 | 3 |

**UI components conditionally render based on capabilities:**
```kotlin
if (capabilities.supportsDriveSelect) {
    DriveSelectCarousel(...)
}
```

---

## 5. Build Variant Strategy

### 5.1 Flavor Dimension: `environment`

| Variant | Application ID Suffix | Data Source |
|---------|----------------------|-------------|
| `mockDebug` | `.mock.debug` | `MockCarFunctionDataSource` |
| `mockRelease` | `.mock` | `MockCarFunctionDataSource` |
| `prodDebug` | `.debug` | `ProdCarFunctionDataSource` |
| `prodRelease` | (none) | `ProdCarFunctionDataSource` |

### 5.2 Source Set Layout

```
app/src/
├── main/          ← Shared code (domain, repo impl, UI, MVI base)
│   └── java/com/example/carfunction/
│       ├── core/          ← MVI base, OEM config, platform
│       ├── data/          ← Interfaces, RepositoryImpl
│       ├── di/            ← AppContainer (shared)
│       ├── domain/        ← Models, Use Cases, Repository interface
│       └── presentation/  ← Screens, Components, ViewModels
├── mock/          ← Mock-only code
│   └── java/com/example/carfunction/
│       ├── data/datasource/MockCarFunctionDataSource.kt
│       └── di/DataSourceProvider.kt  ← returns MockDataSource
├── prod/          ← Prod-only code
│   └── java/com/example/carfunction/
│       ├── data/datasource/ProdCarFunctionDataSource.kt
│       └── di/DataSourceProvider.kt  ← returns ProdDataSource
```

### 5.3 How it works
- `DataSourceProvider` exists in both `src/mock/` and `src/prod/` with identical package/class name
- Gradle compiles only the matching flavor source set
- `AppContainer` calls `DataSourceProvider.provideDataSource()` — resolved at compile time

---

## 6. Module Structure

```
CarFunction/
├── app/                    ← Main application module
│   └── (MVI, Clean Arch, UI, Navigation)
├── common-core-ui/         ← OEM-agnostic UI foundation
│   └── (Design tokens, layout engine, widgets)
├── audi-compose-ui/        ← Audi brand-specific theming
│   └── (Audi widgets, tokens, theme)
├── docs/                   ← Architecture documentation
└── gradle/                 ← Build configuration
```

### Module Dependency Graph
```
app → audi-compose-ui → common-core-ui
app → common-core-ui
```

---

## 7. Screen Architecture (MyCar)

### 7.1 Layout Structure

```
┌─────────────────────────────────────────────────────────────┐
│  [🔍] [MyCar●] [Charging] [Driving Assistance] [D&E] [C&I] │  Top Nav Bar
├──────────────────────┬──────────────────────────────────────┤
│  ┌────┐┌────┐┌────┐┌┐│                                      │
│  │TSW ││LDW ││DW  ││+││                                      │
│  └────┘└────┘└────┘└┘│       3D Vehicle Visualization       │  Quick Access
│  ──────────────────── │                                      │
│   ◀  balanced  ▶     │         ○ ○ ○ ○ ○ ○                 │  Hotspots
│    Drive Select       │                                      │
│  ──────────────────── │                                      │
│  [OFF] [Active]       │                                      │
│   Massage Driver      │                          ┌──────────┐│
│  [OFF] [Mobility]     │                          │Ext●│ Int ││  Ext/Int Toggle
│   Massage Passenger   │                          └──────────┘│
│  ──────────────────── │                                      │
│  ◉Cold ◉Warm ◉Day ◉N │                                      │
│   Ambient light       │                                      │
│  [🟢] Dynamic Content │                                      │
└──────────────────────┴──────────────────────────────────────┘
```

### 7.2 Supported Navigation Tabs
1. **MyCar** (landing page) — Full implementation with MVI
2. **Charging** — Placeholder (ready for MVI implementation)
3. **Driving Assistance** — Placeholder
4. **Driving & Exterior** — Placeholder
5. **Comfort & Interior** — Placeholder

---

## 8. Data Flow

```
┌────────────────┐     ┌──────────────┐     ┌──────────────────┐
│  UI Composable │────▶│  ViewModel   │────▶│  Use Case        │
│  (dispatch)    │     │  (handleInt) │     │  (invoke)        │
└────────────────┘     └──────────────┘     └──────────────────┘
                              │                      │
                              ▼                      ▼
                       ┌──────────────┐     ┌──────────────────┐
                       │  State Flow  │     │  Repository      │
                       │  (UI update) │     │  (interface)     │
                       └──────────────┘     └──────────────────┘
                                                     │
                                                     ▼
                                            ┌──────────────────┐
                                            │  DataSource      │
                                            │  (mock or prod)  │
                                            └──────────────────┘
```

---

## 9. Extensibility Guide

### 9.1 Adding a New Screen
1. Create `<Screen>Contract.kt` (Intent, State, Effect)
2. Create `<Screen>ViewModel.kt` extending `MviViewModel`
3. Create `<Screen>Screen.kt` composable
4. Add route to `AppRoutes` and `CarFunctionNavHost`

### 9.2 Adding a New OEM
1. Add `OemType.<NEW_OEM>` enum entry
2. Create `<new-oem>-compose-ui` module with OEM theme
3. Set `OemConfig` in `AppContainer` at app startup

### 9.3 Adding a New Platform
1. Add `PlatformType.<NEW_PLATFORM>` enum entry
2. Create `<NewPlatform>Capabilities : PlatformCapabilities`
3. Call `AppContainer.configurePlatform(newCapabilities)` at startup

### 9.4 Adding a New Feature to Left Pane
1. Add domain model in `domain/model/`
2. Add repository method in `CarFunctionRepository`
3. Create use case in `domain/usecase/`
4. Add Intent + State field in `MyCarContract`
5. Handle intent in `MyCarViewModel`
6. Create component in `presentation/components/`
7. Add to `MyCarScreen` with platform capability check

---

## 10. Key Design Decisions

| Decision | Rationale |
|----------|-----------|
| MVI over MVVM | Unidirectional flow, single state truth, better testability |
| Manual DI over Hilt | Simpler for automotive deployment, no annotation processing |
| Build flavors for mock/prod | Compile-time source set swap, zero runtime cost |
| Platform capabilities interface | Feature toggles without code duplication |
| Contract pattern | Groups Intent/State/Effect per screen for discoverability |
| Shared RepositoryImpl | Single implementation delegates to variant-specific DataSource |

---

## 11. Compliance & Standards

- **MISRA-aligned** code structure (single responsibility, defensive patterns)
- **ASPICE** compliant layer separation (domain isolated from framework)
- **ISO 26262** consideration in platform capabilities (safety-critical feature gating)
- **CERT** secure coding practices in data handling
- **Kotlin coding conventions** enforced via Detekt + Spotless/ktlint
