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
```
