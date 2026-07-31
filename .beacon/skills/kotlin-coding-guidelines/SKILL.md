---
name: kotlin-coding-guidelines
description: Kotlin coding guidelines and review checklist for Android
  automotive projects using MVI + Clean Architecture, Coroutines, Flows, and
  sealed state modeling. Load when writing, reviewing, or refactoring Kotlin
  code, or when the user asks about Kotlin best practices, coroutine safety,
  state modeling, or code review.
---

# Kotlin Coding Guidelines & Review Checklist
**Source:** ACT-020 + ACT-027 + Kotlin Sub-Skills (control-flow, coroutines-best-practices, coroutines-structured-concurrency, flow-state-event-modeling, flows-best-practices) + KPIT-CV-20B Kotlin rules
**Covers files:** 02_kotlin_coding_guidelines.md, 09_kotlin_code_review_checklist.md, skills/kotlin/SKILL.md + 5 sub-skills, 02_agent_ruleset.md (Kotlin sections)

---

## MANDATORY RULES (Safety & Reliability)

### Null Safety
- `!!` operator is **STRICTLY PROHIBITED** in production code
- Use `requireNotNull()` or `checkNotNull()` with clear exception messages
- Nullable types from Java interop must be explicitly typed at the boundary

### Coroutine Safety
- `GlobalScope` and `runBlocking` **PROHIBITED** in application code
- All coroutines MUST be scoped to lifecycle-aware components (`viewModelScope`, `lifecycleScope`)
- **NEVER** hardcode `Dispatchers.IO`/`Dispatchers.Default` — inject via constructor
- `Dispatchers.IO` MUST be used for all disk and network operations
- Do NOT swallow `CancellationException` — rethrow if catching generic `Exception`
- Handle exceptions using `CoroutineExceptionHandler` or `try/catch` in `async`

### Structured Concurrency
- Every `launch`/`async` must have a parent scope cancelled on lifecycle end
- Never use `SupervisorJob()` unless you need sibling-failure isolation
- `withContext(NonCancellable)` only for cleanup side-effects (logging, analytics)
- Never store a `CoroutineScope` reference in a class that outlives its owner
- Never launch coroutines in `init {}` blocks — use explicit trigger methods

### Exhaustive State Handling
- `when` expressions on `sealed class`/`sealed interface` MUST NOT use `else` branch
- All possible states must be explicitly handled

### State Immutability (MVI)
- UI State MUST be modeled as immutable `data class` objects
- Mutations MUST use `MutableStateFlow.update {}` (atomic)
- NEVER use non-atomic `.value =` on MutableStateFlow
- Prefer `val` over `var` universally
- Expose read-only collections (`List`, `Set`) — never `MutableList`/`MutableSet`

---

## DATA MODELING

### Sealed Interfaces
- Prefer `sealed interface` over `sealed class` for Intent/Action and UI States
- Use `sealed class` only when shared state in base class is needed

```kotlin
sealed interface VehicleState {
    data object Loading : VehicleState
    data class Active(val speed: Int) : VehicleState
    data class Error(val cause: Throwable) : VehicleState
}
```

### Value Classes
- Use `@JvmInline value class` for wrapping primitive IDs

```kotlin
@JvmInline value class UserId(val id: String)
```

---

## COROUTINES BEST PRACTICES

### Scope Ownership
- The class that creates a scope MUST cancel it
- ViewModels → `viewModelScope` (auto-cancelled on `onCleared()`)
- Activities/Fragments → `lifecycleScope` (auto-cancelled on `onDestroy()`)
- Custom scopes → cancel in `close()`/`dispose()`/`onCleared()`

### Main-Safety
- Functions that switch dispatchers should be marked `suspend` and use `withContext()`
- Callers should never need to know which dispatcher a function uses

### Cancellation Cooperation
- Long-running loops must check `isActive` or use `ensureActive()`
- Never wrap `CancellationException` in another exception type

---

## FLOW & STATE/EVENT MODELING

### Flow Collection
- UI layer MUST use `collectAsStateWithLifecycle()` for ViewModel flows
- Use `stateIn(SharingStarted.WhileSubscribed(5000))` for UI-consumed flows
- Never use `stateIn(SharingStarted.Eagerly)` unless the flow must survive subscriber absence

### State vs Events
- **State** → `StateFlow` — current state, always has a value, conflated
- **Events** → `Channel<Effect>` with `receiveAsFlow()` — one-shot side effects
- **NEVER** use `SharedFlow` for one-shot events in MVI
- `SharedFlow(replay=0)` drops events emitted before subscriber attaches

### Flow Operators
- `.collect {}` in `launch` MUST include exception handling
- Prefer `conflate()` or `collectLatest` for high-frequency updates
- `callbackFlow` MUST have `awaitClose {}` to prevent leaks
- Never put side effects in `map`/`filter`/`combine` operators
- `.catch {}` only catches upstream exceptions, not downstream

### Atomic Updates
- Use `MutableStateFlow.update {}` — NOT `.value =`
- `.value =` is non-atomic and loses concurrent updates

---

## CONTROL FLOW
- Prefer `when` over `if-else` chains for 3+ branches
- Use `let`/`run`/`also`/`apply` idiomatically — max 2 nested scope functions
- Avoid `it` in nested lambdas — use named parameters

---

## VISIBILITY & ENCAPSULATION (ASPICE L2)
- Repository implementations MUST be `internal`
- Classes not exposed outside their module MUST be `internal`
- Enforce strict module encapsulation

---

## DOCUMENTATION (KDoc)
```kotlin
/**
 * Brief description.
 *
 * @param seed The initial seed.
 * @return A sequence of numbers.
 *
 * @Traceability
 * - Requirement ID: SRS-REQ-<Module>-XX
 * - UI Requirement / Figma Design ID: [ID or Link]
 */
```

## FILE HEADER (Mandatory)
```kotlin
/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */
```

---

## REVIEW CHECKLIST

### Pre-Review (Automated)
- [ ] KTLint & Detekt green (no `!!`, no `GlobalScope`)
- [ ] FOSSA scan green
- [ ] Unit tests passing, ≥85% coverage

### Safety & Concurrency
- [ ] All coroutines lifecycle-scoped (no `GlobalScope`, no `runBlocking`)
- [ ] Dispatchers injected, not hardcoded
- [ ] `CancellationException` preserved and rethrown
- [ ] All `when` on sealed types exhaustive (no `else`)
- [ ] No stored scope references outliving owners
- [ ] No coroutines launched in `init {}`

### State & Architecture
- [ ] UI States immutable `data class`
- [ ] State updates use `.update {}` (atomic)
- [ ] Repositories marked `internal`
- [ ] Flows collected with `collectAsStateWithLifecycle()`
- [ ] `Channel` used for one-shot events (not `SharedFlow`)

### Traceability
- [ ] PR includes Jira Story/Defect ID
- [ ] `@Traceability` KDoc tags on new/modified code
- [ ] Copyright header present
