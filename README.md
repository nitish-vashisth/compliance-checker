# Compliance Checker

A signal-based compliance, security, privacy, and regulatory applicability engine designed to identify potentially applicable requirements for an organisation.

The goal is to move beyond static classification such as:

> Region × Industry × Customer Segment

and instead determine applicability using:

**Organisation Signals → Evidence → Rules → Applicability → Confidence → Missing Information → Discovery Questions**

The system is designed to be explainable, extensible, auditable, and capable of supporting multiple compliance and regulatory requirement packs such as HIPAA, GDPR, FedRAMP, DORA, Data Residency, and technical security requirements.

---

# Core Concept

The system separates **compliance knowledge** from the **application engine**.

```text
Organisation / Customer Signals
              │
              ▼
          Evidence
              │
              ▼
       Signals / Concepts
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

The long-term architecture follows this principle:

> **Regulatory knowledge is data. The Java application is the generic evaluation engine.**

---

# Implementation Plan

The project is being implemented incrementally.

| Phase        | Focus                                                             | Status      |
| ------------ | ----------------------------------------------------------------- | ----------- |
| **Phase 1**  | Core domain model and rule-engine foundation                      | ✅ Completed |
| **Phase 2A** | Configuration-driven rules and generic rule evaluation            | ✅ Completed |
| **Phase 2B** | AI-assisted regulatory requirement-pack authoring                 | 🔜 Next     |
| **Phase 3**  | Evidence, confidence, missing information and discovery questions | Planned     |
| **Phase 4**  | First complete compliance pack — HIPAA                            | Planned     |
| **Phase 5**  | GDPR and jurisdiction/processing-b                                |             |
