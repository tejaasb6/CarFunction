---
name: commit-message
description: Write a clear Conventional Commits message from the staged changes.
  Use when the user asks to commit, write a commit message, or describe their
  changes.
---

# Commit Message Generator
**Source:** PR/MR templates + Jira traceability requirements

---

## Step 1: Analyze Staged Changes
```bash
git diff --cached --stat
git diff --cached
```

## Step 2: Generate Message

### Format (Conventional Commits)
```
<type>(<scope>): <short description>

<body — what and why, not how>

<footer>
```

### Types
| Type | Use When |
|---|---|
| `feat` | New feature |
| `fix` | Bug fix |
| `refactor` | Code change that neither fixes bug nor adds feature |
| `test` | Adding/updating tests |
| `docs` | Documentation only |
| `style` | Formatting (no code change) |
| `chore` | Build, CI, tooling changes |
| `perf` | Performance improvement |

### Scope
Use module or feature name: `comfort`, `modes`, `core-ui`, `navigation`, `viewmodel`, `test`

### Examples
```
feat(comfort): add temperature slider to interior screen

Implement ComfortInteriorScreen with temperature control using
MVI pattern. Adds GetTemperatureUseCase and ComfortRepository.

Refs: US-042
Generated with BEACON
```

```
fix(modes): prevent state loss on process death

Save activeModeId to SavedStateHandle. Restore in ViewModel init
to prevent blank screen on return from background kill.

Fixes: BUG-118
Generated with BEACON
```

```
test(comfort): add ViewModel unit tests for temperature flow

Cover initial state, LoadData intent, error handling, and effect
emissions using Turbine. Add FakeComfortRepository.

Refs: US-042
Generated with BEACON
```

### Rules
- Subject line: imperative mood, ≤72 chars, no period
- Body: wrap at 72 chars, explain what and why
- Footer: MUST include `Generated with BEACON`
- Reference Jira ID when available
