---
name: compose-coding-guidelines
description: Jetpack Compose coding guidelines, state management, performance,
  side effects, modifiers, animations, stability diagnostics, and review
  checklist for Android automotive UI. Load when writing, reviewing, or
  refactoring Compose UI code.
---

# Jetpack Compose Coding Guidelines & Review Checklist
**Source:** ACT-021 + ACT-028 + Compose Sub-Skills (animations, modifier-and-layout-style, recomposition-performance, side-effects, stability-diagnostics, state-authoring, state-deferred-reads, state-hoisting, state-holder-ui-split) + compose/SKILL.md
**Covers files:** 03_compose_coding_guidelines.md, 10_compose_code_review_checklist.md, skills/compose/SKILL.md + 8 sub-skills

---

## MANDATORY RULES (Safety & Reliability)

### Recomposition Safety
- **NEVER** write to `MutableState` from within composition body (back-writing → infinite recomposition → UI lockup)
- Side effects (network, database, logging) MUST NEVER occur directly inside a Composable body
- All side effects MUST be scoped inside `LaunchedEffect`, `DisposableEffect`, or hoisted to ViewModel

### Process Death & State Persistence
- Critical user states MUST survive process death using `rememberSaveable` or `SavedStateHandle`
- Configuration changes must not lose user state

### State Hoisting (ASPICE L2)
- Reusable UI components MUST NOT accept a ViewModel as a parameter
- MUST accept raw immutable state + event callbacks (lambdas)

```kotlin
// COMPLIANT
@Composable
fun VehicleSpeedDashboard(
    speed: Int,
    onAcknowledge: () -> Unit,
    modifier: Modifier = Modifier
) { ... }

// VIOLATION
@Composable
fun VehicleSpeedDashboard(viewModel: SpeedViewModel) { ... }
```

---

## STATE MANAGEMENT

### State Authoring
- UI State = single immutable `data class` via `StateFlow`
- Use `MutableStateFlow.update {}` (atomic) — never `.value =`
- No bare `var` in composables — use `remember { mutableStateOf() }`
- No `mutableStateListOf` mutation outside snapshot — use immutable copies

### Stable Parameters
- Only pass **Stable** types into Composables
- Replace `List`/`Map`/`Set` with `kotlinx.collections.immutable.ImmutableList`/`ImmutableMap`/`ImmutableSet`
- Unstable params cause unnecessary recomposition every frame

### State-Holder/UI Split
- **Screen composable** = connects ViewModel to UI (calls `collectAsStateWithLifecycle()`)
- **Layout composable** = pure function receiving state + callbacks (no ViewModel)
- Always test Layout composables, not Screen composables

### Deferred State Reads
- High-frequency state (scroll offset, animation) → read inside block-form modifiers
- `Modifier.offset { IntOffset(x, y) }` or `Modifier.graphicsLayer { alpha = ... }`
- Avoids invalidating entire composition on every frame

---

## SIDE EFFECTS

### LaunchedEffect
- Provide correct key — effect restarts only when key changes
- `Unit` as key ONLY for effects that run once per composition lifetime
- Never use for state initialization — use ViewModel `init {}`
- Use `rememberUpdatedState` to capture latest lambda value in long-running effects
- Stale capture trap: if a callback changes, `LaunchedEffect(Unit)` won't see the update

### DisposableEffect
- For registering/unregistering listeners, callbacks, observers
- ALWAYS provide `onDispose {}` to clean up resources

### SideEffect
- For writing to non-Compose state on every successful recomposition (rare)

### Effect API Selection Guide
| Need | Use |
|---|---|
| One-time setup per key | `LaunchedEffect(key)` |
| Register + deregister | `DisposableEffect(key)` |
| Sync non-Compose state | `SideEffect` |
| Fire coroutine from callback | `rememberCoroutineScope()` |

### Event-Flag Anti-Pattern
- NEVER model events as `Boolean` in state (e.g., `showToast = true` → reset to `false`)
- Use `Channel<Effect>` + `receiveAsFlow()` in ViewModel instead

### collectAsStateWithLifecycle
- **ALWAYS** use over `collectAsState()` for ViewModel flows
- Ensures collection stops when app is backgrounded

---

## MODIFIERS & LAYOUT

### Modifier Parameter
- Every public reusable Composable MUST accept `modifier: Modifier = Modifier` as first optional param
- Chain from passed-in modifier — never create new `Modifier` inside

### Modifier Ordering (Outside-In)
- `padding` outside `background` = margin; inside = padding
- `clickable` before `padding` = larger touch target
- `clip` before `background` for rounded corners

### Layout Rules
- `LazyColumn`/`LazyRow` for lists — never `Column` with `forEach` for dynamic content
- Stable `key` for lazy items — avoid index-based keys
- Use `contentType` for heterogeneous lazy lists
- No `Modifier.fillMaxSize()` without layout justification

---

## ANIMATIONS

### API Selection
| Need | Use |
|---|---|
| Show/hide with transition | `AnimatedVisibility` |
| Single property animation | `animateFloatAsState` / `animateColorAsState` |
| Coordinated multi-property | `updateTransition` |
| Imperative control | `Animatable` in `LaunchedEffect` |
| Layout size change | `animateContentSize` |

### Performance
- `Modifier.graphicsLayer { }` for animations — avoids recomposition
- Never run animations in composition phase
- Use `drawBehind` for custom painting during animation

---

## RECOMPOSITION PERFORMANCE

### Stability Diagnostics
1. Enable compiler metrics: `-Pcompose.compiler.metrics=true`
2. Check `*_composables.txt` for unstable composables
3. Check `*_classes.txt` for unstable classes
4. Fix: `ImmutableList`, `@Stable`/`@Immutable`, lambda stabilization

### Performance Triage
| Symptom | Check First |
|---|---|
| Entire screen recomposes on every state change | Stability (unstable params) |
| Jank during scroll/animation | Deferred reads (value-form vs block-form modifier) |
| Infinite recomposition | Back-writing (writing state in composition) |

### Optimization
- `derivedStateOf {}` for computed values from other state
- Lambda stabilization: `remember { { viewModel.onAction(it) } }`
- `remember {}` with stable keys for expensive computations

---

## REVIEW CHECKLIST

### State & Architecture
- [ ] `collectAsStateWithLifecycle()` for ALL ViewModel flows
- [ ] ViewModels NOT passed into reusable composables
- [ ] UI state is immutable `data class`
- [ ] `ImmutableList`/`ImmutableMap` used in state (not `List`/`Map`)
- [ ] State hoisting: Screen vs Layout split
- [ ] Critical state survives process death
- [ ] No event-flag anti-pattern (`Boolean` events)

### Side Effects
- [ ] No side effects in composition body
- [ ] `LaunchedEffect` keys correct
- [ ] `DisposableEffect` has `onDispose` cleanup
- [ ] `rememberUpdatedState` used for callbacks in long-running effects

### Performance
- [ ] No back-writing state in composition
- [ ] High-frequency reads use deferred reads
- [ ] `derivedStateOf` for computed state
- [ ] Lazy list items have stable keys
- [ ] Lambdas are stable

### Modifiers
- [ ] `modifier: Modifier = Modifier` first optional param on public composables
- [ ] Modifier ordering semantically correct
- [ ] Animations use `graphicsLayer` (not recomposition)

### Verdict
- **Block:** Back-writing, missing lifecycle-aware collection, ViewModel in reusable composable
- **Request Changes:** Unstable state classes, missing deferred reads, event-flag pattern
- **Comment:** Modifier ordering, naming suggestions
