# Compliance Checker — Development Status

## Current Phase

**Phase 2B — Regulatory Requirement Pack & Assessment Engine**

Status: **In Progress — Core Engine Implemented**

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
* What migration risks may exist

The system must distinguish between **detection** and **final determination**.

It should not convert incomplete information into a false certainty.

The long-term product is intended to replace simplistic classification such as:

```text
Region × Industry × Segment
```

with a signal-based, explainable assessment model.

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

#### Initial domain model

* `Signal`
* `SignalValue` / typed signal representation
* `Evidence`
* `SignalEvidence`
* `RuleResult`

#### Initial rule engine

The original Phase 1 implementation contained:

```text
HealthcareDataRule
```

This was intentionally hard-coded and existed only as a proof of concept.

The hard-coded healthcare rule was subsequently removed.

#### Initial assessment model

The original implementation also contained:

* `AssessmentStatus`
* `AssessmentResult`
* `AssessmentService`

These were subsequently removed because they were tightly coupled to the early proof-of-concept design and are no longer part of the current runtime architecture.

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

# Phase 2A Architecture

The original configuration-driven runtime flow is:

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

The original `rules.json` implementation remains useful as a proving ground, but the architecture has now evolved toward Requirement Packs.

---

# Implemented Rule Components

## Rule Definition

Implemented:

```text
RuleDefinition
```

Structure:

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

Currently supported by the original rule engine:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

Additional operators should only be introduced when an actual requirement requires them.

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

loads rule definitions from:

```text
src/main/resources/rules.json
```

This is intentionally retained as the original proving-ground implementation.

The production direction is now to load complete Requirement Packs instead of maintaining a single global rule file.

---

## Rule Evaluator

Implemented:

```text
RuleEvaluator
```

The evaluator interprets a `RuleDefinition` against supplied signals.

It supports:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
```

The evaluator explicitly respects signal state and does not treat an unknown value as a known value.

---

# Phase 2B — Regulatory Requirement Pack & Assessment Engine

Status: **Core implementation completed; regulatory knowledge expansion remains**

## Objective

Move from a simple global rule file to a generic, version-controlled Requirement Pack model.

The Requirement Pack is intended to hold regulatory knowledge as data.

The application engine should remain generic.

The current architecture supports:

```text
Requirement Pack
    ↓
Applicability Signals
    ↓
Derived Concepts
    ↓
Applicability Rules
    ↓
Generic Expression Evaluator
    ↓
Applicability Decision
    ↓
Missing Information
    ↓
Discovery Questions
    ↓
Requirements
    ↓
Capability Mapping
```

---

# Requirement Pack Model

The current `RequirementPack` model contains:

```text
RequirementPack
 ├── id
 ├── name
 ├── version
 ├── requirementType
 ├── jurisdiction
 ├── status
 ├── effectiveDate
 ├── sources
 ├── applicabilitySignals
 ├── derivedConcepts
 ├── applicabilityRules
 ├── requirements
 ├── verificationRequirements
 ├── discoveryQuestions
 ├── evidenceRequirements
 ├── capabilityMappings
 └── testScenarios
```

The model is intentionally designed so that regulatory knowledge can evolve independently of Java code.

---

# Requirement Pack Metadata

Supported concepts include:

## Requirement Type

```text
LAW
REGULATION
STANDARD
AUTHORISATION
CUSTOMER_REQUIREMENT
TECHNICAL_REQUIREMENT
PROCESS_RISK
```

This distinction is important because not every migration blocker is a law.

For example:

```text
GDPR
    → LAW

FedRAMP
    → AUTHORISATION

PCI DSS
    → STANDARD

IP Allowlisting
    → TECHNICAL_REQUIREMENT

Customer-specific security policy
    → CUSTOMER_REQUIREMENT
```

---

## Requirement Pack Status

```text
DRAFT
APPROVED
DEPRECATED
```

Only approved packs should be considered for normal production assessment.

---

## Requirement Pack Sources

Requirement Packs support source metadata.

Source types include:

```text
PRIMARY_REGULATION
REGULATOR_GUIDANCE
GOVERNMENT_DOCUMENTATION
OFFICIAL_STANDARD
OFFICIAL_INTERPRETATION
```

The intention is that every meaningful regulatory conclusion can eventually be traced back to an authoritative source.

---

# Typed Signals

Status: **Completed**

The signal model was evolved from a simple value representation to an explicit typed state model.

Current concepts:

```text
Signal
 ├── id
 └── SignalData
       ├── value
       └── state
```

Signal state:

```text
PROVIDED
UNKNOWN
NOT_PROVIDED
```

This is important because:

```text
FALSE
```

is not the same as:

```text
UNKNOWN
```

and neither should be silently converted into the other.

Example:

```text
data.health_data = FALSE
```

means the supplied answer indicates no health data.

Whereas:

```text
data.health_data = UNKNOWN
```

means the system does not currently know.

---

# Signal Data Types

Requirement Packs can declare signal data types:

```text
BOOLEAN
STRING
INTEGER
DECIMAL
DATE
STRING_LIST
```

A basic `SignalValidator` has been introduced to support typed signal validation.

Validation is intentionally lightweight at this stage and can be strengthened as the domain model evolves.

---

# Nested Rule Expressions

Status: **Completed**

Requirement Pack applicability rules now support nested expressions.

Implemented expression operators:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
AND
OR
```

Example:

```text
AND
├── data.health_data = TRUE
└── OR
    ├── organisation.country = US
    └── organisation.is_business_associate = TRUE
```

This allows rules to express relationships between multiple signals without creating regulation-specific Java classes.

---

# Rule Expression Evaluation

Implemented:

```text
RuleExpression
RuleExpressionEvaluator
```

The evaluator recursively evaluates nested expressions.

## AND semantics

```text
ANY FALSE
    ↓
FALSE

NO FALSE + ANY UNKNOWN
    ↓
UNKNOWN

ALL TRUE
    ↓
TRUE
```

## OR semantics

```text
ANY TRUE
    ↓
TRUE

NO TRUE + ANY UNKNOWN
    ↓
UNKNOWN

ALL FALSE
    ↓
FALSE
```

These semantics allow incomplete customer information to propagate safely through complex rules.

---

# UNKNOWN Semantics

The engine explicitly distinguishes:

```text
TRUE
FALSE
UNKNOWN
```

For normal value comparisons:

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
Signal exists but state = UNKNOWN
        ↓
UNKNOWN
```

```text
Signal is missing
        ↓
UNKNOWN
```

The purpose is to avoid treating missing information as evidence that a requirement does not apply.

For existence operators, the semantics are:

| Operator | Signal missing | Signal UNKNOWN | Signal known |
|---|---|---|---|
| `EQUALS` | `UNKNOWN` | `UNKNOWN` | evaluate |
| `NOT_EQUALS` | `UNKNOWN` | `UNKNOWN` | evaluate |
| `EXISTS` | `FALSE` | `UNKNOWN` | `TRUE` |
| `NOT_EXISTS` | `TRUE` | `UNKNOWN` | `FALSE` |

---

# Applicability Decision

Status: **Completed**

The low-level rule engine produces:

```text
TRUE
FALSE
UNKNOWN
```

The higher-level Requirement Pack evaluation converts these into an applicability status.

Current decision precedence:

```text
ANY TRUE
    ↓
LIKELY_APPLICABLE
```

```text
NO TRUE + ANY UNKNOWN
    ↓
INSUFFICIENT_INFORMATION
```

```text
ALL FALSE
    ↓
NOT_CURRENTLY_INDICATED
```

The current higher-level applicability statuses are:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
INSUFFICIENT_INFORMATION
MANUAL_REVIEW_REQUIRED
OUT_OF_SCOPE
```

Not every status is currently generated by the basic decision service. The model exists so that more sophisticated assessment logic can be introduced without changing the underlying rule primitive.

---

# Missing Signal Detection

Status: **Completed**

When a rule evaluates to `UNKNOWN`, the system identifies signals that prevented a definitive evaluation.

Example:

```text
HIPAA rule

data.health_data = TRUE
AND
(
    organisation.country = US
    OR
    organisation.is_business_associate = TRUE
)
```

If only:

```text
data.health_data = TRUE
```

is known, the engine can identify:

```text
organisation.country
organisation.is_business_associate
```

as missing information.

The current implementation recursively collects missing signals from rule expressions.

More sophisticated minimal-question resolution for complex `OR` expressions can be introduced later.

---

# Discovery Questions

Status: **Completed**

Discovery questions are now part of Requirement Packs.

Current model:

```text
DiscoveryQuestion
 ├── id
 ├── question
 ├── purpose
 └── signal
```

Questions are connected directly to signals.

Example:

```text
Signal:
organisation.is_business_associate

Question:
Does the organisation act as a HIPAA business associate?
```

The evaluator returns discovery questions associated with missing signals.

This allows the system to move from:

```text
We don't know.
```

to:

```text
We don't know because this information is missing.

Ask:
<targeted discovery question>
```

---

# Evidence and Confidence

Status: **Completed — Initial Model**

Evidence now supports an explicit confidence classification:

```text
HIGH
MEDIUM
LOW
UNKNOWN
```

`SignalEvidence` can determine the highest available evidence confidence for a signal.

Current precedence:

```text
HIGH
    ↓
MEDIUM
    ↓
LOW
    ↓
UNKNOWN
```

The assessment response exposes signal confidence.

This establishes the foundation for eventually distinguishing:

```text
Customer-provided answer
CRM-derived information
Public-source information
Authoritative evidence
Unverified assumption
```

The evidence model will be expanded later to capture richer provenance and verification state.

---

# Applicable Requirements

Status: **Completed — Initial Mapping**

When a Requirement Pack is assessed as:

```text
LIKELY_APPLICABLE
```

its defined requirements are returned as applicable requirements.

Current model:

```text
RequirementDefinition
```

The assessment therefore moves beyond:

```text
GDPR = TRUE
```

toward:

```text
GDPR
    ↓
LIKELY_APPLICABLE
    ↓
Applicable Requirement(s)
```

More granular requirement-level applicability will be introduced as regulatory packs become more sophisticated.

---

# Atlassian Capability Mapping

Status: **Completed — Initial Mapping**

Requirement Packs can map requirements to Atlassian capabilities.

Current model:

```text
CapabilityMapping
 ├── requirementId
 ├── capability
 ├── supportLevel
 └── description
```

The assessment returns capability mappings associated with applicable requirements.

The intended long-term mapping model is broader:

```text
Requirement
    ↓
Atlassian Cloud Capability
    ↓
Support Level
    ↓
Configuration / Dependency
    ↓
Limitation / Gap
    ↓
Migration Risk
```

Possible support outcomes include:

```text
SUPPORTED
PARTIALLY_SUPPORTED
CONFIGURATION_REQUIRED
ADDITIONAL_PRODUCT_REQUIRED
THIRD_PARTY_DEPENDENCY
UNSUPPORTED
MANUAL_VERIFICATION_REQUIRED
```

The current implementation only exercises the basic mapping mechanism.

---

# Assessment API

Status: **Core implementation completed**

The system now supports assessment across multiple Requirement Packs.

Current conceptual flow:

```text
Customer Signals
       ↓
Assessment API
       ↓
Requirement Pack Repository
       ↓
For each APPROVED Pack
       ↓
Requirement Pack Evaluator
       ↓
Applicability Result
```

The assessment response contains the results for the evaluated packs.

Each result can contain:

```text
Requirement Pack
    ├── status
    ├── rule results
    ├── missing signals
    ├── discovery questions
    ├── signal confidence
    ├── applicable requirements
    └── capability mappings
```

This is the foundation for the future results dashboard and report.

---

# Current Requirement Packs

## HIPAA

Status: **Proving-Ground Pack**

Current pack:

```text
packs/hipaa/requirement.json
```

The pack demonstrates:

* Requirement Pack metadata
* Applicability signals
* Derived concepts
* Nested applicability rule
* Missing signal detection
* Discovery questions
* Evidence requirements
* Applicable requirement mapping
* Atlassian capability mapping
* Test scenario metadata

Current prototype rule:

```text
data.health_data = TRUE
AND
(
    organisation.country = US
    OR
    organisation.is_business_associate = TRUE
)
```

This is intentionally a **prototype rule for exercising the engine**.

It is **not a complete HIPAA applicability implementation** and must not be treated as a legal determination.

A production HIPAA pack must model the relevant covered-entity/business-associate scope and authoritative applicability criteria more rigorously.

---

# GDPR

Status: **Proving-Ground Pack**

Current pack:

```text
packs/gdpr/requirement.json
```

The initial pack demonstrates the same generic Requirement Pack architecture with a different set of signals and rules.

Current prototype rule:

```text
data.personal_data = TRUE
AND
organisation.operates_in_eu = TRUE
```

This is intentionally a **prototype rule for exercising the engine**.

It is **not a complete GDPR territorial-scope determination**.

A production GDPR pack must properly model the relevant territorial scope, processing activities, roles, targeting/monitoring criteria, and other applicable conditions using authoritative sources.

---

# Multi-Requirement Assessment

Status: **Implemented**

The engine is no longer conceptually restricted to one regulation.

The current architecture supports:

```text
Customer Signals
        ↓
 ┌───────────────┐
 │ Requirement   │
 │ Pack Engine   │
 └───────────────┘
        ↓
 ┌────────┬────────┬────────┐
 │ HIPAA  │ GDPR   │ Future │
 └────────┴────────┴────────┘
        ↓
Individual Applicability Results
```

Adding another Requirement Pack should not require adding a regulation-specific evaluator.

This is a key architectural checkpoint.

---

# Current Runtime Architecture

The current runtime is now conceptually:

```text
                    Customer Signals
                          ↓
                    SignalEvidence
                          ↓
               Assessment Service
                          ↓
             Requirement Pack Repository
                          ↓
               Approved Requirement Packs
                          ↓
              Requirement Pack Evaluator
                          ↓
              RuleExpressionEvaluator
                          ↓
                   Rule Results
                          ↓
             Applicability Decision Service
                          ↓
              Applicability Result
                ┌────────┼──────────┐
                ↓        ↓          ↓
             Unknowns  Questions  Requirements
                ↓                   ↓
             Evidence          Capabilities
                └──────────┬────────┘
                           ↓
                       Assessment
```

---

# Current Resource Structure

The intended resource structure is now:

```text
src/main/resources/
├── rules.json
└── packs/
    ├── hipaa/
    │   └── requirement.json
    └── gdpr/
        └── requirement.json
```

`rules.json` represents the original proving-ground rule model.

The `packs/` directory represents the production direction.

---

# Current Generic Architecture

The most important architectural property is:

```text
Regulatory Knowledge
        ↓
Requirement Pack
        ↓
Generic Engine
```

and not:

```text
HIPAA
    ↓
HipaaRule.java

GDPR
    ↓
GdprRule.java

DORA
    ↓
DoraRule.java
```

We do **not** want regulatory knowledge implemented as Java classes.

Instead:

```text
Requirement Pack
       ↓
Applicability Rules
       ↓
Generic Rule Engine
       ↓
Assessment
```

New regulations should ideally require new configuration rather than new Java evaluation logic.

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

The governing principle is:

> **AI generates. Machines validate. Humans approve. The rule engine executes.**

---

# Regulatory Knowledge Model

The long-term Requirement Pack model is:

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
Verification Requirements
        ↓
Discovery Questions
        ↓
Obligations / Requirements
        ↓
Atlassian Capability Mapping
        ↓
Migration Risk
```

This model is intentionally being introduced incrementally.

---

# Target Requirement Themes

The eventual knowledge catalogue is expected to cover multiple categories.

## A. Privacy & Data Protection

```text
GDPR
India DPDP
CCPA / CPRA
LGPD
APPI
PIPL
PDPA
PIPA
POPIA
DPA / SCC
```

## B. Industry / Regulatory

```text
HIPAA
PCI DSS
DORA
NIS2
FedRAMP
StateRAMP
CJIS
DoD Impact Levels
IRAP
C5
TISAX
```

## C. Data & Sovereignty

```text
Data Residency
Data Sovereignty
Government nationality / access restrictions
Export Control
Data Transfer Restrictions
```

## D. Security Architecture

```text
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

## E. Identity & Administration

Identity and administration requirements will be modelled as their own requirement areas as the catalogue expands.

---

# Phase 3 — Regulatory Knowledge Packs

## Next Major Phase

The next major focus is **regulatory knowledge quality**, not additional generic Java infrastructure.

The engine has now demonstrated that it can:

```text
Load configuration
      ↓
Interpret signals
      ↓
Evaluate nested rules
      ↓
Handle UNKNOWN
      ↓
Determine applicability status
      ↓
Identify missing information
      ↓
Generate discovery questions
      ↓
Map requirements
      ↓
Map capabilities
      ↓
Assess multiple packs
```

The next challenge is therefore to make the Requirement Packs **accurate, authoritative, explainable, testable, and maintainable**.

---

# Phase 3 — GDPR Requirement Pack

The first production-oriented regulatory knowledge pack should be GDPR.

The goal is not to create a simplistic:

```text
EU = GDPR
```

rule.

Instead, the pack should model relevant applicability signals and actual applicability conditions from authoritative sources.

Potential signal areas include:

* Organisation establishment
* Location of relevant establishments
* Processing activities
* Personal data processing
* Data subjects located in the EU
* Offering goods or services to individuals in the EU
* Monitoring behaviour of individuals in the EU
* Controller / processor role
* Processing purpose
* Data transfer activity
* Special-category data
* Processing scale
* Relevant evidence and source provenance

The exact rules must be derived from authoritative regulatory sources and reviewed before being treated as production knowledge.

---

# Phase 4 — HIPAA Requirement Pack

After the engine and GDPR model are established, the HIPAA pack should be upgraded from proving-ground status to a properly sourced regulatory knowledge pack.

Potential areas include:

* Covered entity determination
* Business associate determination
* PHI processing
* Relevant relationships
* Applicable safeguards
* Verification evidence
* Contractual requirements
* Scope limitations
* Atlassian Cloud capability mapping

The existing HIPAA pack remains a test vehicle until this work is complete.

---

# Phase 5 — Privacy & Data Protection Catalogue

Expand the privacy family:

```text
GDPR
India DPDP
CCPA / CPRA
LGPD
APPI
PIPL
PDPA
PIPA
POPIA
DPA / SCC
```

Each pack should use the same generic engine while maintaining its own authoritative knowledge.

The implementation should avoid assuming that one country's privacy law can be inferred solely from:

```text
country
+
industry
+
company size
```

Instead, applicability should be based on the actual statutory scope and relevant signals.

---

# Phase 6 — Industry / Regulatory Catalogue

Add:

```text
PCI DSS
DORA
NIS2
FedRAMP
StateRAMP
CJIS
DoD Impact Levels
IRAP
C5
TISAX
```

Each should have:

```text
Applicability Signals
        ↓
Applicability Rules
        ↓
Evidence
        ↓
Verification
        ↓
Requirements
        ↓
Capability Mapping
```

The engine remains generic.

---

# Phase 7 — Data & Sovereignty Requirements

Add requirements such as:

```text
Data Residency
Data Sovereignty
Government nationality / access restrictions
Export Control
Data Transfer Restrictions
```

These requirements are especially important for migration readiness because a customer may have a requirement even where there is no single regulation that directly produces the technical constraint.

The model therefore needs to support:

```text
LAW
REGULATION
STANDARD
AUTHORISATION
CUSTOMER_REQUIREMENT
TECHNICAL_REQUIREMENT
PROCESS_RISK
```

rather than treating everything as a law.

---

# Phase 8 — Security Architecture Requirements

Introduce:

```text
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

These should be modelled as requirements with appropriate signals and evidence rather than artificially converting technical requirements into regulatory laws.

---

# Phase 9 — Evidence, Confidence & Verification

Expand the current initial evidence model.

The future system should distinguish:

```text
Signal
    ↓
Source
    ↓
Evidence
    ↓
Evidence Confidence
    ↓
Verification State
```

Potential confidence dimensions include:

```text
Source reliability
Evidence freshness
Direct customer confirmation
Public-source inference
Regulatory source support
Contradictory evidence
```

The goal is to make the assessment explainable.

---

# Phase 10 — Atlassian Capability Mapping

Expand capability mapping beyond a simple supported/not-supported relationship.

The future model should identify:

```text
Requirement
      ↓
Atlassian capability
      ↓
Support level
      ↓
Configuration required
      ↓
Additional product / dependency
      ↓
Limitation
      ↓
Migration impact
```

Possible outcomes:

```text
SUPPORTED
PARTIALLY_SUPPORTED
CONFIGURATION_REQUIRED
ADDITIONAL_PRODUCT_REQUIRED
THIRD_PARTY_DEPENDENCY
UNSUPPORTED
MANUAL_VERIFICATION_REQUIRED
```

This is where the compliance engine becomes directly useful for Atlassian migration readiness.

---

# Phase 11 — Assessment API, UI & Reporting

Introduce the user-facing workflow:

```text
Questionnaire
      ↓
Signal Collection
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
Evidence / Confidence
      ↓
Security & Compliance Readiness Report
```

Potential UI screens:

```text
Screen 1 — Questionnaire
Screen 2 — Results Overview
Screen 3 — Regulatory Themes
Screen 4 — Missing Information
Screen 5 — Evidence / Verification
Screen 6 — Exportable Report
```

---

# Phase 12 — External Data Sources & Continuous Assessment

Potential inputs:

* CRM / Salesforce
* Public company information
* Third-party data sources
* Customer-provided information
* Security questionnaires
* Future Data Center collectors
* Atlassian environment data

The assessment should improve as additional evidence becomes available.

For example:

```text
Initial assessment
    ↓
Firmographic signals
    ↓
Potentially applicable

Customer discovery
    ↓
More signals
    ↓
Likely applicable

Evidence collection
    ↓
Higher confidence

Migration/environment data
    ↓
Technical requirements
    ↓
Migration readiness
```

---

# Long-Term Assessment Model

The final system should support statuses such as:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
INSUFFICIENT_INFORMATION
MANUAL_REVIEW_REQUIRED
OUT_OF_SCOPE
```

The current:

```text
TRUE
FALSE
UNKNOWN
```

model remains a lower-level rule-engine primitive.

The higher-level assessment status should communicate business meaning without pretending that the engine has made a definitive legal determination.

---

# Detection vs Determination

This is a core product principle.

The system should distinguish:

```text
Detection
```

from:

```text
Final Legal Determination
```

For example:

```text
Signals indicate that GDPR may apply.
```

is different from:

```text
The organisation is legally subject to GDPR.
```

The first is appropriate for this system's detection/readiness role.

The second may require legal interpretation and human review.

Where evidence is insufficient, the system should explicitly say:

```text
INSUFFICIENT_INFORMATION
```

rather than manufacturing certainty.

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
9. **Applicability should be based on meaningful signals rather than simplistic region × industry × segment classification.**
10. **Evidence and confidence should be first-class concepts.**
11. **Technical and customer requirements must be distinguishable from laws and regulations.**
12. **Incomplete information must produce actionable unknowns and discovery questions.**
13. **Every production regulatory pack should be backed by authoritative sources.**
14. **Prototype regulatory packs must be clearly labelled as prototypes.**
15. **Complexity should be introduced only when an actual requirement demands it.**
16. **The architecture should support incremental expansion from a small proving ground to a broad compliance knowledge system.**

---

# Current Implementation Checkpoint

The current implementation has progressed beyond the original Phase 2A rule engine.

The core flow is now:

```text
Signal
   ↓
SignalData
   ↓
SignalEvidence
   ↓
Requirement Pack
   ↓
Applicability Rule
   ↓
RuleExpressionEvaluator
   ↓
RuleResult
   ↓
ApplicabilityDecisionService
   ↓
ApplicabilityStatus
   ↓
Missing Signals
   ↓
Discovery Questions
   ↓
Evidence Confidence
   ↓
Applicable Requirements
   ↓
Capability Mappings
   ↓
Assessment Result
```

---

# Current Capability Checklist

| Capability | Status |
|---|---|
| Java / Spring Boot foundation | Completed |
| Basic signal model | Completed |
| Evidence model | Completed |
| Generic rule model | Completed |
| JSON rule repository | Completed |
| Generic rule evaluator | Completed |
| TRUE / FALSE / UNKNOWN semantics | Completed |
| Explicit signal state | Completed |
| Typed signal data | Completed |
| Signal validation foundation | Completed |
| Nested rule expressions | Completed |
| AND / OR evaluation | Completed |
| Requirement Pack model | Completed |
| Requirement Pack JSON repository | Completed |
| Approved Requirement Pack filtering | Completed |
| Applicability decision | Completed |
| Missing signal detection | Completed |
| Discovery questions | Completed |
| Evidence confidence | Completed |
| Applicable requirement mapping | Completed |
| Atlassian capability mapping | Completed |
| Multi-Requirement Pack assessment | Completed |
| HIPAA proving-ground pack | Completed |
| GDPR proving-ground pack | Completed |
| Production-grade GDPR knowledge | Not started |
| Production-grade HIPAA knowledge | Not started |
| Full regulatory catalogue | Not started |
| Advanced evidence provenance | Not started |
| Full Atlassian capability catalogue | Not started |
| UI / dashboard | Not started |
| Report generation | Not started |
| Salesforce / CRM integration | Not started |
| Public data enrichment | Not started |
| Data Center collector | Not started |
| AI-assisted regulatory authoring | Not started |

---

# Current Next Action

Start the **Regulatory Knowledge Pack phase**.

The next concrete implementation should be:

```text
Upgrade GDPR from proving-ground configuration
to a properly sourced, explainable Requirement Pack.
```

The work should begin with:

```text
Authoritative sources
        ↓
Applicability scope
        ↓
Signals
        ↓
Derived concepts
        ↓
Rules
        ↓
Evidence requirements
        ↓
Verification requirements
        ↓
Discovery questions
        ↓
Requirements / obligations
        ↓
Tests
```

Do not immediately build:

* UI
* Database
* AI runtime
* Salesforce integration
* Data Center collector
* Complex external ingestion pipeline

The existing generic engine should first be proven against a **real, properly sourced regulatory knowledge model**.

Continue using the incremental development approach:

```text
One capability
      ↓
Implement
      ↓
Test
      ↓
Validate
      ↓
Commit
      ↓
Next capability
```

The architectural goal remains:

> **Build a generic compliance assessment engine where regulatory knowledge is version-controlled, source-backed configuration, while the Java application remains a generic evaluation and assessment platform.**
