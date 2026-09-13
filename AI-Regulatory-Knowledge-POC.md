# AI Regulatory Knowledge POC

## From Authoritative Regulation to Executable Compliance Knowledge

**Status:** POC
**Primary proving ground:** HIPAA
**Design goal:** Generic and reusable across laws, regulations, standards, contractual requirements and technical requirements.

---

# 1. Objective

The objective of this POC is to prove that an AI-driven pipeline can take an authoritative regulatory source and automatically transform it into structured regulatory knowledge that can be consumed by a deterministic applicability/rule engine.

The system should reduce the amount of manual effort required to:

* read a regulation
* identify relevant concepts
* identify regulated entities
* identify regulated data
* identify applicability conditions
* discover customer signals
* generate applicability rules
* identify obligations
* identify exceptions
* generate verification requirements
* identify required/suggested evidence
* generate discovery questions
* generate test scenarios
* maintain traceability back to the source

The key principle is:

> **Regulatory knowledge should be data/configuration generated from authoritative sources, not hard-coded into application logic.**

The existing deterministic Java rule engine remains responsible for executing approved knowledge.

AI is responsible for extracting, interpreting and generating candidate knowledge.

---

# 2. Core Architecture

The POC follows this pipeline:

```text
                    AUTHORITATIVE SOURCE
                           |
                           v
                  +-------------------+
                  | Source Ingestion  |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Source Structure  |
                  | & Normalization   |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Knowledge         |
                  | Extraction        |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Signal Discovery  |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Rule Generation   |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Requirement /     |
                  | Obligation        |
                  | Extraction        |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Validation        |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Test Scenario     |
                  | Generation        |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Human Approval    |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Requirement Pack  |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Deterministic     |
                  | Rule Engine       |
                  +-------------------+
                           |
                           v
                  +-------------------+
                  | Applicability     |
                  | Assessment        |
                  +-------------------+
```

---

# 3. Fundamental Design Principle

Separate four things:

```text
AI
=
Perception + Interpretation + Generation
```

```text
Rule Engine
=
Deterministic Execution
```

```text
Evidence / Provenance
=
Accountability
```

```text
Human
=
Final Approval / Legal Judgment
```

AI must **not silently become the legal authority**.

The AI-generated result is a candidate regulatory knowledge model.

The production system should only execute knowledge that has passed validation and approval.

---

# 4. What We Are NOT Building in the POC

Do not build the complete platform.

Do not build:

* production-grade RAG
* complex vector databases
* multi-agent orchestration
* UI
* authentication
* workflow management
* regulatory change monitoring
* large-scale document storage
* sophisticated confidence models
* automatic legal approval
* dozens of laws
* complicated ontology infrastructure

The POC should prove one thing:

> **Can an AI system take one authoritative law/regulation and produce a useful, traceable, executable requirement pack?**

---

# 5. First Regulatory Source

Use HIPAA as the first example.

Prefer authoritative HHS sources.

Recommended starting sources:

* HHS Business Associates guidance
* HHS Security Rule
* HHS Privacy Rule guidance
* Relevant 45 CFR regulatory text

The HHS Business Associates guidance describes covered entities, business associates, PHI-related activities and BAA requirements.

The HHS Security Rule material describes covered entities/business associates, ePHI and administrative, physical and technical safeguards.

For the POC, start with **one source**, not the entire HIPAA ecosystem.

After the pipeline works, add more sources.

---

# 6. POC Folder Structure

Use a simple structure:

```text
ai-regulatory-knowledge-poc/

├── README.md
│
├── sources/
│   └── hipaa/
│       ├── source.json
│       └── source.html
│
├── extracted/
│   └── hipaa/
│       ├── knowledge.json
│       ├── signals.json
│       ├── rules.json
│       ├── requirements.json
│       ├── questions.json
│       ├── evidence.json
│       └── test-scenarios.json
│
├── validated/
│   └── hipaa/
│       └── requirement-pack.json
│
└── prompts/
    ├── 01-knowledge-extraction.md
    ├── 02-signal-discovery.md
    ├── 03-rule-generation.md
    ├── 04-requirement-extraction.md
    ├── 05-validation.md
    └── 06-test-generation.md
```

For the first iteration, these can simply be JSON files.

No database is necessary.

---

# 7. End-to-End POC Steps

The POC consists of the following steps:

```text
Step 1  Source Registration
Step 2  Source Understanding
Step 3  Regulatory Knowledge Extraction
Step 4  Candidate Signal Discovery
Step 5  Derived Concept Generation
Step 6  Applicability Rule Generation
Step 7  Requirement / Obligation Extraction
Step 8  Exception and Boundary Extraction
Step 9  Verification / Evidence Generation
Step 10 Discovery Question Generation
Step 11 Validation
Step 12 Test Scenario Generation
Step 13 Requirement Pack Assembly
Step 14 Execute Against Customer Scenarios
Step 15 Gap Analysis / Human Review
```

The important part is that the steps are **generic**.

Only the source changes.

---

# 8. STEP 1 — Source Registration

Before asking AI to understand the regulation, create a source record.

Example:

```json
{
  "sourceId": "HIPAA-HHS-BA-001",
  "title": "Business Associates",
  "publisher": "U.S. Department of Health and Human Services",
  "sourceType": "REGULATOR_GUIDANCE",
  "jurisdiction": "US",
  "authorityLevel": "HIGH",
  "url": "https://www.hhs.gov/hipaa/for-professionals/privacy/guidance/business-associates/index.html",
  "accessedDate": "2026-09-13",
  "effectiveDate": null,
  "version": null
}
```

Do not invent a version if the source doesn't have one.

Do not assume that a webpage's publication date is its legal effective date.

Keep those concepts separate.

---

# 9. STEP 2 — Source Understanding

Before extracting rules, ask AI to understand the document.

The objective is NOT to create signals yet.

The objective is to identify the structure and scope of the source.

## Prompt

```text
You are a regulatory knowledge extraction system.

You have been given an authoritative regulatory source.

Your job is to understand the source before generating any applicability rules.

Do NOT infer customer applicability yet.

Do NOT create compliance controls yet.

Do NOT invent requirements.

Identify:

1. Document title
2. Issuing authority
3. Jurisdiction
4. Source type
5. Regulatory framework
6. Main subject matter
7. Regulated actors/entities
8. Regulated information/data
9. Activities covered
10. Geographic scope
11. Industry/contextual scope
12. Major applicability concepts
13. Major obligations
14. Exceptions/exclusions
15. Definitions that materially affect applicability
16. Relationships between actors
17. Cross-references to other provisions
18. Source sections/passages supporting each finding

For every finding provide:
- id
- type
- description
- source reference
- confidence
- whether it is explicitly stated or inferred

Never present an inference as an explicit regulatory statement.

Output JSON only.
```

Expected output:

```json
{
  "framework": {
    "name": "HIPAA",
    "jurisdiction": "US"
  },
  "actors": [],
  "data": [],
  "activities": [],
  "applicabilityConcepts": [],
  "obligations": [],
  "exceptions": [],
  "definitions": [],
  "relationships": [],
  "sourceReferences": []
}
```

---

# 10. STEP 3 — Regulatory Knowledge Extraction

Now extract actual regulatory knowledge.

The output should be a structured intermediate representation.

## Important distinction

Do not immediately generate:

```text
signal -> rule
```

First understand:

```text
regulatory statement
        |
        +-- actor
        +-- object
        +-- activity
        +-- condition
        +-- exception
        +-- obligation
        +-- relationship
```

## Prompt

```text
Using only the supplied authoritative source, extract a structured regulatory knowledge model.

For every material regulatory statement identify:

1. Statement ID
2. Statement text or concise paraphrase
3. Statement type:
   - DEFINITION
   - SCOPE
   - APPLICABILITY
   - OBLIGATION
   - PROHIBITION
   - EXCEPTION
   - EXCLUSION
   - PERMISSION
   - CONDITION
   - PROCEDURE
   - CROSS_REFERENCE

4. Actor
5. Action
6. Object
7. Condition
8. Exception
9. Related concepts
10. Source section
11. Source URL
12. Confidence
13. Explicit vs inferred

Rules:

- Do not invent facts.
- Do not use general domain knowledge unless explicitly marked as inference.
- Do not turn guidance into a mandatory legal requirement.
- Do not turn examples into universal rules.
- Preserve ambiguity where the source is ambiguous.
- Preserve exceptions.
- Preserve definitions that affect scope.
- Every extracted item must have source provenance.

Output JSON.
```

Save as:

```text
extracted/<law>/knowledge.json
```

---

# 11. STEP 4 — Candidate Signal Discovery

This is one of the most important steps.

The AI must NOT only extract signals explicitly mentioned in applicability clauses.

It must construct a **candidate customer fact model**.

For example, regulatory applicability may depend on:

```text
Organisation
Data
Activity
Relationship
Geography
Contract
Technology
Industry
Customer type
Regulatory status
```

## Candidate dimensions

### Organisation

```text
organisation.country
organisation.region
organisation.industry
organisation.sub_industry
organisation.organisation_type
organisation.employee_count
organisation.is_healthcare_provider
organisation.is_health_plan
```

### Data

```text
data.personal_data
data.health_data
data.phi
data.ephi
data.financial_data
data.payment_data
data.employee_data
data.children_data
data.biometric_data
```

### Activity

```text
activity.collects_data
activity.processes_data
activity.stores_data
activity.transmits_data
activity.sells_data
activity.profiles_individuals
activity.monitors_individuals
```

### Relationship

```text
relationship.customer
relationship.processor
relationship.controller
relationship.business_associate
relationship.subcontractor
relationship.service_provider
```

### Geography

```text
data.storage_country
data.processing_country
data.transfer_country
data.subject_country
organisation.operating_countries
```

### Contract

```text
contract.dpa_exists
contract.scc_exists
contract.baa_exists
contract.customer_specific_requirement
```

### Technology / Environment

```text
technology.cloud
technology.saas
technology.ai
technology.encrypted
technology.private_connectivity
technology.third_party_processing
```

These are **candidate signals**, not necessarily requirements.

---

# 12. Signal Classification

Every discovered signal should be classified.

Use:

```text
DIRECT_APPLICABILITY
INDIRECT_APPLICABILITY
OBLIGATION_TRIGGER
EXCEPTION_TRIGGER
CONTEXT
DERIVED
```

Example:

```json
{
  "id": "organisation.country",
  "description": "Country where organisation operates",
  "dataType": "STRING",
  "relevance": "CONTEXT",
  "sourceReferences": [],
  "confidence": "MEDIUM"
}
```

The fact that a signal is useful does NOT mean the regulation explicitly says:

> country is required.

It may simply help determine jurisdiction.

---

# 13. Signal Discovery Prompt

```text
You are designing a generic customer fact model for regulatory applicability assessment.

Using the extracted regulatory knowledge, identify all customer facts that could materially affect:

- applicability
- scope
- obligations
- exceptions
- exclusions
- verification
- risk assessment

Do not restrict yourself to signals explicitly named in applicability clauses.

Consider these dimensions:

1. Organisation
2. Geography
3. Industry
4. Sub-industry
5. Organisation type
6. Data types
7. Data subjects
8. Processing activities
9. Storage activities
10. Transfer activities
11. Business relationships
12. Contractual relationships
13. Technology/service characteristics
14. Regulatory status
15. Existing certifications/authorisations
16. Exceptions
17. Obligation-specific facts

For each candidate signal provide:

- id
- name
- description
- data type
- possible values
- relevance classification
- why it matters
- source references
- explicit/inferred
- confidence

Important:

A signal is an observable customer fact.

Do not create derived concepts as raw signals.

For example:

Good:
data.health_data = true

Not good:
organisation.is_hipaa_applicable = true

The second is a conclusion, not an observable fact.

Output JSON only.
```

---

# 14. STEP 5 — Derived Concept Generation

Raw signals are not always enough.

The AI should create intermediate concepts.

Example:

```text
data.phi
activity.maintains_data
relationship.on_behalf_of_covered_entity
```

may produce:

```text
derived.business_associate_relationship
```

Another example:

```text
organisation.country
+
organisation.industry
+
activity.processes_personal_data
```

may produce:

```text
derived.eu_data_processing_context
```

Derived concepts are useful because they make complex rules readable.

## Prompt

```text
Using the candidate customer signals and regulatory knowledge, identify useful derived concepts.

A derived concept is NOT directly collected from the customer.

It is computed from observable signals.

For each derived concept provide:

- id
- name
- description
- input signals
- derivation logic
- regulatory purpose
- source references
- confidence

Do not create derived concepts merely for convenience.

Only create them when they improve regulatory reasoning, reuse or explainability.

Do not encode the final applicability decision as a derived concept.

Output JSON.
```

---

# 15. STEP 6 — Applicability Rule Generation

Now we generate executable logic.

This is where the AI converts regulatory conditions into logical expressions.

Example:

```text
A OR B
```

becomes:

```json
{
  "operator": "OR",
  "children": [
    {
      "operator": "EQUALS",
      "signal": "A",
      "value": "TRUE"
    },
    {
      "operator": "EQUALS",
      "signal": "B",
      "value": "TRUE"
    }
  ]
}
```

For:

```text
A AND (B OR C)
```

generate:

```text
AND
├── A
└── OR
    ├── B
    └── C
```

---

# 16. Rule Generation Prompt

```text
Generate candidate applicability rules from the regulatory knowledge model.

Rules must represent conditions stated or strongly supported by the source.

Use only observable signals and approved derived concepts.

Supported logical operators:

- EQUALS
- NOT_EQUALS
- EXISTS
- NOT_EXISTS
- AND
- OR

For every rule provide:

- rule ID
- description
- output concept/status
- expression
- referenced signals
- referenced derived concepts
- source references
- confidence
- assumptions
- unresolved questions

Important rules:

1. Do not encode legal conclusions that are not supported by the source.
2. Do not silently simplify legal conditions.
3. Preserve AND/OR semantics.
4. Preserve exceptions.
5. Identify missing signals when the source requires a fact that is not currently available.
6. Distinguish raw signals from derived concepts.
7. Never create a signal representing the final applicability decision.
8. If the source is ambiguous, mark the rule as requiring review.
9. Every rule must have provenance.

If a required signal does not exist, explicitly create a proposed missing signal rather than silently ignoring the condition.

Output JSON.
```

---

# 17. Iterative Signal/Rule Discovery

Rule generation may discover missing signals.

That is expected.

Example:

```text
Regulation
    ↓
Rule
    ↓
Missing signal
    ↓
Signal discovery
    ↓
Updated rule
```

Therefore the process is iterative:

```text
Knowledge
   ↓
Signals
   ↓
Rules
   ↓
Missing Signal?
   |
  YES
   |
   v
Signal Discovery
   |
   v
Rules again
```

Do not treat this as an error.

It is an expected property of the POC.

---

# 18. STEP 7 — Requirement / Obligation Extraction

Now identify what the applicable organisation must actually do.

This must remain separate from applicability.

Example:

```text
Applicability:
Security Rule applies

Requirement:
Implement appropriate safeguards
```

Do not combine them.

---

# 19. Requirement Model

Each requirement should contain:

```text
Requirement
├── actor
├── action
├── object
├── mandatory
├── trigger
├── exceptions
├── source
├── verification
└── evidence
```

Example:

```json
{
  "id": "REQ-001",
  "name": "Protect regulated information",
  "actor": [
    "REGULATED_ENTITY"
  ],
  "action": "PROTECT",
  "object": "REGULATED_INFORMATION",
  "mandatory": true,
  "trigger": "RULE-001",
  "exceptions": [],
  "sourceReferences": []
}
```

---

# 20. Requirement Extraction Prompt

```text
Extract regulatory requirements and obligations from the source.

Identify statements that impose, prohibit, permit or condition actions.

For each requirement provide:

- id
- name
- actor
- action
- object
- mandatory
- trigger condition
- exceptions
- related applicability rule
- source references
- exact supporting passage or concise paraphrase
- confidence
- explicit/inferred

Classify each requirement as:

- MANDATORY
- CONDITIONAL
- PROHIBITION
- PERMISSION
- GUIDANCE

Important:

Do not convert recommendations into mandatory requirements.

Do not invent implementation controls.

Do not infer specific technologies unless the source explicitly requires them.

For example, if a regulation requires appropriate security safeguards, do not automatically generate:

- AES-256
- SIEM
- MFA
- firewall
- private network

unless the source explicitly requires them.

Output JSON.
```

---

# 21. STEP 8 — Exception and Boundary Extraction

This step is mandatory.

Many regulatory mistakes happen because the system finds the main rule but misses the exceptions.

Search specifically for:

```text
except
unless
does not apply
not applicable
excluding
provided that
subject to
only if
where
when
limited to
```

Also identify:

* exemptions
* exclusions
* thresholds
* special cases
* transitional provisions
* sector-specific rules
* geography-specific exceptions
* entity-specific exceptions

## Prompt

```text
Perform a dedicated exception and boundary analysis.

Do not simply repeat the main regulatory rules.

Identify:

1. Explicit exemptions
2. Explicit exclusions
3. Conditional exceptions
4. Thresholds
5. Special entity treatment
6. Special industry treatment
7. Geographic exceptions
8. Data-specific exceptions
9. Activity-specific exceptions
10. Temporary/transitional provisions
11. Exceptions to obligations
12. Exceptions to applicability

For every exception provide:

- exception ID
- affected rule/requirement
- condition
- effect
- source reference
- confidence

Then identify whether any previously generated rule or requirement must be modified because of this exception.

Output JSON.
```

---

# 22. STEP 9 — Verification and Evidence

The system now needs to determine:

> How can we verify whether the generated applicability or requirement is actually true for a customer?

Separate:

### Regulatory evidence

Explicitly required by the regulation.

### Verification evidence

Evidence useful to determine whether a condition is true.

### Suggested evidence

AI-generated recommendation that is not explicitly required.

Never mix them.

---

# 23. Verification Model

```json
{
  "id": "VERIFY-001",
  "requirementId": "REQ-001",
  "question": "Does the organisation maintain the required safeguard?",
  "evidence": [
    {
      "type": "VERIFICATION_EVIDENCE",
      "description": "Relevant policy or control documentation"
    }
  ]
}
```

---

# 24. Evidence Prompt

```text
For every applicability rule and requirement, identify how a customer assessment could verify it.

For each item provide:

- id
- related rule or requirement
- verification question
- expected evidence
- evidence classification:
  - REGULATORY_EVIDENCE
  - VERIFICATION_EVIDENCE
  - SUGGESTED_EVIDENCE

Rules:

1. Do not claim evidence is legally required unless the source says so.
2. Clearly distinguish verification evidence from regulatory evidence.
3. Prefer observable evidence.
4. Identify when evidence is insufficient.
5. Preserve source references.

Output JSON.
```

---

# 25. STEP 10 — Discovery Question Generation

If the assessment does not have enough information, the system should ask targeted questions.

Bad:

```text
Do you comply with HIPAA?
```

Good:

```text
Does your organisation provide healthcare services covered by the regulation?
```

Better:

```text
Does your organisation transmit health information electronically
in transactions covered by the applicable regulatory framework?
```

Questions should map to signals.

---

# 26. Discovery Question Model

```json
{
  "id": "Q-001",
  "question": "Does the organisation process regulated health information?",
  "purpose": "Determine whether the regulated data condition is present",
  "signal": "data.health_data",
  "requiredFor": [
    "RULE-001"
  ]
}
```

---

# 27. Discovery Question Prompt

```text
Generate targeted discovery questions for signals required by applicability rules or obligations but currently unknown.

For each question provide:

- id
- question
- purpose
- signal
- related rules
- related requirements
- expected answer type
- possible answers

Rules:

1. Ask only questions that materially reduce uncertainty.
2. Prefer observable facts over legal conclusions.
3. Do not ask the customer whether a law applies.
4. Ask for the underlying fact.
5. Avoid duplicate questions.
6. Keep questions understandable to a business/customer user.
7. Preserve the technical signal ID.
8. Mark questions requiring legal interpretation for review.

Output JSON.
```

---

# 28. STEP 11 — Validation

This is one of the most important steps.

Never allow:

```text
AI
 ↓
Requirement Pack
 ↓
Production
```

Instead:

```text
AI
 ↓
Candidate Knowledge
 ↓
Validation
 ↓
Human Review
 ↓
Approved Knowledge
```

---

# 29. Validation Dimensions

Validate at least:

### 1. Schema validity

Does the output conform to the expected JSON schema?

### 2. Source traceability

Does every rule have a source?

### 3. Signal integrity

Does every rule reference a valid signal?

### 4. Requirement integrity

Does every requirement have a trigger?

### 5. Orphan detection

Are there signals never used anywhere?

### 6. Missing signal detection

Are there regulatory concepts without corresponding signals?

### 7. Logical consistency

Are AND/OR/NOT relationships preserved?

### 8. Exception coverage

Were exceptions incorporated?

### 9. Unsupported claims

Did AI invent something?

### 10. Scope leakage

Did the model incorrectly apply the regulation to entities outside its scope?

### 11. Contradiction detection

Do two generated rules contradict each other?

### 12. Provenance completeness

Can every important conclusion be traced back to the source?

---

# 30. Validation Prompt

Use a **different AI call/model/persona** for validation.

Do not simply ask the same prompt:

> "Is your previous answer correct?"

Instead:

```text
You are an independent regulatory knowledge validator.

You are given:

1. The authoritative source
2. Extracted regulatory knowledge
3. Candidate signals
4. Derived concepts
5. Applicability rules
6. Requirements
7. Exceptions

Your job is to find errors.

Do not assume the generated model is correct.

Check:

1. Missing applicability conditions
2. Incorrect applicability conditions
3. Missing actors
4. Missing data types
5. Missing geography
6. Missing industry/context
7. Missing activities
8. Missing relationships
9. Missing exceptions
10. Incorrect AND/OR logic
11. Unsupported assumptions
12. Invented requirements
13. Invented controls
14. Incorrect mandatory classification
15. Incorrect interpretation of guidance
16. Missing source references
17. Orphan signals
18. Orphan requirements
19. Requirements without triggers
20. Rules without source support

For every issue provide:

- severity
- affected object
- problem
- source evidence
- recommended correction

Classify severity:

- CRITICAL
- HIGH
- MEDIUM
- LOW

Do not silently modify the model.

Output a validation report.
```

---

# 31. STEP 12 — Test Scenario Generation

AI should generate test cases from the regulatory knowledge.

This is extremely important because it allows the deterministic Java engine to test AI-generated rules.

Example:

```text
Scenario A
US healthcare provider
PHI = true
ePHI = true

Expected:
LIKELY_APPLICABLE
```

Another:

```text
Scenario B
Non-healthcare company
PHI = false

Expected:
NOT_CURRENTLY_INDICATED
```

Another:

```text
Scenario C
Healthcare-related company
PHI = unknown

Expected:
INSUFFICIENT_INFORMATION
```

---

# 32. Test Scenario Prompt

```text
Generate test scenarios for the regulatory applicability model.

Create scenarios covering:

1. Clearly applicable
2. Clearly not applicable
3. Insufficient information
4. Boundary condition
5. Exception
6. Multiple conditions satisfied
7. One AND condition missing
8. OR condition satisfied
9. Conflicting evidence
10. Unknown signals
11. Missing signals
12. Unusual but valid customer profile

For each scenario provide:

- scenario ID
- customer facts
- expected rule results
- expected applicability status
- expected missing signals
- expected requirements
- reasoning
- source references

Do not create expected results that are unsupported by the generated rules.

Output JSON.
```

---

# 33. STEP 13 — Requirement Pack Assembly

After validation, assemble everything into one generic requirement pack.

Example:

```json
{
  "id": "HIPAA",
  "name": "Health Insurance Portability and Accountability Act",
  "version": "POC-1",

  "sources": [],

  "applicabilitySignals": [],

  "derivedConcepts": [],

  "applicabilityRules": [],

  "requirements": [],

  "verificationRequirements": [],

  "discoveryQuestions": [],

  "evidenceRequirements": [],

  "testScenarios": [],

  "validation": {
    "status": "REVIEW_REQUIRED"
  }
}
```

The important point:

**The application should not know that this is HIPAA.**

It should know how to process a `RequirementPack`.

---

# 34. STEP 14 — Execute Against Customer Scenarios

Now use the existing Java engine.

Input:

```json
{
  "organisation.country": {
    "signal": {
      "id": "organisation.country",
      "value": {
        "value": "US",
        "state": "PROVIDED"
      }
    },
    "evidence": []
  },

  "data.health_data": {
    "signal": {
      "id": "data.health_data",
      "value": {
        "value": true,
        "state": "PROVIDED"
      }
    },
    "evidence": []
  }
}
```

The engine should return something like:

```json
{
  "requirementPackId": "HIPAA",

  "status": "LIKELY_APPLICABLE",

  "ruleResults": {
    "HIPAA-R01": "TRUE"
  },

  "missingSignals": [],

  "discoveryQuestions": [],

  "applicableRequirements": [
    "HIPAA-REQ-001"
  ]
}
```

---

# 35. Important: AI Should NOT Execute the Final Rule

Do not do this:

```text
Customer
   ↓
LLM
   ↓
"HIPAA applies"
```

Instead:

```text
Customer
   ↓
Structured Signals
   ↓
Deterministic Rule Engine
   ↓
Applicability
```

AI can help transform:

```text
Customer natural language
```

into:

```text
Structured signals
```

but the final applicability calculation should remain deterministic.

---

# 36. STEP 15 — Compare AI Output With Expected Results

For the POC, create approximately 5–10 customer scenarios.

For each:

```text
AI-generated rule result
        VS
Expected regulatory interpretation
```

Create a simple table:

| Scenario                 | Expected                   | Engine        | Result |
| ------------------------ | -------------------------- | ------------- | ------ |
| US covered entity        | Applicable                 | Applicable    | PASS   |
| Non-covered entity       | Not indicated              | Not indicated | PASS   |
| Missing data information | Unknown                    | Unknown       | PASS   |
| BA relationship          | Applicable/conditional     | Applicable    | PASS   |
| Exception case           | Not applicable/conditional | ...           | REVIEW |

Do not optimize the model until you understand the failure.

---

# 37. POC Success Criteria

The POC succeeds if AI can produce a useful first version of a regulatory knowledge pack with:

### Source

* authoritative source identified
* source metadata captured
* provenance preserved

### Knowledge

* concepts extracted
* actors extracted
* data types extracted
* activities extracted
* relationships extracted
* geography/context captured
* definitions captured

### Signals

* candidate customer facts generated
* signals classified
* missing signals identified

### Rules

* applicability rules generated
* nested logic supported
* exceptions represented
* source references preserved

### Requirements

* obligations extracted
* mandatory vs guidance distinguished
* requirements linked to triggers

### Verification

* discovery questions generated
* evidence suggestions generated
* verification requirements generated

### Validation

* independent validation performed
* unsupported claims identified
* missing conditions identified
* test scenarios generated

### Runtime

* generated pack can be executed by the deterministic rule engine

---

# 38. What the POC Should NOT Claim

At the end of the POC, do NOT say:

> "AI can determine whether a company complies with HIPAA."

That is too strong.

Instead say:

> "AI can automatically transform authoritative regulatory sources into a structured, traceable candidate knowledge model that can be validated and executed by a deterministic applicability engine."

That is the actual technology proposition.

---

# 39. Generic Architecture After the POC

Once HIPAA works, the architecture becomes:

```text
                  +--------------------+
                  | Regulatory Source  |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Source Analyzer    |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Knowledge          |
                  | Extractor          |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Signal Discovery   |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Concept Generator  |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Rule Generator     |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Requirement        |
                  | Extractor          |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Exception          |
                  | Analyzer           |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Validator          |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Human Approval     |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Requirement Pack   |
                  +--------------------+
                            |
                            v
                  +--------------------+
                  | Rule Engine        |
                  +--------------------+
```

---

# 40. The Same Pipeline Must Work Across Laws

The pipeline should not become:

```text
HIPAAAgent
GDPRAgent
DPDPAgent
DORAgent
NIS2Agent
```

Instead:

```text
Generic Regulatory Intelligence Pipeline
```

with:

```text
Source
   ↓
Knowledge
   ↓
Signals
   ↓
Rules
   ↓
Requirements
   ↓
Validation
   ↓
Requirement Pack
```

Only the regulatory source and resulting knowledge differ.

---

# 41. Example: GDPR

The same pipeline should discover concepts such as:

```text
organisation
controller
processor
data_subject
personal_data
special_category_data
processing
cross_border_transfer
eu_establishment
targeting_eu_residents
```

Then derive:

```text
EU_PROCESSING_CONTEXT
```

Then generate applicability rules.

Then extract obligations such as:

```text
data subject rights
records
DPA
DPIA
breach notification
international transfer mechanisms
```

The important thing is:

**We should not manually tell the AI what GDPR's signals are.**

The AI should discover them from the authoritative sources.

---

# 42. Example: PCI DSS

The pipeline should discover concepts such as:

```text
cardholder_data
sensitive_authentication_data
payment_environment
merchant
service_provider
storage
processing
transmission
```

Then derive:

```text
CARD_DATA_ENVIRONMENT
```

Then generate rules and requirements.

The pipeline remains unchanged.

---

# 43. Example: DORA

The pipeline should discover concepts such as:

```text
financial_entity
ICT_service
ICT_third_party
critical_provider
outsourcing
incident
operational_resilience
contractual_requirement
```

Again:

```text
Source
 ↓
Knowledge
 ↓
Signals
 ↓
Rules
 ↓
Requirements
```

No application-code change should be necessary.

---

# 44. Source Authority Model

Every source should receive an authority classification.

Suggested:

```text
PRIMARY_REGULATION
REGULATOR_GUIDANCE
GOVERNMENT_DOCUMENTATION
OFFICIAL_STANDARD
OFFICIAL_INTERPRETATION
SECONDARY_SOURCE
```

For legal applicability, prefer authoritative primary/regulator sources.

Secondary sources may help explain concepts but should not silently become the basis for a legal rule.

---

# 45. Provenance Is Mandatory

Every important object should be traceable:

```text
Requirement
    ↓
Rule
    ↓
Signal
    ↓
Regulatory Statement
    ↓
Source Section
    ↓
Source Document
```

For example:

```json
{
  "sourceReference": {
    "sourceId": "HIPAA-HHS-BA-001",
    "section": "What is a Business Associate?",
    "location": "...",
    "retrievedAt": "2026-09-13"
  }
}
```

This is essential.

If somebody asks:

> Why did the system decide this?

the system should be able to answer:

```text
Because:
Signal X was true
→ Rule Y evaluated true
→ Rule Y was generated from regulatory statement Z
→ Statement Z came from source section A
```

---

# 46. Confidence Model

Do not use a single confidence number for everything.

At minimum distinguish:

```text
EXTRACTION_CONFIDENCE
RULE_CONFIDENCE
SOURCE_AUTHORITY
```

Example:

```text
Source Authority:
HIGH

Extraction Confidence:
HIGH

Rule Confidence:
MEDIUM

Human Review:
REQUIRED
```

A highly authoritative source does not mean the AI interpreted it correctly.

---

# 47. AI Output States

Every generated object should eventually have a lifecycle:

```text
GENERATED
    ↓
VALIDATED
    ↓
REVIEW_REQUIRED
    ↓
APPROVED
    ↓
PUBLISHED
```

Potentially:

```text
DEPRECATED
SUPERSEDED
REJECTED
```

The Java engine should consume only:

```text
APPROVED / PUBLISHED
```

knowledge.

---

# 48. Important Guardrail: No Silent Legal Inference

The AI must distinguish:

### Explicit

The source directly says it.

### Derived

The conclusion follows logically from explicit statements.

### Inferred

The AI believes the conclusion is likely but the source does not directly establish it.

Example:

```json
{
  "statement": "...",
  "classification": "INFERRED",
  "confidence": "MEDIUM",
  "humanReviewRequired": true
}
```

Never silently convert:

```text
INFERRED
```

into:

```text
MANDATORY
```

---

# 49. Important Guardrail: No Control Hallucination

This is particularly important for security/compliance.

If a regulation says:

> implement appropriate safeguards

AI must not automatically generate:

```text
AES-256
MFA
SIEM
EDR
firewall
zero trust
```

Those may be useful implementation controls.

They are not automatically regulatory requirements.

Keep separate models:

```text
REGULATORY REQUIREMENT
        ↓
ORGANISATIONAL CONTROL
        ↓
TECHNICAL IMPLEMENTATION
```

The POC should focus on the first layer.

---

# 50. Important Guardrail: Do Not Ask "Does the Law Apply?"

Customer questions should ask for facts.

Bad:

```text
Does GDPR apply to you?
```

Good:

```text
Does the organisation offer goods or services to individuals in the EU?
```

Bad:

```text
Are you a HIPAA Business Associate?
```

Better:

```text
Does the organisation provide services to a covered entity that involve
creating, receiving, maintaining or transmitting regulated health information?
```

The engine should derive the conclusion.

---

# 51. Important Guardrail: Unknown Is a Valid State

The system must never force:

```text
TRUE / FALSE
```

when information is missing.

Use:

```text
TRUE
FALSE
UNKNOWN
```

and ultimately:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
INSUFFICIENT_INFORMATION
MANUAL_REVIEW_REQUIRED
OUT_OF_SCOPE
```

This is particularly important for regulatory applicability.

---

# 52. AI Pipeline Master Prompt

Once the individual prompts work, they can eventually be combined into an orchestration prompt.

```text
You are a Regulatory Intelligence Extraction System.

Your objective is to transform authoritative regulatory sources
into structured, traceable candidate regulatory knowledge.

You must execute the following stages:

1. SOURCE ANALYSIS
2. KNOWLEDGE EXTRACTION
3. SIGNAL DISCOVERY
4. DERIVED CONCEPT GENERATION
5. APPLICABILITY RULE GENERATION
6. REQUIREMENT / OBLIGATION EXTRACTION
7. EXCEPTION ANALYSIS
8. VERIFICATION GENERATION
9. DISCOVERY QUESTION GENERATION
10. VALIDATION
11. TEST SCENARIO GENERATION

Core principles:

- Source authority matters.
- Every material conclusion must have provenance.
- Never invent legal requirements.
- Never convert guidance into mandatory requirements without support.
- Never silently resolve ambiguity.
- Preserve exceptions.
- Preserve definitions affecting scope.
- Distinguish observable signals from derived concepts.
- Distinguish applicability from obligations.
- Distinguish regulatory evidence from suggested verification evidence.
- Distinguish explicit statements from inference.
- Generate candidate knowledge, not legal advice.
- Flag human-review requirements.
- Do not generate application code.
- Generate structured data/configuration.
- Maintain traceability from final requirement back to source.

The output must be generic and reusable across regulatory frameworks.

Do not assume the framework is HIPAA.

The framework should be determined from the supplied source.
```

---

# 53. Recommended Execution Order for Your POC

Do NOT give another agent the entire project and ask it to build everything.

Run it step by step.

### Run 1

Give the agent:

```text
source + Step 2 prompt
```

Verify:

```text
knowledge.json
```

### Run 2

Give it:

```text
knowledge.json + Step 4 prompt
```

Verify:

```text
signals.json
```

### Run 3

Give it:

```text
knowledge.json
signals.json
```

Generate:

```text
derived-concepts.json
```

### Run 4

Generate:

```text
rules.json
```

### Run 5

Generate:

```text
requirements.json
```

### Run 6

Generate:

```text
exceptions.json
```

### Run 7

Generate:

```text
verification.json
questions.json
```

### Run 8

Run independent validation.

### Run 9

Generate test scenarios.

### Run 10

Create final:

```text
requirement-pack.json
```

### Run 11

Load the pack into your existing Java engine.

### Run 12

Run customer scenarios.

---

# 54. What You Should Manually Review

You do NOT need to manually reread the entire regulation every time.

Focus human review on:

```text
1. Applicability boundaries
2. Exceptions
3. Ambiguous interpretations
4. High-impact rules
5. AI-inferred statements
6. Mandatory vs recommended classification
7. Cross-references
8. Requirements with high business impact
9. Rules producing unexpected scenarios
```

This changes the role of the human from:

```text
Read everything → manually build rules
```

to:

```text
Review AI-generated regulatory model
→ challenge questionable interpretations
→ approve/reject
```

That is the productivity gain we are trying to prove.

---

# 55. What We Want to Prove

At the end of this POC, we should be able to take:

```text
One authoritative regulatory source
```

and automatically produce:

```text
        Source
          |
          v
      Concepts
          |
          v
       Signals
          |
          v
  Derived Concepts
          |
          v
        Rules
          |
          v
    Requirements
          |
          v
     Exceptions
          |
          v
   Verification
          |
          v
 Discovery Questions
          |
          v
   Test Scenarios
          |
          v
 Requirement Pack
          |
          v
 Deterministic Engine
```

If that works for HIPAA, we then repeat the exact same pipeline against another framework.

---

# 56. The Most Important POC Experiment

Do not judge the POC by:

> "Did the LLM produce a nice JSON?"

Judge it by:

> **"Can the generated knowledge actually drive our existing deterministic assessment engine and produce correct results for previously unseen customer scenarios?"**

That is the real test.

---

# 57. POC Exit Criteria

The POC is successful when:

```text
[ ] Authoritative source ingested
[ ] Source metadata captured
[ ] Regulatory knowledge extracted
[ ] Actors identified
[ ] Data types identified
[ ] Activities identified
[ ] Geography/context identified
[ ] Relationships identified
[ ] Candidate signals generated
[ ] Derived concepts generated
[ ] Applicability rules generated
[ ] Requirements extracted
[ ] Exceptions extracted
[ ] Verification questions generated
[ ] Discovery questions generated
[ ] Evidence model generated
[ ] Independent validation performed
[ ] Test scenarios generated
[ ] Requirement pack assembled
[ ] Existing Java engine executes generated rules
[ ] Customer scenarios produce sensible results
[ ] Every important result is traceable to source
```

---

# 58. What Comes After the POC

Only after this works should we build the real platform.

Potential next phases:

```text
Phase A
POC
    ↓
Phase B
Multi-source ingestion
    ↓
Phase C
Regulatory document parser
    ↓
Phase D
RAG / regulatory knowledge store
    ↓
Phase E
AI extraction pipeline
    ↓
Phase F
Validation pipeline
    ↓
Phase G
Human approval workflow
    ↓
Phase H
Regulatory versioning
    ↓
Phase I
Change detection / impact analysis
    ↓
Phase J
Multi-regulation assessment
    ↓
Phase K
Customer profile
    ↓
Phase L
Atlassian capability mapping
    ↓
Phase M
Migration readiness
```

---

# 59. Final Architecture Vision

The eventual system should look like:

```text
                    REGULATORY INTELLIGENCE
                           PLATFORM

 ┌──────────────────────────────────────────────────────────┐
 │                                                          │
 │  Authoritative Sources                                   │
 │                                                          │
 │  GDPR | HIPAA | DPDP | DORA | NIS2 | PCI | FedRAMP ... │
 │                                                          │
 └─────────────────────────────┬────────────────────────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Source Intelligence │
                    └──────────┬──────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Regulatory Knowledge│
                    │ Graph / Model       │
                    └──────────┬──────────┘
                               |
              ┌────────────────┼────────────────┐
              |                |                |
              v                v                v
          Signals          Rules          Requirements
              |                |                |
              └────────────────┼────────────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Validation / Human  │
                    │ Approval            │
                    └──────────┬──────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Requirement Packs   │
                    └──────────┬──────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Deterministic       │
                    │ Assessment Engine   │
                    └──────────┬──────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Customer Profile    │
                    └──────────┬──────────┘
                               |
                               v
                    ┌─────────────────────┐
                    │ Compliance /        │
                    │ Migration Readiness │
                    └─────────────────────┘
```

The central idea is:

> **AI does not replace the compliance engine. AI creates and maintains the knowledge that powers the compliance engine.**

That is what makes the architecture scalable across regulations.

---

# 60. One-Sentence Product Definition

A concise way to describe the eventual capability:

> **An AI-powered regulatory knowledge compiler that transforms authoritative regulations into traceable signals, applicability rules, obligations and verification requirements that can be executed by a deterministic compliance assessment engine.**

---

# 61. First POC Task

Do not implement anything else yet.

Start with:

```text
HIPAA official HHS source
        ↓
Step 1 — Source Registration
        ↓
Step 2 — Source Understanding
        ↓
Step 3 — Knowledge Extraction
```

Save the result as:

```text
sources/hipaa/source.json
extracted/hipaa/knowledge.json
```

Then inspect the output manually.

Only after you are satisfied with the extracted knowledge should you run:

```text
Step 4 — Signal Discovery
```

This prevents errors early in the pipeline from propagating into hundreds of generated rules.
