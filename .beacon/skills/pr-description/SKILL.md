---
name: pr-description
description: Draft a pull request title and description from the current
  branch's diff against its base. Use when the user asks to open a PR, write a
  PR description, or summarize a branch for review.
---

# PR/MR Description Generator
**Source:** ACT-003 + sup8/04_merge_request_template.md + sup8/05_pr_mr_creation_template.md + Jira Comment Guidelines + Story Ticket Template + Story Example + Defect Tracking Guidelines + Functional Requirements Spec
**Covers files:** 04_merge_request_template.md, 05_pr_mr_creation_template.md, 02_jira_comment_guidelines.md, 01_story_ticket_template.md, 03_story_template_example.md, 01_defect_tracking_guidelines.md, 02_functional_requirements_specification.md

---

## Step 1: Gather Context

1. `git diff main...HEAD --stat` — changed files
2. `git log main...HEAD --oneline` — commit history
3. Read key changed files

---

## Step 2: PR Title

Format: `<Story ID> | <Short descriptive title>`

Examples:
- `US-042 | Implement Comfort Interior temperature slider`
- `BUG-118 | Fix process death state loss on Modes screen`
- `TECH-005 | Add MainDispatcherRule for ViewModel tests`

---

## Step 3: PR Description Template

```markdown
# Title: <Story ID> | <Short Title>

**Description:**
This merge request implements the required changes for **<feature/fix description>** associated with the referenced user story. The implementation updates **<affected module/screen/API>** and covers functional behavior, validations, error handling, and edge cases.

**User Story ID:** <ID>

**Video / Image Proof:** <link or attachment>

**Compliance Report:**
- [ ] UT Added
- [ ] IT Added
- [ ] Developer Testing

## Changes Summary
### What changed
- <bullet list>

### Why
- <motivation/requirement>

### How to test
1. <step-by-step>

### Impact
- Modules affected: <list>
- Risk: low/medium/high
```

---

## Jira Story Update Template

```
**Status Update:**
<Minimum 60 words. Describe progress, what was completed, test results.>

**Next Step:**
<What will be done next>

**Blocker:**
<Dependency or "None">
```

## Jira Bug Update Template

```
**Status Update:**
<Minimum 60 words. Current investigation progress.>

**Log Analysis:**
<Root cause, suspected cause, logs observed, API/UI/DB behavior>

**Log File Details:**
<Log files and folders checked>

**Next Step:**
<What will be done next>

**Next Action Required from (Optional):**
<Team for defect transfer>

**Blocker:**
<Dependency or "None">
```

---

## Story Ticket Template (for new stories)

### Story Tab
- **User Story** (MANDATORY): `As a [persona], I want to [action] so that [value]`
- **Scope / Requirement** (MANDATORY): Developer-perspective requirements, boundaries, edge cases
- **Assumptions** (OPTIONAL): Unconfirmed conditions to validate
- **Validation Steps** (OPTIONAL): Step-by-step verification procedure
- **References** (OPTIONAL): Figma, API spec, architecture diagrams

### Checklist Tab
- **Definition of Ready** (MANDATORY): All prerequisites met before sprint
- **Acceptance Criteria** (MANDATORY): 4-8 testable conditions
- **Definition of Done** (MANDATORY): Code review, tests passing, build clean, demo ready

---

## Commit Signature
All commits MUST include: `Generated with BEACON`
