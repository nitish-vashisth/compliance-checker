# AI Regulatory Knowledge POC v2

## From Authoritative Regulatory Sources to Customer-Specific Regulatory and Atlassian Readiness

**Status:** POC
**Primary proving ground:** HIPAA
**Primary use case:** Pre-sales regulatory and Atlassian readiness assessment
**Implementation principle:** AI-generated knowledge + deterministic execution + provenance + human validation

---

# 1. Objective

The objective of this POC is to prove that an AI-driven pipeline can transform authoritative regulatory sources into structured, traceable and executable regulatory knowledge.

The system should then use that knowledge to:

1. Understand a regulatory framework.
2. Determine the types of organisations, relationships, data and activities that can bring a customer into scope.
3. Discover candidate customer signals.
4. Identify high-value signals that should actually be collected from a customer.
5. Generate targeted discovery questions.
6. Generate a small customer-facing assessment profile.
7. Execute deterministic applicability rules against customer input.
8. Identify applicable regulatory requirements.
9. Understand the customer's Atlassian usage context.
10. Map applicable requirements to relevant Atlassian capabilities.
11. Identify coverage, configuration requirements, customer responsibilities and gaps.
12. Maintain traceability from every important conclusion back to authoritative evidence.

The POC is **not** intended to provide legal advice or make an authoritative legal determination.

The system should produce results such as:

```text
HIPAA
  |
  +-- Applicability: LIKELY_APPLICABLE
  |
  +-- Reason:
  |     Customer processes PHI
  |     on behalf of a US healthcare organisation
  |
  +-- Relevant requirements:
  |     Business Associate requirements
  |     Security requirements
  |     etc.
  |
  +-- Atlassian relevance:
  |     Jira Cloud is used to process/store customer data
  |
  +-- Atlassian capability mapping:
  |     Supported
  |     Configuration required
  |     Customer responsibility
  |     Unknown / requires verification
```

The key objective is therefore:

> **Do not build a "law checker". Build a regulatory reasoning and readiness pipeline.**

---

# 2. Core Design Principles

## 2.1 AI is not the legal authority

AI is responsible for:

* source understanding
* knowledge extraction
* candidate signal discovery
* candidate rule generation
* requirement extraction
* question generation
* Atlassian knowledge extraction
* candidate mappings

The deterministic application is responsible for:

* executing approved rules
* resolving signals
* evaluating expressions
* producing deterministic results
* calculating missing information
* producing repeatable assessments

Humans are responsible for:

* validating generated knowledge
* approving rules
* resolving legal ambiguity
* approving Atlassian mappings
* deciding whether generated knowledge is production-ready

---

## 2.2 Preserve source provenance

Every important regulatory statement should be traceable to:

```text
Framework
    |
    Source
    |
    Section
    |
    Source passage / reference
    |
    Extracted knowledge
    |
    Rule / requirement
    |
    Assessment result
```

Never generate an unexplained rule such as:

```text
if industry == HEALTHCARE
then HIPAA = TRUE
```

without being able to explain:

```text
Why does this rule exist?
Which source supports it?
Which section supports it?
Is this explicitly stated or inferred?
What assumptions were made?
```

---

# 3. What This POC Is Actually Proving

The POC should prove the following end-to-end flow:

```text
AUTHORITATIVE SOURCES
        |
        v
SOURCE REGISTRATION
        |
        v
SOURCE UNDERSTANDING
        |
        v
REGULATORY KNOWLEDGE
        |
        +----------------+
        |                |
        v                v
     SIGNALS         REQUIREMENTS
        |
        v
DERIVED CONCEPTS
        |
        v
APPLICABILITY RULES
        |
        v
QUESTION / EVIDENCE MODEL
        |
        v
ASSESSMENT PROFILE
        |
        v
CUSTOMER INPUT
        |
        v
DETERMINISTIC RULE ENGINE
        |
        v
REGULATORY APPLICABILITY
        |
        v
REGULATORY REQUIREMENTS
        |
        v
ATLASSIAN USAGE CONTEXT
        |
        v
ATLASSIAN CAPABILITY MAPPING
        |
        v
READINESS / GAP ANALYSIS
```

---

# 4. Three-Layer Assessment Model

The system must explicitly separate three layers.

## Layer 1 — Regulatory Applicability

Question:

> Does this regulatory framework or provision potentially apply to this customer in this context?

Inputs can include:

* organisation
* geography
* industry
* organisation type
* regulatory status
* data
* data subjects
* activities
* customer relationships
* supplier relationships
* processing relationships
* contractual relationships

Example:

```text
Customer:
  HQ = India
  Industry = SaaS
  Serves = US healthcare organisation
  Processes = PHI
  Acts on behalf of customer = Yes

Result:

HIPAA = LIKELY_APPLICABLE
```

The system must not assume:

```text
HQ = India
+
Industry = Software
=
HIPAA NOT APPLICABLE
```

because regulatory applicability may depend on activities and relationships.

---

# 5. Layer 2 — Regulatory Requirements

Question:

> Given the customer's role, activities, data and relationships, which regulatory requirements become relevant?

This layer must consider context.

For example:

```text
Customer A:
  Healthcare provider

Customer B:
  SaaS provider acting as a business associate

Customer C:
  Generic software company with no PHI processing
```

All three can interact with HIPAA differently.

Therefore:

```text
Regulatory Framework
+
Customer Role
+
Customer Activities
+
Data
+
Relationships
+
Geography
        |
        v
Relevant Requirements
```

This layer should identify:

* obligations
* safeguards
* documentation requirements
* contractual requirements
* notification requirements
* data handling requirements
* security requirements
* privacy requirements
* exceptions
* customer responsibilities
* verification requirements

The system must not automatically convert every regulatory requirement into a specific Atlassian product feature.

---

# 6. Layer 3 — Atlassian Capability Mapping

Question:

> How does the customer's Atlassian usage intersect with the applicable regulatory requirements, and what Atlassian capabilities can address those requirements?

This is a separate knowledge problem from regulatory applicability.

The system therefore needs an independent Atlassian knowledge base.

```text
REGULATORY REQUIREMENT
        |
        v
CUSTOMER ATLASSIAN USAGE
        |
        v
ATLASSIAN PRODUCT
        |
        v
ATLASSIAN CAPABILITY
        |
        v
COVERAGE / LIMITATION
        |
        v
CONFIGURATION / CUSTOMER RESPONSIBILITY
        |
        v
EVIDENCE
```

Possible outcomes:

```text
SUPPORTED
SUPPORTED_WITH_CONFIGURATION
PARTIALLY_SUPPORTED
CUSTOMER_RESPONSIBILITY
NOT_SUPPORTED
NOT_APPLICABLE_TO_ATLASSIAN_USAGE
UNKNOWN_REQUIRES_VERIFICATION
```

---

# 7. Why Atlassian Knowledge Must Be Separate

Regulatory sources answer:

> What does HIPAA require?

Atlassian sources answer:

> What does Atlassian provide?

These are different claims and require different provenance.

Do not combine:

```text
HIPAA regulation
+
Atlassian documentation
```

into one undifferentiated knowledge model.

Instead:

```text
REGULATORY KNOWLEDGE
    |
    +-- HIPAA
    +-- GDPR
    +-- DORA
    +-- etc.

ATLASSIAN KNOWLEDGE
    |
    +-- Products
    +-- Capabilities
    +-- Security controls
    +-- Privacy capabilities
    +-- Data residency
    +-- Encryption
    +-- Identity
    +-- Audit
    +-- Compliance programs
    +-- Configuration
    +-- Limitations
```

The mapping between the two is its own artifact.

---

# 8. HIPAA Source Strategy

Do not start the POC with only the Business Associates page.

Start with the authoritative HHS HIPAA professional resource as the **source family root**.

The source family should identify and register relevant child sources such as:

```text
HHS HIPAA Overview
    |
    +-- Privacy Rule
    |
    +-- Security Rule
    |
    +-- Breach Notification Rule
    |
    +-- Enforcement
    |
    +-- Business Associates
    |
    +-- Relevant guidance
    |
    +-- Relevant regulatory references
```

The goal is not to scrape the entire HHS website.

The goal is to prove:

> Can the system start from an authoritative regulatory root and identify the authoritative source set needed to answer applicability and requirement questions?

---

# 9. POC Scope

## Included

* HIPAA source family
* HHS authoritative sources
* Regulatory source registration
* Source understanding
* Regulatory knowledge extraction
* Candidate signal generation
* Derived concepts
* Applicability rules
* Requirements
* Exceptions
* Evidence
* Questions
* High-value signal selection
* Assessment profile generation
* Customer input template generation
* Deterministic execution
* Atlassian knowledge model
* Atlassian capability mapping
* End-to-end customer scenarios
* Human validation

## Not included

* Production UI
* Authentication
* Production database
* Automated legal approval
* Automatic legal advice
* Full HIPAA implementation
* All US state laws
* All Atlassian products
* Production-grade RAG
* Continuous regulatory monitoring
* Automatic regulatory change management
* Complex vector databases
* Multi-agent orchestration

---

# 10. Folder Structure

Use the following structure:

```text
ai-regulatory-knowledge-poc/
│
├── README.md
│
├── sources/
│   │
│   ├── hipaa/
│   │   │
│   │   ├── source-family.json
│   │   │
│   │   ├── source-001-hhs-overview.json
│   │   ├── source-002-privacy-rule.json
│   │   ├── source-003-security-rule.json
│   │   ├── source-004-business-associates.json
│   │   ├── source-005-breach-notification.json
│   │   │
│   │   └── raw/
│   │       ├── source-001.html
│   │       ├── source-002.html
│   │       └── ...
│   │
│   └── atlassian/
│       ├── source-001-trust.json
│       ├── source-002-security.json
│       ├── source-003-hipaa.json
│       └── raw/
│
├── extracted/
│   │
│   ├── hipaa/
│   │   ├── source-understanding.json
│   │   ├── knowledge.json
│   │   ├── signals.json
│   │   ├── derived-concepts.json
│   │   ├── rules.json
│   │   ├── requirements.json
│   │   ├── exceptions.json
│   │   ├── evidence.json
│   │   ├── questions.json
│   │   ├── signal-dependencies.json
│   │   └── assessment-profile.json
│   │
│   └── atlassian/
│       ├── products.json
│       ├── capabilities.json
│       ├── controls.json
│       ├── evidence.json
│       └── mappings/
│           └── hipaa.json
│
├── assessments/
│   │
│   ├── schemas/
│   │   ├── customer-input-schema.json
│   │   └── assessment-result-schema.json
│   │
│   └── scenarios/
│       ├── scenario-001-hipaa-applicable.json
│       ├── scenario-002-hipaa-not-applicable.json
│       ├── scenario-003-insufficient-information.json
│       └── scenario-004-atlassian-usage.json
│
├── validated/
│   └── hipaa/
│       ├── approved-knowledge.json
│       ├── approved-rules.json
│       ├── approved-requirements.json
│       ├── approved-assessment-profile.json
│       └── approved-atlassian-mappings.json
│
└── prompts/
    ├── 01-source-discovery.md
    ├── 02-source-understanding.md
    ├── 03-knowledge-extraction.md
    ├── 04-signal-discovery.md
    ├── 05-derived-concepts.md
    ├── 06-rule-generation.md
    ├── 07-requirement-extraction.md
    ├── 08-exception-extraction.md
    ├── 09-evidence-generation.md
    ├── 10-question-generation.md
    ├── 11-high-value-signal-selection.md
    ├── 12-assessment-profile-generation.md
    ├── 13-validation.md
    ├── 14-test-scenario-generation.md
    ├── 15-atlassian-knowledge-extraction.md
    ├── 16-atlassian-capability-mapping.md
    └── 17-final-assessment.md
```

---

# 11. End-to-End Steps

The POC consists of these steps:

```text
01 Source Registration
02 Source Discovery / Source Family Construction
03 Source Understanding
04 Regulatory Knowledge Extraction
05 Candidate Signal Discovery
06 Derived Concept Generation
07 Applicability Rule Generation
08 Requirement / Obligation Extraction
09 Exception / Boundary Extraction
10 Evidence Generation
11 Discovery Question Generation
12 High-Value Signal Selection
13 Assessment Profile Generation
14 Customer Input Template Generation
15 Validation
16 Test Scenario Generation
17 Requirement Pack Assembly
18 Atlassian Source Registration
19 Atlassian Knowledge Extraction
20 Regulatory → Atlassian Mapping
21 Execute Customer Assessment
22 Adaptive Missing-Information Analysis
23 Atlassian Readiness Assessment
24 Final Pre-Sales Readiness Result
25 Human Review
```

---

# 12. STEP 1 — Source Registration

Create a source-family record.

Example:

```json
{
  "sourceFamilyId": "HIPAA-HHS",
  "frameworkId": "HIPAA",
  "frameworkName": "Health Insurance Portability and Accountability Act",
  "jurisdiction": {
    "country": "US",
    "scope": "FEDERAL"
  },
  "publisher": "U.S. Department of Health and Human Services",
  "publisherType": "GOVERNMENT_REGULATOR",
  "rootSource": {
    "title": "HIPAA for Professionals",
    "url": "https://www.hhs.gov/hipaa/for-professionals/index.html"
  },
  "purpose": [
    "framework_understanding",
    "scope_analysis",
    "applicability_analysis",
    "requirement_extraction"
  ],
  "authorityLevel": "HIGH",
  "status": "REGISTERED",
  "humanReviewRequired": true
}
```

Important:

* Do not call a guidance page the law itself.
* Do not invent effective dates.
* Do not invent versions.
* Keep source type explicit.
* Preserve publisher and authority.
* Preserve URLs.
* Preserve retrieval date.
* Preserve relationships between parent and child sources.

---

# 13. STEP 2 — Source Discovery / Source Family Construction

The AI should inspect the root authoritative page and identify relevant authoritative sub-sources.

### Prompt

```text
You are an authoritative regulatory source discovery system.

You have been given an authoritative regulatory framework landing page.

Your objective is to construct a source family for the framework.

Do not attempt to extract all regulatory knowledge yet.

Identify authoritative child sources that are necessary to understand:

1. What the framework is
2. Who may be covered
3. What data or information is covered
4. What activities are covered
5. What major rules or regulations exist
6. What major obligations exist
7. What important exceptions or exclusions exist
8. Which definitions materially affect applicability
9. Which regulatory text or government sources should be treated as primary authority
10. Which guidance pages are useful for interpretation

For every source provide:

- sourceId
- title
- publisher
- sourceType
- URL
- relationshipToParent
- purpose
- authorityLevel
- jurisdiction
- whether it should be used for rule generation
- whether it should be used only for contextual understanding
- confidence
- reason for inclusion

Do not invent sources.

Prefer authoritative government or regulator sources.

Distinguish:

PRIMARY_REGULATION
REGULATOR_GUIDANCE
GOVERNMENT_OVERVIEW
DEFINITIONAL_REFERENCE
SECONDARY_SOURCE

Do not treat secondary sources as primary legal authority.

Output JSON only.
```

---

# 14. STEP 3 — Source Understanding

Before generating rules, understand the source.

### Prompt

```text
You are a regulatory knowledge extraction system.

Understand the supplied authoritative source before generating rules.

Do not determine customer applicability yet.

Do not create technical controls.

Do not invent requirements.

Identify:

1. Document title
2. Issuing authority
3. Jurisdiction
4. Source type
5. Regulatory framework
6. Main subject matter
7. Regulated actors/entities
8. Regulated information/data
9. Covered activities
10. Geographic scope
11. Industry/contextual scope
12. Applicability concepts
13. Major obligations
14. Exceptions
15. Exclusions
16. Definitions affecting scope
17. Actor relationships
18. Cross-references
19. Important ambiguities

For every finding provide:

- id
- type
- description
- source reference
- explicit/inferred
- confidence

Never present an inference as an explicit regulatory statement.

Output JSON only.
```

Save:

```text
extracted/hipaa/source-understanding.json
```

---

# 15. STEP 4 — Regulatory Knowledge Extraction

Convert the source into structured regulatory knowledge.

Represent statements as:

```text
Statement
  |
  +-- Actor
  +-- Action
  +-- Object
  +-- Condition
  +-- Exception
  +-- Relationship
  +-- Obligation
  +-- Source
```

### Prompt

```text
Using only the supplied authoritative regulatory source, extract a structured regulatory knowledge model.

For every material regulatory statement identify:

1. Statement ID
2. Concise statement
3. Statement type

Allowed types:

DEFINITION
SCOPE
APPLICABILITY
OBLIGATION
PROHIBITION
EXCEPTION
EXCLUSION
PERMISSION
CONDITION
PROCEDURE
CROSS_REFERENCE

4. Actor
5. Action
6. Object
7. Condition
8. Exception
9. Relationship
10. Related concepts
11. Source section
12. Source URL
13. Explicit/inferred
14. Confidence

Rules:

- Do not invent facts.
- Do not turn guidance into mandatory law.
- Do not turn examples into universal rules.
- Preserve ambiguity.
- Preserve exceptions.
- Preserve definitions.
- Preserve actor relationships.
- Every material statement requires provenance.

Output JSON only.
```

Save:

```text
extracted/hipaa/knowledge.json
```

---

# 16. STEP 5 — Candidate Signal Discovery

This step intentionally produces a potentially large signal universe.

That is acceptable.

The internal signal model should be comprehensive.

Possible dimensions:

```text
Organisation
Geography
Industry
Organisation Type
Data
Data Subjects
Activities
Relationships
Contracts
Technology
Regulatory Status
Customer Context
Supplier Context
Atlassian Usage
```

Examples:

```text
organisation.country
organisation.industry
organisation.organisation_type
organisation.employee_count

data.phi
data.ephi
data.personal_data
data.financial_data

activity.collects_data
activity.processes_data
activity.stores_data
activity.transmits_data

relationship.customer
relationship.processor
relationship.business_associate
relationship.subcontractor

data.subject_country
data.storage_country
data.processing_country

contract.baa_exists
contract.dpa_exists

technology.cloud
technology.saas
technology.third_party_processing
```

### Prompt

```text
You are designing a generic customer fact model for regulatory applicability.

Using the extracted regulatory knowledge, identify all observable customer facts that could materially affect:

- applicability
- scope
- obligations
- exceptions
- exclusions
- verification
- risk assessment

Consider:

1. Organisation
2. Geography
3. Industry
4. Organisation type
5. Regulatory status
6. Data
7. Data subjects
8. Processing activities
9. Storage activities
10. Transfer activities
11. Business relationships
12. Contractual relationships
13. Technology/service characteristics
14. Customer relationships
15. Supplier relationships
16. Existing certifications
17. Exceptions
18. Obligation-specific facts

For each signal provide:

- id
- name
- description
- dataType
- possibleValues
- classification
- whyItMatters
- sourceReferences
- explicitOrInferred
- confidence

Signal classifications:

DIRECT_APPLICABILITY
INDIRECT_APPLICABILITY
OBLIGATION_TRIGGER
EXCEPTION_TRIGGER
CONTEXT
VERIFICATION
ATLASSIAN_USAGE
DERIVED_INPUT

Important:

A signal is an observable customer fact.

Do not create conclusions as signals.

Good:

data.phi = true

Bad:

organisation.hipaa_applicable = true

The second is a conclusion.

Output JSON only.
```

Save:

```text
extracted/hipaa/signals.json
```

---

# 17. STEP 6 — Derived Concept Generation

Derived concepts are intermediate conclusions computed from raw signals.

Example:

```text
data.phi
+
activity.processes_data
+
relationship.on_behalf_of_customer
+
customer.is_healthcare_entity
```

may produce:

```text
derived.potential_business_associate_relationship
```

### Prompt

```text
Using the regulatory knowledge and candidate signals, identify useful derived concepts.

A derived concept is calculated from observable customer signals.

It must not simply rename an existing signal.

For each concept provide:

- id
- name
- description
- inputSignals
- derivationLogic
- regulatoryPurpose
- sourceReferences
- confidence

Only create a derived concept when it:

- simplifies rule logic
- improves reuse
- improves explainability
- represents a meaningful regulatory concept

Do not create the final applicability result as a derived concept.

Output JSON only.
```

Save:

```text
extracted/hipaa/derived-concepts.json
```

---

# 18. STEP 7 — Applicability Rule Generation

Now convert approved knowledge into executable candidate rules.

Supported operators:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
AND
OR
```

Example:

```json
{
  "ruleId": "HIPAA-R001",
  "description": "Potential business associate context",
  "expression": {
    "operator": "AND",
    "children": [
      {
        "operator": "EQUALS",
        "signal": "data.phi",
        "value": true
      },
      {
        "operator": "EQUALS",
        "signal": "relationship.on_behalf_of_customer",
        "value": true
      }
    ]
  },
  "outcome": {
    "status": "LIKELY_APPLICABLE"
  },
  "sourceReferences": [],
  "confidence": "HIGH",
  "assumptions": [],
  "unresolvedQuestions": []
}
```

### Prompt

```text
Generate candidate applicability rules from the regulatory knowledge model.

Rules must represent conditions supported by the source.

Use only:

- observable signals
- approved derived concepts

Allowed operators:

EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
AND
OR

For every rule provide:

- ruleId
- description
- expression
- outcome
- referencedSignals
- referencedDerivedConcepts
- sourceReferences
- confidence
- assumptions
- unresolvedQuestions

Rules:

1. Do not invent legal conclusions.
2. Do not use industry alone unless the source supports it.
3. Do not use geography alone unless the source supports it.
4. Preserve OR conditions.
5. Preserve AND conditions.
6. Preserve exceptions.
7. Preserve uncertainty.
8. Do not turn missing information into FALSE.
9. UNKNOWN must remain UNKNOWN.

Output JSON only.
```

Save:

```text
extracted/hipaa/rules.json
```

---

# 19. STEP 8 — Requirement / Obligation Extraction

Applicability is not the same as requirements.

Extract obligations separately.

### Prompt

```text
From the approved regulatory knowledge model, identify regulatory requirements and obligations.

For every requirement provide:

- requirementId
- frameworkId
- title
- description
- actor
- triggerConditions
- obligationType
- requirementStrength
- exceptions
- sourceReferences
- evidenceExpected
- customerResponsibility
- confidence

Allowed obligation types:

PRIVACY
SECURITY
CONTRACTUAL
ADMINISTRATIVE
TECHNICAL
PHYSICAL
NOTIFICATION
DOCUMENTATION
GOVERNANCE
DATA_HANDLING
ACCESS_CONTROL
RETENTION
OTHER

Do not create technical product requirements.

Do not mention Atlassian products.

The output must represent the regulatory obligation independently from technology.

Output JSON only.
```

Save:

```text
extracted/hipaa/requirements.json
```

---

# 20. STEP 9 — Exception / Boundary Extraction

Exceptions are first-class knowledge.

### Prompt

```text
Identify all material exceptions, exclusions, thresholds, boundaries and special conditions.

For each provide:

- exceptionId
- description
- appliesWhen
- excludesWhen
- affectedRules
- affectedRequirements
- sourceReferences
- confidence

Do not simplify away exceptions.

If an exception depends on information not currently available as a signal, identify the missing signal.

Output JSON only.
```

Save:

```text
extracted/hipaa/exceptions.json
```

---

# 21. STEP 10 — Evidence Generation

Evidence should explain why a result was produced.

Evidence types:

```text
REGULATORY_SOURCE
CUSTOMER_PROVIDED
CUSTOMER_DOCUMENT
SYSTEM_DERIVED
ATLASSIAN_SOURCE
CONFIGURATION
VERIFICATION_REQUIRED
```

### Prompt

```text
For each rule and requirement, identify what evidence could support the conclusion.

Do not invent evidence.

For each evidence item provide:

- evidenceId
- type
- supports
- description
- sourceReference
- whether customer-provided
- whether externally verifiable
- whether required for initial assessment
- confidence

Distinguish:

1. Evidence needed to determine applicability
2. Evidence needed to verify the conclusion
3. Evidence needed for audit/compliance proof

Output JSON only.
```

Save:

```text
extracted/hipaa/evidence.json
```

---

# 22. STEP 11 — Discovery Question Generation

Questions should collect facts, not ask customers to interpret law.

Bad:

```text
Are you subject to HIPAA?
```

Better:

```text
Do you provide services to a US healthcare organisation?
```

Better:

```text
Do your services create, receive, maintain or transmit
protected health information on behalf of a US healthcare
organisation or other HIPAA-regulated entity?
```

### Prompt

```text
Generate customer discovery questions for unresolved regulatory signals.

Questions must ask for observable facts.

Do not ask the customer to make a legal determination.

For each question provide:

- questionId
- signalId
- question
- answerType
- allowedValues
- required
- priority
- reason
- resolvesRules
- resolvesRequirements
- sourceReferences

Prefer questions that resolve multiple rules.

Prefer questions that are:

- simple
- unambiguous
- high information value
- answerable by business users

Avoid duplicate questions.

Output JSON only.
```

Save:

```text
extracted/hipaa/questions.json
```

---

# 23. STEP 12 — High-Value Signal Selection

This is a critical new step.

The internal signal universe may contain hundreds of signals.

The customer should see only a small set.

Select signals based on:

```text
Applicability impact
+
Number of rules affected
+
Ability to reduce UNKNOWN
+
Information value
+
Ease of answering
+
Firmographic availability
+
Ability to derive other signals
```

### Signal tiers

```text
TIER_1 — Initial assessment
TIER_2 — Targeted follow-up
TIER_3 — Requirement-specific
TIER_4 — Verification/evidence
```

The UI should initially expose only TIER_1.

### Prompt

```text
You are designing a minimal high-value regulatory assessment.

The internal signal model may contain many candidate signals.

Select only the signals that should be collected during an initial customer assessment.

Optimize for:

1. Regulatory applicability coverage
2. Number of rules resolved
3. Reduction of UNKNOWN results
4. Information value
5. Customer answerability
6. Reusability across requirements
7. Ability to derive additional concepts
8. Ability to distinguish materially different regulatory contexts

Classify every signal:

TIER_1_INITIAL
TIER_2_TARGETED
TIER_3_REQUIREMENT_SPECIFIC
TIER_4_VERIFICATION

Do not select a signal merely because it appears in a rule.

Prefer signals that resolve multiple rules.

Prefer firmographic signals where reliable.

Prefer targeted questions for facts that cannot be inferred reliably.

For every selected signal provide:

- signalId
- tier
- reason
- rulesAffected
- informationValue
- customerAnswerability
- preferredCollectionMethod
- whether it can be prefilled
- whether it requires a question

Output JSON only.
```

Save:

```text
extracted/hipaa/signal-dependencies.json
```

---

# 24. STEP 13 — Assessment Profile Generation

This is the customer-facing contract.

It should be significantly smaller than `signals.json`.

Example:

```json
{
  "assessmentProfileId": "HIPAA-INITIAL",
  "frameworkId": "HIPAA",
  "version": "1.0",

  "purpose": "Initial regulatory applicability assessment",

  "sections": [
    {
      "id": "organisation",
      "title": "Organisation",
      "signals": [
        {
          "signalId": "organisation.country",
          "required": true,
          "collectionMethod": "FORM"
        },
        {
          "signalId": "organisation.industry",
          "required": true,
          "collectionMethod": "FORM"
        },
        {
          "signalId": "organisation.organisation_type",
          "required": true,
          "collectionMethod": "FORM"
        }
      ]
    },
    {
      "id": "regulatory_context",
      "title": "Regulatory Context",
      "questions": [
        {
          "questionId": "HIPAA-Q001",
          "required": false
        },
        {
          "questionId": "HIPAA-Q002",
          "required": false
        }
      ]
    }
  ],

  "adaptiveQuestioning": true,

  "allowUnknown": true,

  "minimumInformationPolicy": {
    "doNotAskUnnecessaryQuestions": true,
    "stopWhenMaterialRulesResolved": true
  }
}
```

The profile should be sufficient to generate a UI.

---

# 25. STEP 14 — Customer Input Template Generation

The assessment profile should generate a customer input template.

Example:

```json
{
  "assessmentId": "CUSTOMER-001",
  "assessmentProfileId": "HIPAA-INITIAL",

  "customer": {
    "country": null,
    "industry": null,
    "organisationType": null
  },

  "answers": {
    "HIPAA-Q001": null,
    "HIPAA-Q002": null
  }
}
```

This serves two purposes:

1. Future UI input contract.
2. Deterministic rule-engine test input.

The same JSON should be usable in automated tests.

---

# 26. STEP 15 — Validation

Validate every generated artifact.

Validation must check:

```text
Source traceability
Signal validity
Rule validity
Rule references
Requirement references
Question references
Exception references
No orphan signals
No orphan rules
No unsupported legal claims
No missing provenance
No invalid operators
No circular derived concepts
```

### Validation Prompt

```text
Validate the generated regulatory knowledge package.

Check:

1. Every rule references valid signals or derived concepts.
2. Every derived concept references valid signals.
3. Every requirement references source evidence.
4. Every question resolves at least one meaningful signal.
5. Every important applicability rule has provenance.
6. No rule invents unsupported legal conclusions.
7. No customer-facing question asks for a legal conclusion.
8. Exceptions are represented.
9. UNKNOWN is distinguishable from FALSE.
10. No orphaned artifacts exist.
11. No circular derived concepts exist.
12. High-value signals are sufficient to resolve important rules.
13. Assessment profile references valid questions and signals.

Output:

- validationStatus
- errors
- warnings
- recommendations

Output JSON only.
```

---

# 27. STEP 16 — Test Scenario Generation

Generate scenarios specifically to test boundaries.

At minimum:

```text
Scenario 1 — HIPAA likely applicable
Scenario 2 — HIPAA likely not applicable
Scenario 3 — insufficient information
Scenario 4 — customer relationship changes result
Scenario 5 — data type changes result
Scenario 6 — Atlassian usage introduces downstream relevance
Scenario 7 — exception applies
Scenario 8 — multiple rules produce different outcomes
```

---

# 28. STEP 17 — Requirement Pack Assembly

The regulatory requirement pack should contain:

```text
Framework
Source references
Signals
Derived concepts
Rules
Requirements
Exceptions
Evidence
Questions
Assessment profile
Test scenarios
```

It must represent **approved regulatory knowledge**, not raw AI output.

Structure:

```json
{
  "framework": {},
  "sources": [],
  "signals": [],
  "derivedConcepts": [],
  "rules": [],
  "requirements": [],
  "exceptions": [],
  "evidence": [],
  "questions": [],
  "assessmentProfile": {},
  "testScenarios": [],
  "validation": {}
}
```

---

# 29. STEP 18 — Atlassian Source Registration

Atlassian sources are registered separately.

Examples of source categories:

```text
Atlassian Trust
Atlassian Compliance
Atlassian Security Documentation
Atlassian Product Documentation
Atlassian Data Residency Documentation
Atlassian Privacy Documentation
Atlassian Product Configuration Documentation
```

Each source must contain:

```text
sourceId
publisher
product
sourceType
URL
publication/update information if available
authority
scope
evidence type
```

Do not use generic web sources as authoritative evidence for Atlassian capability claims when an official Atlassian source exists.

---

# 30. STEP 19 — Atlassian Knowledge Extraction

Extract knowledge about what Atlassian provides.

Do NOT extract regulatory applicability here.

Extract:

```text
Product
Capability
Feature
Configuration
Availability
Prerequisite
Limitation
Customer responsibility
Compliance evidence
Data handling
Data residency
Security properties
```

### Prompt

```text
You are an Atlassian product and capability knowledge extraction system.

Using only the supplied authoritative Atlassian source, extract structured knowledge about:

1. Product
2. Capability
3. Security capability
4. Privacy capability
5. Data handling
6. Data residency
7. Encryption
8. Identity/access control
9. Audit/logging
10. Administrative controls
11. Configuration requirements
12. Availability restrictions
13. Customer responsibilities
14. Limitations
15. Compliance evidence

For each item provide:

- id
- product
- capability
- description
- availability
- prerequisites
- limitations
- customerResponsibility
- sourceReference
- confidence

Do not claim that a capability satisfies a regulatory requirement.

Only extract what the Atlassian source supports.

Output JSON only.
```

Save:

```text
extracted/atlassian/capabilities.json
```

---

# 31. STEP 20 — Regulatory → Atlassian Capability Mapping

This is the bridge between Layer 2 and Layer 3.

Example:

```text
HIPAA Requirement
        |
        v
Security Requirement
        |
        v
Relevant Atlassian Capability
        |
        v
Coverage
```

The mapping should never say simply:

```text
HIPAA = Atlassian compliant
```

Instead:

```json
{
  "mappingId": "HIPAA-ATL-001",
  "regulatoryRequirementId": "HIPAA-REQ-001",
  "atlassianProduct": "JIRA_CLOUD",
  "capabilityId": "ATL-CAP-001",
  "coverage": "PARTIALLY_SUPPORTED",
  "configurationRequired": true,
  "customerResponsibility": [
    "..."
  ],
  "limitations": [
    "..."
  ],
  "evidenceReferences": [
    "ATL-SOURCE-001"
  ],
  "confidence": "MEDIUM"
}
```

### Prompt

```text
Map approved regulatory requirements to Atlassian capabilities.

Important:

Do not claim that Atlassian satisfies a regulation in its entirety.

Do not infer product capabilities from the regulation.

Do not infer regulatory compliance from a product feature.

For each candidate mapping determine:

1. Regulatory requirement
2. Atlassian product
3. Atlassian capability
4. Relationship
5. Coverage
6. Configuration required
7. Customer responsibility
8. Limitations
9. Evidence
10. Confidence
11. Unknowns requiring verification

Allowed coverage values:

FULLY_RELEVANT
PARTIALLY_RELEVANT
SUPPORTS_REQUIREMENT
SUPPORTS_WITH_CONFIGURATION
CUSTOMER_RESPONSIBILITY
NOT_SUPPORTED
NOT_APPLICABLE
UNKNOWN_REQUIRES_VERIFICATION

Every mapping requires:

- regulatory source evidence
- Atlassian source evidence

Output JSON only.
```

Save:

```text
extracted/atlassian/mappings/hipaa.json
```

---

# 32. Customer Atlassian Usage Context

Regulatory applicability should not assume that merely using Atlassian makes a law applicable.

Instead, capture Atlassian usage as a separate context.

Example:

```json
{
  "atlassianUsage": {
    "products": [
      "JIRA_CLOUD"
    ],
    "useCases": [
      "CUSTOMER_SUPPORT",
      "INCIDENT_MANAGEMENT"
    ],
    "dataTypes": [
      "PHI"
    ],
    "containsSensitiveData": true,
    "dataSubjects": [
      "CUSTOMER_USERS"
    ],
    "processingRole": "PROCESSOR"
  }
}
```

The usage context may itself contain signals.

Examples:

```text
atlassian.product_used
atlassian.jira_used
atlassian.confluence_used
atlassian.data_contains_phi
atlassian.data_contains_personal_data
atlassian.data_contains_financial_data
atlassian.use_case
atlassian.processing_role
atlassian.data_residency_requirement
```

These should be included only when necessary for the relevant assessment.

---

# 33. Important Example: Indian SaaS Company

Consider:

```text
HQ:
India

Industry:
SaaS

Product:
Jira Cloud

Customer:
US healthcare organisation

Data:
PHI

Activity:
Processes PHI on behalf of customer
```

A weak system might conclude:

```text
India
+
Software
=
HIPAA not applicable
```

That is not sufficient.

The assessment should reason:

```text
Organisation
      |
      +-- India
      +-- SaaS
      |
      +-- Relationship with US healthcare organisation
      |
      +-- Processes PHI
      |
      +-- Acts on behalf of customer
              |
              v
      HIPAA relevance
              |
              v
      Business Associate context
              |
              v
      Relevant requirements
              |
              v
      Does Atlassian usage involve this data?
              |
              v
      Jira Cloud
              |
              v
      Atlassian capability mapping
```

This is the scenario the POC must prove.

---

# 34. STEP 21 — Execute Customer Assessment

Customer input:

```text
customer-input.json
```

should be converted into signals.

Architecture:

```text
Customer Answers
       |
       v
Signal Resolver
       |
       v
Observable Signals
       |
       v
Derived Concepts
       |
       v
Rule Engine
       |
       v
Applicability
```

The AI should not execute the rules.

The Java rule engine should.

---

# 35. STEP 22 — Adaptive Missing-Information Analysis

After every evaluation, determine:

```text
Which important rules are UNKNOWN?
```

Then:

```text
Which missing signal would resolve the most important UNKNOWN rules?
```

Then select the next question.

Example:

```text
Initial answers
      |
      v
HIPAA = UNKNOWN
      |
      v
Missing:
data.phi
relationship.on_behalf_of_customer
      |
      v
Question:
"Do your services create, receive, maintain or transmit
PHI on behalf of another organisation?"
      |
      v
Customer answers YES
      |
      v
Re-run rules
```

Stop asking questions when:

```text
All material applicability rules resolved
```

or:

```text
Remaining unknowns cannot materially change the result
```

or:

```text
Required information is unavailable
```

The result should explicitly state which one occurred.

---

# 36. STEP 23 — Atlassian Readiness Assessment

Once regulatory requirements are identified:

```text
Applicable Requirements
        |
        v
Customer Atlassian Usage
        |
        v
Relevant Atlassian Mappings
        |
        v
Coverage Analysis
```

Example output:

```json
{
  "framework": "HIPAA",
  "regulatoryApplicability": {
    "status": "LIKELY_APPLICABLE"
  },
  "requirements": [
    {
      "requirementId": "HIPAA-REQ-001",
      "status": "RELEVANT",
      "atlassianRelevance": "RELEVANT"
    }
  ],
  "atlassianAssessment": [
    {
      "product": "JIRA_CLOUD",
      "coverage": "SUPPORTS_WITH_CONFIGURATION",
      "customerResponsibility": [
        "..."
      ],
      "unknowns": [
        "..."
      ]
    }
  ]
}
```

---

# 37. STEP 24 — Final Pre-Sales Readiness Result

The final result should answer five questions.

## 1. What regulations may apply?

```text
HIPAA — Likely Applicable
```

## 2. Why?

```text
Customer processes PHI on behalf of a US healthcare organisation.
```

## 3. What requirements matter?

```text
Business Associate requirements
Security requirements
etc.
```

## 4. Does the customer's Atlassian usage intersect with them?

```text
Yes — Jira Cloud is used to process/store relevant customer data.
```

## 5. What does Atlassian provide?

```text
Capability
Coverage
Configuration
Customer responsibility
Unknowns
Evidence
```

---

# 38. Final Assessment Output

The final output should look conceptually like:

```json
{
  "customer": {},
  
  "regulatoryAssessment": {
    "framework": "HIPAA",
    "status": "LIKELY_APPLICABLE",
    "confidence": "HIGH",
    "reasons": [],
    "unknowns": []
  },

  "requirements": [
    {
      "requirementId": "",
      "status": "RELEVANT",
      "reason": "",
      "sourceReferences": []
    }
  ],

  "atlassianContext": {
    "products": [],
    "dataTypes": [],
    "useCases": []
  },

  "atlassianAssessment": [
    {
      "product": "",
      "capability": "",
      "coverage": "",
      "configurationRequired": false,
      "customerResponsibility": [],
      "limitations": [],
      "evidenceReferences": []
    }
  ],

  "missingInformation": [],

  "recommendations": [],

  "humanReviewRequired": true
}
```

---

# 39. Assessment Status Model

Do not force binary TRUE/FALSE outcomes.

Use:

```text
APPLICABLE
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
NOT_APPLICABLE
UNKNOWN
```

For early assessment, prefer:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
UNKNOWN
```

This prevents false certainty.

---

# 40. Confidence Model

Confidence should not mean "AI confidence".

It should represent evidence completeness.

Example:

```text
HIGH

All material applicability conditions have been resolved
using reliable customer signals and authoritative rules.

MEDIUM

Core conditions are resolved but one or more important
assumptions remain.

LOW

Important applicability conditions remain unknown.

UNKNOWN

The available customer information is insufficient.
```

---

# 41. High-Value Signal Philosophy

The system may generate:

```text
100+ candidate signals
```

That is fine.

The customer assessment might use:

```text
5–15 initial signals
```

Then:

```text
0–10 targeted follow-up questions
```

The goal is:

> **Maximum regulatory discrimination with minimum customer effort.**

A signal is high-value when it can materially change the assessment.

Examples:

### High-value

```text
Country
Industry
Organisation type
Countries served
Data types processed
Whether PHI is processed
Whether processing is on behalf of another organisation
Whether regulated-sector customers are served
Atlassian products used
Sensitive data in Atlassian
```

### Usually lower-value for initial intake

```text
Detailed encryption algorithm
Exact retention period
Specific SIEM product
Exact network architecture
Detailed configuration
Evidence documents
```

Those can be deferred until a relevant requirement is identified.

---

# 42. Initial vs Targeted Questions

The system should not ask:

```text
50 questions upfront
```

Instead:

```text
INITIAL QUESTIONS
        |
        v
REGULATORY CLASSIFICATION
        |
        v
TARGETED QUESTIONS
        |
        v
REQUIREMENT CLASSIFICATION
        |
        v
ATLASSIAN QUESTIONS
```

This creates a progressive assessment.

---

# 43. Example Assessment Profile

A HIPAA profile might initially contain:

```text
Organisation
-----------------------------
Country
Industry
Organisation Type

Regulatory Context
-----------------------------
Do you serve healthcare organisations?

Do you process health information?

Do you process PHI on behalf of another organisation?

Do your services create, receive, maintain or transmit PHI?

Atlassian Context
-----------------------------
Which Atlassian products are used?

Is regulated/sensitive data stored or processed
in those products?
```

Not every question needs to appear immediately.

---

# 44. Assessment Profile Must Drive the UI

The future UI should not have HIPAA-specific hard-coded forms.

Instead:

```text
assessment-profile.json
          |
          v
      UI Renderer
          |
          v
      Questionnaire
```

The UI should read:

```text
question
answerType
options
required
conditionalOn
section
helpText
```

Example:

```json
{
  "questionId": "HIPAA-Q003",
  "question": "Do your services process PHI?",
  "answerType": "BOOLEAN",
  "required": false,
  "conditionalOn": {
    "questionId": "HIPAA-Q001",
    "equals": true
  }
}
```

This makes the questionnaire dynamically generated.

---

# 45. Automated Testing

The same assessment profile should generate test input.

Example:

```text
assessment-profile.json
        |
        +----> UI
        |
        +----> Test Generator
        |
        +----> Customer Input JSON
```

Then:

```text
Customer Input
      |
      v
Java Rule Engine
      |
      v
Expected Result
```

The test scenarios should contain:

```json
{
  "scenarioId": "HIPAA-001",
  "description": "US healthcare-related SaaS provider processing PHI",
  "input": {},
  "expected": {
    "HIPAA": "LIKELY_APPLICABLE"
  }
}
```

---

# 46. POC Success Criteria

The POC is successful if it can demonstrate all of the following.

## Regulatory Knowledge

* [ ] Starts from an authoritative source family.
* [ ] Identifies relevant sub-sources.
* [ ] Extracts structured regulatory knowledge.
* [ ] Preserves provenance.
* [ ] Preserves exceptions.
* [ ] Distinguishes explicit statements from inference.

## Signals

* [ ] Generates comprehensive candidate signals.
* [ ] Separates raw signals from derived concepts.
* [ ] Identifies signal dependencies.
* [ ] Selects a small high-value initial signal set.

## Assessment

* [ ] Generates an assessment profile.
* [ ] Profile can drive a future UI.
* [ ] Profile can generate customer input JSON.
* [ ] Customer input can be passed to the deterministic rule engine.
* [ ] UNKNOWN is preserved.
* [ ] Missing information is identified.
* [ ] Questions can be asked adaptively.

## Regulatory Requirements

* [ ] Applicability is separate from requirements.
* [ ] Customer role is considered.
* [ ] Customer activities are considered.
* [ ] Customer relationships are considered.
* [ ] Data context is considered.

## Atlassian

* [ ] Atlassian knowledge is separately sourced.
* [ ] Atlassian capabilities have provenance.
* [ ] Regulatory requirements can be mapped to capabilities.
* [ ] Mapping does not claim complete regulatory compliance.
* [ ] Configuration requirements are represented.
* [ ] Customer responsibilities are represented.
* [ ] Limitations are represented.
* [ ] Unknowns are represented.

## End-to-End

The following scenario must work:

```text
Indian SaaS company
        +
US healthcare customer
        +
Processes PHI
        +
Uses Jira Cloud
        |
        v
HIPAA potentially/likely applicable
        |
        v
Relevant HIPAA requirements
        |
        v
Jira Cloud usage relevant
        |
        v
Atlassian capability mapping
        |
        v
Coverage / configuration / responsibility / gaps
```

---

# 47. AI Guardrails

The following rules are mandatory.

## Never:

* invent regulatory requirements
* invent legal conclusions
* invent source references
* treat guidance as law without qualification
* assume industry alone determines applicability
* assume geography alone determines applicability
* treat UNKNOWN as FALSE
* claim Atlassian compliance based only on a feature
* claim that one product satisfies an entire regulation
* infer unsupported Atlassian capabilities
* ask customers to determine their own legal status
* remove exceptions for simplicity

## Always:

* preserve provenance
* preserve uncertainty
* distinguish fact from inference
* distinguish law/regulation/guidance/standard
* distinguish applicability from requirements
* distinguish requirements from controls
* distinguish regulatory controls from Atlassian capabilities
* preserve customer responsibility
* preserve limitations
* require human approval for generated knowledge

---

# 48. Recommended Artifact Relationships

The final knowledge graph should conceptually look like:

```text
SOURCE
  |
  v
REGULATORY STATEMENT
  |
  +----------------+
  |                |
  v                v
SIGNAL          REQUIREMENT
  |
  v
DERIVED CONCEPT
  |
  v
APPLICABILITY RULE
  |
  v
ASSESSMENT PROFILE
  |
  v
CUSTOMER INPUT
  |
  v
ASSESSMENT RESULT
  |
  v
RELEVANT REQUIREMENT
  |
  v
ATLASSIAN USAGE
  |
  v
ATLASSIAN CAPABILITY
  |
  v
MAPPING
  |
  v
COVERAGE / GAP
```

---

# 49. The Most Important Architectural Separation

Keep these concepts separate:

```text
CUSTOMER FACT

Example:
"Customer processes PHI."

            ↓

DERIVED CONCEPT

Example:
"Potential business associate context."

            ↓

APPLICABILITY

Example:
"HIPAA likely applies."

            ↓

REQUIREMENT

Example:
"Business associate requirements are relevant."

            ↓

ATLASSIAN CONTEXT

Example:
"PHI may be processed in Jira Cloud."

            ↓

ATLASSIAN CAPABILITY

Example:
"Capability X supports part of the requirement."

            ↓

READINESS

Example:
"Supported with configuration; customer responsibility remains."
```

Do not collapse these into one object.

---

# 50. Recommended Implementation Order

Do not implement all artifacts at once.

Implement in this order:

```text
PHASE 1
Source Family
    |
    v
Source Understanding
    |
    v
Knowledge Extraction

PHASE 2
Signals
    |
    v
Derived Concepts
    |
    v
Rules

PHASE 3
Requirements
    |
    v
Questions
    |
    v
High-Value Signal Selection
    |
    v
Assessment Profile

PHASE 4
Customer Input
    |
    v
Java Rule Engine
    |
    v
Test Scenarios

PHASE 5
Atlassian Sources
    |
    v
Atlassian Capabilities
    |
    v
Regulatory → Atlassian Mapping

PHASE 6
End-to-End Assessment
    |
    v
Regulatory Applicability
    |
    v
Requirements
    |
    v
Atlassian Readiness
```

---

# 51. Final POC Definition

The POC should ultimately demonstrate:

```text
                AUTHORITATIVE SOURCES
                         |
             +-----------+-----------+
             |                       |
             v                       v
       REGULATORY SOURCES     ATLASSIAN SOURCES
             |                       |
             v                       v
     REGULATORY KNOWLEDGE     ATLASSIAN KNOWLEDGE
             |                       |
             v                       |
          SIGNALS                     |
             |                       |
             v                       |
      DERIVED CONCEPTS               |
             |                       |
             v                       |
           RULES                     |
             |                       |
             v                       |
      REQUIREMENTS                   |
             |                       |
             +-----------+-----------+
                         |
                         v
                 ASSESSMENT PROFILE
                         |
                         v
                 CUSTOMER QUESTIONS
                         |
                         v
                  CUSTOMER INPUT
                         |
                         v
                DETERMINISTIC ENGINE
                         |
                         v
              REGULATORY APPLICABILITY
                         |
                         v
                RELEVANT REQUIREMENTS
                         |
                         +----------------+
                                          |
                                          v
                                ATLASSIAN USAGE
                                          |
                                          v
                              CAPABILITY MAPPING
                                          |
                                          v
                                  READINESS RESULT
```

The final product is therefore not:

> "Is this customer HIPAA compliant?"

It is:

> **"Based on the customer's organisation, relationships, activities, data and Atlassian usage, which regulatory frameworks and requirements appear relevant, what information is still missing, and how does Atlassian map to those requirements?"**

That is the POC worth building.
