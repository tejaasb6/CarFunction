---
name: skill-gate
description: ALWAYS load this skill FIRST before any other skill. It requires
  the agent to ask the user for explicit permission before loading any skill,
  explaining what each skill does and why it is relevant. Load on EVERY request,
  before any other skill activation.
---

# Skill Gate — Ask Before Using Any Skill

> **This skill overrides the default auto-load behavior.** Before loading ANY other skill, you MUST ask the user for explicit permission first.

---

## MANDATORY BEHAVIOR

### On Every New Session / Every Request:

1. **DO NOT auto-load any skill.** Even if the user's request clearly matches a skill (e.g., "review my code" → `code-reviewer`), do NOT load it silently.

2. **Identify which skill(s) would be relevant** to the user's request.

3. **Present the skill(s) to the user** with:
   - Skill name
   - What it does (1-2 sentence summary)
   - Which documentation files it's built from
   - Why it's relevant to the current request
   - What rules/checks it will apply

4. **Ask for explicit confirmation** before proceeding.

5. **Only after the user says yes**, load the skill via the `Skill` tool and proceed.

---

## PRESENTATION FORMAT

When a user makes a request that matches one or more skills, respond like this:

```
Before I proceed, I'd like to use the following skill(s) for this task:

📋 **Skill: <skill-name>**
   • What it does: <1-2 sentence description>
   • Built from: <source documentation files>
   • Why it's relevant: <how it applies to your current request>
   • Rules it will apply: <key rules/checks — 3-5 bullet points>

Would you like me to:
  (a) Use this skill ✅
  (b) Skip the skill and proceed without it ⏭️
  (c) Show me the full skill content first 📖
```

If multiple skills are relevant, list ALL of them and let the user choose which to activate:

```
I identified 3 relevant skills for this task:

1. 📋 **kotlin-coding-guidelines** — <summary>
2. 📋 **compose-coding-guidelines** — <summary>  
3. 📋 **code-reviewer** — <summary>

Which would you like me to use? (all / 1,2 / none)
```

---

## SKILL QUICK-REFERENCE (for explanations)

| Skill | What It Does | Key Rules |
|---|---|---|
| `kotlin-coding-guidelines` | Enforces Kotlin best practices: coroutine safety, sealed state modeling, immutability, Flow patterns | No `!!`, no `GlobalScope`, `update {}` for StateFlow, exhaustive `when`, injected dispatchers |
| `compose-coding-guidelines` | Enforces Compose UI rules: state hoisting, recomposition safety, side effects, modifiers, animations | No back-writing, `collectAsStateWithLifecycle()`, `ImmutableList`, no ViewModel in reusable composables |
| `android-automotive-guidelines` | Android platform rules: lifecycle, memory, Hilt DI, navigation, ANR prevention | No Activity context in ViewModel, lifecycle-scoped coroutines, `onTrimMemory()`, process death survival |
| `architecture-design` | Clean Architecture enforcement: module structure, layer boundaries, component specs | Domain = pure Kotlin, no cross-feature deps, `internal` repos, mappers at boundaries |
| `code-reviewer` | Full code review with max 14 findings, severity-ranked, diff-scope only | Architecture + Kotlin + Compose + Android + Security + Reliability checks |
| `security-reliability-guidelines` | ISO 21434 security + ISO 26262 reliability for automotive HMI | Input validation, no SQL concat, no hardcoded keys, no main-thread blocking, process death survival |
| `test-writer` | Generate tests: ViewModel, UseCase, Mapper, Repository, Compose UI, Integration, Screenshots | Turbine for Flows, fakes over mocks, MainDispatcherRule, 60/25/15 pyramid |
| `pr-description` | Generate PR title + description + Jira update templates | Story template, MR template, compliance report, defect tracking format |
| `commit-message` | Generate Conventional Commits message from staged changes | feat/fix/refactor/test types, Jira reference, `Generated with BEACON` footer |

---

## EXAMPLES

### Example 1: User says "review my code"

```
Before I proceed, I'd like to use the following skill:

📋 **Skill: code-reviewer**
   • What it does: Performs a Senior Android Code Review with max 14 
     findings, severity-ranked (CRITICAL > HIGH > MEDIUM), diff-scope only.
   • Built from: CodeReviewAgent v3.1, KPIT-CV-20A/20B Agent Ruleset, 
     all 5 skill orchestrators (Kotlin, Compose, Android, Architecture, Security)
   • Why it's relevant: You asked for a code review — this skill applies 
     the full enterprise review checklist covering architecture, coroutines, 
     Compose state, security, and reliability.
   • Rules it will apply:
     - Architecture boundary enforcement (domain purity, dependency direction)
     - Kotlin coroutine safety (no GlobalScope, injected dispatchers)
     - Compose stability (ImmutableList, state hoisting, deferred reads)
     - Security (no !! on external data, no SQL concat, no secrets in logs)
     - Reliability (no main-thread blocking, process death survival)

Would you like me to use this skill? (yes / no / show full content)
```

### Example 2: User says "write tests for ComfortViewModel"

```
Before I proceed, I'd like to use the following skill:

📋 **Skill: test-writer**
   • What it does: Generates layer-specific tests following the testing 
     pyramid (60% unit / 25% UI / 15% integration). For ViewModels, it 
     uses Turbine for Flow assertions, fakes over mocks, and MainDispatcherRule.
   • Built from: SWE.4 Unit Verification (3 files), SWE.5 Integration (1 file), 
     SWE.6 Qualification (4 files), Local Testing Skill + 6 sub-skills
   • Why it's relevant: You want ViewModel tests — this skill ensures proper 
     setup (create VM after mocks), Turbine for effects, and correct dispatcher handling.
   • Rules it will apply:
     - MainDispatcherRule before ViewModel creation
     - Turbine for StateFlow/Channel assertions
     - Fakes preferred over mocks
     - One behavior per test, descriptive names
     - runTest for all coroutine tests

Would you like me to use this skill? (yes / no / show full content)
```

---

## RULES

1. **NEVER skip the ask.** Even for simple requests. Even if the user previously approved the same skill.
2. **Each new session starts fresh.** No memory of previous approvals.
3. **If user says "skip" or "no"**, proceed with general knowledge — do NOT load the skill.
4. **If user says "always use" or "don't ask again"**, note it but still explain briefly (1 line) which skill you're using for transparency.
5. **If no skill is relevant**, proceed normally without this gate.
