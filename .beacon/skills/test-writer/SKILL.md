---
name: test-writer
description: Generate or extend unit tests for a file, function, or recent
  change. Use when the user asks to write tests, add coverage, or test a
  specific piece of code.
---

# Android JVM Testing — Complete Test Writer
**Source:** SWE.4 (3 files) + SWE.5 (1 file) + SWE.6 (4 files) + local-testing SKILL + 6 sub-skills + unit test checklists
**Covers files:** 01_unit_test_checklist.md, 02_jvm_unit_testing_framework.md, 03_unit_test_code_review_checklist.md, 01_integration_testing_framework.md, 01_instrumentation_test_checklist.md, 02_screenshot_testing_checklist.md, 03_ui_presentation_testing.md, 04_instrumentation_test_code_review_checklist.md, local-testing/SKILL.md + sub-skill-data.md + sub-skill-domain.md + sub-skill-integration.md + sub-skill-presentation-ui.md + sub-skill-presentation-viewmodel.md + sub-skill-utilities.md

---

## ROUTING: Pick the Right Sub-Skill

| Task | Strategy |
|---|---|
| ViewModel tests | Presentation-ViewModel |
| Compose UI / Screenshot tests | Presentation-UI |
| Domain use case tests | Domain |
| Data layer (Repository, Mapper, DataSource) | Data |
| Integration / DI graph tests | Integration |
| Test utilities (rules, fakes) | Utilities |

---

## STEP 1: Analyze Existing Setup

Before writing ANY test, check the project's stack:
- DI: Hilt | Test framework: JUnit 4/5 | Mocking: MockK/fakes | Assertions: Truth/AssertK
- Flow testing: Turbine | Robolectric: yes/no | Version catalog: `libs.versions.toml`

> ALWAYS use project's existing libraries. Never hardcode versions.

---

## TESTING PYRAMID

```
60% Unit Tests     — ViewModel, UseCase, Repository, Mapper
25% UI Tests       — Compose rendering, screenshots
15% Integration    — DI graph, navigation, cross-layer
```

---

## VIEWMODEL TESTS (sub-skill-presentation-viewmodel)

### Setup
```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class MyViewModelTest {
    @get:Rule val mainRule = MainDispatcherRule()
    private lateinit var fakeRepo: FakeMyRepository
    private lateinit var viewModel: MyViewModel

    @Before fun setup() {
        fakeRepo = FakeMyRepository()
        viewModel = MyViewModel(fakeRepo) // AFTER mocks — init runs immediately
    }
}
```

### What to Test
- Initial state after creation
- Intent → state transition
- Effect emissions (Turbine)
- Error handling (repo failure → error state)
- Loading → Success/Error transitions

### Patterns
```kotlin
@Test fun `dispatch LoadData emits Success`() = runTest {
    fakeRepo.setResult(listOf(item1))
    viewModel.dispatch(MyIntent.LoadData)
    assertThat(viewModel.state.value.items).containsExactly(item1)
}

@Test fun `dispatch Submit emits NavigateBack effect`() = runTest {
    viewModel.effects.test {
        viewModel.dispatch(MyIntent.Submit)
        assertThat(awaitItem()).isEqualTo(MyEffect.NavigateBack)
    }
}
```

---

## DOMAIN TESTS (sub-skill-domain)

```kotlin
class GetSpeedUseCaseTest {
    private val fakeRepo = FakeVehicleRepository()
    private val useCase = GetSpeedUseCase(fakeRepo)

    @Test fun `returns speed from repo`() = runTest {
        fakeRepo.setSpeed(120)
        assertThat(useCase()).isEqualTo(120)
    }
}
```
- One `invoke()` per use case — test it
- Passthrough use cases (no logic) = no test needed
- Prefer fakes over mocks

---

## DATA LAYER TESTS (sub-skill-data)

### Mapper (pure — no mocks)
```kotlin
@Test fun `maps valid DTO`() {
    val result = mapper.toDomain(VehicleDto(id = "1", speed = 120))
    assertThat(result.speed).isEqualTo(120)
}
@Test fun `maps null speed to default`() {
    val result = mapper.toDomain(VehicleDto(id = "1", speed = null))
    assertThat(result.speed).isEqualTo(0)
}
```

### Repository — test DataSource + Mapper coordination, error propagation
### DataSource — Robolectric if Android Context needed
### DAO — in-memory Room database

---

## COMPOSE UI TESTS (sub-skill-presentation-ui)

### Rules
- Test **Screen composables** (pure functions), NOT Route composables
- Semantics-first: `onNodeWithText` > `contentDescription` > `testTag` (last resort)

```kotlin
@Test fun `displays speed`() {
    composeTestRule.setContent {
        SpeedScreen(state = SpeedState(speed = 120), onIntent = {})
    }
    composeTestRule.onNodeWithText("120").assertIsDisplayed()
}
```

### Screenshot Tests (Roborazzi)
- Baseline for: default, loading, error, empty, edge-case states
- Use `@GraphicsMode(GraphicsMode.Mode.NATIVE)` with Robolectric
- One screenshot per state variant

### Dispatcher Alignment (CRITICAL)
- When `MainDispatcherRule` is present, `runTest` MUST receive `mainRule.dispatcher`
- Two separate schedulers = guaranteed flake

---

## INTEGRATION TESTS (sub-skill-integration / SWE.5)

### Hilt DI Graph Test
```kotlin
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [34])
class DependencyGraphTest {
    @get:Rule val hiltRule = HiltAndroidRule(this)
    @Inject lateinit var repo: MyRepository

    @Test fun `graph resolves repository`() {
        hiltRule.inject()
        assertThat(repo).isNotNull()
    }
}
```

### Navigation Test — verify routes resolve, deep links work
### Cross-Layer — verify ViewModel → UseCase → Repository → DataSource chain

---

## INSTRUMENTATION TESTS (SWE.6)

### Rules
- No `Thread.sleep()` — use `IdlingResource` or Compose `waitUntil`
- Setup/teardown: clean database, reset state between tests
- Device config: specify screen density, locale if relevant
- Flake prevention: retry rules, stable selectors, deterministic data

### Instrumentation Review Checklist
- [ ] No `Thread.sleep()` in any test
- [ ] Proper setup/teardown (data, state, permissions)
- [ ] Tests independent (no order dependency)
- [ ] Stable selectors (semantics, not view hierarchy)
- [ ] Device configuration documented
- [ ] Flaky tests quarantined and tracked

---

## UNIVERSAL RULES

1. `runTest` for ALL coroutine tests
2. Turbine for ALL Flow/StateFlow/Channel assertions
3. `MainDispatcherRule` before ViewModel creation
4. Create ViewModel AFTER mocks (init runs immediately)
5. `UnconfinedTestDispatcher` default; `StandardTestDispatcher` for time-sensitive
6. NEVER `Thread.sleep` or `delay` — use `advanceUntilIdle()`
7. One behavior per test method
8. Prefer fakes over mocks
9. Test names: `` `dispatch X emits Y state` ``
10. `@Config(sdk = [34])` for Robolectric
11. `runOnIdle` for Compose assertions after recomposition

---

## WHAT NOT TO TEST
- Data classes (generated equals/hashCode/copy)
- Passthrough use cases
- Route composables (test Screen instead)
- Android framework code (Activity.onCreate)
- Third-party library behavior
- Private functions (test through public API)
- DI config (test via integration)

---

## FAKES vs MOCKS
| Fakes | Mocks |
|---|---|
| Simple interface (1-3 methods) | Many methods |
| Simulate emissions over time | Need `coVerify` |
| Closer to production | Verify interaction order |
| Shared across tests | Single interaction focus |

---

## DISPATCHER INJECTION
Only inject when: class dispatches to non-main AND is directly tested.
ViewModels with only `viewModelScope` → use `MainDispatcherRule`.

---

## SavedStateHandle
```kotlin
val ssh = SavedStateHandle(mapOf("id" to "123"))
val vm = DetailViewModel(ssh, fakeRepo)
```

---

## NEW FEATURE CHECKLIST
- [ ] ViewModel test — initial state, intents, transitions, effects, errors
- [ ] Screen test — rendering, interaction, empty/loading/error
- [ ] Screenshot — baselines for all state variants
- [ ] Use case tests — every use case with logic
- [ ] Mapper — valid, empty, null, edge cases
- [ ] Repository — coordination, error propagation
- [ ] DataSource — Robolectric if Context needed
- [ ] DAO — in-memory DB for complex queries
- [ ] MainDispatcherRule used
- [ ] State restoration verified in UI tests

## TEST QUALITY CHECKLIST
- [ ] No `Thread.sleep`/`delay`
- [ ] No hardcoded versions
- [ ] No mocks where fakes suffice
- [ ] No testing private details
- [ ] Single assertion focus
- [ ] Descriptive test names
- [ ] CI runs without device/emulator
