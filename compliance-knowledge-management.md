# Compliance Knowledge Management

## Purpose

The Compliance Checker uses a **configuration-driven compliance knowledge model**.

The core application is responsible for evaluating rules, managing signals and evidence, calculating applicability, identifying missing information, and producing assessments.

It should **not contain regulatory knowledge hard-coded in Java**.

Regulatory knowledge such as HIPAA, GDPR, FedRAMP, DORA, Data Residency, and other requirements will be maintained as versioned **Requirement Packs**.

The objective is to keep the compliance engine stable while allowing regulatory knowledge to evolve independently.

---

## Core Principle

> **The engine executes compliance knowledge; it does not define the compliance knowledge.**

The architecture is:

```text
Official Regulatory Sources
          │
          ▼
Compliance / Regulatory Review
          │
          ▼
Requirement Pack
          │
          ├── Requirement
          ├── Signals
          ├── Concepts
          ├── Rules
          ├── Obligations
          ├── Questions
          └── Sources
          │
          ▼
Generic Rule Engine
          │
          ▼
Assessment
```

---

# Who Creates and Maintains the Rules?

Initially, regulatory knowledge will be created and maintained manually by a combination of:

* Compliance / privacy / security SMEs
* Regulatory analysts
* Engineers familiar with the domain
* Product or security compliance owners

The system should not assume that a developer alone is the authority on regulatory interpretation.

The recommended ownership model is:

| Activity                           | Primary owner                |
| ---------------------------------- | ---------------------------- |
| Identify authoritative source      | Compliance / Regulatory SME  |
| Interpret applicability            | Compliance SME + Engineering |
| Define required signals            | Compliance SME + Engineering |
| Encode rules                       | Engineering                  |
| Define discovery questions         | Compliance SME + Engineering |
| Create automated tests             | Engineering                  |
| Review rule changes                | Compliance SME               |
| Version and publish knowledge pack | Knowledge owner              |
| AI-assisted change detection       | AI assistant                 |
| Final approval                     | Human                        |

The exact organisational ownership can evolve later.

---

# Authoritative Sources

Every important regulatory rule should be traceable to an authoritative source.

For example:

```text
HIPAA
  │
  └── Official US regulatory source
          │
          └── Relevant regulation / section
                  │
                  └── Applicability interpretation
                          │
                          └── Rule
```

A rule should therefore contain source metadata rather than simply stating:

```text
HIPAA applies when X = true
```

Instead, it should contain information such as:

```json
{
  "id": "hipaa-001",
  "requirementId": "HIPAA",
  "description": "...",
  "sources": [
    {
      "authority": "...",
      "reference": "...",
      "section": "...",
      "url": "...",
      "accessedAt": "..."
    }
  ]
}
```

This provides traceability and makes future updates easier.

---

# Requirement Packs

Each regulation or requirement should eventually be represented as an independent pack.

Example:

```text
src/main/resources/packs/

├── hipaa/
│   ├── requirement.json
│   ├── signals.json
│   ├── concepts.json
│   ├── rules.json
│   ├── obligations.json
│   ├── questions.json
│   └── sources.json
│
├── gdpr/
│   ├── requirement.json
│   ├── signals.json
│   ├── concepts.json
│   ├── rules.json
│   ├── obligations.json
│   ├── questions.json
│   └── sources.json
│
└── fedramp/
    └── ...
```

The application should be able to load a pack without requiring changes to the core rule engine.

---

# What a Requirement Pack Contains

## 1. Requirement

Defines what is being assessed.

Example:

```text
HIPAA
GDPR
FedRAMP
DORA
```

It should contain:

* ID
* Name
* Description
* Jurisdiction
* Requirement type
* Status
* Version
* Effective date
* Sources

---

## 2. Signals

Defines the facts required to evaluate applicability.

Examples:

```text
organisation.country
organisation.industry
organisation.government_customer
data.personal_data
data.health_data
data.payment_card_data
data.financial_data
processing.monitors_individuals
```

A signal should represent a **fact**, not a regulation-specific decision.

For example:

```text
data.health_data = TRUE
```

is a useful reusable signal.

This is preferable to creating:

```text
hipaa.applies = TRUE
```

because the same signal may be relevant to multiple requirements.

---

## 3. Concepts

Some regulatory decisions cannot be made directly from a single signal.

For example:

```text
personal_data_present
eu_establishment
regulated_financial_entity
government_customer
phi_processing
```

These may be derived from multiple signals.

Conceptually:

```text
Raw Signals
     │
     ├── signal A
     ├── signal B
     └── signal C
          │
          ▼
      Derived Concept
          │
          ▼
         Rule
```

Concepts should remain explainable and traceable to their underlying signals.

---

## 4. Rules

Rules determine how signals and concepts contribute to an assessment.

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

The exact schema will be defined during Phase 2.

The important architectural rule is:

> Rules belong to the requirement pack, not to Java application logic.

---

## 5. Obligations

Applicability is only the first step.

A requirement may introduce obligations such as:

```text
Access control
Audit logging
Incident management
Data protection
Contractual requirements
Data residency
Third-party oversight
```

These will eventually map to Atlassian capabilities.

---

## 6. Discovery Questions

If the system cannot determine applicability with sufficient confidence, the requirement pack should define questions that can resolve the uncertainty.

Example:

```text
Question:
Is the organisation a HIPAA covered entity?

Purpose:
Determines whether HIPAA applicability can be established.

Signals affected:
organisation.hipaa_covered_entity

Priority:
HIGH
```

This allows the system to move from:

```text
UNKNOWN
```

toward:

```text
HIGH CONFIDENCE
```

through an iterative discovery process.

---

# Evidence and Rule Evaluation

A rule should never be treated as an isolated assertion.

The intended flow is:

```text
Evidence
   │
   ▼
Signal
   │
   ▼
Concept
   │
   ▼
Rule
   │
   ▼
Assessment
```

For example:

```text
Customer questionnaire:
"Organisation processes patient health information."

             │
             ▼

data.health_data = TRUE

             │
             ▼

HIPAA applicability rule

             │
             ▼

POTENTIALLY_APPLICABLE
```

The final assessment should retain the evidence that led to the result.

---

# Versioning

Regulatory knowledge changes.

Therefore requirement packs must be versioned.

Example:

```text
HIPAA
 ├── v1.0
 ├── v1.1
 └── v2.0
```

An assessment should be able to identify which knowledge-pack version was used.

For example:

```text
Assessment
Requirement: HIPAA
Knowledge Pack: 1.2
Evaluated: 2026-09-12
```

Existing assessments should not silently change because a rule was subsequently updated.

Git will provide the initial version-control mechanism. A dedicated knowledge registry may be introduced later if required.

---

# Testing Regulatory Knowledge

Every requirement pack should have automated test scenarios.

Example:

```text
packs/hipaa/tests/

├── positive/
├── negative/
├── unknown/
├── edge/
└── conflict/
```

Tests should cover at least:

### Positive

Known facts indicate potential applicability.

### Negative

Known facts indicate the requirement is unlikely to apply.

### Unknown

Required information is unavailable.

### Edge cases

Less common combinations of signals.

### Conflicting evidence

Different sources provide contradictory information.

Example:

```text
CRM:
health_data = FALSE

Customer questionnaire:
health_data = TRUE
```

The system should not silently choose one source.

It should eventually produce:

```text
CONFLICT
```

and request verification.

---

# AI's Role

AI may eventually assist with regulatory knowledge management, but it should **not be the authoritative decision-maker**.

The desired workflow is:

```text
Official Regulatory Source
          │
          ▼
       AI Analysis
          │
          ├── Detect potential change
          ├── Extract candidate signals
          ├── Suggest rule changes
          ├── Suggest new questions
          └── Identify affected requirements
          │
          ▼
     Human Review
          │
          ▼
    Updated Rule Pack
          │
          ▼
    Automated Tests
          │
          ▼
       Publish
```

AI can therefore accelerate knowledge maintenance while keeping the final regulatory interpretation human-reviewed.

---

# What We Do Not Want

### ❌ Regulatory logic embedded in Java

```java
if (customer.isHealthcare()
        && customer.processesHealthData()) {
    return HIPAA;
}
```

### ❌ LLM directly deciding applicability

```text
Customer profile
       ↓
LLM
       ↓
"HIPAA applies"
```

### ❌ Rules without sources

```text
HIPAA applies because health_data = true
```

without knowing where the interpretation came from.

### ❌ Unversioned regulatory knowledge

Changing a rule should not silently change historical assessments.

---

# Target Architecture

The final architecture should separate three concerns:

```text
┌─────────────────────────────────────────┐
│          Regulatory Knowledge           │
│                                         │
│ HIPAA / GDPR / DORA / FedRAMP / etc.   │
│                                         │
│ Rules + Signals + Concepts + Sources    │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│             Compliance Engine           │
│                                         │
│ Rule evaluation                         │
│ Evidence processing                     │
│ Applicability                           │
│ Confidence                              │
│ Missing information                     │
│ Question generation                     │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│               Assessment                │
│                                         │
│ Result + Reason + Evidence + Questions │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│         Atlassian Capability Layer      │
│                                         │
│ Supported / Partial / Configuration    │
│ Contract / Roadmap / Unsupported       │
└─────────────────────────────────────────┘
```

---

# Initial Approach

For the first implementation, regulatory knowledge will be:

**Manually researched → human reviewed → encoded as JSON → version controlled → automatically tested.**

Over time this can evolve into:

**Official sources → automated change detection → AI-assisted rule proposal → human review → versioned requirement pack → automated testing → publication.**

This provides a practical path from a developer-maintained MVP to a scalable **Compliance Knowledge Management System**, without making the core compliance engine dependent on either hard-coded regulatory logic or an LLM.

---

## Phase Alignment

This work primarily becomes part of **Phase 2 and Phase 3**.

### Phase 2

Build the configuration-driven rule system:

```text
RuleDefinition
RuleRepository
JSON schema
Rule loader
Generic evaluator
Rule validation
```

### Phase 3

Build the knowledge lifecycle around:

```text
Evidence
Confidence
Sources
Missing information
Questions
Versioning
Conflict detection
```

### Later

Introduce:

```text
Knowledge ingestion
Regulatory change detection
AI-assisted rule generation
Human approval workflow
Knowledge-pack registry
```

The immediate goal is therefore **not to automate regulatory interpretation**.

The immediate goal is to create a reliable architecture where regulatory knowledge can be added, reviewed, tested, versioned, and executed independently from the compliance engine.
