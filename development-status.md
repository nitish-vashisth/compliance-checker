# Compliance Checker — Development Status

## Current Phase

**Phase 2A — Configuration-Driven Rule Engine**

Status: **Completed**

---

# Project Objective

Build a signal-based security, privacy, compliance, and regulatory applicability engine.

The system should eventually determine:

* Which requirements may apply to an organisation
* Why they may apply
* What evidence supports the assessment
* What information is still unknown
* What needs to be verified
* What discovery questions should be asked
* What confidence can be assigned to the assessment
* What Atlassian Cloud capabilities or limitations are relevant

The system must distinguish between **detection** and **final determination**.

It should not convert incomplete information into a false certainty.

---

# Phase 1 — Core Foundation

Status: **Completed**

## Objective

Establish the initial domain model and prove the basic signal → rule → assessment concept.

### Implemented

#### Project foundation

* Java 21
* Spring Boot
* Maven
* Basic application configuration

#### Domain model

* `Signal`
* `SignalValue`
* `Evidence`
* `SignalEvidence`
* `RuleResult`

#### Initial rule engine

The original Phase 1 implementation contained:

```text
HealthcareDataRule
```

This was intentionally hard-coded and existed only as a proof of concept.

#### Assessment model

The initial implementation also contained:

* `AssessmentStatus`
* `AssessmentResult`
* `AssessmentService`

These were subsequently removed because they were not part of the actual runtime architecture and were too tightly coupled to the early proof-of-concept design.

---

# Phase 2A — Configuration-Driven Rule Engine

Status: **Completed**

## Objective

Replace hard-coded regulatory evaluation logic with a generic configuration-driven rule engine.

The engine must not know whether a rule belongs to:

* HIPAA
* GDPR
* FedRAMP
* DORA
* Data Residency
* IP Allowlisting
* or any other requirement

Regulatory knowledge should be represented as configuration rather than Java implementation.

---

# Current Architecture

The current runtime flow is:

```text
rules.json
    ↓
JsonRuleRepository
    ↓
RuleEngine
    ↓
RuleEvaluator
    ↓
RuleResult
```

The rule engine is generic and does not contain HIPAA-specific evaluation logic.

---

# Implemented Components

## Rule Definition

Implemented:

```text
RuleDefinition
```

Current structure:

```text
RuleDefinition
 ├── id
 ├── requirementId
 └── condition
```

Example:

```json
{
  "id": "HIPAA-001-R01",
  "requirementId": "HIPAA-001",
  "condition": {
    "signal": "data.health_data",
    "operator": "EQUALS",
    "value": "TRUE"
  }
}
```

The `result` field was deliberately removed.

The evaluator derives the result from the condition instead of storing a redundant result inside the rule definition.

---

## Rule Conditions

Implemented:

```text
RuleCondition
```

Structure:

```text
signal
operator
value
```

Example:

```text
data.health_data
    ↓
EQUALS
    ↓
TRUE
```

---

## Rule Operators

Currently supported:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

Additional operators will only be introduced when an actual regulatory requirement requires them.

---

## Rule Repository

Implemented:

```text
RuleRepository
```

with:

```text
findAll()
findById()
findByRequirementId()
```

Current implementation:

```text
JsonRuleRepository
```

The repository loads rule definitions from:

```text
src/main/resources/rules.json
```

This is intentionally a simple proving-ground implementation.

The repository will later evolve to load complete Requirement Packs rather than a single global rule file.

---

## Rule Evaluator

Implemented:

```text
RuleEvaluator
```

The evaluator is responsible for interpreting a `RuleDefinition` against supplied signals.

It currently supports:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

---

# UNKNOWN Semantics

The engine explicitly distinguishes between:

```text
TRUE
FALSE
UNKNOWN
```

This is important for compliance assessment.

For example:

```text
Signal exists and value = TRUE
        ↓
TRUE
```

```text
Signal exists and value = FALSE
        ↓
FALSE
```

```text
Signal exists but value = UNKNOWN
        ↓
UNKNOWN
```

```text
Signal is missing
        ↓
UNKNOWN
```

for normal value comparisons.

Specific existence semantics are:

| Operator     | Signal missing | Signal UNKNOWN | Signal known |
| ------------ | -------------- | -------------- | ------------ |
| `EQUALS`     | `UNKNOWN`      | `UNKNOWN`      | evaluate     |
| `NOT_EQUALS` | `UNKNOWN`      | `UNKNOWN`      | evaluate     |
| `EXISTS`     | `FALSE`        | `UNKNOWN`      | `TRUE`       |
| `NOT_EXISTS` | `TRUE`         | `UNKNOWN`      | `FALSE`      |

The purpose is to avoid treating missing information as evidence that a requirement does not apply.

---

# Testing

The rule engine currently has tests covering:

## Rule evaluation

* Matching `EQUALS`
* Non-matching `EQUALS`
* Matching `NOT_EQUALS`
* Non-matching `NOT_EQUALS`
* `EXISTS`
* `NOT_EXISTS`
* `UNKNOWN` values

## Missing signals

* `EQUALS` with missing signal
* `NOT_EQUALS` with missing signal
* `EXISTS` with missing signal
* `NOT_EXISTS` with missing signal

## Unknown signals

* `EQUALS` with `UNKNOWN`
* `NOT_EQUALS` with `UNKNOWN`
* `EXISTS` with `UNKNOWN`
* `NOT_EXISTS` with `UNKNOWN`

## Rule engine

* Successful rule evaluation
* `UNKNOWN` propagation
* Invalid rule ID

## Repository

* Loading rules from JSON
* Finding a rule by ID
* Finding rules by requirement ID
* Unknown rule ID
* Unknown requirement ID
* Rule condition deserialization
* Multiple rules for a requirement

The complete Maven test suite currently passes.

---

# Removed Phase 1 Artifacts

The following obsolete Phase 1 concepts have been removed:

```text
HealthcareDataRule
Rule interface
AssessmentService
AssessmentResult
AssessmentStatus
```

The original hard-coded healthcare rule is no longer part of the production architecture.

---

# Important Architectural Decision

## Regulatory knowledge is data, not application code

We do **not** want regulatory knowledge implemented as classes such as:

```text
GDPRRule.java
HIPAARule.java
DORARule.java
FedRAMPRule.java
```

Instead:

```text
Requirement Pack
       ↓
Rule Definitions
       ↓
Generic Rule Engine
       ↓
Assessment
```

Regulatory knowledge should eventually be represented as version-controlled, source-backed Requirement Packs.

---

# Requirement Pack Direction

A Requirement Pack will eventually contain more than rules.

The target conceptual model is:

```text
Requirement Theme
        ↓
Requirement
        ↓
Applicability Signals
        ↓
Derived Concepts
        ↓
Applicability Rules
        ↓
Evidence Requirements
        ↓
Unknowns
        ↓
Verification Questions
        ↓
Obligations / Requirements
        ↓
Atlassian Capability Mapping
        ↓
Migration Risk
```

Example:

```text
HIPAA
 ├── Metadata
 ├── Applicability signals
 ├── Derived concepts
 ├── Applicability rules
 ├── Evidence
 ├── Verification questions
 ├── Requirements
 ├── Sources
 └── Atlassian capability mapping
```

This model will be introduced incrementally rather than implemented all at once.

---

# Regulatory Knowledge Governance

Regulatory knowledge must be based on authoritative sources.

The intended lifecycle is:

```text
Authoritative Regulatory Sources
            ↓
AI-assisted extraction / authoring
            ↓
Draft Requirement Pack
            ↓
Schema validation
            ↓
Rule / test validation
            ↓
Human review
            ↓
Approved Requirement Pack
            ↓
Version control
            ↓
Runtime Rule Engine
```

AI may assist with:

* extracting requirements
* identifying applicability signals
* proposing rules
* identifying evidence requirements
* generating discovery questions
* generating tests
* linking authoritative sources

AI must not be the final authority for production regulatory decisions.

The principle is:

> **AI generates. Machines validate. Humans approve. The rule engine executes.**

---

# Current Proving-Ground Rule

The current implementation contains a minimal HIPAA example:

```text
HIPAA-001
    ↓
HIPAA-001-R01
    ↓
data.health_data = TRUE
```

This is intentionally **not a complete HIPAA implementation**.

Its purpose is to prove that regulatory applicability logic can be represented as configuration and evaluated by a generic engine.

---

# Phase 2B — Requirement Pack Model

## Next Phase

The next major implementation phase is:

**Phase 2B — Regulatory Requirement Pack Model**

The goal is to move from:

```text
rules.json
```

to a structured Requirement Pack model.

The first pack will remain intentionally small.

We will introduce concepts incrementally, beginning with:

```text
Requirement Pack
 ├── Metadata
 ├── Requirement
 ├── Applicability Signals
 └── Rules
```

The model will later expand to include evidence, sources, unknowns, questions, obligations, and capability mappings.

---

# Future Phases

## Phase 3 — Evidence, Confidence & Missing Information

Introduce:

```text
Evidence
    ↓
Confidence
    ↓
Missing Information
    ↓
Verification Requirements
    ↓
Discovery Questions
```

The system should explain not only its conclusion, but also why confidence is high or low.

---

## Phase 4 — Complete HIPAA Requirement Pack

Build the first meaningful regulatory knowledge pack.

The goal is not to encode all of HIPAA immediately.

Instead, identify and implement a well-defined set of applicability signals, rules, evidence requirements, and verification questions.

---

## Phase 5 — GDPR / Privacy Requirement Pack

Add GDPR and related privacy/contractual requirements.

Potential areas include:

* Personal data processing
* Controller / processor relationship
* EU establishment or targeting
* Data subject rights
* Data transfers
* DPA
* SCCs
* Sub-processors
* Privacy impact assessment
* Data residency / transfer considerations

---

## Phase 6 — FedRAMP and DORA

Add regulatory/authorisation packs for:

```text
FedRAMP
StateRAMP
CJIS
DoD Impact Levels
DORA
```

Each should use the same generic engine while maintaining its own authoritative knowledge pack.

---

## Phase 7 — Data Residency & Security Architecture

Introduce themes such as:

```text
Data Residency
Data Sovereignty
IP Allowlisting
Egress Restrictions
Private Connectivity
SIEM
Penetration Testing
Vulnerability Scanning
Encryption / CMK / HSM
DLP
Data Classification
```

These may not always be laws. The system therefore needs to distinguish between regulatory requirements and technical/customer requirements.

---

## Phase 8 — Atlassian Capability Mapping

Map identified requirements to:

```text
Atlassian Cloud capability
Atlassian Cloud limitation
Available control
Configuration requirement
Additional Atlassian product
Third-party dependency
Unsupported requirement
Manual verification required
```

This is where the regulatory assessment becomes useful for migration readiness.

---

## Phase 9 — Assessment API, UI & Reporting

Introduce:

```text
Questionnaire
    ↓
Assessment API
    ↓
Requirement Evaluation
    ↓
Results Dashboard
    ↓
Missing Information
    ↓
Discovery Questions
    ↓
Security / Compliance Readiness Report
```

---

## Phase 10 — External Data Sources & Continuous Assessment

Potential inputs:

* CRM / Salesforce
* Public company information
* Third-party data sources
* Customer-provided information
* Security questionnaires
* Future Data Center collectors
* Atlassian environment data

The assessment should improve as additional evidence becomes available.

---

# Long-Term Assessment Model

The eventual assessment should produce statuses such as:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
INSUFFICIENT_INFORMATION
MANUAL_REVIEW_REQUIRED
OUT_OF_SCOPE
```

The current `TRUE / FALSE / UNKNOWN` rule result is an intentionally lower-level engine primitive.

The higher-level assessment model will be introduced later.

---

# Design Principles

Every phase should leave the repository:

* Buildable
* Testable
* Runnable
* Demonstrable

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

Additional principles:

1. **Regulatory knowledge is data, not Java code.**
2. **Rules must be explainable.**
3. **Unknown information must remain unknown.**
4. **Authoritative sources must be traceable.**
5. **AI must not silently become the regulatory decision-maker.**
6. **Requirement Packs must be version-controlled.**
7. **Rules should be testable independently of the application.**
8. **New regulations should ideally require new configuration, not new Java evaluation logic.**
9. **Complexity should be introduced only when an actual requirement demands it.**
10. **The architecture should support incremental expansion from a small proving ground to a broad compliance knowledge system.**

---

# Current Architecture Checkpoint

Current production flow:

```text
Signal
  ↓
SignalEvidence
  ↓
RuleDefinition
  ↓
JsonRuleRepository
  ↓
RuleEngine
  ↓
RuleEvaluator
  ↓
RuleResult
```

Current configuration:

```text
src/main/resources/
└── rules.json
```

Current rule model:

```text
RuleDefinition
 ├── id
 ├── requirementId
 └── condition
      ├── signal
      ├── operator
      └── value
```

Current operators:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

---

# Current Next Action

Start **Phase 2B — Regulatory Requirement Pack Model**.

The first implementation should introduce the **Requirement Pack metadata model** without prematurely implementing the complete compliance architecture.

Do not implement yet:

* UI
* Database
* AI runtime
* External regulatory ingestion
* Complete HIPAA knowledge model
* Salesforce integration
* Data Center collector
* Complex rule expressions

Continue using the incremental development approach:

```text
One capability
      ↓
Test
      ↓
Validate
      ↓
Commit
      ↓
Next capability
```
