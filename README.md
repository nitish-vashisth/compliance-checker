# Compliance Checker

A signal-based compliance and security applicability engine designed to identify potentially applicable regulatory, privacy, security, and contractual requirements for an organisation.

The goal is to move beyond static classification such as:

> Region × Industry × Customer Segment

and instead determine applicability using **organisation signals, evidence, rules, confidence, and missing information**.

The system is designed to be explainable, extensible, and capable of supporting multiple compliance/regulatory requirement packs such as HIPAA, GDPR, FedRAMP, DORA, Data Residency, and technical security requirements.

---

## Overall Plan

The implementation will be delivered incrementally through multiple phases.

```text
Organisation / Customer Signals
              │
              ▼
          Evidence
              │
              ▼
       Signal / Concepts
              │
              ▼
        Rule Evaluation
              │
              ▼
         Applicability
              │
       ┌──────┴──────┐
       ▼             ▼
   Confidence    Missing Info
                     │
                     ▼
              Discovery Questions
                     │
                     ▼
             Assessment Result
                     │
                     ▼
          Requirements / Obligations
                     │
                     ▼
          Atlassian Capabilities
                     │
                     ▼
             Migration Readiness
```

### Planned phases

| Phase        | Focus                                                          | Status      |
| ------------ | -------------------------------------------------------------- | ----------- |
| **Phase 1**  | Core domain model and rule-engine foundation                   | ✅ Completed |
| **Phase 2**  | JSON-driven rules and generic rule evaluation                  | 🔜 Next     |
| **Phase 3**  | Evidence, confidence, missing information and questions        | Planned     |
| **Phase 4**  | First complete compliance pack — HIPAA                         | Planned     |
| **Phase 5**  | GDPR and jurisdiction/processing-based applicability           | Planned     |
| **Phase 6**  | FedRAMP, DORA and other regulatory packs                       | Planned     |
| **Phase 7**  | Data Residency and technical/security requirements             | Planned     |
| **Phase 8**  | Atlassian capability mapping and migration readiness           | Planned     |
| **Phase 9**  | REST API, UI, reporting and export                             | Planned     |
| **Phase 10** | External data sources, AI assistance and continuous assessment | Planned     |

---

# Phase 1 — Core Foundation

**Status: Completed**

The objective of Phase 1 was to establish the initial domain model and prove the basic assessment flow before introducing complex compliance logic.

## What was implemented

### 1. Spring Boot application

Created the initial Spring Boot application using:

* Java 21
* Spring Boot
* Maven

The project can be built and tested using:

```bash
mvn clean test
```

---

### 2. Signal model

Introduced the basic signal abstraction:

```text
Signal
 ├── id
 └── value
```

Supported values:

```text
TRUE
FALSE
UNKNOWN
```

Example:

```text
data.health_data = TRUE
```

The signal represents a fact used by the compliance engine.

The signal itself does not contain information about where the fact came from.

---

### 3. Evidence model

Introduced evidence associated with signals.

```text
Evidence
 ├── sourceType
 ├── sourceReference
 ├── statement
 └── collectedAt
```

This allows a signal to eventually be backed by information from different sources, such as:

* Customer questionnaire
* CRM
* Public website
* Manual verification
* DC environment
* Cloud configuration
* Third-party APIs
* AI-assisted extraction

Example:

```text
Signal:
    data.health_data = TRUE

Evidence:
    sourceType = CUSTOMER_PROVIDED
    sourceReference = questionnaire-001
    statement = Customer processes patient health information
```

This establishes the foundation for explainability and auditability.

---

### 4. Signal + Evidence relationship

Introduced:

```text
SignalEvidence
```

which connects a signal to its supporting evidence.

```text
Signal
   │
   └── Evidence
        ├── source 1
        ├── source 2
        └── source 3
```

A future version will support multiple pieces of potentially conflicting evidence.

---

### 5. Rule abstraction

Introduced a generic `Rule` interface.

A rule receives signals and produces:

```text
TRUE
FALSE
UNKNOWN
```

The rule is associated with a requirement.

Conceptually:

```text
Rule
 ├── id
 ├── requirementId
 └── evaluate(signals)
```

This creates the initial separation between:

```text
Compliance knowledge
        vs
Compliance engine
```

---

### 6. Rule Engine

Introduced the initial `RuleEngine`.

Its responsibility is to execute a rule against the available signals.

Current implementation is intentionally simple.

The architecture will later evolve into a generic engine capable of loading rules from configuration rather than Java classes.

---

### 7. Assessment model

Introduced the initial assessment result:

```text
AssessmentResult
 ├── requirementId
 ├── status
 └── ruleResult
```

Assessment status currently supports:

```text
POTENTIALLY_APPLICABLE
NOT_APPLICABLE
UNKNOWN
```

This deliberately distinguishes an applicability assessment from a definitive legal determination.

---

### 8. Initial test

Implemented the first end-to-end rule-engine test using a healthcare-data signal.

Example:

```text
data.health_data = TRUE
        │
        ▼
Healthcare Data Rule
        │
        ▼
TRUE
        │
        ▼
HIPAA
POTENTIALLY_APPLICABLE
```

The test validates that the basic signal → rule → assessment flow works.

---

## Phase 1 architecture

The current implementation is intentionally small:

```text
Evidence
    │
    ▼
Signal
    │
    ▼
Rule
    │
    ▼
Rule Engine
    │
    ▼
Assessment
```

The objective was not to implement complete HIPAA/GDPR logic in Phase 1.

The objective was to establish the foundation on which those requirement packs can be built.

---

# Phase 2 — Configuration-Driven Rule Engine

**Status: Next**

Phase 2 will remove the hard-coded compliance rule implementation and make rules configuration-driven.

Currently, the prototype contains a concrete Java rule such as:

```text
HealthcareDataRule.java
```

This is useful for proving the architecture, but it does not scale.

We do **not** want the application to eventually contain:

```text
GDPRRule.java
HIPAARule.java
FedRAMPRule.java
DORARule.java
DPDPRule.java
...
```

Instead, compliance knowledge should be represented as data/configuration.

## Phase 2 objective

Move toward:

```text
Requirement Pack
       │
       ├── requirement.json
       ├── rules.json
       └── questions.json
              │
              ▼
       Rule Repository
              │
              ▼
        Generic Rule Engine
              │
              ▼
          Assessment
```

For example:

```text
src/main/resources/packs/
└── hipaa/
    └── rules.json
```

A rule could eventually look conceptually like:

```json
{
  "id": "hipaa-health-data",
  "requirementId": "HIPAA",
  "operator": "EQUALS",
  "signal": "data.health_data",
  "value": "TRUE"
}
```

The Java engine should then evaluate this definition without knowing that it is a HIPAA rule.

## Phase 2 implementation tasks

1. Create `RuleDefinition`
2. Create `RuleRepository`
3. Create JSON rule schema
4. Load rules from `src/main/resources`
5. Implement generic operators
6. Update `RuleEngine` to evaluate `RuleDefinition`
7. Remove the hard-coded `HealthcareDataRule`
8. Add rule validation
9. Add tests for:

    * TRUE
    * FALSE
    * UNKNOWN
    * missing signals
    * invalid rule definitions
10. Introduce the first `HIPAA` requirement configuration

The end result should be:

```text
JSON Rule
    │
    ▼
Rule Repository
    │
    ▼
Generic Rule Engine
    │
    ▼
Assessment
```

rather than:

```text
Java Rule Class
    │
    ▼
Rule Engine
```

---

# Future Architecture

As development progresses, the system will evolve toward:

```text
                    ┌─────────────────────┐
                    │ Organisation Signals│
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Evidence       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Signals / Concepts  │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Rule Engine      │
                    └──────────┬──────────┘
                               │
                 ┌─────────────┼─────────────┐
                 ▼             ▼             ▼
           Applicability   Confidence   Missing Info
                 │                           │
                 │                           ▼
                 │                    Discovery Questions
                 │                           │
                 └─────────────┬─────────────┘
                               ▼
                    ┌─────────────────────┐
                    │     Assessment      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Requirements /      │
                    │ Obligations         │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Atlassian Capability│
                    │ Mapping             │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ Migration Readiness │
                    └─────────────────────┘
```

## Design principles

The implementation will follow these principles:

### 1. Engine ≠ Compliance Knowledge

Java implements the evaluation engine.

Compliance knowledge is stored in versioned requirement packs.

### 2. Evidence-first

Every important signal should eventually be traceable to supporting evidence.

### 3. Explainable

The system should explain:

* Why a requirement was detected
* Which signals caused it
* Which evidence supports those signals
* What is still unknown
* What needs to be verified

### 4. Unknown is a valid result

The system should never invent information.

If applicability cannot be determined:

```text
UNKNOWN
```

should be returned along with the information required to improve confidence.

### 5. Detection ≠ Legal determination

The system identifies potentially relevant requirements and migration risks.

It should not present itself as providing definitive legal advice.

### 6. Configuration over hard-coded logic

Adding a new regulation should primarily involve creating a new requirement pack rather than modifying the core engine.

### 7. Incremental implementation

Every phase should leave the project:

* Buildable
* Testable
* Runnable
* Demonstrable

---

## Initial Requirement Packs

The architectural proving ground will eventually include:

```text
1. HIPAA
2. GDPR
3. FedRAMP
4. DORA
5. Data Residency
6. IP Allowlisting
```

These were deliberately selected because they exercise different applicability patterns:

| Requirement     | Primary applicability pattern                |
| --------------- | -------------------------------------------- |
| HIPAA           | Data + organisation relationship             |
| GDPR            | Jurisdiction + processing + individuals      |
| FedRAMP         | Government/customer type + authorisation     |
| DORA            | Industry + jurisdiction + regulated entity   |
| Data Residency  | Geography + contractual/customer requirement |
| IP Allowlisting | Technical/customer environment requirement   |

Once these work through the same engine, additional requirements should become primarily a **knowledge-pack expansion problem**, rather than a new software-engineering problem.

---

## Current Status

```text
Phase 1  ████████████████████  Complete
Phase 2  ░░░░░░░░░░░░░░░░░░░░  Next
Phase 3  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 4  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 5  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 6  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 7  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 8  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 9  ░░░░░░░░░░░░░░░░░░░░  Planned
Phase 10 ░░░░░░░░░░░░░░░░░░░░  Planned
```

The immediate next milestone is therefore:

> **Phase 2 — Build a configuration-driven rule engine and create the first HIPAA rule pack.**
