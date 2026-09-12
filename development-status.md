# Compliance Checker — Development Status

## Current Phase

**Phase 1 — Core Foundation**

Status: **Completed**

---

## Phase 1 Objective

Establish the initial domain model and prove the basic flow:

```text
Evidence
   ↓
Signal
   ↓
Rule
   ↓
Rule Engine
   ↓
Assessment
```

---

## Implemented

### Project foundation

* Java 21
* Spring Boot
* Maven
* Basic application configuration

### Domain model

Implemented:

* `Signal`
* `SignalValue`
* `Evidence`
* `SignalEvidence`
* `RuleResult`

### Rule engine

Implemented:

* `Rule` interface
* `RuleEngine`
* Initial `HealthcareDataRule`

The healthcare rule is intentionally hard-coded and exists only as a Phase 1 proof of concept.

### Assessment

Implemented:

* `AssessmentStatus`
* `AssessmentResult`
* `AssessmentService`

Current assessment states:

```text
POTENTIALLY_APPLICABLE
NOT_APPLICABLE
UNKNOWN
```

### Testing

Added a basic rule-engine test covering:

```text
data.health_data = TRUE
        ↓
HealthcareDataRule
        ↓
TRUE
```

---

# Important Architectural Decision

The Phase 1 implementation contains a hard-coded rule:

```text
HealthcareDataRule
```

This is **temporary**.

We do not want regulatory knowledge implemented as Java classes.

The target architecture is:

```text
Requirement Pack
       ↓
Rule Definition
       ↓
Generic Rule Engine
       ↓
Assessment
```

Regulatory knowledge should be stored as versioned, source-backed configuration.

---

# Phase 2 — Next

## Objective

Convert the current hard-coded rule implementation into a **configuration-driven rule engine**.

The engine should be capable of evaluating rules without knowing whether they belong to HIPAA, GDPR, FedRAMP, DORA, etc.

---

## Phase 2 Tasks

### 1. Create RuleDefinition

Introduce a model representing a configuration-driven rule.

Example:

```json
{
  "id": "hipaa-health-data",
  "requirementId": "HIPAA",
  "condition": {
    "signal": "data.health_data",
    "operator": "EQUALS",
    "value": "TRUE"
  },
  "result": "POTENTIALLY_APPLICABLE"
}
```

---

### 2. Create RuleRepository

Responsible for loading rule definitions from configuration.

Initial source:

```text
src/main/resources/packs/
```

---

### 3. Create first requirement pack

Start with:

```text
packs/
└── hipaa/
    ├── requirement.json
    └── rules.json
```

Do not attempt to model all of HIPAA yet.

The first goal is simply to prove that HIPAA can be represented as configuration.

---

### 4. Implement generic operators

Initially support a small set:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

More complex operators should be added only when required.

---

### 5. Refactor RuleEngine

Current:

```text
HealthcareDataRule
       ↓
RuleEngine
```

Target:

```text
RuleDefinition
       ↓
RuleRepository
       ↓
RuleEngine
```

---

### 6. Remove hard-coded regulatory rule

Once the JSON-driven rule works, remove:

```text
HealthcareDataRule
```

from the production flow.

---

### 7. Add tests

At minimum:

```text
TRUE
FALSE
UNKNOWN
Missing signal
Invalid rule
Multiple rules
```

---

# Phase 2 Definition of Done

Phase 2 is complete when the following works:

```text
POST /api/v1/assessments
        ↓
Signals
        ↓
Rules loaded from JSON
        ↓
Generic Rule Engine
        ↓
Assessment
```

and the engine does not contain HIPAA-specific evaluation logic.

The following should be possible:

```text
Add / modify a rule
        ↓
Modify JSON
        ↓
Run tests
        ↓
No Java code change
```

---

# After Phase 2

The next major phase will introduce:

```text
Evidence
   ↓
Confidence
   ↓
Missing Information
   ↓
Discovery Questions
   ↓
Conflict Detection
```

After that, we will build the first complete compliance knowledge pack.

Initial proving-ground packs:

1. HIPAA
2. GDPR
3. FedRAMP
4. DORA
5. Data Residency
6. IP Allowlisting

---

# Development Principle

Every phase should leave the repository:

* Buildable
* Testable
* Runnable
* Demonstrable

Avoid implementing the entire target architecture upfront.

Prefer:

```text
Small implementation
      ↓
Test
      ↓
Validate architecture
      ↓
Refactor
      ↓
Next capability
```

over building large amounts of unused infrastructure.

---

# Current Next Action

Start **Phase 2** by implementing:

```text
RuleDefinition
RuleRepository
rules.json
Generic RuleEvaluator
HIPAA requirement pack
Tests
```

Do not implement the UI, database, AI, external regulatory ingestion, or complete HIPAA knowledge model yet.
