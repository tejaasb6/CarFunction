---
name: android-automotive-guidelines
description: Android platform coding guidelines for automotive apps including
  lifecycle management, context usage, memory optimization, Hilt DI, Compose
  Navigation, and performance auditing. Load when writing Android-specific code,
  debugging ANRs, or optimizing automotive head-unit performance.
---

# Android Platform Coding Guidelines
**Source:** ACT-022 + skills/android/SKILL.md + performance-audit.md + testing.md
**Covers files:** 04_android_coding_guidelines.md, skills/android/SKILL.md, performance-audit.md, testing.md

---

## LIFECYCLE MANAGEMENT

### Context Usage
- `applicationContext` for singletons, databases, SharedPreferences
- Activity context ONLY for UI operations (dialogs, toasts, theme)
- NEVER store Activity context in ViewModel/Repository — memory leak
- Inject `@ApplicationContext context: Context` via Hilt

### Lifecycle-Scoped Coroutines
- ViewModel: `viewModelScope` (cancelled on `onCleared()`)
- Activity/Fragment: `lifecycleScope` (cancelled on `onDestroy()`)
- Use `repeatOnLifecycle(Lifecycle.State.STARTED)` for flow collection in Activities
- NEVER use `GlobalScope`

### Configuration Changes & Process Death
- ViewModel survives config changes — store transient state there
- `rememberSaveable` for Compose state surviving process death
- `SavedStateHandle` in ViewModel for critical navigation state

---

## MEMORY & PERFORMANCE

### ANR Prevention (Safety-Critical)
- Main thread MUST NOT be blocked >5 seconds (ANR = safety hazard on head-unit)
- ALL disk I/O, network, database on `Dispatchers.IO`
- Use `StrictMode` in debug builds
- Target <16ms per frame

### Memory
- Release bitmaps in `onStop()`/`onCleared()`
- Coil/Glide with caching for images
- `onTrimMemory()` MUST release non-essential resources
- No unbounded caches/lists

### Performance Audit
- [ ] No `Thread.sleep()` on main thread
- [ ] No synchronous network calls
- [ ] Lazy initialization (`by lazy {}`) for heavy objects
- [ ] Stable keys in LazyColumn items
- [ ] Image loading uses caching library
- [ ] Startup profiled with Android Profiler

---

## HILT DEPENDENCY INJECTION

### Scoping
- `@Singleton` — app-wide (repositories, API clients)
- `@ViewModelScoped` — ViewModel-specific
- `@ActivityScoped` / `@FragmentScoped` — only when needed
- Never manual singletons — always Hilt

### Module Organization
- One `@Module` per feature domain
- `@Binds` over `@Provides` for interface → implementation
- No business logic in DI modules

---

## NAVIGATION (Compose Navigation)
- Routes as sealed class/enum — never raw strings
- Only primitive arguments via navigation
- Complex state in ViewModel `SavedStateHandle`
- Deep links registered in AndroidManifest.xml

---

## BROADCAST & SERVICES
- Flow-based event buses over global broadcasts
- Foreground services MUST show notification
- `WorkManager` for guaranteed background work

---

## REVIEW CHECKLIST
- [ ] No Activity context in ViewModel/Repository
- [ ] All coroutines lifecycle-scoped
- [ ] No main-thread I/O
- [ ] Hilt modules properly scoped
- [ ] Navigation routes type-safe
- [ ] Process death survival verified
- [ ] Performance profiled on target device
- [ ] `onTrimMemory()` implemented
