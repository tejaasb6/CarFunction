---
name: architecture-design
description: Android Clean Architecture design templates, module structure,
  component specification, directory rules, design review checklist, and
  architecture enforcement rules. Load when designing new modules, reviewing
  architecture decisions, creating component specs, or enforcing layer
  boundaries.
---

# Architecture & Design Guidelines
**Source:** SWE.2 (5 files) + KPIT-CV-20C (Directory Structure) + KPIT-CV-20S (Component Spec) + architecture/SKILL.md
**Covers files:** 01_architecture_structure_template.md, 02_component_specification_template.md, 03_design_review_checklist.md, 04_software_architecture_reference.md, 05_android_app_code_structure_template.md, KPIT-CV-20C, KPIT-CV-20S, skills/architecture/SKILL.md

---

## MODULE STRUCTURE (Multi-Module Clean Architecture)

### Standard Module Layout
```
:app                           → Application entry, Hilt setup, navigation host
:feature:<name>                → Feature modules (MyCar, Comfort, Modes, etc.)
  ├── data/                    → Repositories, DataSources, DTOs, Mappers
  │   ├── repository/
  │   ├── datasource/
  │   ├── model/dto/
  │   └── mapper/
  ├── domain/                  → Use cases, domain models, repository interfaces
  │   ├── usecase/
  │   ├── model/
  │   └── repository/          → Interfaces only
  └── presentation/            → ViewModels, UI State, Screens, Components
      ├── viewmodel/
      ├── model/               → UI state, Intent, Effect
      ├── screen/
      └── component/
:core:<name>                   → Shared infrastructure (:core:network, :core:designsystem, :core:common)
:common:<name>                 → Shared UI/utilities (:common:core-ui)
:domain                        → Shared domain (pure Kotlin library — NO Android imports)
:data                          → Shared data layer
```

### Dependency Rules (MANDATORY)
```
presentation → domain → data       (NEVER reverse)
:feature:A ✗→ :feature:B           (features NEVER depend on each other)
:domain → NO Android imports       (pure Kotlin only)
:feature:* → :core:*, :common:*    (shared dependencies OK)
:app → :feature:*, :core:*         (app wires everything)
```

---

## LAYER RULES

### Domain Layer (Pure Kotlin)
- **NO** Android framework imports (`android.*`, `androidx.*`)
- Repository interfaces defined here (implementations in data layer)
- Use cases: one public `operator fun invoke()` per use case
- Domain models: immutable `data class` or `sealed interface`
- No Hilt annotations — use constructor injection only

### Data Layer
- Repository implementations MUST be `internal`
- DTOs separate from domain models — mappers at boundary
- DataSource interfaces + implementations
- Room DAOs, Retrofit services, SharedPreferences wrappers

### Presentation Layer
- ViewModel extends `MviViewModel<Intent, State, Effect>`
- State: immutable `data class` with `ImmutableList` for collections
- Intent: `sealed interface` for user actions
- Effect: `sealed interface` for one-shot events (navigation, toast)
- Screen composable: connects ViewModel to Layout composable
- Layout composable: pure function (state + callbacks, no ViewModel)

---

## COMPONENT SPECIFICATION (SPEC.md)

Every module/component SHOULD have a `SPEC.md` with:

```markdown
# Component: <Name>
## Purpose
<What this component does>

## Public API / Interfaces
<Exposed classes, functions, interfaces>

## Dependencies
<What this component depends on>

## Error Handling
<How errors are handled, propagated>

## Threading Model
<Which dispatchers, scope ownership>

## State Management
<State flow, persistence strategy>

## Security Classification
<Is it security-sensitive? ISO 21434 relevant?>

## Reliability Requirements
<ASIL level, recovery strategy, safe state>
```

---

## DESIGN REVIEW CHECKLIST (Pre-Development Gate)

### Architecture Review
- [ ] Module boundaries follow Clean Architecture dependency rules
- [ ] Domain layer is pure Kotlin (no Android imports)
- [ ] Feature modules don't depend on other feature modules
- [ ] Repository implementations are `internal`
- [ ] Mappers exist at layer boundaries

### UI/UX Review
- [ ] Figma designs reviewed and approved
- [ ] Screen states defined: Loading, Content, Error, Empty
- [ ] Accessibility requirements identified
- [ ] Navigation flow documented
- [ ] Automotive HMI constraints considered (screen size, touch targets, glance-ability)

### MVI Contract Review
- [ ] Intent sealed interface covers all user actions
- [ ] State data class is immutable with sensible defaults
- [ ] Effects cover all one-shot events (navigation, toasts, dialogs)
- [ ] State machine transitions are deterministic

---

## DIRECTORY/FILE STRUCTURE RULES (KPIT-CV-20C)

### Naming Conventions
- Packages: `lowercase` (e.g., `com.audi.carfunction.feature.comfort`)
- Classes: `PascalCase` (e.g., `ComfortViewModel`)
- Files: match class name (e.g., `ComfortViewModel.kt`)
- Test files: `<ClassName>Test.kt` (e.g., `ComfortViewModelTest.kt`)
- Feature modules: `:feature:<kebab-case>` (e.g., `:feature:comfort-interior`)

### File Organization per Feature
```
feature/<name>/
  data/
    repository/<Name>RepositoryImpl.kt
    datasource/<Name>LocalDataSource.kt
    model/dto/<Name>Dto.kt
    mapper/<Name>DtoMapper.kt
  domain/
    usecase/Get<Name>UseCase.kt
    model/<Name>.kt
    repository/<Name>Repository.kt       ← Interface
  presentation/
    viewmodel/<Name>ViewModel.kt
    model/<Name>State.kt
    model/<Name>Intent.kt
    model/<Name>Effect.kt
    screen/<Name>Screen.kt               ← Screen (ViewModel wiring)
    screen/<Name>Content.kt              ← Layout (pure composable)
    component/<Name>Card.kt              ← Reusable UI components
```

---

## ARCHITECTURE ENFORCEMENT (Agent Rules)

### Boundary Violations to Flag
| Violation | Severity |
|---|---|
| Domain layer imports `android.*` or `androidx.*` | CRITICAL |
| Feature A depends on Feature B directly | CRITICAL |
| Presentation layer instantiates Repository directly (bypassing UseCase) | HIGH |
| Repository implementation is `public` (not `internal`) | HIGH |
| DTO used directly in presentation layer (no mapper) | MEDIUM |
| Domain model has Hilt annotations | MEDIUM |
| Use case has more than one public method | MEDIUM |

### Migration Path (Single → Multi-Module)
1. `app/.../domain/` → `:domain` module (pure Kotlin library)
2. `app/.../data/` → `:data` module
3. `app/.../core/` → `:core:network`, `:core:designsystem`, `:core:common`
4. `app/.../feature/<name>/` → `:feature:<name>` module
