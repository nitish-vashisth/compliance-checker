# Compliance Checker — High-Level Design

> **Status:** Architecture / Design
> **Purpose:** Define the generic architecture for a signal-driven requirements intelligence platform that identifies regulatory, compliance, security, architecture, sovereignty, and governance requirements that may affect a customer's migration to Atlassian Cloud.

---

## Table of Contents

1. [Problem Statement](#1-problem-statement)
2. [Clean Up the Current Theme List](#2-clean-up-the-current-theme-list)
3. [Proposed Generic Model](#3-proposed-generic-model)
4. [Requirement Types](#4-requirement-types)
5. [Complete Requirement Taxonomy](#5-complete-requirement-taxonomy)
6. [Final Requirement Hierarchy](#6-final-requirement-hierarchy)
7. [Generic Requirement Pack](#7-generic-requirement-pack)
8. [Assessment Model](#8-assessment-model)
9. [Detection vs Determination](#9-detection-vs-determination)
10. [Recommended Implementation Plan](#10-recommended-implementation-plan)
11. [Iterative Workflow for Every Requirement Pack](#11-iterative-workflow-for-every-requirement-pack)
12. [Proposed Repository Structure](#12-proposed-repository-structure)
13. [Recommended First Six Iterations](#13-recommended-first-six-iterations)
14. [Key Design Principles](#14-key-design-principles)

---

# 1. Problem Statement

Customers migrating from Atlassian Data Center to Atlassian Cloud can encounter security, compliance, privacy, sovereignty, architecture, and governance requirements late in the migration process.

These requirements may result in:

* Migration delays
* Additional security reviews
* Legal reviews
* Architecture changes
* Customer-side approval dependencies
* Product capability gaps
* Contractual requirements
* Data residency or sovereignty concerns
* Migration blockers
* Customer dissatisfaction or churn

The current approach of using a static:

```text
Region × Industry × Customer Segment
```

classification is insufficient.

A more useful approach is to build a **signal-driven requirements intelligence platform**.

The platform should evaluate available customer signals, derive concepts from those signals, apply explainable rules, identify missing information, ask targeted questions, and ultimately determine which requirements may affect the customer's migration.

The high-level flow is:

```text
Customer Signals
       ↓
Evidence
       ↓
Signal / Concepts
       ↓
Rule Evaluation
       ↓
Applicability
       ↓
Confidence
       ↓
Missing Information
       ↓
Targeted Questions
       ↓
Assessment
       ↓
Requirements / Obligations
       ↓
Atlassian Capability Mapping
       ↓
Migration Risk
```

The system must not pretend to provide definitive legal advice.

Instead, it should clearly distinguish between:

* What is known
* What is inferred
* What is unknown
* What is likely applicable
* What requires customer confirmation
* What requires legal/compliance review

---

# 2. Clean Up the Current Theme List

The original theme list contains duplicate themes.

For example:

* Theme 9 and Theme 18 → Multi-Org / Tenant Consolidation Governance
* Theme 10 and Theme 19 → NIS2 / Critical Infrastructure

Therefore, there are currently **23 unique themes rather than 25**.

The taxonomy should also be decomposed where a single theme currently combines several unrelated regulatory systems.

## 2.1 Government / Sovereign Requirements

The previous:

```text
FedRAMP / US Government Authorisation
```

theme actually contains:

* FedRAMP
* StateRAMP
* CJIS
* DoD Impact Levels
* Federal contractor requirements

These should eventually become separate requirement packs because they have different:

* Applicability rules
* Authorities
* Evidence requirements
* Sources
* Customer signals
* Verification requirements

---

## 2.2 Privacy Requirements

Similarly:

```text
India DPDP / APAC Data Protection
```

should not be treated as one rule pack.

It should eventually become separate packs:

* India DPDP
* China PIPL
* Japan APPI
* Singapore PDPA
* South Korea PIPA
* South Africa POPIA
* Brazil LGPD
* California CCPA / CPRA

The UI can still group these under:

```text
Privacy & Data Protection
```

However, the knowledge system should treat each law independently.

This allows each law to have its own:

* Jurisdiction
* Applicability rules
* Thresholds
* Definitions
* Sources
* Effective dates
* Requirements
* Evidence
* Questions
* Tests

---

# 3. Proposed Generic Model

The earlier model was effectively:

```text
Regulation
    ↓
Rules
```

This is too restrictive for the problem we are trying to solve.

The platform needs to support laws, regulations, standards, government authorisations, technical requirements, customer requirements, and migration process risks.

The proposed generic model is:

```text
Requirement Theme
        ↓
Requirement Type
        ↓
Applicability Signals
        ↓
Derived Concepts
        ↓
Applicability / Trigger Rules
        ↓
Requirements / Obligations
        ↓
Verification Requirements
        ↓
Atlassian Capability Mapping
        ↓
Migration Risk
```

The important addition is:

```text
Requirement Type
```

This allows the same engine to process fundamentally different kinds of requirements.

For example:

```text
HIPAA
Type = LAW / REGULATORY FRAMEWORK
```

while:

```text
CMK
Type = TECHNICAL_REQUIREMENT
```

and:

```text
CAB Review
Type = PROCESS_RISK
```

The engine can process all three using the same underlying model.

---

# 4. Requirement Types

The platform should support the following top-level requirement types.

---

## 4.1 Laws & Regulations

These are based on statutory or regulatory applicability.

Examples:

* HIPAA
* GDPR
* DORA
* NIS2
* India DPDP
* China PIPL
* Japan APPI
* Singapore PDPA
* South Korea PIPA
* South Africa POPIA
* Brazil LGPD
* CCPA / CPRA
* EU AI Act

The engine asks:

> **Does this organisation fall within the scope of this law or regulation?**

---

## 4.2 Government / Sovereign Authorisation

Examples:

* FedRAMP
* StateRAMP
* CJIS
* DoD Impact Levels
* IRAP

The engine asks:

> **Does the organisation operate in a government or regulated environment where this authorisation may be required?**

---

## 4.3 Industry / Security Standards

Examples:

* PCI DSS
* ISO 27001
* SOC 2
* C5
* TISAX
* BSI IT-Grundschutz
* HITRUST

The engine asks:

> **Is this standard mandatory, contractually required, regulator-driven, or simply requested by the customer's internal policy?**

This distinction is important.

For example:

### PCI DSS

PCI DSS can become mandatory based on:

* Cardholder data
* Payment processing
* Cardholder Data Environment (CDE)
* Scope of the environment

### SOC 2

SOC 2 may instead be:

* A vendor assurance requirement
* A procurement requirement
* A customer security requirement
* An internal enterprise requirement

Therefore, the system should not always return:

```text
SOC 2 applies
```

It may instead return:

```text
SOC 2 evidence likely to be requested
```

---

## 4.4 Customer Security / Architecture Requirements

Examples:

* Data Residency
* Data Sovereignty
* Customer Managed Keys (CMK)
* HSM
* IP Allowlisting
* Private Connectivity
* SIEM
* Egress Control
* DLP
* Sensitive Data Classification
* Granular RBAC
* SCIM
* SSO

These are generally **not laws by themselves**.

They may be triggered by:

```text
Law
+
Industry Regulation
+
Government Policy
+
Customer Security Policy
+
Architecture Standard
```

Example:

```text
Government Customer
        ↓
Data Sovereignty Requirement
        ↓
Specific Personnel Access Requirement
        ↓
Atlassian Cloud Capability Assessment
```

---

## 4.5 Internal Governance / Migration Process

Examples:

* Legal Review
* InfoSec Review
* CAB Approval
* Security Questionnaire
* Tenant Consolidation Governance
* Migration Approval Workflow

The engine asks:

> **Is this likely to become a migration process blocker?**

This is not a legal applicability engine.

It is a:

```text
Migration Risk Detection Engine
```

---

# 5. Complete Requirement Taxonomy

The recommended taxonomy is divided into the following groups.

---

# A. Privacy & Data Protection

## A01 — GDPR

### Signals

* EU establishment
* EU customers
* EU data subjects
* Personal data processing
* Offering services to EU individuals
* Monitoring EU individuals

---

## A02 — GDPR Contractual & Data Transfer

This should be separated from the core GDPR applicability pack.

Potential requirements include:

* DPA
* SCC
* International data transfers
* Subprocessor transparency
* Data Protection Impact Assessment
* Data subject rights

These may be consequences of GDPR or customer privacy requirements rather than separate laws.

---

## A03 — India DPDP

### Signals

* India operations
* India customers
* Indian data principals
* Digital personal data
* Processing activities
* Cross-border transfers

---

## A04 — California CCPA / CPRA

### Signals

* California consumers
* Business thresholds
* Revenue
* Consumer personal information
* Commercial activity

---

## A05 — Brazil LGPD

### Signals

* Brazilian data subjects
* Brazil operations
* Personal data processing

---

## A06 — China PIPL

### Signals

* China operations
* Chinese individuals
* Personal information processing
* Cross-border transfer
* Critical information infrastructure

---

## A07 — Japan APPI

Japan's privacy requirements should use the shared privacy signal ontology while maintaining an independent requirement pack.

---

## A08 — Singapore PDPA

Singapore privacy requirements should use the shared privacy signal ontology while maintaining an independent requirement pack.

---

## A09 — South Korea PIPA

South Korean privacy requirements should use the shared privacy signal ontology while maintaining an independent requirement pack.

---

## A10 — South Africa POPIA

South African privacy requirements should use the shared privacy signal ontology while maintaining an independent requirement pack.

---

## Shared Privacy Signal Ontology

The privacy packs should reuse common signals.

For example:

```text
Shared Privacy Signals
        │
        ├── GDPR Rules
        ├── DPDP Rules
        ├── PIPL Rules
        ├── LGPD Rules
        ├── APPI Rules
        ├── PDPA Rules
        ├── PIPA Rules
        └── POPIA Rules
```

The goal is **not** to create ten completely separate questionnaires.

Instead:

```text
Shared Signal Model
        ↓
Law-specific applicability rules
```

This is one of the key scalability mechanisms of the platform.

---

# B. Healthcare & Life Sciences

## B01 — HIPAA

### Signals

* Healthcare provider
* Health plan
* Healthcare clearinghouse
* Healthcare customer
* Patient data
* Identifiable health information
* Electronic PHI
* Acting on behalf of a covered entity

---

## B02 — HITRUST

Important distinction:

```text
HIPAA
    ↓
Legal / Regulatory Framework

HITRUST
    ↓
Assurance / Certification Framework
```

The system should detect:

```text
HITRUST likely to be requested
```

rather than:

```text
HITRUST legally applies
```

unless there is a specific contractual or regulatory requirement.

---

# C. Financial Services & Payments

## C01 — DORA

### Signals

* EU financial entity
* Bank
* Insurance firm
* Investment firm
* Financial market infrastructure
* ICT service dependency
* EU operations

---

## C02 — PCI DSS

### Signals

* Payment card data
* PAN
* Cardholder Data Environment
* Payment processing
* Payment service
* Card data stored in Jira
* Card data stored in Confluence

---

## C03 — Financial Sector Regulatory Signals

Future packs may include:

* Financial services outsourcing
* Operational resilience
* Third-party risk
* Data retention
* Financial authority requirements

This can become a reusable:

```text
Financial Services Regulatory Profile
```

---

# D. Government & Public Sector

Government requirements should be broken into separate packs.

---

## D01 — FedRAMP

### Signals

* US federal agency customer
* Federal contractor
* Federal information
* Federal system
* Cloud service for government
* FedRAMP mentioned in procurement

---

## D02 — StateRAMP

### Signals

* US state government customer
* State procurement
* StateRAMP requirement

---

## D03 — CJIS

### Signals

* Law enforcement customer
* Criminal justice information
* State/local law enforcement agency
* CJIS data

---

## D04 — DoD Impact Levels

### Signals

* US Department of Defense customer
* Defense contractor
* Controlled Unclassified Information
* DoD workload
* Impact Level requirement

---

## D05 — IRAP

### Signals

* Australian government customer
* Australian government data
* PROTECTED classification
* Defence-related workload
* Australian government procurement

---

# E. Critical Infrastructure

## E01 — NIS2

NIS2 should **not** simply be evaluated as:

```text
EU company = NIS2
```

The rules need to evaluate multiple dimensions:

```text
Jurisdiction
    +
Sector
    +
Entity Category
    +
Size
    +
National Implementation
```

### Signals

* EU entity
* Sector
* Entity size
* Energy
* Transport
* Healthcare
* Digital infrastructure
* Water
* Wastewater
* Financial market infrastructure
* Public administration
* Manufacturing
* Critical digital services

This is exactly the type of requirement where a signal-based engine provides value.

---

# F. Data Location & Sovereignty

This area should be separated into multiple requirement packs.

---

## F01 — Data Residency

### Signals

* Required data location
* Customer country
* Data classification
* Regulatory requirement
* Government requirement
* Contractual requirement

The output should distinguish between:

| Requirement Source               | Meaning                                          |
| -------------------------------- | ------------------------------------------------ |
| Explicit legal requirement       | Required by applicable law                       |
| Regulatory expectation           | Expected by regulator or regulatory framework    |
| Customer contractual requirement | Required by contract                             |
| Customer preference              | Customer preference rather than legal obligation |

---

## F02 — Data Sovereignty

### Signals

* Government customer
* National security workload
* Personnel nationality requirement
* Security clearance requirement
* Foreign legal exposure concern
* Export control

---

## F03 — Export Controls

Potential future sub-packs:

* ITAR
* EAR
* Korean strategic technology / NCT-related controls
* Other national restrictions

These should not be merged with generic data residency.

---

# G. AI Governance

## G01 — EU AI Act

### Signals

* EU deployment
* AI usage
* AI feature usage
* Purpose of AI
* Affected individuals
* Employment use
* Credit use
* Healthcare use
* Law enforcement use
* Public sector use
* High-risk use case

The system should **not** simply ask:

```text
Are you high risk under the EU AI Act?
```

Instead, it should ask questions that allow the engine to derive the classification:

```text
What is the AI being used for?

Who is affected?

Does it influence decisions?

Is it used for employment?

Is it used in critical infrastructure?
```

The engine can then derive a possible classification.

---

## G02 — Enterprise AI Governance

This should remain separate from the EU AI Act.

### Signals

* AI prohibited by policy
* Customer requires AI feature controls
* Data sharing restrictions
* Model provider restrictions
* BYO model requirement
* Content exclusion requirement

This is primarily:

```text
Customer Policy / Governance
```

rather than:

```text
Law Applicability
```

---

# H. National & Sector Cloud Assurance

## H01 — C5

### Signals

* Germany
* German government
* BaFin-regulated organisation
* Cloud procurement
* C5 explicitly required

---

## H02 — TISAX

### Signals

* Automotive industry
* Automotive OEM customer
* Automotive supplier
* TISAX requirement

---

## H03 — BSI IT-Grundschutz

### Signals

* German public sector
* Critical infrastructure
* Government procurement

---

## H04 — Other National Cloud Schemes

Potential future packs:

* Korean CSP Stability Assessment
* EUCS
* Other sovereign cloud schemes

---

# I. Security Assurance & Evidence

These should be treated differently from laws.

---

## I01 — ISO 27001

Likely output:

```text
Customer assurance requirement detected
```

### Signals

* Industry
* Procurement
* Security questionnaire
* ISO requirement
* Government / enterprise policy

---

## I02 — SOC 2

### Signals

* Vendor security review
* US enterprise
* Procurement requirement
* Customer security policy

---

## I03 — CSA STAR

Same category:

```text
Security Assurance Evidence Requirement
```

These can share a parent capability:

```text
Third-Party Security Assurance Requirement
```

---

# J. Network & Connectivity

These are not regulatory packs.

They belong to:

```text
Architecture Requirement Detection
```

---

## J01 — IP Allowlisting

### Signals

* Corporate network restriction
* Zero Trust policy
* Restricted inbound access
* Customer IP policy
* Security policy

---

## J02 — Egress Control

### Signals

* Outbound filtering
* CASB
* Proxy
* Firewall policy
* Domain allowlisting

---

## J03 — Private Connectivity

### Signals

* Private network requirement
* No public internet policy
* Private connectivity architecture

---

# K. Security Monitoring & Investigation

## K01 — Audit Logging

### Signals

* Security operations maturity
* Regulated industry
* Incident response requirement
* Forensics requirement
* Audit retention

---

## K02 — SIEM Integration

### Signals

* Splunk
* Datadog
* Sumo Logic
* Security monitoring requirement
* Central logging policy

---

# L. Encryption & Key Management

## L01 — Encryption Requirement

### Signals

* Sensitive data
* Regulated workload
* Customer encryption policy

---

## L02 — Customer Managed Keys

### Signals

* Customer controls encryption keys
* Government policy
* Financial sector
* Highly regulated environment
* Explicit CMK requirement

---

## L03 — HSM / External Key Management

### Signals

* HSM requirement
* External key control
* XKS requirement
* Sovereign key requirement

---

# M. Identity & Access Management

These should reuse a common IAM signal model.

---

## M01 — SSO / Identity Federation

### Signals

* Corporate IdP
* Okta
* Microsoft Entra
* Ping
* ADFS
* Federated authentication
* Multiple identity providers

---

## M02 — SCIM / Identity Lifecycle

### Signals

* Automated provisioning
* Automated deprovisioning
* Group synchronization
* Lifecycle automation

---

## M03 — Granular RBAC

### Signals

* Complex roles
* Separation of duties
* Compliance administrator
* Security administrator
* Read-only security access
* Business-unit separation

---

# N. Data Protection Controls

## N01 — Sensitive Data Classification

### Signals

* Personal data
* PHI
* Financial data
* Government data
* Intellectual property
* Data classification policy

---

## N02 — DLP

### Signals

* Sensitive content
* Data leakage concern
* Pre-migration scanning
* Ongoing detection requirement

---

# O. Security Testing & Review

## O01 — Penetration Testing

### Signals

* Customer security sign-off
* Penetration test requirement
* Vendor assessment
* Regulated industry

---

## O02 — Vulnerability Scanning

### Signals

* Customer-managed scanning policy
* Vulnerability management requirement
* Security approval process

---

## O03 — Security Questionnaire

### Signals

* SIG
* CAIQ
* Bespoke questionnaire
* Third-party risk review

---

# P. Governance & Organisational Structure

## P01 — Legal / InfoSec / CAB Review

This is not a regulatory rule.

Instead, the engine detects:

```text
Internal Approval Risk
```

### Signals

* Legal review required
* InfoSec approval
* CAB process
* Change freeze
* Procurement dependency
* Security questionnaire

### Output

```text
Migration Process Risk: HIGH

Likely blocker:
Customer-side security approval
```

---

## P02 — Tenant / Organisation Governance

### Signals

* Multiple Atlassian organisations
* Multiple sites
* Business-unit separation
* M&A
* Tenant consolidation
* Central administration
* Security administration

### Output

```text
Migration Architecture Complexity
```

rather than:

```text
Regulation applies
```

---

# 6. Final Requirement Hierarchy

The final target hierarchy is:

```text
REQUIREMENTS INTELLIGENCE PLATFORM
│
├── A. Privacy & Data Protection
│   ├── GDPR
│   ├── GDPR Contractual / DPA / SCC
│   ├── India DPDP
│   ├── CCPA / CPRA
│   ├── LGPD
│   ├── PIPL
│   ├── APPI
│   ├── PDPA
│   ├── PIPA
│   └── POPIA
│
├── B. Healthcare
│   ├── HIPAA
│   └── HITRUST
│
├── C. Financial & Payments
│   ├── DORA
│   ├── PCI DSS
│   └── Future financial regulations
│
├── D. Government & Public Sector
│   ├── FedRAMP
│   ├── StateRAMP
│   ├── CJIS
│   ├── DoD Impact Levels
│   └── IRAP
│
├── E. Critical Infrastructure
│   └── NIS2
│
├── F. Data Location & Sovereignty
│   ├── Data Residency
│   ├── Data Sovereignty
│   └── Export Controls
│
├── G. AI Governance
│   ├── EU AI Act
│   └── Enterprise AI Governance
│
├── H. National / Sector Cloud Assurance
│   ├── C5
│   ├── TISAX
│   ├── BSI IT-Grundschutz
│   └── Other national schemes
│
├── I. Security Assurance
│   ├── ISO 27001
│   ├── SOC 2
│   └── CSA STAR
│
├── J. Network & Connectivity
│   ├── IP Allowlisting
│   ├── Egress Control
│   └── Private Connectivity
│
├── K. Monitoring & Audit
│   ├── Audit Logging
│   └── SIEM Integration
│
├── L. Encryption & Key Management
│   ├── Encryption
│   ├── CMK
│   └── HSM / XKS
│
├── M. Identity & Access
│   ├── SSO
│   ├── SCIM
│   └── RBAC
│
├── N. Data Protection Controls
│   ├── Data Classification
│   └── DLP
│
├── O. Security Assessment
│   ├── Penetration Testing
│   ├── Vulnerability Scanning
│   └── Security Questionnaires
│
└── P. Migration Governance
    ├── Legal / InfoSec / CAB
    └── Multi-Org Governance
```

---

# 7. Generic Requirement Pack

The core engine should not care whether the requirement pack is HIPAA, GDPR, FedRAMP, CMK, or CAB Review.

Every requirement pack should follow a common structure.

```text
Requirement Pack
│
├── Metadata
│
├── Requirement Type
│
├── Applicability Signals
│
├── Derived Concepts
│
├── Rules
│
├── Evidence
│
├── Unknowns
│
├── Questions
│
├── Sources
│
├── Requirements / Obligations
│
├── Capability Mappings
│
└── Tests
```

---

## 7.1 Requirement Type Examples

| Requirement    | Type                                               |
| -------------- | -------------------------------------------------- |
| HIPAA          | `LAW` / `REGULATORY_FRAMEWORK`                     |
| GDPR           | `LAW`                                              |
| DORA           | `REGULATION`                                       |
| FedRAMP        | `AUTHORISATION`                                    |
| PCI DSS        | `STANDARD`                                         |
| ISO 27001      | `STANDARD`                                         |
| SOC 2          | `CUSTOMER_REQUIREMENT` / `ASSURANCE`               |
| CMK            | `TECHNICAL_REQUIREMENT`                            |
| Data Residency | `TECHNICAL_REQUIREMENT` / `REGULATORY_CONSEQUENCE` |
| CAB Review     | `PROCESS_RISK`                                     |

The exact classification can evolve as the ontology matures.

The important principle is that the engine must not assume every requirement is a law.

---

# 8. Assessment Model

The system must **never force a Yes / No answer**.

Every assessment should support uncertainty.

## 8.1 Assessment Status

| Status                     | Meaning                                                      |
| -------------------------- | ------------------------------------------------------------ |
| `LIKELY_APPLICABLE`        | Available evidence strongly indicates applicability          |
| `POTENTIALLY_APPLICABLE`   | Some signals indicate relevance, but evidence is incomplete  |
| `NOT_CURRENTLY_INDICATED`  | Available evidence does not currently indicate applicability |
| `INSUFFICIENT_INFORMATION` | Important information is missing                             |
| `MANUAL_REVIEW_REQUIRED`   | Human legal/compliance validation is required                |
| `OUT_OF_SCOPE`             | Requirement is not relevant to the assessed scenario         |

---

## 8.2 Example

### DORA

```text
Status:
MANUAL_REVIEW_REQUIRED

Reason:
The organisation appears to operate in financial services,
but the available information is insufficient to determine
whether it falls within the relevant entity category.
```

### What is known

```text
✓ EU operations
✓ Financial services customer
```

### What is missing

```text
? Legal entity classification
? Regulated activity
? Supervisory authority
```

### Source

```text
[Authoritative regulatory reference]
```

### Recommended action

```text
Validate with customer's compliance/legal team.
```

This is safer than:

```text
DORA = YES
```

---

# 9. Detection vs Determination

The product should explicitly distinguish between **Detection** and **Determination**.

This is one of the most important concepts in the system.

---

## 9.1 Level 1 — Detection

The system says:

> We detected signals that this may be relevant.

Example:

```text
Potential DORA relevance detected.
```

This means the engine has identified relevant signals, but does not necessarily have enough information to conclude applicability.

---

## 9.2 Level 2 — Determination

Only when sufficient evidence exists should the system produce a stronger conclusion.

Example:

```text
DORA applicability strongly indicated.
```

For complex cases:

```text
Legal / compliance verification required.
```

This prevents the product from pretending to provide definitive legal advice.

---

# 10. Recommended Implementation Plan

We should not start by implementing 30+ requirement packs.

Instead, the platform should be built iteratively.

---

## Phase 0 — Build the Generic Foundation

Before adding many themes, implement:

1. Signal ontology
2. Requirement Pack schema
3. Source model
4. Evidence model
5. Rule model
6. Three-valued logic
7. Derived concept engine
8. Confidence model
9. Missing-information detection
10. Question prioritisation
11. Versioning
12. Regression testing
13. Assessment reproducibility

This is the actual platform foundation.

---

# Phase 1 — HIPAA

HIPAA becomes the reference implementation.

Implement the complete flow:

```text
Signals
   ↓
Derived Concepts
   ↓
Rules
   ↓
Evidence
   ↓
Confidence
   ↓
Missing Signals
   ↓
Targeted Questions
   ↓
Final Assessment
   ↓
Atlassian Capability Mapping
```

This proves that the generic architecture works end-to-end.

---

# Phase 2 — One Requirement from Each Category

Instead of immediately adding multiple privacy laws, validate the generic architecture against different requirement types.

Recommended sequence:

| Category                 | Requirement                  |
| ------------------------ | ---------------------------- |
| Law                      | GDPR                         |
| Government Authorisation | FedRAMP                      |
| Industry Regulation      | DORA                         |
| Technical Requirement    | Data Residency               |
| Security Standard        | PCI DSS                      |
| Process Blocker          | Legal / InfoSec / CAB Review |

After these six, evaluate whether the architecture is genuinely generic.

---

# Phase 3 — Build Shared Signal Domains

Once the initial requirement packs work, create reusable signal domains.

Recommended domains:

```text
organisation-signals
jurisdiction-signals
industry-signals
customer-signals
data-signals
processing-signals
government-signals
security-signals
identity-signals
network-signals
data-location-signals
ai-signals
governance-signals
```

Future requirement packs should reuse these domains rather than creating duplicate signals.

---

# Phase 4 — Expand by Shared Domain

Recommended expansion order:

## Privacy

* GDPR
* DPDP
* CCPA / CPRA
* LGPD
* PIPL
* APPI
* PDPA
* PIPA
* POPIA

## Government

* FedRAMP
* StateRAMP
* CJIS
* DoD IL
* IRAP

## Financial

* DORA
* PCI DSS

## Critical Infrastructure

* NIS2

## Cloud Assurance

* C5
* TISAX

## Architecture

* Data Residency
* Data Sovereignty
* CMK
* IP Allowlisting
* Private Connectivity

## Security Operations

* Audit Logging
* SIEM
* Penetration Testing
* Vulnerability Management
* DLP
* Data Classification

## Identity

* SSO
* SCIM
* RBAC

## Governance

* Legal Review
* InfoSec Review
* CAB
* Multi-Org Governance

---

# 11. Iterative Workflow for Every Requirement Pack

Every requirement pack should follow the same workflow.

---

## Step 1 — Define the Requirement

Example:

```text
Theme:
DORA

Type:
LAW / REGULATION

Jurisdiction:
European Union

Primary Authority:
[Official authority]

Purpose:
Digital operational resilience for financial entities.
```

---

# Step 2 — Break It Into Sub-Concepts

For DORA:

```text
financial_entity
eu_jurisdiction
ict_service_dependency
third_party_ict_provider
operational_resilience
incident_management
resilience_testing
third_party_risk
```

---

# Step 3 — Identify Authoritative Sources

Every concept and rule must be linked to:

* Primary source
* Official guidance
* Relevant article / section
* Effective date
* Last reviewed date

Example:

```text
Source
├── authority
├── title
├── URL
├── article / section
├── effectiveDate
├── retrievedDate
└── lastReviewedDate
```

The system should prefer authoritative sources over secondary sources.

---

# Step 4 — Identify Signals

Example DORA signals:

* Headquarters country
* Operating countries
* Industry
* Sub-industry
* Financial licence
* Financial regulator
* Customer types
* ICT dependency
* Atlassian usage

---

# Step 5 — Classify Signals by Collection Stage

Signals should be collected progressively.

| Stage   | Description                     |
| ------- | ------------------------------- |
| Phase 0 | Automatically available         |
| Phase 1 | Low-friction customer signals   |
| Phase 2 | Usage / architecture signals    |
| Phase 3 | Targeted verification questions |
| Phase 4 | Manual expert review            |

The goal is to avoid asking customers a huge questionnaire upfront.

---

# Step 6 — Define Derived Concepts

Example:

```text
eu_financial_entity_candidate
regulated_financial_activity_candidate
dora_scope_candidate
```

Derived concepts are intermediate conclusions produced from raw signals.

---

# Step 7 — Create Applicability Rules

Rules should use:

```text
TRUE
FALSE
UNKNOWN
```

rather than simple Boolean classification.

Example:

```text
IF
    customer_operates_in_eu = TRUE
AND
    financial_services = TRUE
AND
    regulated_entity = UNKNOWN

THEN

    dora_scope_candidate = UNKNOWN
```

This preserves uncertainty.

---

# Step 8 — Define Confidence

Example:

| Confidence | Evidence                                           |
| ---------- | -------------------------------------------------- |
| High       | Customer-confirmed regulated entity                |
| Medium     | Industry and geography strongly indicate relevance |
| Low        | Public information only                            |

Confidence should be tied to evidence rather than simply assigned by the rule.

---

# Step 9 — Define Targeted Questions

Only ask questions that can materially change the result.

For example:

```text
Does the organisation hold a financial services licence
or operate under a financial supervisory authority?
```

is more valuable than asking a generic:

```text
Are you regulated?
```

The question engine should prioritize questions based on their ability to reduce uncertainty.

---

# Step 10 — Define Obligations

Separate:

```text
Applicability
```

from:

```text
What becomes important if applicable?
```

For example:

```text
DORA
    ↓
Applicability
    ↓
ICT third-party risk
    ↓
Operational resilience
    ↓
Incident management
    ↓
Testing
```

This distinction prevents the engine from confusing applicability with downstream obligations.

---

# Step 11 — Map to Atlassian Capability

Each requirement should eventually map to an Atlassian capability assessment.

Supported outcomes:

```text
SUPPORTED
SUPPORTED_WITH_CONFIGURATION
SUPPORTED_WITH_CONTRACT
PARTIALLY_SUPPORTED
NOT_SUPPORTED
ROADMAP
UNKNOWN
MANUAL_VERIFICATION_REQUIRED
```

Example:

```text
Requirement
    ↓
Atlassian Capability
    ↓
Capability Status
    ↓
Migration Impact
```

---

# Step 12 — Create Regression Tests

Every requirement pack must include:

* Positive cases
* Negative cases
* Unknown cases
* Edge cases
* Conflicting evidence
* Historical version tests

Example:

```text
Test Case
├── Input Signals
├── Expected Derived Concepts
├── Expected Rule Results
├── Expected Assessment
├── Expected Confidence
└── Expected Missing Information
```

This is essential because regulatory logic will evolve over time.

---

# 12. Proposed Repository Structure

The project should eventually move toward a structure similar to:

```text
requirements-intelligence/
│
├── README.md
│
├── docs/
│   ├── architecture.md
│   ├── signal-ontology.md
│   ├── rule-engine.md
│   ├── evidence-model.md
│   ├── source-management.md
│   ├── question-engine.md
│   ├── confidence-model.md
│   ├── capability-mapping.md
│   └── roadmap.md
│
├── core/
│   │
│   ├── schemas/
│   │   ├── signal.schema.json
│   │   ├── requirement-pack.schema.json
│   │   ├── rule.schema.json
│   │   ├── evidence.schema.json
│   │   └── assessment.schema.json
│   │
│   ├── ontology/
│   │   ├── organisation.json
│   │   ├── jurisdiction.json
│   │   ├── industry.json
│   │   ├── customer.json
│   │   ├── data.json
│   │   ├── processing.json
│   │   └── security.json
│   │
│   └── engine/
│
├── packs/
│   │
│   ├── privacy/
│   │   ├── gdpr/
│   │   ├── dpdp/
│   │   ├── ccpa-cpra/
│   │   ├── lgpd/
│   │   ├── pipl/
│   │   ├── appi/
│   │   ├── pdpa/
│   │   ├── pipa/
│   │   └── popia/
│   │
│   ├── healthcare/
│   │   ├── hipaa/
│   │   └── hitrust/
│   │
│   ├── government/
│   │   ├── fedramp/
│   │   ├── stateramp/
│   │   ├── cjis/
│   │   ├── dod-il/
│   │   └── irap/
│   │
│   ├── financial/
│   │   ├── dora/
│   │   └── pci-dss/
│   │
│   ├── critical-infrastructure/
│   │   └── nis2/
│   │
│   ├── data/
│   │   ├── data-residency/
│   │   ├── data-sovereignty/
│   │   └── export-controls/
│   │
│   ├── ai/
│   │   ├── eu-ai-act/
│   │   └── enterprise-ai-governance/
│   │
│   ├── assurance/
│   │   ├── iso27001/
│   │   ├── soc2/
│   │   ├── c5/
│   │   └── tisax/
│   │
│   ├── architecture/
│   │   ├── cmk/
│   │   ├── ip-allowlisting/
│   │   ├── egress/
│   │   └── private-connectivity/
│   │
│   ├── identity/
│   │   ├── sso/
│   │   ├── scim/
│   │   └── rbac/
│   │
│   └── governance/
│       ├── legal-review/
│       ├── infosec-review/
│       ├── cab/
│       └── tenant-governance/
│
├── sources/
│
├── registry/
│
├── capabilities/
│   └── atlassian/
│
└── tests/
```

---

# 13. Recommended First Six Iterations

To prove that the platform works across different requirement types, use the following order.

| Iteration | Requirement                            | Why                                                     |
| --------- | -------------------------------------- | ------------------------------------------------------- |
| 1         | HIPAA                                  | Complex applicability based on data + relationship      |
| 2         | GDPR                                   | Privacy law with jurisdiction + processing logic        |
| 3         | FedRAMP                                | Government / customer-driven authorisation              |
| 4         | DORA                                   | Industry + jurisdiction + legal entity logic            |
| 5         | Data Residency                         | Requirement can originate from law, contract, or policy |
| 6         | IP Allowlisting / Private Connectivity | Pure technical requirement detection                    |

After these six iterations, stop and evaluate the architecture.

The core question is:

> Does the same generic model work across all six requirement types?

The expected common flow is:

```text
Signals
   ↓
Concepts
   ↓
Rules
   ↓
Evidence
   ↓
Questions
   ↓
Assessment
   ↓
Capability
```

If the same model works across all six, the generic platform architecture has been validated.

After that, adding the remaining requirements should become primarily a **knowledge-pack development problem**, rather than a platform-development problem.

---

# 14. Key Design Principles

## 14.1 Evidence Over Assumptions

The system should never claim something that the available evidence does not support.

```text
Evidence
   ↓
Inference
   ↓
Assessment
```

not:

```text
Guess
   ↓
YES / NO
```

---

## 14.2 Never Force Yes / No

Every result must be able to express uncertainty.

Supported states include:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
INSUFFICIENT_INFORMATION
MANUAL_REVIEW_REQUIRED
OUT_OF_SCOPE
```

---

## 14.3 Detection Is Not Determination

The system may detect:

```text
Potential DORA relevance
```

without determining:

```text
DORA definitely applies
```

The distinction must be explicit in both the domain model and UI.

---

## 14.4 Separate Applicability from Obligations

The engine should first determine:

```text
Does this requirement potentially apply?
```

Then:

```text
What obligations or controls become relevant?
```

These are separate questions.

---

## 14.5 Separate Law from Standard from Customer Requirement

The system should never treat all compliance requirements as laws.

For example:

```text
HIPAA
    → Legal / regulatory framework

ISO 27001
    → Security standard / assurance

SOC 2
    → Assurance / customer requirement

FedRAMP
    → Government authorisation

CMK
    → Technical requirement

CAB Review
    → Migration process risk
```

This distinction is fundamental to the architecture.

---

## 14.6 Reuse Signals

Do not build independent questionnaires for every requirement.

Instead:

```text
Shared Signal Ontology
        ↓
Requirement-specific rules
```

For example:

```text
country
industry
data_type
processing_activity
customer_type
government_customer
regulated_entity
security_policy
data_location
```

can be reused by many requirement packs.

---

## 14.7 Ask the Minimum Necessary Questions

Customer questions should be selected based on their ability to reduce uncertainty.

The ideal flow is:

```text
Automatically available signals
            ↓
Low-friction questions
            ↓
Architecture / usage signals
            ↓
Targeted verification
            ↓
Manual review only when necessary
```

The system should avoid presenting customers with a huge compliance questionnaire upfront.

---

## 14.8 Every Rule Must Be Explainable

For every assessment, the system should be able to answer:

```text
Why did you reach this result?
```

The answer should identify:

* Signals used
* Evidence supporting those signals
* Rules triggered
* Derived concepts
* Missing information
* Confidence
* Source
* Recommended next action

Example:

```text
Result:
Potential DORA relevance

Why:
✓ Customer operates in the EU
✓ Customer operates in financial services
✓ Customer uses cloud ICT services

Unknown:
? Regulated entity classification
? Supervisory authority

Next question:
Does the organisation operate under a financial
services licence or supervisory authority?

Source:
[Authoritative source]
```

---

## 14.9 Sources Must Be First-Class Data

Every important requirement and rule should have traceability to its source.

At minimum:

```text
Source
├── Authority
├── Title
├── URL
├── Article / Section
├── Effective Date
├── Retrieved Date
└── Last Reviewed Date
```

This enables:

* Auditing
* Review
* Versioning
* Regulatory change management
* Explainability
* Human validation

---

## 14.10 Assessments Must Be Reproducible

Given the same:

```text
Input Signals
+
Evidence
+
Requirement Pack Version
+
Rule Version
```

the system should produce the same assessment.

Therefore, assessment results should retain:

```text
assessmentId
timestamp
requirementPackVersion
ruleVersion
signals
evidence
ruleResults
derivedConcepts
assessmentStatus
confidence
missingInformation
questions
```

This will be important when requirements or rules change.

---

## 14.11 Historical Versions Matter

Regulatory requirements change.

Therefore:

```text
Requirement Pack
        ↓
Version 1
Version 2
Version 3
...
```

should be supported.

Historical assessments should remain explainable against the version that produced them.

---

# Final Architecture Principle

The system should not be positioned as simply a:

```text
Privacy Law Checker
```

The broader and more accurate description is:

> **A signal-driven requirements intelligence platform that detects regulatory, compliance, security, architecture, sovereignty, and governance requirements that may affect a customer's migration to Atlassian Cloud.**

The central architecture is:

```text
                    ┌───────────────────────┐
                    │   Customer Signals    │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │       Evidence        │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │   Signal Ontology     │
                    │   + Derived Concepts  │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │     Rule Engine       │
                    │ TRUE / FALSE / UNKNOWN│
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │ Applicability Engine  │
                    └───────────┬───────────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
                    ▼                       ▼
          ┌─────────────────┐    ┌──────────────────┐
          │    Confidence   │    │ Missing Information│
          └────────┬────────┘    └─────────┬────────┘
                   │                       │
                   └───────────┬───────────┘
                               ▼
                    ┌───────────────────────┐
                    │   Question Engine     │
                    └───────────┬───────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │     Assessment        │
                    │                       │
                    │ Likely Applicable     │
                    │ Potentially Applicable│
                    │ Not Indicated         │
                    │ Insufficient Info     │
                    │ Manual Review         │
                    └───────────┬───────────┘
                                │
                ┌───────────────┼────────────────┐
                │               │                │
                ▼               ▼                ▼
        ┌──────────────┐ ┌──────────────┐ ┌───────────────┐
        │ Requirements │ │  Atlassian   │ │   Migration   │
        │ / Obligations│ │  Capability  │ │     Risk      │
        └──────────────┘ └──────────────┘ └───────────────┘
```

## The Most Important Rule

> **The system must never pretend to know something that the available evidence does not support.**

Therefore, every result must be capable of saying:

```text
Likely applicable
Potentially applicable
Not currently indicated
Insufficient information
Manual review required
Cannot determine from available signals
```

That principle is what should make this system more trustworthy than:

* A generic AI chatbot
* A static region × industry × segment matrix
* A simple checklist
* A rule engine that only produces Yes / No answers

---

# Recommended Next Step

The next implementation step should be:

```text
Iteration 0
    ↓
Generic Core Schemas & Architecture
    ↓
HIPAA Requirement Pack
    ↓
End-to-End Validation
    ↓
GDPR
    ↓
FedRAMP
    ↓
DORA
    ↓
Data Residency
    ↓
Technical Requirement Pack
```

The first goal is **not to build every regulation**.

The first goal is to prove that:

```text
Signals
   ↓
Concepts
   ↓
Rules
   ↓
Evidence
   ↓
Questions
   ↓
Assessment
   ↓
Capability
   ↓
Migration Risk
```

works as a **generic, explainable, evidence-driven architecture** across fundamentally different requirement types.

Once that is proven, expanding the system should primarily involve adding and maintaining high-quality requirement packs rather than repeatedly changing the core platform.
