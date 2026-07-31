---
name: security-reliability-guidelines
description: Security (ISO 21434) and reliability (ISO 26262) coding guidelines
  for automotive Android apps. Load when reviewing security-sensitive code,
  handling external inputs, implementing cryptography, managing automotive HMI
  reliability, or performing reliability analysis.
---

# Security & Reliability Coding Guidelines
**Source:** KPIT-CV-20E + KPIT-CV-20F + KPIT-CV-20FA + security/SKILL.md + KPIT-CV-20G templates
**Covers files:** 05_security_coding_guidelines.md, 06_reliability_coding_guidelines.md, KPIT-CV-20E, KPIT-CV-20F, KPIT-CV-20FA, security/SKILL.md, KPIT-CV-20G-PROMPT template, KPIT-CV-20G-System_Context_Template.yaml

---

## PART 1: SECURITY (ISO/SAE 21434)

### When Security Review Applies
- Component processes/transmits/stores Confidential data
- Component exposes network, diagnostic, or bus interface
- Component participates in auth, software update, or secure boot
- Component marked `security_sensitive: true` in project YAML

### Universal Principles
- Defense in depth, least privilege, fail secure
- Economy of mechanism, complete mediation

### Input Validation (MANDATORY)
- ALL external/untrusted input validated before use
- SQL: Room DAO parameterized queries ONLY — no concatenation (CWE-89)
- `!!` on external data PROHIBITED (CWE-476)
- Nullable types from Java interop explicitly typed at boundary
- Validate: type, length, range, format, encoding, business rules

### Cryptography
- Android Keystore for key storage — never hardcode
- `SecureRandom` for security-relevant randomness
- Prohibited: MD5, SHA-1 (security), DES, 3DES, RC4
- Minimum: AES-256, RSA-2048, ECDSA P-256
- TLS 1.2+ for all network communication

### Data Protection
- Sensitive data MUST NOT appear in logs
- `Log.d` only debug builds — strip from release
- `char[]` not `String` for credentials — zero after use
- `EncryptedSharedPreferences` for sensitive data
- No sensitive data in Intent extras without encryption

### Secure Communication
- IPC: validate caller identity (UID/package)
- `exported=false` for internal-only components
- ContentProviders: enforce read/write permissions separately

### Error Handling (Security)
- Error messages MUST NOT reveal: stack traces, file paths, DB schemas
- Sanitize user data before logging (log injection prevention)

### Kotlin Security Rules
| Rule | CWE | Description |
|---|---|---|
| KOT-SEC-NULL-001 | CWE-476 | No `!!` on external data |
| KOT-SEC-COR-001 | CWE-400 | Coroutines lifecycle-scoped (unscoped = resource exhaustion) |
| KOT-SEC-INP-001 | CWE-89 | Room DAO parameterized queries only |

---

## PART 2: RELIABILITY (ISO 26262 / Automotive HMI)

### Posture
> *"Will this component survive 72 hours of continuous operation?"*

### HMI Reliability Rules
| Rule | Severity | Description |
|---|---|---|
| REL-HMI-001 | MANDATORY | No blocking on main thread. ANR after 5s. |
| REL-HMI-002 | MANDATORY | UI state survives config changes (ViewModel + StateFlow) |
| REL-HMI-003 | MANDATORY | Critical state survives process death (SavedStateHandle) |
| REL-HMI-004 | MANDATORY | Coroutine scopes lifecycle-bounded |
| REL-HMI-005 | REQUIRED | System service connections handle death (DeathRecipient) |
| REL-HMI-006 | REQUIRED | DisposableEffect.onDispose cleans up |
| REL-HMI-007 | REQUIRED | Resources released in onPause() not onDestroy() |
| REL-HMI-009 | REQUIRED | onTrimMemory() releases non-essential resources |

### Performance Rules
| Rule | Severity | Description |
|---|---|---|
| REL-PERF-001 | MANDATORY | Every allocation has matching deallocation (including error paths) |
| REL-PERF-002 | REQUIRED | No unbounded caches/lists — eviction policy required |
| REL-PERF-003 | REQUIRED | Minimize allocation in hot paths (remember {}) |
| REL-PERF-012 | REQUIRED | Closeable resources: `.use {}` or try/finally |

### Recovery Rules
- Define safe/degraded state for dependency failures
- Validate state restoration (no corrupt state)
- Mid-transaction failures: no intermediate state on shared resources
- Error conditions MUST be latched until explicitly cleared

### Reliability Anti-Patterns
| Anti-Pattern | Risk |
|---|---|
| Unbounded queue/cache | Memory exhaustion |
| Catch-and-ignore | Silent failures |
| Assuming onDestroy() called | Resource leak on kill |
| Timer retry without backoff | Thundering herd |
| Unlatched error | Error clears silently |
| Missing safe state | Undefined behavior on fault |

### Automotive Explainability
- Magic numbers PROHIBITED — named `const val` or enum with origin comment

### Reliability Analysis Finding Format
```
FINDING: <RULE-ID>
Severity    : MANDATORY | REQUIRED | ADVISORY | GAP
Analysis    : STATIC | SPEC-GAP | TEST-GAP | REASON | MEASURE
Location    : <file:line>
Scenario    : <concrete failure scenario>
Consequence : <system-level impact>
Test Coverage: <existing test or "None">
Recommendation: <specific actionable change>
```

---

## REVIEW CHECKLIST

### Security
- [ ] No `!!` on external data
- [ ] No SQL concatenation
- [ ] No sensitive data in logs
- [ ] No hardcoded secrets
- [ ] External input validated
- [ ] IPC callers validated
- [ ] Errors don't reveal internals

### Reliability
- [ ] No blocking on main thread
- [ ] UI state survives config changes + process death
- [ ] Coroutine scopes lifecycle-bounded
- [ ] Resources released in onPause()
- [ ] onTrimMemory() implemented
- [ ] No unbounded collections
- [ ] Error paths close resources
- [ ] Recovery/degraded state defined
- [ ] No magic numbers
