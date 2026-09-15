# AI Regulatory Knowledge POC v2

## From Authoritative Regulatory Sources to Customer-Specific Regulatory Assessment and Walmart Readiness

**Status:** POC
**Primary proving ground:** HIPAA
**Primary objective:** Regulatory applicability + regulatory requirement assessment
**Future objective:** Walmart capability/readiness mapping
**Implementation principle:** AI-generated knowledge + deterministic execution + provenance + human validation

---

# 1. Objective

The objective of this POC is to prove that an AI-assisted pipeline can transform authoritative regulatory sources into structured, traceable and executable regulatory knowledge.

The first POC must answer:

> Given a limited set of high-value facts about a customer, which regulatory frameworks/rules may apply, why do they apply, what regulatory requirements become relevant, and what information is still missing?

The POC must **not** require a customer to provide hundreds of regulatory signals.

The regulatory knowledge model may contain a large number of internal signals and rules. However, the customer-facing assessment must expose only a small number of high-value firmographic signals and targeted questions.

The first POC intentionally stops after:

```text
Customer Input
      |
      v
Signal Resolution
      |
      v
Derived Concepts
      |
      v
Regulatory Applicability
      |
      v
Regulatory Requirements
      |
      v
Missing Information
```

Walmart capability mapping is a separate Phase B.

---

# 2. What This POC Is Trying to Build

The goal is **not** to build a simple:

> "Does HIPAA apply?"

checker.

The goal is to build a reusable regulatory reasoning pipeline:

```text
Authoritative Sources
        |
        v
Source Family
        |
        v
Regulatory Knowledge
        |
        v
Candidate Signals
        |
        v
Derived Concepts
        |
        v
Applicability Rules
        |
        v
Regulatory Requirements
        |
        v
High-Value Assessment Profile
        |
        v
Customer Input
        |
        v
Deterministic Assessment
        |
        v
Regulatory Applicability
        |
        v
Regulatory Requirements
        |
        v
Missing Information
```

The architecture should eventually support:

```text
HIPAA
GDPR
DORA
NIS2
PCI DSS
FedRAMP
CJIS
DPDP
CCPA/CPRA
LGPD
and other frameworks
```

but HIPAA is the first proving ground.

---

# 3. Important Scope Decision

The POC has two phases.

## Phase A — Regulatory Assessment

This is the first and primary POC.

```text
Authoritative Regulatory Sources
              |
              v
      Regulatory Knowledge
              |
              v
           Signals
              |
              v
      Derived Concepts
              |
              v
      Applicability Rules
              |
              v
         Requirements
              |
              v
       High-Value Signals
              |
              v
      Assessment Profile
              |
              v
       Customer Input
              |
              v
       Deterministic Engine
              |
              v
   Regulatory Applicability
              |
              v
   Regulatory Requirements
```

## Phase B — Walmart Readiness

Only after Phase A works:

```text
Phase A Result
      |
      v
Customer Walmart Usage
      |
      v
Walmart Knowledge
      |
      v
Capability Mapping
      |
      v
Coverage / Configuration / Responsibility
      |
      v
Readiness / Gap Analysis
```

This separation is intentional.

The POC must first prove:

> **Can we reliably determine what regulatory requirements matter to a customer?**

Only then should we ask:

> **How does Walmart help address those requirements?**

---

# 4. Core Design Principles

## 4.1 AI is not the legal authority

AI is responsible for:

* source discovery
* source understanding
* regulatory knowledge extraction
* candidate signal discovery
* derived concept generation
* candidate rule generation
* requirement extraction
* exception extraction
* question generation
* high-value signal selection
* assessment profile generation
* test scenario generation

The deterministic application is responsible for:

* resolving customer signals
* executing approved rules
* evaluating expressions
* calculating applicability
* determining missing information
* producing repeatable results

Humans are responsible for:

* validating regulatory knowledge
* approving rules
* resolving legal ambiguity
* validating source interpretation
* approving production-ready regulatory packs

---

# 5. Preserve Provenance

Every important conclusion must be traceable.

The minimum traceability chain is:

```text
Assessment Result
      |
      v
Rule
      |
      v
Derived Concept / Signal
      |
      v
Regulatory Requirement / Statement
      |
      v
Source Section
      |
      v
Authoritative Source
```

For example:

```text
HIPAA = LIKELY_APPLICABLE
        |
        v
HIPAA-R001
        |
        v
potential_business_associate_relationship
        |
        +-- data.phi = TRUE
        +-- acts_on_behalf_of_customer = TRUE
        |
        v
Regulatory statement
        |
        v
HHS source
```

The system must always be able to answer:

1. Why did this rule exist?
2. Which source supports it?
3. Which section supports it?
4. Is the conclusion explicitly stated or inferred?
5. Which customer facts caused the rule to evaluate to TRUE?
6. What assumptions were made?
7. What information is still missing?

---

# 6. Do Not Confuse Regulatory Concepts

The following must remain separate.

## 6.1 Customer Fact

An observable fact.

Example:

```text
customer processes PHI = TRUE
```

## 6.2 Derived Concept

A concept derived from one or more facts.

Example:

```text
potential_business_associate_relationship = TRUE
```

## 6.3 Applicability

A conclusion from approved rules.

Example:

```text
HIPAA = LIKELY_APPLICABLE
```

## 6.4 Regulatory Requirement

An obligation resulting from the applicable regulatory context.

Example:

```text
Business Associate requirements are relevant.
```

## 6.5 Technical Control

A technical implementation concept.

Example:

```text
access control
encryption
audit logging
```

These are not automatically equivalent to regulatory requirements.

---

# 7. Regulatory Assessment Model

The regulatory assessment has two layers.

## Layer 1 — Regulatory Applicability

Question:

> Does this regulatory framework or provision potentially apply to this customer in this context?

The assessment must consider more than:

* headquarters
* industry
* company size

It may need to consider:

* geography
* industry
* organisation type
* regulatory status
* customer relationships
* supplier relationships
* processing relationships
* data types
* data subjects
* processing activities
* storage activities
* transfer activities
* contractual relationships
* role performed for another organisation

Example:

```text
Customer:

HQ = India
Industry = SaaS
Serves = US healthcare organisation
Processes = PHI
Processes PHI on behalf of customer = YES
```

Potential result:

```text
HIPAA = LIKELY_APPLICABLE
```

The system must not make simplistic assumptions such as:

```text
HQ = India
+
Industry = Software
=
HIPAA NOT APPLICABLE
```

---

# 8. Layer 2 — Regulatory Requirements

Question:

> Given the customer's regulatory role, activities, data and relationships, which regulatory requirements become relevant?

For example, these customers may interact with HIPAA differently:

```text
Customer A:
Healthcare provider

Customer B:
SaaS provider processing PHI on behalf of a healthcare organisation

Customer C:
Generic software company with no PHI processing
```

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

Requirements may include:

* privacy obligations
* security obligations
* administrative safeguards
* technical safeguards
* physical safeguards
* contractual requirements
* documentation requirements
* notification requirements
* data handling obligations
* access requirements
* governance obligations
* retention obligations
* other regulatory obligations

The POC must preserve the distinction between:

```text
"Regulation applies"
```

and:

```text
"Requirement X is relevant"
```

---

# 9. Why Customer Relationships Matter

A core design principle of this POC is:

> Regulatory applicability can depend on what the customer does for another organisation, not just who the customer is.

For example:

```text
Customer:
Indian SaaS company

Customer relationship:
US healthcare organisation

Activity:
Processes PHI on behalf of that organisation
```

The following signal:

```text
HQ = India
```

is insufficient to determine HIPAA applicability.

A targeted signal such as:

```text
processes_phi_on_behalf_of_another_organisation
```

can be much more valuable.

Therefore the system should model:

```text
Organisation
    |
    +-- Customers
    +-- Suppliers
    +-- Partners
    +-- Healthcare organisations
    +-- Financial institutions
    +-- Government organisations
    +-- Other regulated entities
```

---

# 10. HIPAA Source Strategy

The POC should not begin with only one narrow HIPAA page.

Start from the authoritative HHS HIPAA professional resource as the source-family root.

Conceptually:

```text
HHS HIPAA for Professionals
        |
        +-- HIPAA overview
        |
        +-- Privacy Rule
        |
        +-- Security Rule
        |
        +-- Breach Notification Rule
        |
        +-- Business Associates
        |
        +-- Enforcement
        |
        +-- Relevant guidance
        |
        +-- Relevant regulatory references
```

The objective is not to scrape the entire HHS website.

The objective is:

> Start with an authoritative regulatory root, identify the authoritative sources needed to understand scope and requirements, then extract structured regulatory knowledge from those sources.

---

# 11. Step 2 vs Step 3

These two steps must remain separate.

## Step 2 — Source Discovery

Question:

> Which authoritative sources do we need?

Output:

```text
HIPAA source family

Source 1
HHS HIPAA overview

Source 2
Privacy Rule

Source 3
Security Rule

Source 4
Business Associates

...
```

## Step 3 — Source Understanding

Question:

> What does each source actually say?

Output:

```text
Actors
Data
Activities
Relationships
Conditions
Definitions
Obligations
Exceptions
Scope
```

Therefore:

```text
STEP 2
Find the right documents.

STEP 3
Understand the contents of those documents.
```

---

# 12. Folder Structure

Recommended structure:

```text
ai-regulatory-knowledge-poc/
│
├── README.md
│
├── sources/
│   │
│   ├── hipaa/
│   │   ├── source-family.json
│   │   ├── source-001-hhs-overview.json
│   │   ├── source-002-privacy-rule.json
│   │   ├── source-003-security-rule.json
│   │   ├── source-004-business-associates.json
│   │   ├── source-005-breach-notification.json
│   │   └── raw/
│   │
│   └── walmart/
│       ├── source-001.json
│       ├── source-002.json
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
│   │   ├── assessment-profile.json
│   │   └── customer-input-template.json
│   │
│   └── walmart/
│       ├── products.json
│       ├── capabilities.json
│       ├── controls.json
│       ├── evidence.json
│       └── mappings/
│           └── hipaa.json
│
├── assessments/
│   ├── schemas/
│   │   ├── customer-input-schema.json
│   │   └── assessment-result-schema.json
│   │
│   └── scenarios/
│       ├── scenario-001-hipaa-applicable.json
│       ├── scenario-002-hipaa-not-applicable.json
│       ├── scenario-003-insufficient-information.json
│       └── scenario-004-boundary-case.json
│
├── validated/
│   └── hipaa/
│       ├── approved-knowledge.json
│       ├── approved-rules.json
│       ├── approved-requirements.json
│       ├── approved-assessment-profile.json
│       └── approved-customer-input-schema.json
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
    ├── 13-customer-input-template-generation.md
    ├── 14-validation.md
    ├── 15-test-scenario-generation.md
    │
    ├── phase-b/
    │   ├── 01-walmart-source-discovery.md
    │   ├── 02-walmart-knowledge-extraction.md
    │   └── 03-walmart-capability-mapping.md
    │
    └── final-assessment.md
```

---

# 13. End-to-End POC Steps

## Phase A — Regulatory Assessment

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
18 Execute Customer Assessment
19 Adaptive Missing-Information Analysis
20 Final Regulatory Assessment
```

## Phase B — Walmart Readiness

```text
21 Walmart Source Registration
22 Walmart Source Discovery
23 Walmart Knowledge Extraction
24 Walmart Usage Model
25 Regulatory → Walmart Capability Mapping
26 Walmart Readiness Assessment
27 Final Pre-Sales Readiness Result
```

---

# 14. STEP 1 — Source Registration

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

Important rules:

* Do not call guidance "the law".
* Do not invent effective dates.
* Do not invent versions.
* Preserve source type.
* Preserve publisher.
* Preserve authority.
* Preserve retrieval date.
* Preserve source relationships.
* Preserve URLs.
* Preserve provenance.

---

# 15. STEP 2 — Source Discovery / Source Family Construction

## Objective

Identify the authoritative source set required to understand the framework.

Input:

```text
Root regulatory source
```

Output:

```text
Source family
```

For every discovered source capture:

```text
sourceId
title
publisher
sourceType
URL
relationshipToParent
purpose
authorityLevel
jurisdiction
ruleGenerationAllowed
contextOnly
confidence
reasonForInclusion
```

## Prompt

```text
You are an authoritative regulatory source discovery system.

You have been given an authoritative regulatory framework landing page.

Your objective is to construct the authoritative source family for this framework.

Do not extract detailed regulatory rules yet.

Identify authoritative sources needed to understand:

1. What the framework is.
2. Who may be covered.
3. What data or information is covered.
4. What activities are covered.
5. What major rules or regulations exist.
6. What major obligations exist.
7. What important exceptions or exclusions exist.
8. Which definitions materially affect applicability.
9. Which primary regulatory sources should be used for rule generation.
10. Which government or regulator guidance is useful for interpretation.

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
- ruleGenerationAllowed
- contextOnly
- confidence
- reasonForInclusion

Allowed source types:

PRIMARY_REGULATION
REGULATOR_GUIDANCE
GOVERNMENT_OVERVIEW
DEFINITIONAL_REFERENCE
SECONDARY_SOURCE

Rules:

- Do not invent sources.
- Prefer official government/regulator sources.
- Do not treat secondary sources as primary legal authority.
- Do not assume every linked page is relevant.
- Include a source only when it contributes to scope, applicability, requirements, definitions, exceptions or interpretation.

Output JSON only.
```

---

# 16. STEP 3 — Source Understanding

## Objective

Understand the contents of each registered source before generating signals or rules.

Input:

```text
Registered source
```

Output:

```text
Structured source understanding
```

Extract:

* document title
* authority
* jurisdiction
* source type
* subject matter
* regulated actors
* regulated information
* covered activities
* relationships
* geographic scope
* industry/contextual scope
* applicability concepts
* obligations
* exceptions
* exclusions
* definitions
* cross-references
* ambiguity

## Prompt

```text
You are a regulatory source understanding system.

Understand the supplied authoritative source before generating rules.

Do not determine customer applicability yet.

Do not generate technical controls.

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
- sourceReference
- explicitOrInferred
- confidence

Important:

Do not turn an inference into an explicit regulatory statement.

Output JSON only.
```

---

# 17. STEP 4 — Regulatory Knowledge Extraction

## Objective

Convert source understanding into structured regulatory statements.

Model:

```text
Regulatory Statement
    |
    +-- Actor
    +-- Action
    +-- Object
    +-- Condition
    +-- Relationship
    +-- Exception
    +-- Obligation
    +-- Source
```

## Prompt

```text
Using only the supplied authoritative regulatory source and source understanding, extract structured regulatory knowledge.

For every material regulatory statement identify:

1. statementId
2. conciseStatement
3. statementType
4. actor
5. action
6. object
7. condition
8. exception
9. relationship
10. relatedConcepts
11. sourceSection
12. sourceURL
13. explicitOrInferred
14. confidence

Allowed statement types:

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

Rules:

- Do not invent facts.
- Do not turn guidance into mandatory law without qualification.
- Do not turn examples into universal rules.
- Preserve exceptions.
- Preserve definitions.
- Preserve actor relationships.
- Preserve uncertainty.
- Every material statement requires provenance.

Output JSON only.
```

---

# 18. STEP 5 — Candidate Signal Discovery

## Objective

Generate the comprehensive internal signal universe.

The output may be large.

That is expected.

The internal signal universe should not be confused with the customer questionnaire.

Potential signal categories:

```text
Organisation
Geography
Industry
Organisation Type
Regulatory Status
Data
Data Subjects
Activities
Processing
Storage
Transfers
Relationships
Contracts
Technology
Customer Context
Supplier Context
```

Examples:

```text
organisation.country
organisation.industry
organisation.organisation_type

data.phi
data.ephi
data.personal_data

activity.collects_data
activity.processes_data
activity.stores_data
activity.transmits_data

relationship.customer
relationship.processor
relationship.business_associate
relationship.subcontractor

data.storage_country
data.processing_country

contract.baa_exists
contract.dpa_exists
```

## Prompt

```text
You are designing a generic customer fact model for regulatory applicability.

Using the extracted regulatory knowledge, identify observable customer facts that could materially affect:

- applicability
- scope
- obligations
- exceptions
- exclusions
- verification
- regulatory requirements

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

- signalId
- name
- description
- dataType
- possibleValues
- classification
- whyItMatters
- sourceReferences
- explicitOrInferred
- confidence

Allowed classifications:

DIRECT_APPLICABILITY
INDIRECT_APPLICABILITY
OBLIGATION_TRIGGER
EXCEPTION_TRIGGER
CONTEXT
VERIFICATION
DERIVED_INPUT

Important:

A signal is an observable customer fact.

Good:

data.phi = true

Bad:

organisation.hipaa_applicable = true

The second is a conclusion, not a signal.

Output JSON only.
```

---

# 19. STEP 6 — Derived Concept Generation

## Objective

Create reusable intermediate regulatory concepts derived from raw signals.

Example:

```text
data.phi = TRUE
+
acts_on_behalf_of_customer = TRUE
+
customer_is_healthcare_entity = TRUE

        |
        v

potential_business_associate_relationship = TRUE
```

## Prompt

```text
Using the regulatory knowledge and candidate signals, identify useful derived concepts.

A derived concept is a meaningful regulatory concept calculated from observable customer signals.

It must not simply rename an existing signal.

For each concept provide:

- conceptId
- name
- description
- inputSignals
- derivationLogic
- regulatoryPurpose
- sourceReferences
- confidence

Create a derived concept only when it:

- simplifies rule logic
- improves reuse
- improves explainability
- represents a meaningful regulatory concept

Do not create final applicability results as derived concepts.

Do not create:

hipaa_applicable = true

as a derived concept.

Output JSON only.
```

---

# 20. STEP 7 — Applicability Rule Generation

## Objective

Convert approved regulatory knowledge into executable candidate rules.

Allowed operators:

```text
EQUALS
NOT_EQUALS
EXISTS
NOT_EXISTS
AND
OR
```

Rules must operate on:

```text
Raw signals
+
Approved derived concepts
```

## Prompt

```text
Generate candidate regulatory applicability rules from the approved regulatory knowledge model.

Rules must represent conditions supported by authoritative sources.

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
2. Do not use industry alone unless supported by the source.
3. Do not use geography alone unless supported by the source.
4. Preserve AND conditions.
5. Preserve OR conditions.
6. Preserve exceptions.
7. Preserve uncertainty.
8. Do not turn missing information into FALSE.
9. UNKNOWN must remain UNKNOWN.
10. Every rule must have source provenance.

Output JSON only.
```

---

# 21. STEP 8 — Regulatory Requirement / Obligation Extraction

## Objective

Separate:

```text
Does the framework apply?
```

from:

```text
What requirements become relevant?
```

## Prompt

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

Important:

- Do not create product-specific requirements.
- Do not mention Walmart.
- Do not infer technical implementation from a regulatory statement.
- Keep regulatory requirements technology-neutral.
- Preserve the conditions under which the requirement applies.

Output JSON only.
```

---

# 22. STEP 9 — Exception / Boundary Extraction

Exceptions are first-class knowledge.

## Prompt

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

---

# 23. STEP 10 — Evidence Generation

Evidence should explain both:

```text
Why is this applicable?
```

and:

```text
What evidence would verify it?
```

Evidence types:

```text
REGULATORY_SOURCE
CUSTOMER_PROVIDED
CUSTOMER_DOCUMENT
SYSTEM_DERIVED
VERIFICATION_REQUIRED
```

## Prompt

```text
For each rule and requirement, identify evidence that could support the conclusion.

Do not invent evidence.

For each evidence item provide:

- evidenceId
- type
- supports
- description
- sourceReference
- customerProvided
- externallyVerifiable
- requiredForInitialAssessment
- confidence

Distinguish:

1. Evidence needed to determine applicability.
2. Evidence needed to verify the conclusion.
3. Evidence needed for audit/compliance proof.

Output JSON only.
```

---

# 24. STEP 11 — Discovery Question Generation

Questions must collect facts.

They must not ask customers to interpret law.

Bad:

```text
Are you subject to HIPAA?
```

Better:

```text
Do you provide services to US healthcare organisations?
```

Better:

```text
Do your services create, receive, maintain or transmit
protected health information on behalf of another organisation?
```

## Prompt

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

Prefer questions that:

- resolve multiple rules
- resolve high-impact UNKNOWN states
- are easy for business users to answer
- avoid legal terminology where possible
- cannot be reliably inferred from other signals

Avoid duplicate questions.

Output JSON only.
```

---

# 25. STEP 12 — High-Value Signal Selection

This is one of the most important steps in the POC.

The internal signal universe can be large.

The customer-facing input must be small.

The goal is:

> Maximum regulatory discrimination with minimum customer effort.

## Signal tiers

```text
TIER_1_INITIAL
TIER_2_TARGETED
TIER_3_REQUIREMENT_SPECIFIC
TIER_4_VERIFICATION
```

### Tier 1

Initial information required for broad classification.

Examples:

```text
organisation.country
organisation.industry
organisation.organisation_type
organisation.operating_countries
```

### Tier 2

Questions asked only when relevant.

Examples:

```text
serves_healthcare_organisations
processes_phi
acts_on_behalf_of_another_organisation
```

### Tier 3

Requirement-specific questions.

Examples:

```text
data_transfer_outside_jurisdiction
specific_retention_requirement
specific_contractual_requirement
```

### Tier 4

Verification/evidence.

Examples:

```text
contract_document
certification
architecture_document
configuration_evidence
```

## Signal ranking criteria

Prioritize signals that:

* materially affect applicability
* affect multiple rules
* resolve UNKNOWN
* distinguish regulatory contexts
* enable multiple derived concepts
* are easy to answer
* cannot safely be inferred from weaker signals
* have high information value

## Prompt

```text
You are designing a minimal high-value regulatory assessment.

The internal signal model may contain a very large number of signals.

Your job is NOT to expose all signals to the customer.

Your job is to select the smallest useful set of signals that can determine regulatory applicability with minimal customer effort.

Optimize for:

1. Regulatory applicability coverage.
2. Number of rules affected.
3. Reduction of UNKNOWN results.
4. Information value.
5. Customer answerability.
6. Reusability across requirements.
7. Ability to derive other concepts.
8. Ability to distinguish materially different regulatory contexts.

Classify every candidate signal as exactly one of:

TIER_1_INITIAL
TIER_2_TARGETED
TIER_3_REQUIREMENT_SPECIFIC
TIER_4_VERIFICATION

Classify collection method as:

PREFILLED
CUSTOMER_QUESTION
DERIVED
EVIDENCE
SYSTEM_LOOKUP

Important:

Do not select a signal simply because it appears in a rule.

Prefer signals that resolve multiple rules.

Prefer firmographic signals where reliable.

Prefer targeted questions for facts that cannot be reliably inferred.

Do not ask customers to determine legal applicability.

For each selected signal provide:

- signalId
- tier
- priority
- collectionMethod
- reason
- rulesAffected
- derivedConceptsAffected
- informationValue
- customerAnswerability
- canBePrefilled
- requiredForInitialAssessment

For every Tier 2 signal provide:

- triggerCondition
- whyItIsDeferred

For every Tier 3 signal provide:

- requirementTrigger
- whyItIsDeferred

Also identify:

- redundantSignals
- lowValueSignals
- signalsThatShouldNotBeCustomerFacing
- signalsThatShouldBeDerivedInstead

Finally produce:

1. initialSignals
2. targetedSignals
3. requirementSpecificSignals
4. verificationSignals
5. excludedSignals
6. rationale
7. estimatedInitialQuestionCount
8. stoppingConditions
9. adaptiveQuestionRules

Output JSON only.
```

---

# 26. STEP 13 — Assessment Profile Generation

## Objective

Create the **small customer-facing assessment definition**.

This is not another copy of `signals.json`.

It answers:

> What should the customer be asked during the initial assessment?

The profile is intended to drive a future UI.

Therefore:

```text
assessment-profile.json
        |
        v
     UI Renderer
        |
        v
Customer Questionnaire
```

It should contain:

* sections
* fields
* questions
* question types
* options
* required status
* conditional logic
* collection method
* high-value signals
* adaptive-questioning metadata

---

## 26.1 Assessment Profile Methodology

### Step 13.1 — Determine rule dependencies

For every applicability rule:

```text
Rule
  |
  +-- Required signals
  +-- Required derived concepts
```

Example:

```text
HIPAA-R001

Requires:
  data.phi
  acts_on_behalf_of_customer
```

### Step 13.2 — Determine whether the signal can be prefilled

Example:

```text
organisation.country
```

may be available from customer metadata.

Use:

```text
PREFILLED
```

instead of asking.

### Step 13.3 — Identify customer questions

Facts such as:

```text
processes_phi
```

may require direct customer input.

### Step 13.4 — Rank questions

Prefer questions that:

* affect many rules
* eliminate UNKNOWN
* distinguish important regulatory contexts
* are easy to answer

### Step 13.5 — Remove redundant questions

If one question can resolve the same dependency as three questions, prefer the single high-quality question.

### Step 13.6 — Define conditional questions

Example:

```text
If customer serves healthcare organisations = YES

then ask:

Do you process PHI?
```

### Step 13.7 — Define stopping conditions

The assessment should stop when:

```text
All material applicability rules are resolved
```

or:

```text
Remaining UNKNOWN rules cannot materially change the result
```

or:

```text
Required information is unavailable
```

---

## 26.2 Assessment Profile Example

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
      "fields": [
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

  "minimumInformationPolicy": {
    "doNotAskUnnecessaryQuestions": true,
    "stopWhenMaterialRulesResolved": true
  }
}
```

---

## 26.3 Step 13 Prompt

```text
You are designing a minimal customer-facing regulatory assessment.

You are given:

1. Regulatory signals
2. Derived concepts
3. Applicability rules
4. Regulatory requirements
5. Discovery questions
6. Signal dependencies

The internal regulatory model may contain a very large number of signals.

Your job is NOT to expose all signals to the customer.

Your job is to design the smallest high-value initial assessment that can determine regulatory applicability with minimal customer effort.

OBJECTIVE

Select the minimum useful set of customer inputs required to:

1. Determine important regulatory applicability conditions.
2. Resolve as many material UNKNOWN results as possible.
3. Distinguish materially different regulatory contexts.
4. Identify which regulatory requirements may become relevant.
5. Avoid asking questions that can be reliably derived or prefilled.
6. Avoid asking customers to interpret legal requirements.

SIGNAL CLASSIFICATION

Classify each candidate signal as exactly one of:

TIER_1_INITIAL
TIER_2_TARGETED
TIER_3_REQUIREMENT_SPECIFIC
TIER_4_VERIFICATION

COLLECTION METHOD

Classify the preferred collection method as:

PREFILLED
CUSTOMER_QUESTION
DERIVED
EVIDENCE
SYSTEM_LOOKUP

HIGH-VALUE SIGNAL CRITERIA

Prioritize signals that:

- materially affect applicability
- affect multiple rules
- resolve UNKNOWN
- distinguish different regulatory contexts
- enable multiple derived concepts
- are easy for a customer to answer
- are unlikely to be ambiguous
- cannot be safely inferred from weaker signals

IMPORTANT

Do not select a signal simply because it appears in a rule.

Do not ask:

"Are you subject to HIPAA?"

Instead ask for observable facts such as:

"Do you provide services to a US healthcare organisation?"

"Do your services create, receive, maintain or transmit PHI?"

"Do you process PHI on behalf of another organisation?"

Do not ask customers to make legal determinations.

For each selected signal provide:

- signalId
- tier
- priority
- collectionMethod
- reason
- rulesAffected
- derivedConceptsAffected
- informationValue
- customerAnswerability
- canBePrefilled
- requiredForInitialAssessment

For every Tier 2 signal provide:

- triggerCondition
- whyItIsDeferred

For every Tier 3 signal provide:

- requirementTrigger
- whyItIsDeferred

Also identify:

- redundantSignals
- lowValueSignals
- signalsThatShouldNotBeCustomerFacing
- signalsThatShouldBeDerivedInstead

Finally produce:

1. initialSignals
2. targetedSignals
3. requirementSpecificSignals
4. verificationSignals
5. excludedSignals
6. rationale
7. estimatedInitialQuestionCount
8. stoppingConditions
9. adaptiveQuestionRules

Output JSON only.
```

---

# 27. STEP 14 — Customer Input Template Generation

This step is different from Step 13.

Step 13 answers:

> What information should we collect?

Step 14 answers:

> How should the customer's answers be represented so the UI and rule engine can consume them?

Therefore:

```text
Step 13
Assessment Profile
       |
       v
What do we ask?

Step 14
Customer Input Template
       |
       v
How are the answers represented?
```

---

# 28. Customer Input Design Principles

## 28.1 Customer input contains facts, not conclusions

Bad:

```json
{
  "hipaaApplicable": true
}
```

Good:

```json
{
  "processesPHI": true
}
```

Good:

```json
{
  "actsOnBehalfOfAnotherOrganisation": true
}
```

---

## 28.2 UNKNOWN must be supported

Do not force customers to answer:

```text
TRUE
FALSE
```

when they don't know.

Use:

```text
TRUE
FALSE
UNKNOWN
```

Example:

```json
{
  "processesPHI": "UNKNOWN"
}
```

UNKNOWN must never be silently converted into FALSE.

---

## 28.3 Keep internal signal IDs separate

The customer should answer:

```text
Do you process PHI?
YES
```

The system resolves that to:

```text
data.phi = TRUE
```

The customer does not need to understand internal signal identifiers.

---

# 29. Customer Input Template Example

```json
{
  "assessmentId": "CUSTOMER-001",
  "assessmentProfileId": "HIPAA-INITIAL",

  "customer": {
    "country": "IN",
    "industry": "SOFTWARE",
    "organisationType": "SAAS_PROVIDER"
  },

  "answers": {
    "HIPAA-Q001": "TRUE",
    "HIPAA-Q002": "TRUE",
    "HIPAA-Q003": "UNKNOWN"
  }
}
```

The signal resolver converts this to:

```text
organisation.country = IN
organisation.industry = SOFTWARE
organisation.organisation_type = SAAS_PROVIDER

serves_healthcare_organisations = TRUE
processes_phi = TRUE
acts_on_behalf_of_another_organisation = UNKNOWN
```

The rule engine then evaluates the rules.

---

# 30. Step 14 Methodology

### Step 14.1

Read the assessment profile.

### Step 14.2

Create the customer-facing schema.

### Step 14.3

Map every answer to an internal signal.

### Step 14.4

Support:

```text
TRUE
FALSE
UNKNOWN
```

### Step 14.5

Identify prefillable fields.

### Step 14.6

Identify conditional questions.

### Step 14.7

Generate empty template.

### Step 14.8

Generate example filled template.

### Step 14.9

Generate JSON Schema for automated validation.

### Step 14.10

Use the same input contract for:

```text
Future UI
+
Manual testing
+
Automated test scenarios
+
Rule engine execution
```

---

# 31. Step 14 Prompt

```text
You are designing the customer input contract for a regulatory assessment engine.

You are given:

1. Assessment profile
2. Selected high-value signals
3. Discovery questions
4. Signal definitions
5. Derived concepts
6. Applicability rules

Generate a customer-facing JSON input template.

OBJECTIVE

The template must:

1. Represent all required Tier 1 inputs.
2. Represent questions selected for the initial assessment.
3. Support UNKNOWN / unanswered values.
4. Preserve question-to-signal mapping.
5. Be suitable for a future UI.
6. Be directly consumable by a signal resolver.
7. Be usable for deterministic rule-engine testing.
8. Hide internal implementation details wherever possible.

CUSTOMER INPUT MUST REPRESENT FACTS.

Bad:

"hipaaApplicable": true

Good:

"processesPHI": true

Good:

"actsOnBehalfOfAnotherOrganisation": true

UNKNOWN

The template must support:

TRUE
FALSE
UNKNOWN

Do not convert UNKNOWN into FALSE.

STRUCTURE

Create:

- schema
- emptyTemplate
- exampleInput
- fieldDescriptions
- signalMappings
- conditionalQuestions
- prefillableFields

Each field should contain:

- field name
- type
- required
- nullable / UNKNOWN support
- allowed values
- mapped signal
- mapped question, if applicable

The generated JSON should be simple enough that a frontend can render a form from the assessment profile.

Do not expose final regulatory conclusions as customer input fields.

Output JSON only.
```

---

# 32. STEP 15 — Validation

Validation must happen before the generated regulatory pack is executed.

Validate:

```text
Source traceability
Signal validity
Derived concept validity
Rule validity
Requirement validity
Question validity
Exception coverage
Assessment profile references
Customer input schema
No orphan signals
No orphan rules
No unsupported legal claims
No missing provenance
No circular derived concepts
UNKNOWN handling
```

## Validation Prompt

```text
Validate the generated regulatory knowledge package.

Check:

1. Every rule references valid signals or derived concepts.
2. Every derived concept references valid signals.
3. Every requirement references authoritative evidence.
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
14. Customer input template references valid profile fields.
15. Conditional questions reference valid signals/questions.
16. Every rule has deterministic executable syntax.

Output:

- validationStatus
- errors
- warnings
- recommendations

Output JSON only.
```

---

# 33. STEP 16 — Test Scenario Generation

The test suite must test both positive and negative cases.

At minimum:

```text
Scenario 1 — HIPAA likely applicable
Scenario 2 — HIPAA not currently indicated
Scenario 3 — insufficient information
Scenario 4 — customer relationship changes result
Scenario 5 — PHI processing changes result
Scenario 6 — acting on behalf of another organisation changes result
Scenario 7 — exception applies
Scenario 8 — multiple rules produce different outcomes
Scenario 9 — UNKNOWN remains UNKNOWN
Scenario 10 — targeted question resolves UNKNOWN
```

## Prompt

```text
Generate deterministic customer assessment test scenarios.

The scenarios must test:

1. Positive applicability
2. Negative applicability
3. UNKNOWN / insufficient information
4. Boundary conditions
5. Exceptions
6. Customer relationship changes
7. Data type changes
8. Processing activity changes
9. Multiple rules
10. Conditional questions
11. UNKNOWN resolution

For every scenario provide:

- scenarioId
- description
- customerInput
- expectedSignals
- expectedDerivedConcepts
- expectedRuleResults
- expectedApplicability
- expectedRequirements
- expectedMissingInformation

Do not invent legal outcomes.

Expected results must be derived from the approved rules.

Output JSON only.
```

---

# 34. STEP 17 — Requirement Pack Assembly

The approved regulatory knowledge pack should contain:

```text
Framework
Sources
Source Understanding
Knowledge
Signals
Derived Concepts
Rules
Requirements
Exceptions
Evidence
Questions
Signal Dependencies
Assessment Profile
Customer Input Schema
Test Scenarios
Validation
```

Example:

```json
{
  "framework": {},
  "sources": [],
  "sourceUnderstanding": {},
  "knowledge": [],
  "signals": [],
  "derivedConcepts": [],
  "rules": [],
  "requirements": [],
  "exceptions": [],
  "evidence": [],
  "questions": [],
  "signalDependencies": [],
  "assessmentProfile": {},
  "customerInputSchema": {},
  "testScenarios": [],
  "validation": {}
}
```

Only validated artifacts should be used by the deterministic assessment engine.

---

# 35. STEP 18 — Execute Customer Assessment

This is the first major POC demonstration.

Input:

```text
customer-input.json
```

Processing:

```text
Customer Input
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
Approved Rules
      |
      v
Rule Engine
      |
      v
Applicability Results
      |
      v
Requirement Evaluation
```

AI should not execute the rules.

The deterministic rule engine should.

---

# 36. Signal Resolver

The customer-facing answer:

```text
Do you process PHI?
YES
```

should become:

```text
data.phi = TRUE
```

Similarly:

```text
Do you process PHI on behalf of another organisation?
YES
```

becomes:

```text
relationship.on_behalf_of_customer = TRUE
```

The signal resolver should also validate:

* data type
* allowed values
* UNKNOWN
* required fields
* mappings
* question dependencies

---

# 37. Example Customer Scenario

Customer:

```text
HQ:
India

Industry:
Software

Organisation Type:
SaaS Provider

Serves healthcare organisations:
Yes

Processes PHI:
Yes

Processes PHI on behalf of another organisation:
Yes
```

Customer input:

```json
{
  "assessmentId": "CUSTOMER-001",
  "assessmentProfileId": "HIPAA-INITIAL",

  "customer": {
    "country": "IN",
    "industry": "SOFTWARE",
    "organisationType": "SAAS_PROVIDER"
  },

  "answers": {
    "HIPAA-Q001": "TRUE",
    "HIPAA-Q002": "TRUE",
    "HIPAA-Q003": "TRUE"
  }
}
```

---

# 38. Expected Assessment Result

The engine might produce:

```text
REGULATORY APPLICABILITY

HIPAA
Status: LIKELY_APPLICABLE

Reasons:

- Customer serves healthcare organisations.
- Customer processes PHI.
- Customer processes PHI on behalf of another organisation.
```

Then:

```text
REGULATORY REQUIREMENTS

Business Associate requirements
Status: RELEVANT

Security requirements
Status: RELEVANT

Additional requirements
Status: REQUIRES FURTHER ASSESSMENT
```

Then:

```text
MISSING INFORMATION

No additional information required for initial applicability.

Requirement-specific information may still be required.
```

The exact result must come from the approved rules and source evidence.

---

# 39. Negative Scenario

Customer:

```text
HQ:
India

Industry:
Software

Organisation Type:
SaaS Provider

Serves healthcare organisations:
No

Processes PHI:
No

Processes PHI on behalf of another organisation:
No
```

Expected:

```text
HIPAA
Status: NOT_CURRENTLY_INDICATED
```

The result should still include:

```text
Reason:
The available customer facts do not satisfy
the approved HIPAA applicability conditions.
```

---

# 40. UNKNOWN Scenario

Customer:

```text
HQ:
India

Industry:
Software

Serves healthcare organisations:
UNKNOWN

Processes PHI:
UNKNOWN

Processes PHI on behalf of another organisation:
UNKNOWN
```

Expected:

```text
HIPAA
Status: UNKNOWN
```

Missing information:

```text
1. Whether customer serves healthcare organisations.
2. Whether customer processes PHI.
3. Whether customer processes PHI on behalf of another organisation.
```

The system must not say:

```text
HIPAA = NOT APPLICABLE
```

just because information is missing.

---

# 41. STEP 19 — Adaptive Missing-Information Analysis

After every assessment, identify unresolved material rules.

Process:

```text
Assessment
    |
    v
Which material rules are UNKNOWN?
    |
    v
Which signals are missing?
    |
    v
Which question resolves the most important UNKNOWNs?
    |
    v
Ask next question
    |
    v
Re-run assessment
```

The system should prefer the question with the highest information value.

Example:

```text
Current:

HIPAA = UNKNOWN

Missing:
data.phi
acts_on_behalf_of_customer

Available questions:

Q1 — Do you process PHI?
Q2 — Do you store data?
Q3 — Do you have an information security policy?

Choose:

Q1
```

because it directly resolves the material applicability uncertainty.

---

# 42. Adaptive Question Selection Prompt

```text
You are selecting the next best customer question for a regulatory assessment.

You are given:

- Current customer signals
- Current rule results
- UNKNOWN rules
- Missing signals
- Available questions
- Rule dependencies

Select the single highest-value next question.

Prioritize questions that:

1. Resolve the greatest number of material UNKNOWN rules.
2. Can change regulatory applicability.
3. Can distinguish important regulatory contexts.
4. Are easy for the customer to answer.
5. Do not duplicate information already available.
6. Do not ask the customer to interpret legal requirements.

Do not ask questions whose answers cannot materially affect the current assessment.

Return:

- questionId
- question
- signalsResolved
- rulesResolved
- reason
- informationValue

Output JSON only.
```

---

# 43. Assessment Stopping Conditions

The system should stop asking questions when one of the following is true:

## Condition 1

All material applicability rules are resolved.

## Condition 2

Remaining UNKNOWN rules cannot materially change the assessment.

## Condition 3

Required information is unavailable.

## Condition 4

The assessment has reached a configured question budget.

Example:

```text
Initial questions: 5
Targeted questions: maximum 5
```

The system should then report:

```text
Assessment incomplete
```

rather than inventing a conclusion.

---

# 44. Regulatory Result Model

Do not use only:

```text
TRUE
FALSE
```

Use:

```text
APPLICABLE
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
NOT_APPLICABLE
UNKNOWN
```

For the initial POC, the preferred statuses are:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
UNKNOWN
```

This avoids false certainty.

---

# 45. Confidence Model

Confidence should reflect evidence completeness, not generic AI confidence.

## HIGH

All material applicability conditions are resolved using reliable customer signals and approved rules.

## MEDIUM

Core conditions are resolved but one or more assumptions remain.

## LOW

Important applicability conditions remain unresolved.

## UNKNOWN

Available customer information is insufficient to reach a meaningful conclusion.

---

# 46. Assessment Result Schema

Example:

```json
{
  "assessmentId": "CUSTOMER-001",

  "frameworkResults": [
    {
      "frameworkId": "HIPAA",
      "status": "LIKELY_APPLICABLE",
      "confidence": "HIGH",

      "reasons": [
        {
          "ruleId": "HIPAA-R001",
          "description": "Customer processes PHI on behalf of another organisation"
        }
      ],

      "requirements": [
        {
          "requirementId": "HIPAA-REQ-001",
          "status": "RELEVANT",
          "reason": "Applicable based on customer role and processing context"
        }
      ],

      "missingInformation": []
    }
  ]
}
```

---

# 47. Explainability

Every result should be explainable.

For:

```text
HIPAA = LIKELY_APPLICABLE
```

the system should show:

```text
Why?

Rule HIPAA-R001 evaluated TRUE.

Inputs:

data.phi = TRUE
acts_on_behalf_of_customer = TRUE

Derived concept:

potential_business_associate_relationship = TRUE

Source:

HHS source reference

Confidence:

HIGH
```

This is more useful than simply showing:

```text
HIPAA = TRUE
```

---

# 48. POC Phase A Success Criteria

Phase A is successful if it can demonstrate:

## Source

* authoritative source family
* source provenance
* source relationships

## Regulatory Knowledge

* structured regulatory statements
* definitions
* scope
* applicability concepts
* obligations
* exceptions

## Signals

* comprehensive internal signal universe
* derived concepts
* signal dependencies

## Customer Experience

* small number of high-value initial inputs
* targeted questions
* adaptive questions
* no unnecessary questions

## Assessment

* customer input JSON
* deterministic rule execution
* applicability results
* explanations
* confidence
* missing information

## Requirements

* applicability separated from requirements
* requirements depend on customer context
* requirement provenance
* requirement-specific unknowns

## Testing

* positive scenario
* negative scenario
* UNKNOWN scenario
* boundary cases
* exception cases
* adaptive question scenario
* automated deterministic tests

---

# 49. What Phase A Does NOT Do

Phase A does not:

* determine legal compliance
* provide legal advice
* provide regulatory certification
* map requirements to Walmart
* claim Walmart satisfies HIPAA
* determine technical architecture
* automatically approve regulatory interpretation
* replace legal/compliance review

Phase A answers:

> **What regulatory obligations appear relevant based on the information currently available?**

---

# 50. Phase B — Walmart Readiness

Only after Phase A succeeds.

Phase B answers:

> Given the applicable regulatory requirements, how does the customer's Walmart usage intersect with those requirements?

---

# 51. Walmart Knowledge Must Be Separate

Regulatory sources answer:

```text
What does HIPAA require?
```

Walmart sources answer:

```text
What does Walmart provide?
```

These are different claims and require independent provenance.

Therefore:

```text
REGULATORY KNOWLEDGE
    |
    +-- HIPAA
    +-- GDPR
    +-- DORA
    +-- ...

WALMART KNOWLEDGE
    |
    +-- Products
    +-- Services
    +-- Capabilities
    +-- Security
    +-- Privacy
    +-- Data Handling
    +-- Compliance Programs
    +-- Configuration
    +-- Limitations
```

The mapping between these domains is a separate artifact.

---

# 52. Phase B — Walmart Source Registration

Register authoritative Walmart sources relevant to the use case.

Examples may include:

* Walmart corporate compliance documentation
* Walmart security documentation
* Walmart privacy documentation
* Walmart product/service documentation
* Walmart data handling documentation
* Walmart contractual documentation
* Walmart regulatory/compliance documentation

Prefer official Walmart sources for Walmart capability claims.

The exact source set must be discovered and validated as part of Phase B.

---

# 53. Phase B — Walmart Knowledge Extraction

Extract:

```text
Product
Service
Capability
Feature
Configuration
Availability
Prerequisite
Limitation
Customer Responsibility
Compliance Evidence
Data Handling
Security Properties
Privacy Properties
```

Do not determine regulatory applicability here.

---

# 54. Phase B — Walmart Usage Context

The customer may use Walmart products/services in a way that makes a regulatory requirement relevant.

Example:

```text
Customer:
Indian SaaS company

Customer:
US healthcare organisation

Data:
PHI

Walmart:
Relevant Walmart service

Use:
Customer support / business operation
```

The assessment must be able to represent:

```json
{
  "walmartUsage": {
    "products": [
      "WALMART_SERVICE"
    ],
    "useCases": [
      "CUSTOMER_SUPPORT"
    ],
    "dataTypes": [
      "PHI"
    ],
    "containsSensitiveData": true,
    "processingRole": "PROCESSOR"
  }
}
```

The actual product/service names and values should be based on validated Walmart knowledge.

---

# 55. Phase B — Regulatory Requirement to Walmart Capability Mapping

The mapping should look like:

```text
Regulatory Requirement
        |
        v
Customer Walmart Usage
        |
        v
Walmart Product / Service
        |
        v
Walmart Capability
        |
        v
Coverage
        |
        v
Configuration
        |
        v
Customer Responsibility
        |
        v
Evidence
```

Possible mapping results:

```text
FULLY_RELEVANT
PARTIALLY_RELEVANT
SUPPORTS_REQUIREMENT
SUPPORTS_WITH_CONFIGURATION
CUSTOMER_RESPONSIBILITY
NOT_SUPPORTED
NOT_APPLICABLE
UNKNOWN_REQUIRES_VERIFICATION
```

Never output:

```text
Walmart is HIPAA compliant.
```

Instead output:

```text
Requirement X
    |
    +-- Walmart capability Y
    +-- Supports requirement
    +-- Configuration required
    +-- Customer responsibility
    +-- Evidence
```

---

# 56. Phase B Mapping Prompt

```text
Map approved regulatory requirements to Walmart capabilities.

Important:

Do not claim that Walmart satisfies a regulation in its entirety.

Do not infer product capabilities from the regulation.

Do not infer regulatory compliance from a product feature.

For each candidate mapping determine:

1. Regulatory requirement
2. Walmart product/service
3. Walmart capability
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
- Walmart source evidence

Output JSON only.
```

---

# 57. Final Architecture

The complete future architecture is:

```text
                         AUTHORITATIVE SOURCES
                                  |
                   +--------------+--------------+
                   |                             |
                   v                             v
           REGULATORY SOURCES             WALMART SOURCES
                   |                             |
                   v                             v
          REGULATORY KNOWLEDGE          WALMART KNOWLEDGE
                   |
                   v
                SIGNALS
                   |
                   v
          DERIVED CONCEPTS
                   |
                   v
         APPLICABILITY RULES
                   |
                   v
             REQUIREMENTS
                   |
                   v
        HIGH-VALUE SIGNAL MODEL
                   |
                   v
         ASSESSMENT PROFILE
                   |
                   v
           CUSTOMER INPUT
                   |
                   v
          SIGNAL RESOLUTION
                   |
                   v
          DETERMINISTIC ENGINE
                   |
                   v
       REGULATORY APPLICABILITY
                   |
                   v
       REGULATORY REQUIREMENTS
                   |
                   +----------------------+
                                          |
                                          v
                                  WALMART USAGE
                                          |
                                          v
                               CAPABILITY MAPPING
                                          |
                                          v
                                  READINESS / GAP
```

---

# 58. Final Knowledge Model

Keep these objects separate:

```text
Source
   |
   v
Regulatory Statement
   |
   +---- Signal
   |
   +---- Derived Concept
   |
   +---- Rule
   |
   +---- Requirement
   |
   +---- Exception
   |
   +---- Evidence
   |
   +---- Question
   |
   v
Assessment Profile
   |
   v
Customer Input
   |
   v
Assessment Result
```

Later:

```text
Assessment Result
       |
       v
Relevant Requirement
       |
       v
Walmart Usage Context
       |
       v
Walmart Capability
       |
       v
Capability Mapping
       |
       v
Readiness Result
```

---

# 59. Recommended Implementation Order

Do not implement everything simultaneously.

## Milestone 1 — HIPAA Source Foundation

Implement:

```text
Source Registration
Source Discovery
Source Understanding
```

Output:

```text
source-family.json
source-understanding.json
```

---

## Milestone 2 — HIPAA Regulatory Knowledge

Implement:

```text
Knowledge Extraction
Signal Discovery
Derived Concepts
Rule Generation
Requirement Extraction
Exception Extraction
```

Output:

```text
knowledge.json
signals.json
derived-concepts.json
rules.json
requirements.json
exceptions.json
```

---

## Milestone 3 — Customer Assessment

Implement:

```text
Evidence
Questions
High-Value Signal Selection
Assessment Profile
Customer Input Template
```

Output:

```text
evidence.json
questions.json
signal-dependencies.json
assessment-profile.json
customer-input-template.json
```

---

## Milestone 4 — Deterministic Execution

Use:

```text
Customer Input
       |
       v
Signal Resolver
       |
       v
Derived Concepts
       |
       v
Java Rule Engine
       |
       v
Applicability
       |
       v
Requirements
```

Test:

```text
Applicable
Not Applicable
Unknown
Boundary
Exception
```

---

## Milestone 5 — Adaptive Questions

Implement:

```text
UNKNOWN rules
      |
      v
Missing signals
      |
      v
Highest-value question
      |
      v
Customer answer
      |
      v
Re-run assessment
```

---

## Milestone 6 — Walmart

Only after the above works:

```text
Walmart Sources
      |
      v
Walmart Knowledge
      |
      v
Walmart Usage
      |
      v
Capability Mapping
```

---

# 60. The First End-to-End Demo

The first demo should be deliberately small.

### Customer

```text
Country:
India

Industry:
Software

Organisation Type:
SaaS Provider

Serves US healthcare organisations:
Yes

Processes PHI:
Yes

Processes PHI on behalf of another organisation:
Yes
```

### Engine

```text
HIPAA
LIKELY_APPLICABLE
```

### Explanation

```text
The customer processes PHI on behalf of another organisation
and serves a US healthcare-related customer context.

Relevant regulatory concepts:
- PHI
- covered entity/customer relationship
- acting on behalf of another organisation
```

### Requirements

```text
Business Associate-related requirements
Security-related requirements
Other applicable HIPAA requirements
```

### Missing Information

```text
None required for initial applicability.

Additional information may be required for
requirement-specific assessment.
```

Then run the same engine with:

```text
Processes PHI = FALSE
```

and demonstrate that the result changes.

Then run:

```text
Processes PHI = UNKNOWN
```

and demonstrate:

```text
HIPAA = UNKNOWN
```

with a targeted question generated.

That is the **core POC**.

---

# 61. What Success Looks Like

The most important success criterion is not:

> "Can AI generate a lot of JSON?"

It is:

> **Can AI generate a trustworthy regulatory knowledge package that produces a useful customer assessment with only a small number of high-value inputs?**

The POC should demonstrate:

```text
Large Regulatory Knowledge Model
             |
             v
Small Customer Assessment
             |
             v
Deterministic Result
             |
             v
Explainable Regulatory Requirements
```

For example:

```text
100+ internal signals
        |
        v
10–15 high-value customer inputs
        |
        v
5–10 targeted questions
        |
        v
Regulatory applicability
        |
        v
Relevant requirements
```

The exact numbers are not fixed targets.

The principle is:

> **Do not make the customer consume the complexity of the regulatory knowledge model.**

---

# 62. Final Product Vision

The long-term product should not ask customers:

> "Which laws do you think apply to you?"

It should ask a small number of factual questions.

For example:

```text
Tell us about your organisation
--------------------------------

Country
Industry
Organisation type
Countries served


Tell us about your business
--------------------------------

Do you serve healthcare organisations?

Do you process health information?

Do you process information on behalf of another organisation?

Do you provide services to regulated organisations?


Assessment
--------------------------------

HIPAA
Likely Applicable

Why:
...

Requirements:
...

Missing information:
...
```

The complexity remains inside the regulatory knowledge engine.

---

# 63. Final Architectural Principle

The system should ultimately follow:

```text
CUSTOMER FACTS
      |
      v
REGULATORY CONCEPTS
      |
      v
REGULATORY APPLICABILITY
      |
      v
REGULATORY REQUIREMENTS
      |
      v
WALMART USAGE
      |
      v
WALMART CAPABILITIES
      |
      v
READINESS / GAP ANALYSIS
```

And never collapse these layers.

The core principle is:

> **Facts are collected from customers.**
>
> **Regulatory conclusions are derived by approved rules.**
>
> **Requirements are derived from applicable regulatory context.**
>
> **Walmart capabilities are mapped separately using Walmart evidence.**
>
> **Every important conclusion remains traceable to its source and inputs.**

---

# 64. POC Completion Checklist

## Phase A — Regulatory Assessment

### Sources

* [ ] HIPAA source family registered
* [ ] HHS root source registered
* [ ] Relevant child sources discovered
* [ ] Source authority classified
* [ ] Source provenance captured

### Knowledge

* [ ] Source understanding generated
* [ ] Regulatory statements extracted
* [ ] Definitions extracted
* [ ] Scope extracted
* [ ] Applicability concepts extracted
* [ ] Exceptions extracted
* [ ] Requirements extracted

### Signals

* [ ] Candidate signal universe generated
* [ ] Signal classifications generated
* [ ] Derived concepts generated
* [ ] Signal dependencies generated

### Customer Assessment

* [ ] Questions generated
* [ ] High-value signals selected
* [ ] Tier 1 / Tier 2 / Tier 3 / Tier 4 classification created
* [ ] Assessment profile generated
* [ ] Customer input template generated
* [ ] UNKNOWN supported
* [ ] Conditional questions supported
* [ ] UI-compatible schema created

### Execution

* [ ] Customer input resolves to signals
* [ ] Derived concepts resolve deterministically
* [ ] Rules execute deterministically
* [ ] Applicability result generated
* [ ] Requirements generated
* [ ] Missing information generated
* [ ] Explanation generated
* [ ] Confidence generated

### Testing

* [ ] Positive scenario
* [ ] Negative scenario
* [ ] UNKNOWN scenario
* [ ] Boundary scenario
* [ ] Exception scenario
* [ ] Adaptive question scenario
* [ ] Automated rule-engine tests

---

## Phase B — Walmart Readiness

* [ ] Walmart sources registered
* [ ] Walmart knowledge extracted
* [ ] Products/services modeled
* [ ] Capabilities modeled
* [ ] Limitations modeled
* [ ] Customer responsibility modeled
* [ ] Walmart usage signals modeled
* [ ] Regulatory requirements mapped to capabilities
* [ ] Coverage generated
* [ ] Configuration requirements generated
* [ ] Evidence attached
* [ ] Gaps generated

---

# 65. Final Definition

The POC is complete when the following flow works end-to-end:

```text
                    AUTHORITATIVE SOURCE
                            |
                            v
                    SOURCE FAMILY
                            |
                            v
                 REGULATORY KNOWLEDGE
                            |
                            v
                        SIGNALS
                            |
                            v
                   DERIVED CONCEPTS
                            |
                            v
                  APPLICABILITY RULES
                            |
                            v
                    REQUIREMENTS
                            |
                            v
                HIGH-VALUE SIGNALS
                            |
                            v
                  ASSESSMENT PROFILE
                            |
                            v
                   CUSTOMER INPUT
                            |
                            v
                   SIGNAL RESOLVER
                            |
                            v
                    RULE ENGINE
                            |
                            v
              REGULATORY APPLICABILITY
                            |
                            v
               REGULATORY REQUIREMENTS
                            |
                            v
                 MISSING INFORMATION
```

Then, and only then:

```text
              REGULATORY REQUIREMENTS
                            |
                            v
                   WALMART USAGE
                            |
                            v
                  WALMART KNOWLEDGE
                            |
                            v
                 CAPABILITY MAPPING
                            |
                            v
                 READINESS / GAPS
```

The first POC therefore proves **Regulatory Assessment**.

The second POC proves **Walmart Readiness**.

Together they form the foundation for a reusable **Regulatory & Walmart Readiness Assistant**.
