# AI Regulatory Knowledge Authoring

## 1. Purpose

The Compliance Checker uses a signal-based, explainable rule engine to determine which security, privacy, regulatory, and technical requirements may apply to an organisation.

The application should not require developers to manually encode regulatory interpretation in Java code.

Instead, regulatory knowledge should be represented as version-controlled **Requirement Packs** containing:

* Applicability signals
* Derived concepts
* Applicability rules
* Obligations
* Evidence requirements
* Verification requirements
* Discovery questions
* Authoritative sources
* Test cases
* Version and review metadata

AI can be used to create the initial draft of these Requirement Packs from authoritative regulatory documents.

The resulting pack must be validated and reviewed by a qualified human before it becomes an approved production Requirement Pack.

---

# 2. Core Principle

The central architectural principle is:

> **AI generates. Machines validate. Humans approve. The rule engine executes.**

AI is an authoring assistant, not the final regulatory authority.

The system must never treat an AI-generated rule as authoritative merely because an LLM produced it.

---

# 3. Why Use AI for Regulatory Pack Authoring?

Regulatory requirements can be large, complex, and distributed across:

* Regulations
* Government legislation
* Regulatory guidance
* Official FAQs
* Regulatory interpretations
* Standards
* Certification requirements
* Government frameworks
* Official implementation guidance

Manually converting all of this information into a structured Requirement Pack is time-consuming and error-prone.

AI can accelerate the transformation:

```text
Official Regulatory Sources
        ↓
AI-assisted extraction
        ↓
Structured Requirement Pack
        ↓
Validation
        ↓
Human review
```

This allows compliance/security/privacy experts to spend more time reviewing interpretation rather than manually formatting JSON.

---

# 4. What AI Is Responsible For

AI may assist with:

### Regulatory extraction

Identify relevant information from authoritative documents.

### Requirement identification

Identify candidate:

* Requirements
* Obligations
* Applicability criteria
* Exceptions
* Scope conditions

### Signal identification

Identify organisation attributes that may be required to determine applicability.

Examples:

```text
organisation.country
organisation.industry
organisation.regulated_entity
organisation.government_customer
data.personal_data
data.health_data
data.financial_data
data.phi
data_subject.location
```

### Rule generation

Translate regulatory applicability criteria into structured rules supported by the Requirement Pack schema.

### Evidence identification

Identify what evidence would support a signal.

### Unknown identification

Identify information that cannot be determined from currently available evidence.

### Discovery question generation

Generate questions that can resolve unknown signals.

### Source mapping

Associate requirements and rules with authoritative sources.

### Test generation

Generate positive, negative, unknown, boundary, and conflict test cases for generated rules.

### Pack documentation

Generate descriptions and explanations that make the Requirement Pack understandable to reviewers.

---

# 5. What AI Must Not Do

AI must not be treated as the final authority for:

* Legal interpretation
* Regulatory approval
* Final applicability determination
* Publishing a production Requirement Pack
* Overriding authoritative sources
* Inventing requirements
* Inventing regulatory sources
* Fabricating citations
* Silently resolving conflicting evidence
* Converting uncertainty into certainty

The system should explicitly preserve uncertainty.

For example:

```text
TRUE
FALSE
UNKNOWN
```

should remain distinct.

Where evidence conflicts, the system should eventually support:

```text
CONFLICT
```

and require manual review.

---

# 6. Regulatory Pack Authoring Workflow

The proposed workflow is:

```text
                  ┌───────────────────────┐
                  │ Official Sources      │
                  │                       │
                  │ Laws                  │
                  │ Regulations           │
                  │ Regulator Guidance    │
                  │ Official FAQs         │
                  └───────────┬───────────┘
                              │
                              ▼
                  ┌───────────────────────┐
                  │ AI Pack Generator     │
                  │                       │
                  │ Prompt                │
                  │ Pack Schema           │
                  │ Source Documents      │
                  └───────────┬───────────┘
                              │
                              ▼
                  ┌───────────────────────┐
                  │ Draft Requirement     │
                  │ Pack                  │
                  └───────────┬───────────┘
                              │
                ┌─────────────┴─────────────┐
                ▼                           ▼
      ┌─────────────────┐         ┌─────────────────┐
      │ Schema          │         │ Rule/Test       │
      │ Validation      │         │ Validation      │
      └────────┬────────┘         └────────┬────────┘
               └─────────────┬─────────────┘
                             ▼
                  ┌───────────────────────┐
                  │ Human Review          │
                  │                       │
                  │ Approve               │
                  │ Modify                │
                  │ Reject                │
                  └───────────┬───────────┘
                              │
                              ▼
                  ┌───────────────────────┐
                  │ Approved Requirement  │
                  │ Pack                  │
                  └───────────┬───────────┘
                              │
                              ▼
                  ┌───────────────────────┐
                  │ Git / Version Control │
                  └───────────┬───────────┘
                              │
                              ▼
                  ┌───────────────────────┐
                  │ Compliance Checker    │
                  │ Runtime               │
                  └───────────────────────┘
```

---

# 7. Requirement Pack Lifecycle

A Requirement Pack should have an explicit lifecycle.

Suggested states:

```text
DRAFT
   ↓
AI_GENERATED
   ↓
VALIDATION_FAILED / VALIDATION_PASSED
   ↓
HUMAN_REVIEW
   ↓
CHANGES_REQUESTED
   ↓
APPROVED
   ↓
PUBLISHED
   ↓
SUPERSEDED
   ↓
RETIRED
```

Not every implementation needs all states immediately.

The initial implementation may use:

```text
DRAFT
APPROVED
PUBLISHED
RETIRED
```

and expand later.

---

# 8. AI Input

The AI Pack Generator should receive structured inputs rather than only a generic prompt.

Conceptually:

```text
AI Pack Generation Request

├── Requirement Pack Schema
├── Regulatory Source Documents
├── Source Metadata
├── Generation Instructions
├── Existing Requirement Packs
├── Existing Signal Catalogue
├── Existing Rule Operators
└── Validation Constraints
```

This allows the generated pack to conform to the architecture.

---

# 9. Source Documents

The preferred source hierarchy is:

```text
Tier 1
Official legislation / regulation
        ↓
Tier 2
Official regulator guidance
        ↓
Tier 3
Official FAQs / implementation guidance
        ↓
Tier 4
Recognised standards / frameworks
        ↓
Tier 5
Secondary sources
```

Secondary sources should not silently become the authority for a regulatory rule.

If a secondary source is used, it should be explicitly marked as supporting information.

---

# 10. AI Generation Prompt

The project should maintain a reusable prompt/template for generating Requirement Packs.

The prompt should instruct the model to:

1. Use only the supplied authoritative sources for regulatory claims.
2. Extract rather than invent requirements.
3. Preserve uncertainty.
4. Identify the scope and applicability conditions.
5. Identify required signals.
6. Identify derived concepts where necessary.
7. Generate deterministic rules using the supported rule schema.
8. Generate evidence requirements.
9. Generate verification questions.
10. Generate authoritative source references.
11. Generate test cases.
12. Identify ambiguities.
13. Identify conflicting or insufficient source information.
14. Flag areas requiring human review.
15. Never fabricate citations or source sections.
16. Never assume applicability solely from industry or geography when the source requires additional conditions.

The prompt should also require the AI to explain the reasoning behind each generated rule.

---

# 11. Example: HIPAA Starter Pack

HIPAA is the first proposed proving ground for the AI authoring pipeline.

The goal is not to immediately create a complete HIPAA implementation.

The first goal is to demonstrate:

```text
Official HIPAA documents
        ↓
AI
        ↓
Draft HIPAA Requirement Pack
        ↓
Validation
        ↓
Human review
        ↓
Approved HIPAA Pack
        ↓
Generic Rule Engine
```

---

# 12. Example Generated Pack

Conceptually, AI could generate:

```json
{
  "pack": {
    "id": "hipaa",
    "version": "0.1",
    "status": "DRAFT"
  },
  "requirements": [
    {
      "id": "HIPAA-001",
      "name": "HIPAA applicability",
      "type": "REGULATION",

      "signals": [
        {
          "id": "organisation.covered_entity"
        },
        {
          "id": "organisation.business_associate"
        },
        {
          "id": "data.phi_present"
        }
      ],

      "rules": [
        {
          "id": "HIPAA-001-R01",
          "condition": {
            "operator": "ALL",
            "conditions": [
              {
                "signal": "organisation.covered_entity",
                "operator": "EQUALS",
                "value": true
              },
              {
                "signal": "data.phi_present",
                "operator": "EQUALS",
                "value": true
              }
            ]
          },
          "result": "TRUE"
        }
      ],

      "verification": {
        "questions": [
          "Is the organisation a HIPAA Covered Entity?",
          "Does the organisation create, receive, maintain, or transmit PHI?"
        ]
      },

      "sources": [
        {
          "authority": "U.S. Department of Health & Human Services",
          "reference": "...",
          "section": "...",
          "url": "..."
        }
      ]
    }
  ]
}
```

This is a **draft**, not automatically an approved compliance determination.

---

# 13. AI Should Generate Tests

A major requirement of the AI authoring pipeline is that rules should be accompanied by tests.

For example:

```json
{
  "tests": [
    {
      "name": "Covered Entity with PHI",
      "inputs": {
        "organisation.covered_entity": true,
        "data.phi_present": true
      },
      "expected": "TRUE"
    },
    {
      "name": "No PHI",
      "inputs": {
        "organisation.covered_entity": true,
        "data.phi_present": false
      },
      "expected": "FALSE"
    },
    {
      "name": "PHI status unknown",
      "inputs": {
        "organisation.covered_entity": true,
        "data.phi_present": "UNKNOWN"
      },
      "expected": "UNKNOWN"
    }
  ]
}
```

The application should execute these tests automatically.

The human reviewer therefore reviews both:

```text
Rule
+
Expected behaviour
```

rather than only reading the rule itself.

---

# 14. Automated Validation

Before human review, the generated pack should pass machine validation.

Validation should eventually include:

### Schema validation

Does the JSON conform to the Requirement Pack schema?

### Structural validation

Are all referenced signals, operators, requirements, and concepts defined?

### Rule validation

Are rules syntactically valid?

### Source validation

Does every regulatory rule have a source?

### Test validation

Does every rule have appropriate test coverage?

### Unknown validation

Does the rule behave correctly when required signals are UNKNOWN?

### Reference validation

Are referenced sections and sources present?

### Duplicate detection

Are duplicate requirements or signals being generated?

### Circular dependency detection

Do derived concepts create circular dependencies?

---

# 15. Human Review

Human review is mandatory for a production Requirement Pack.

The reviewer should be able to inspect:

```text
Requirement
    ↓
Applicability logic
    ↓
Signals
    ↓
Evidence
    ↓
Rule
    ↓
Source
    ↓
Tests
```

The reviewer can:

```text
APPROVE
REJECT
MODIFY
REQUEST_SOURCE
REQUEST_CLARIFICATION
```

The reviewer should also be able to see what the AI changed between versions.

---

# 16. Git as the Initial Governance Mechanism

The repository itself can provide an effective first implementation of governance.

Example:

```text
AI generates pack
        ↓
Git branch
        ↓
Pull Request
        ↓
Automated validation
        ↓
Automated tests
        ↓
Human review
        ↓
Approval
        ↓
Merge
```

The Git history becomes an initial audit trail.

Example:

```text
hipaa/
  requirement-pack.json
```

A future implementation may introduce richer metadata and a dedicated knowledge-management UI, but Git is sufficient for the initial system.

---

# 17. Requirement Pack Versioning

Every published pack should eventually have:

```text
packId
version
status
createdAt
updatedAt
effectiveDate
supersededDate
sourceVersions
reviewStatus
reviewedBy
```

Example:

```text
HIPAA
v0.1
DRAFT

HIPAA
v0.2
APPROVED

HIPAA
v1.0
PUBLISHED
```

Historical versions should not be silently overwritten.

---

# 18. AI-Assisted Regulatory Updates

The same architecture can later support regulatory change detection.

```text
Official Source
       ↓
New / Changed Content
       ↓
AI Change Analysis
       ↓
Affected Requirement Packs
       ↓
Suggested Rule Changes
       ↓
Generated Tests
       ↓
Human Review
       ↓
New Pack Version
```

For example:

```text
GDPR guidance changed
        ↓
AI identifies affected requirement
        ↓
AI proposes rule change
        ↓
AI updates test cases
        ↓
Reviewer approves
        ↓
GDPR pack v2
```

AI should propose changes rather than silently modifying published rules.

---

# 19. AI and Runtime Assessment Are Separate

This separation is critical.

## Authoring Time

AI may be heavily involved.

```text
Documents
   ↓
AI
   ↓
Draft Pack
```

## Runtime

The production assessment engine should primarily use approved deterministic rules.

```text
Customer Signals
       ↓
Evidence
       ↓
Approved Requirement Packs
       ↓
Deterministic Rule Engine
       ↓
Assessment
```

AI may later assist with:

* Signal extraction
* Evidence classification
* Natural-language explanations
* Discovery question refinement
* Public-web research

But the core regulatory applicability logic should remain explainable and traceable to the approved Requirement Pack.

---

# 20. Separation of Responsibilities

The architecture should maintain this separation:

| Component             | Responsibility                              |
| --------------------- | ------------------------------------------- |
| AI Pack Generator     | Generate draft regulatory knowledge         |
| Schema Validator      | Validate pack structure                     |
| Rule Validator        | Validate rule semantics                     |
| Test Runner           | Verify expected rule behaviour              |
| Human Reviewer        | Approve regulatory interpretation           |
| Git                   | Version and audit changes                   |
| Requirement Pack      | Store approved regulatory knowledge         |
| Rule Engine           | Execute approved rules                      |
| Assessment Engine     | Produce applicability results               |
| AI Runtime Components | Optional assistance with evidence/questions |

---

# 21. Anti-Patterns

The project should explicitly avoid the following.

## AI as the final authority

```text
Customer data
    ↓
LLM
    ↓
HIPAA = YES
```

This is not the target architecture.

---

## AI directly editing production rules

```text
Official document
    ↓
AI
    ↓
Production rules.json
```

This is not allowed.

---

## Regulatory logic hidden inside prompts

The prompt should generate structured knowledge, not become the only place where regulatory logic exists.

The resulting Requirement Pack must contain the actual rules and sources.

---

## Regulation-specific Java code

Avoid:

```text
GDPRRule.java
HIPAARule.java
DORARule.java
FedRAMPRequirement.java
```

Prefer:

```text
Generic Rule Engine
+
Requirement Packs
```

---

# 22. Target Architecture

The long-term architecture is:

```text
                    REGULATORY KNOWLEDGE AUTHORING

 Official Sources
       │
       ▼
 ┌───────────────┐
 │ AI Generator  │
 └───────┬───────┘
         │
         ▼
 ┌───────────────────┐
 │ Draft Pack        │
 └─────────┬─────────┘
           │
     ┌─────┴──────┐
     ▼            ▼
 Validation     Tests
     │            │
     └─────┬──────┘
           ▼
 ┌───────────────────┐
 │ Human Review      │
 └─────────┬─────────┘
           │
           ▼
 ┌───────────────────┐
 │ Approved Pack     │
 └─────────┬─────────┘
           │
           ▼
       Git / Version
           │
           │
           ▼
====================================================
                 RUNTIME ENGINE
====================================================
           │
           ▼
    Customer Signals
           │
           ▼
      Evidence Layer
           │
           ▼
   Approved Requirement
          Packs
           │
           ▼
    Generic Rule Engine
           │
           ▼
      Applicability
           │
     ┌─────┼──────────────┐
     ▼     ▼              ▼
  Reasons Unknown      Questions
     │     │              │
     └─────┴──────────────┘
           │
           ▼
     Compliance Report
```

---

# 23. Architectural Principle to Preserve

The following principle should remain unchanged as the system evolves:

> **Regulatory knowledge is data, not application code.**

And:

> **AI is a knowledge-authoring assistant, not the regulatory authority.**

And:

> **Every production regulatory rule must be traceable to an authoritative source and an approved Requirement Pack version.**

---

# 24. Implementation Strategy

The implementation should proceed incrementally.

### Phase 1

Core domain and rule-engine foundation.

**Status:** Completed.

### Phase 2A

Configuration-driven rule engine.

Build:

* RuleDefinition
* RuleCondition
* RuleOperator
* RuleRepository
* JSON schema
* Generic evaluator
* Rule tests

### Phase 2B

AI Requirement Pack Generator.

Build:

* Pack schema
* Generation prompt
* Source-document input
* Draft JSON generation
* Schema validation
* Rule validation
* Test generation

### Phase 2C

Human review workflow.

Initially this may simply be:

```text
AI-generated JSON
        ↓
Git Pull Request
        ↓
Human review
        ↓
Merge
```

A dedicated UI can come later.

### Phase 3

Evidence, confidence, missing information, conflict detection, and discovery questions.

### Phase 4+

Expand Requirement Packs:

* HIPAA
* GDPR
* FedRAMP
* DORA
* Data Residency
* IP Allowlisting
* Private Connectivity
* Additional privacy laws
* Industry regulations
* Security standards
* Government requirements

---

# 25. Immediate Next Step

Before continuing implementation, update:

```text
docs/high-level-design.md
```

to incorporate the AI Regulatory Knowledge Authoring architecture described in this document.

Then update:

```text
docs/development-status.md
```

to reflect the revised Phase 2 plan.

After the architecture is updated, implementation should begin with:

```text
Phase 2A
Configuration-driven Rule Engine
```

followed by:

```text
Phase 2B
AI Requirement Pack Generator
```

The first end-to-end proving ground should be:

```text
Official HIPAA Sources
        ↓
AI-generated HIPAA Starter Pack
        ↓
Validation
        ↓
Human Review
        ↓
Approved HIPAA Pack
        ↓
Generic Rule Engine
        ↓
HIPAA Applicability Assessment
```

This will validate both halves of the architecture:

1. **How regulatory knowledge is created**
2. **How regulatory knowledge is consumed**
