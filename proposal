# Regulatory Intelligence & Compliance Readiness Platform

> **Status:** Proposal / Architecture
> **Initial Use Case:** Atlassian Data Center → Cloud Migration Readiness
> **Initial Regulatory Pack:** HIPAA
> **Future Regulatory Packs:** GDPR, DPDP, CCPA/CPRA, DORA, FedRAMP, C5, TISAX, PCI DSS, NIS2, IRAP, Data Residency, etc.

---

## 1. Executive Summary

Customers migrating from Atlassian Data Center to Cloud frequently discover security, privacy, regulatory, and compliance requirements late in the migration process.

The problem is not necessarily that customers do not have security or compliance requirements. In many cases:

* the customer does not know all applicable regulations,
* different teams own different pieces of the information,
* requirements are discovered only after detailed security reviews,
* the customer may not know the legal classification of their organisation,
* existing classification systems rely heavily on broad dimensions such as region, industry, and company size,
* and the actual applicability of a regulation depends on much more specific facts about the organisation, its customers, data, and processing activities.

The proposed solution is a **Regulatory Intelligence & Compliance Readiness Platform** that determines which regulatory, privacy, security, and data-residency requirements are **likely to apply to an organisation based on observable signals**.

Instead of asking:

> "Are you subject to HIPAA?"

the platform asks or obtains factual information such as:

> "Do you provide healthcare services?"
> "Do you have healthcare organisations as customers?"
> "What types of information are stored in Atlassian products?"
> "Does the information relate to identifiable patients?"
> "Are you processing this information on behalf of a healthcare organisation?"

The system then derives the relevant legal concepts and determines the likelihood of regulatory applicability.

The core principle is:

> **AI interprets. Rules decide. Evidence explains.**

The system should not depend on an LLM making an unconstrained legal decision. Instead, authoritative regulatory knowledge is converted into **versioned, reviewable, machine-executable applicability rules**.

---

# 2. Problem Statement

## 2.1 Current Problem

A traditional compliance classification model may look like:

```text
Region
   ×
Industry
   ×
Company Segment
   ↓
Compliance Classification
```

For example:

```text
Region = AMER
Industry = Healthcare
Segment = Enterprise
        ↓
HIPAA = Required
```

This is useful as an initial heuristic, but it is insufficient for accurate regulatory applicability.

Two organisations in the same industry and region may have completely different regulatory obligations.

### Example

Consider two companies:

**Company A**

* US healthcare software vendor
* Stores patient information
* Provides services to hospitals
* Processes patient information on behalf of healthcare organisations

**Company B**

* US healthcare consulting company
* Does not store patient information
* Uses Jira only for internal project management
* Does not process PHI

Both may be classified as:

```text
Region = AMER
Industry = Healthcare
Segment = Enterprise
```

But the regulatory implications are significantly different.

The platform therefore needs to move from:

```text
Classification by organisation attributes
```

to:

```text
Regulatory applicability based on observable facts
```

---

# 3. Product Vision

The platform should answer:

> **"Given what we know about this organisation, which regulations, security frameworks, privacy laws, and compliance requirements are likely to apply, why, what evidence supports that conclusion, and what information should we verify next?"**

The output should not simply be:

```text
HIPAA = YES
```

Instead:

```text
HIPAA
Status: Likely Applicable
Confidence: High

Why:
- Customer operates in healthcare
- Healthcare organisations are customers
- Health-related information is stored
- Information may relate to identifiable patients
- Information is electronically stored
- Platform is used to provide services to healthcare organisations

Need to verify:
- Whether the information constitutes PHI
- Exact relationship between customer and healthcare organisation
- Whether Atlassian is creating, receiving, maintaining or transmitting PHI on behalf of the covered entity
```

This produces an **explainable regulatory assessment** rather than a black-box classification.

---

# 4. Design Principles

## 4.1 Facts Before Legal Conclusions

The system should collect factual signals rather than asking users to determine their own legal status.

Bad:

```text
Are you a HIPAA covered entity?
```

Better:

```text
Do you provide healthcare services?

Do you operate hospitals, clinics, pharmacies,
health plans, or similar healthcare services?

Do you have healthcare organisations as customers?

What types of information are stored in Atlassian products?

Does this information relate to identifiable patients?

Do you process this information on behalf of another organisation?
```

The system derives:

```text
covered_entity_candidate
business_associate_candidate
phi_candidate
ephi_candidate
```

from these facts.

---

# 4.2 Unknown Is Not False

A critical design principle is:

```text
Unknown ≠ No
```

For example:

```text
PHI = Unknown
```

must not be interpreted as:

```text
PHI = False
```

Instead:

```text
PHI = Unknown

HIPAA:
Potentially Applicable

Reason:
A healthcare relationship and health-related information
have been identified, but PHI status has not been verified.
```

---

# 4.3 Explainability Is Mandatory

Every regulatory result must be explainable.

The system should answer:

1. Why was this regulation identified?
2. Which signals contributed?
3. Which rules fired?
4. Which legal concepts were derived?
5. What evidence supports the conclusion?
6. What information is missing?
7. What should be verified next?

---

# 4.4 Evidence Must Be Traceable

Every important rule should have provenance.

Example:

```json
{
  "authority": "US HHS",
  "document": "Covered Entities and Business Associates",
  "section": "45 CFR 160.103",
  "url": "...",
  "retrievedAt": "2026-09-01",
  "effectiveFrom": "..."
}
```

This allows a reviewer to trace:

```text
Assessment
   ↓
Rule
   ↓
Legal concept
   ↓
Authoritative source
```

---

# 4.5 AI Should Not Be the Final Decision Maker

AI can be used for:

* extracting regulatory concepts,
* comparing document versions,
* identifying changes,
* proposing rule changes,
* generating candidate questions,
* summarising regulatory requirements,
* identifying relationships between concepts.

AI should **not automatically publish a new legal interpretation**.

The final process should be:

```text
Authoritative Source
        ↓
Change Detection
        ↓
AI Analysis
        ↓
Proposed Knowledge Change
        ↓
Human Review
        ↓
Regression Tests
        ↓
Publish
```

---

# 5. High-Level Architecture

```text
                         ┌─────────────────────────┐
                         │ Regulatory Sources      │
                         │                         │
                         │ HHS / Regulators /      │
                         │ Government / Standards  │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ Source Monitor          │
                         │                         │
                         │ Fetch / Snapshot / Diff │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ AI Regulatory Analysis  │
                         │                         │
                         │ Change interpretation   │
                         │ Concept extraction      │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ Regulatory Knowledge    │
                         │ Base                    │
                         │                         │
                         │ Laws                    │
                         │ Legal concepts          │
                         │ Obligations             │
                         │ Sources                 │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ Versioned Rule Engine   │
                         └────────────┬────────────┘
                                      │
                                      │
                ┌─────────────────────┴─────────────────────┐
                │                                           │
                ▼                                           ▼
     ┌──────────────────────┐                    ┌─────────────────────┐
     │ Customer Signals     │                    │ Atlassian Capability│
     │                      │                    │ Knowledge            │
     │ CRM                   │                    │                     │
     │ Public data           │                    │ Data residency      │
     │ Questionnaire         │                    │ Encryption          │
     │ Product usage         │                    │ BAA                 │
     │ Discovery questions   │                    │ Security controls   │
     └──────────┬───────────┘                    └──────────┬──────────┘
                │                                           │
                ▼                                           │
     ┌──────────────────────┐                               │
     │ Canonical Signal     │                               │
     │ Layer                │                               │
     └──────────┬───────────┘                               │
                │                                           │
                ▼                                           │
     ┌──────────────────────┐                               │
     │ Regulatory Decision  │                               │
     │ Engine               │                               │
     └──────────┬───────────┘                               │
                │                                           │
                ▼                                           │
     ┌──────────────────────┐                               │
     │ Confidence + Gaps    │                               │
     └──────────┬───────────┘                               │
                │                                           │
                ▼                                           │
     ┌──────────────────────┐                               │
     │ Question Optimizer   │                               │
     └──────────┬───────────┘                               │
                │                                           │
                ▼                                           │
     ┌──────────────────────┐                               │
     │ Targeted Questions   │                               │
     └──────────┬───────────┘                               │
                │                                           │
                └───────────────────┬───────────────────────┘
                                    ▼
                         ┌─────────────────────────┐
                         │ Regulatory Assessment  │
                         └────────────┬────────────┘
                                      │
                                      ▼
                         ┌─────────────────────────┐
                         │ Migration Readiness    │
                         │ Report                  │
                         └─────────────────────────┘
```

---

# 6. Core Architecture Model

The most important architectural abstraction is:

```text
Raw Facts
    ↓
Canonical Signals
    ↓
Legal Concepts
    ↓
Applicability Rules
    ↓
Regulatory Outcomes
    ↓
Obligations
    ↓
Product Capability Mapping
```

Each layer has a different responsibility.

---

# 7. Layer 1 — Raw Facts

Raw facts are observations about an organisation.

Examples:

```text
Headquarters country = United States

Operating countries =
[
  United States,
  Germany,
  India
]

Industry = Healthcare

Sub-industry = Health Technology

Employees = 12,000

Customers =
[
  Hospitals,
  Health Plans,
  Enterprises
]

Data stored =
[
  Customer data,
  Employee data,
  Health information
]
```

These facts may come from:

* CRM
* Salesforce
* customer-provided information
* public websites
* third-party data providers
* product configuration
* migration discovery
* questionnaires
* security reviews

---

# 8. Layer 2 — Canonical Signals

Raw facts should be normalised into a common ontology.

Example:

```json
{
  "signal": "health_information",
  "value": true,
  "confidence": 0.95,
  "source": "customer_questionnaire"
}
```

Another example:

```json
{
  "signal": "customer_type",
  "value": [
    "hospital",
    "health_plan"
  ],
  "confidence": 0.9
}
```

Canonical signals allow multiple regulations to reuse the same facts.

---

# 9. Canonical Signal Ontology

## 9.1 Organisation

```text
organisation_country
operating_countries
customer_countries
employee_count
revenue
public_company
subsidiary
parent_company
government_owned
```

## 9.2 Industry

```text
industry
sub_industry
regulated_industry
```

Examples:

```text
Healthcare
Financial Services
Government
Defence
Automotive
Retail
Education
Technology
```

---

# 10. Customer Signals

```text
customer_type
customer_country
government_customer
federal_government_customer
state_government_customer
healthcare_customer
financial_institution_customer
automotive_customer
defence_customer
```

Examples:

```text
Hospital
Health Plan
Federal Agency
Bank
Insurance Company
Automotive Manufacturer
Defence Contractor
```

---

# 11. Data Signals

```text
data_types
personal_data
sensitive_personal_data
health_information
financial_information
payment_card_data
biometric_data
genetic_data
children_data
employee_data
government_data
classified_data
customer_confidential_data
```

---

# 12. Data Subject Signals

```text
data_subject_types
```

Examples:

```text
customers
patients
employees
children
citizens
government personnel
financial customers
```

---

# 13. Data Processing Signals

```text
data_processing
data_storage
data_transmission
data_analysis
profiling
monitoring
automated_decision
ai_processing
data_sharing
data_sale
```

---

# 14. Relationship Signals

These are particularly important for regulations such as HIPAA.

```text
relationship_to_customer
acts_on_behalf_of_customer
service_provider
subprocessor
independent_controller
processor
data_hosting_provider
```

Example:

```text
Atlassian provides a service to a healthcare organisation
and processes information on its behalf.
```

This is much more useful than simply:

```text
Industry = Healthcare
```

---

# 15. Technical / Security Signals

```text
encryption
customer_managed_keys
identity_provider
sso
mfa
audit_logging
siem
private_connectivity
ip_allowlisting
egress_filtering
data_loss_prevention
vulnerability_management
penetration_testing
security_monitoring
```

---

# 16. Data Location Signals

```text
data_residency_requirement
data_storage_countries
processing_countries
backup_countries
data_transfer_countries
customer_required_region
```

Examples:

```text
EU
Germany
France
Switzerland
Australia
United States
India
```

These signals can contribute to:

* GDPR
* DPDP
* data sovereignty
* contractual residency requirements
* government requirements
* sector-specific requirements

---

# 17. Layer 3 — Legal Concepts

Raw signals should not directly map to laws.

Instead:

```text
Signals
   ↓
Legal Concepts
   ↓
Regulations
```

Example:

```text
healthcare industry
+
healthcare customers
+
patient information
+
processing on behalf of customer
        ↓
health_information_processing
        ↓
phi_candidate
        ↓
business_associate_candidate
        ↓
HIPAA
```

This intermediate layer makes the architecture much more reusable.

---

# 18. Example Legal Concepts

## HIPAA

```text
covered_entity_candidate
business_associate_candidate
phi_candidate
ephi_candidate
healthcare_activity
health_information_processing
acts_on_behalf_of_covered_entity
```

## GDPR

```text
personal_data_processing
eu_data_subject
eu_establishment
controller_candidate
processor_candidate
special_category_data
cross_border_transfer
automated_decision_making
```

## FedRAMP

```text
federal_customer
federal_information
federal_system
government_cloud_service
federal_contract
authorization_requirement
```

## DORA

```text
financial_entity
ict_service_provider
critical_ict_service
eu_financial_operations
ict_third_party_dependency
```

## Data Residency

```text
explicit_residency_requirement
regulated_data_location
customer_required_region
processing_location_constraint
backup_location_constraint
```

---

# 19. Layer 4 — Applicability Rules

Rules operate on canonical signals and legal concepts.

Example:

```text
IF

healthcare_customer = true
AND
health_information = true
AND
acts_on_behalf_of_customer = true

THEN

business_associate_candidate = likely
```

A more detailed rule may be:

```text
IF

customer_type contains healthcare_entity

AND

data_types contains identifiable_health_information

AND

relationship_to_customer indicates services performed
on behalf of the healthcare entity

AND

processing_activity includes
create / receive / maintain / transmit

THEN

HIPAA.business_associate_candidate = likely
```

The rule should not simply say:

```text
Industry = Healthcare
→ HIPAA
```

---

# 20. Layer 5 — Regulatory Outcome

The final outcome should contain:

```text
regulation
status
confidence
evidence
triggered_rules
missing_information
verification_questions
effective_date
knowledge_version
```

Example:

```json
{
  "regulation": "HIPAA",
  "status": "LIKELY_APPLICABLE",
  "confidence": 0.93,
  "evidence": [
    "Healthcare organisations are customers",
    "Health information is stored",
    "Information relates to patients",
    "Processing occurs electronically",
    "Service is provided on behalf of healthcare organisation"
  ],
  "missing": [
    "Exact contractual relationship",
    "Confirmation that information constitutes PHI"
  ]
}
```

---

# 21. Regulatory Status Model

The system should avoid pretending that every classification is binary.

Recommended states:

```text
LIKELY_APPLICABLE
POTENTIALLY_APPLICABLE
NOT_CURRENTLY_INDICATED
UNABLE_TO_DETERMINE
```

Optional:

```text
APPLICABILITY_CONFIRMED
NOT_APPLICABLE
```

These stronger states should only be used when sufficient evidence exists.

---

# 22. Confidence Model

Confidence should not simply be:

```text
AI says 93%
```

It should be derived from evidence quality.

Example:

```text
Confidence =
    signal confidence
  × source reliability
  × rule completeness
  × legal concept certainty
```

Potential confidence sources:

```text
CRM verified data
Customer provided data
Authoritative source
Public website
Third-party provider
Inferred signal
AI-extracted signal
Unknown
```

Example:

```text
Healthcare customer = 0.98
```

if explicitly recorded in CRM.

But:

```text
Healthcare customer = 0.55
```

if inferred from a company website.

---

# 23. Phase 0 — Firmographic Signals

The first stage should use information that is already available.

Example:

```text
Organisation
-----------------------------
Headquarters country
Operating countries
Industry
Sub-industry
Employee count
Revenue
Customer types
Government relationship
```

This stage should require very little customer interaction.

### Objective

Generate an initial regulatory candidate set.

Example:

```text
Initial result:

HIPAA       Potentially applicable
GDPR        Potentially applicable
DORA        Not enough information
FedRAMP     Not indicated
DPDP        Potentially applicable
```

This is not the final answer.

It establishes the initial search space.

---

# 24. Stage 1 — Data & Usage Signals

The second stage introduces information about how the organisation uses the platform.

Example questions:

### What types of information are stored?

```text
Customer information
Employee information
Health information
Financial information
Payment card information
Government information
Confidential business information
Public information
Other
```

### Who does the information relate to?

```text
Customers
Patients
Employees
Citizens
Government personnel
Children
Suppliers
Partners
```

### How is the information used?

```text
Storage
Project management
Customer support
Analytics
Monitoring
Decision making
AI processing
Reporting
```

### Who is the customer?

```text
Direct customer
Healthcare organisation
Financial institution
Government organisation
Enterprise
Consumer
```

---

# 25. Stage 2/3 — Targeted Questions

The system should not ask every possible question.

Instead, it should determine:

> Which unanswered question has the highest potential to change the regulatory assessment?

For example:

```text
HIPAA = Potentially Applicable

Unknown:
Does the health information relate to identifiable patients?
```

The system asks:

> Does the health information stored in Atlassian products relate to identifiable patients?

If the answer is:

```text
No
```

the HIPAA assessment may change substantially.

If:

```text
Yes
```

the system may proceed to the next question.

---

# 26. Question Optimisation

The question engine should use **decision impact / information gain**.

Conceptually:

```text
Question Value =
    Potential Regulatory Impact
    ×
    Uncertainty Reduced
    ×
    Evidence Quality
```

Example:

```text
Question A:
What is your annual revenue?

Impact:
Low

Question B:
Does the information stored relate to identifiable patients?

Impact:
High

Question C:
What is your company website?

Impact:
Low
```

The engine should prioritise:

```text
Question B
```

---

# 27. Question Stopping Rule

The system should stop asking questions when:

```text
No remaining unanswered question
can materially change a high-impact regulatory result.
```

This prevents:

```text
30–50 question questionnaire
```

and instead aims for:

```text
5–8 initial questions
+
1–3 targeted questions
```

depending on the customer.

---

# 28. Example: HIPAA Decision Flow

HIPAA is an ideal first regulatory pack because it demonstrates why simple industry-based classification is insufficient.

HHS states that HIPAA applies to covered entities and business associates. Covered entities include certain healthcare providers, health plans, and healthcare clearinghouses. Business associates generally perform certain functions or services on behalf of covered entities involving PHI.

The Security Rule protects electronic protected health information and applies to covered entities and business associates.

Therefore, the system should not use:

```text
Healthcare industry
→ HIPAA
```

Instead:

```text
Healthcare signals
        ↓
Healthcare role
        ↓
Health information
        ↓
PHI candidate
        ↓
Relationship / on-behalf-of
        ↓
Electronic processing
        ↓
HIPAA applicability
```

---

# 29. HIPAA Signal Model

Example canonical signals:

```text
industry
sub_industry
healthcare_customer
healthcare_provider
health_plan
healthcare_clearinghouse

health_information
identifiable_health_information

data_subject = patient

electronic_storage
electronic_transmission
electronic_processing

acts_on_behalf_of_healthcare_entity
service_provider
data_hosting_provider

creates_phi
receives_phi
maintains_phi
transmits_phi
```

---

# 30. HIPAA Derived Legal Concepts

The rule engine derives:

```text
healthcare_entity_candidate
covered_entity_candidate
health_information_candidate
phi_candidate
ephi_candidate
business_associate_candidate
```

---

# 31. HIPAA Example

Input:

```text
Industry:
Healthcare

Customers:
Hospitals

Data:
Patient information

Data subject:
Patients

Processing:
Electronic storage

Relationship:
Provides service to hospitals

Processing relationship:
On behalf of healthcare organisation
```

Derived concepts:

```text
healthcare_entity = likely

health_information = likely

identifiable_health_information = likely

ephi = likely

business_associate_candidate = likely
```

Final result:

```text
HIPAA
Status: Likely Applicable
Confidence: High
```

---

# 32. HIPAA Evidence Chain

The UI should show:

```text
HIPAA
│
├── Healthcare customer
│
├── Patient-related information
│
├── Electronic storage
│
├── Information potentially constitutes PHI
│
└── Service provided on behalf of healthcare organisation
        │
        └── Business associate relationship candidate
```

---

# 33. HIPAA Missing Information

Example:

```text
Information still requiring verification:

1. Is the health information individually identifiable?
2. Is the information PHI under the HIPAA definition?
3. Is the organisation performing services on behalf of a covered entity?
4. Does the service involve creating, receiving,
   maintaining, or transmitting PHI?
5. Is there a BAA relationship?
```

These questions should be generated dynamically rather than hard-coded as a static questionnaire.

HHS guidance specifically describes business associates in terms of functions or services performed on behalf of covered entities involving PHI, and describes BAA requirements.

---

# 34. Important HIPAA Distinction

The system must distinguish:

```text
Healthcare company
```

from:

```text
HIPAA covered entity
```

and:

```text
Health information
```

from:

```text
PHI
```

and:

```text
Healthcare customer
```

from:

```text
Business associate relationship
```

This distinction is critical for accuracy.

For example:

```text
Healthcare industry
+
No PHI
+
Internal Jira usage
```

should not automatically produce:

```text
HIPAA = Required
```

Similarly:

```text
Technology company
+
Healthcare customer
+
Processes PHI on behalf of customer
```

may create a significant HIPAA relationship even though:

```text
Industry = Technology
```

---

# 35. Regulatory Knowledge Base

The Regulatory Knowledge Base should be the core intellectual asset of the platform.

It should contain:

```text
Regulation
Legal concepts
Definitions
Applicability conditions
Obligations
Exceptions
Jurisdiction
Effective dates
Authoritative sources
Interpretations
Rules
Evidence
Version history
```

---

# 36. Regulation Model

Example:

```json
{
  "id": "HIPAA",
  "name": "Health Insurance Portability and Accountability Act",
  "jurisdiction": "US",
  "type": "privacy_security",
  "authority": "US HHS",
  "status": "active"
}
```

---

# 37. Legal Concept Model

```json
{
  "id": "business_associate",
  "regulation": "HIPAA",
  "definition": "...",
  "relatedSignals": [
    "health_information",
    "acts_on_behalf_of_customer",
    "healthcare_customer",
    "creates_phi",
    "receives_phi",
    "maintains_phi",
    "transmits_phi"
  ]
}
```

---

# 38. Rule Model

Example:

```json
{
  "ruleId": "HIPAA-BA-001",
  "regulation": "HIPAA",
  "legalConcept": "business_associate",

  "conditions": {
    "all": [
      {
        "signal": "healthcare_customer",
        "operator": "equals",
        "value": true
      },
      {
        "signal": "identifiable_health_information",
        "operator": "equals",
        "value": true
      },
      {
        "signal": "acts_on_behalf_of_customer",
        "operator": "equals",
        "value": true
      }
    ]
  },

  "result": {
    "status": "LIKELY",
    "confidence": 0.9
  },

  "source": {
    "authority": "US HHS",
    "document": "Business Associates",
    "section": "45 CFR 160.103",
    "url": "...",
    "retrievedAt": "2026-09-01"
  },

  "version": 1
}
```

---

# 39. Rule Versioning

Rules must never simply be overwritten.

Instead:

```text
HIPAA-BA-001 v1
        ↓
HIPAA-BA-001 v2
        ↓
HIPAA-BA-001 v3
```

Each version should have:

```text
effectiveFrom
effectiveUntil
sourceVersion
knowledgeVersion
reviewedBy
reviewedAt
changeReason
```

This makes historical assessments reproducible.

---

# 40. Assessment Reproducibility

Every assessment should record:

```text
Assessment ID
Customer ID
Timestamp

Signal snapshot
Knowledge base version
Rule versions
Source versions

Regulatory outcomes
Confidence
Evidence
Questions asked
Answers received
```

Therefore:

> An assessment generated six months ago can be reproduced using the exact regulatory knowledge that existed at that time.

---

# 41. Regulatory Source Management

The platform should maintain an authoritative source registry.

Example:

```text
HIPAA
→ US HHS / OCR

DORA
→ European Union / relevant supervisory authorities

FedRAMP
→ FedRAMP / US government sources

GDPR
→ EUR-Lex / European Commission / relevant supervisory authorities

DPDP
→ Government of India / MeitY / official regulatory sources
```

The source registry should distinguish:

```text
Primary authoritative source
Secondary guidance
Interpretive guidance
Industry guidance
Non-authoritative commentary
```

---

# 42. Source Monitoring

The platform should periodically check authoritative sources.

Pipeline:

```text
Fetch source
    ↓
Create snapshot
    ↓
Calculate hash
    ↓
Compare with previous version
    ↓
Detect change
```

If no change:

```text
No action
```

If changed:

```text
Create regulatory change event
```

---

# 43. Regulatory Change Analysis

Example:

```text
Source changed
      ↓
AI compares old/new versions
      ↓
Identifies changed provisions
      ↓
Maps change to legal concepts
      ↓
Identifies affected rules
      ↓
Generates proposed rule changes
      ↓
Generates regression tests
      ↓
Human review
      ↓
Publish
```

---

# 44. AI's Role in Knowledge Maintenance

AI can perform:

```text
Document summarisation
Change detection assistance
Legal concept extraction
Rule impact analysis
Candidate rule generation
Candidate question generation
Regression case generation
```

AI should not silently modify production rules.

Recommended workflow:

```text
AI Proposed Change
        ↓
Human Review
        ↓
Approved / Rejected
        ↓
Regression Tests
        ↓
Publish
```

---

# 45. Why Human Review Is Required

Regulatory interpretation can involve:

* definitions,
* exceptions,
* jurisdiction,
* effective dates,
* enforcement guidance,
* cross-references,
* legal ambiguity,
* sector-specific interpretation.

Therefore:

```text
Automation should scale knowledge maintenance.

Human review should control legal interpretation.
```

---

# 46. Regression Testing

Every rule change should generate tests.

Example:

```text
Test Case 1

Healthcare organisation
+
Identifiable patient data
+
On behalf of healthcare entity

Expected:
HIPAA = Likely
```

Test Case 2:

```text
Technology company
+
No health information
+
Internal Jira usage

Expected:
HIPAA = Not indicated
```

Test Case 3:

```text
Technology company
+
Hospital customer
+
Patient information
+
Processes on behalf of hospital

Expected:
HIPAA = Potentially/Likely
```

---

# 47. Regulatory Pack Architecture

The platform should support modular regulatory packs.

```text
core/
    signals
    rule-engine
    question-engine
    evidence
    assessment
    versioning

regulations/
    hipaa/
    gdpr/
    dpdp/
    ccpa/
    dora/
    fedramp/
    c5/
    tisax/
    pci-dss/
```

Adding a new regulation should primarily involve adding a new knowledge pack.

The core engine should not need to change.

---

# 48. Example Regulatory Pack

```text
HIPAA Pack
│
├── regulation.json
├── definitions.json
├── legal-concepts.json
├── rules.json
├── questions.json
├── sources.json
├── obligations.json
└── tests/
      ├── positive.json
      ├── negative.json
      └── edge-cases.json
```

---

# 49. Generic Rule Engine

The engine should support:

```text
AND
OR
NOT
IN
CONTAINS
EQUALS
GREATER_THAN
LESS_THAN
UNKNOWN
EXISTS
```

Example:

```text
IF

industry IN ["healthcare", "health-tech"]

AND

health_information = true

AND

acts_on_behalf_of_customer = true

THEN

healthcare_processing = likely
```

---

# 50. Three-Valued Logic

The engine should support:

```text
TRUE
FALSE
UNKNOWN
```

Example:

```text
health_information = UNKNOWN
```

Rule:

```text
health_information = TRUE
```

does not become:

```text
FALSE
```

Instead:

```text
Rule status = INDETERMINATE
```

This is critical for partial-information assessments.

---

# 51. Evidence Model

Every derived conclusion should contain evidence.

Example:

```json
{
  "concept": "business_associate_candidate",

  "status": "likely",

  "evidence": [
    {
      "signal": "healthcare_customer",
      "value": true,
      "source": "CRM"
    },
    {
      "signal": "health_information",
      "value": true,
      "source": "customer"
    },
    {
      "signal": "acts_on_behalf_of_customer",
      "value": true,
      "source": "questionnaire"
    }
  ]
}
```

---

# 52. Evidence Quality

Evidence should have a source type.

```text
AUTHORITATIVE
CUSTOMER_CONFIRMED
CRM_VERIFIED
PUBLIC_SOURCE
THIRD_PARTY
INFERRED
AI_INFERRED
UNKNOWN
```

The system can then distinguish:

```text
High confidence because customer explicitly confirmed
```

from:

```text
Potentially applicable because company website suggests healthcare activity
```

---

# 53. Question Generation

Questions should be generated from unresolved legal concepts.

Example:

```text
Legal concept:
phi_candidate

Required signal:
identifiable_health_information

Question:
"Does the health information stored in Atlassian products
identify individual patients, directly or indirectly?"
```

Another:

```text
Legal concept:
business_associate_candidate

Required signal:
acts_on_behalf_of_customer

Question:
"Is Atlassian used to provide services to the healthcare
organisation involving this information?"
```

---

# 54. Question Reuse Across Regulations

The same question should be reusable.

For example:

> What types of sensitive information are stored in Atlassian products?

may affect:

```text
HIPAA
GDPR
CCPA/CPRA
DPDP
LGPD
PIPL
APPI
PCI DSS
```

This prevents separate questionnaires for every regulation.

---

# 55. Regulatory Dependency Graph

The knowledge model can be represented as a graph:

```text
Signal
  ↓
Legal Concept
  ↓
Regulation
  ↓
Obligation
  ↓
Product Requirement
```

Example:

```text
patient_information
        ↓
identifiable_health_information
        ↓
PHI
        ↓
Business Associate
        ↓
HIPAA
        ↓
Security Rule
        ↓
ePHI safeguards
        ↓
Atlassian capability assessment
```

---

# 56. Atlassian Capability Layer

Regulatory applicability is only the first half of the migration problem.

The next question is:

> Can Atlassian Cloud support the requirement?

Therefore, the platform should maintain a separate capability knowledge base.

Example:

```text
Requirement
    ↓
Atlassian capability
    ↓
Supported?
    ↓
Configuration required?
    ↓
Additional contract required?
    ↓
Roadmap?
    ↓
Unsupported?
```

---

# 57. Separation of Concerns

The system must keep these three questions separate:

### Question 1 — Applicability

```text
Does HIPAA apply?
```

### Question 2 — Obligation

```text
What HIPAA obligations are relevant?
```

### Question 3 — Product Capability

```text
Can Atlassian Cloud satisfy those obligations?
```

This prevents the regulatory engine from incorrectly becoming an Atlassian product-answer engine.

---

# 58. Final Migration Readiness Model

```text
Regulation
     ↓
Applicable requirement
     ↓
Customer requirement
     ↓
Atlassian capability
     ↓
Configuration / Contract / Architecture
     ↓
Migration blocker?
```

Example:

```text
HIPAA
  ↓
BAA + ePHI safeguards
  ↓
Customer requires HIPAA support
  ↓
Atlassian Cloud capability
  ↓
BAA / security / residency requirements
  ↓
Migration readiness
```

---

# 59. Proposed UI

## Screen 1 — Organisation Profile

```text
Organisation
------------------------------

Headquarters
Operating countries
Industry
Sub-industry
Employee count
Revenue
Customer types
Government relationship
```

---

## Screen 2 — Data & Usage

```text
What information is stored?

☐ Customer information
☐ Employee information
☐ Health information
☐ Financial information
☐ Payment information
☐ Government information
☐ Personal information
☐ Sensitive information
☐ Other

Who does the information relate to?

☐ Customers
☐ Patients
☐ Employees
☐ Citizens
☐ Government personnel
☐ Children
```

---

# 60. Screen 3 — Regulatory Results

Example:

```text
Regulatory Assessment

HIPAA
██████████████████░░ 93%
Likely Applicable

GDPR
████████████████░░░░ 82%
Likely Applicable

DPDP
███████████░░░░░░░░░ 57%
Potentially Applicable

DORA
████░░░░░░░░░░░░░░░░ 21%
Not currently indicated
```

---

# 61. Screen 4 — Why?

For each regulation:

```text
HIPAA
Likely Applicable

Why?

✓ Healthcare customers
✓ Patient-related information
✓ Electronic storage
✓ Processing on behalf of healthcare organisation

Unknown:

? Is information individually identifiable?
? Is the organisation acting as a business associate?
```

---

# 62. Screen 5 — Missing Information

```text
Information required to increase confidence:

HIGH PRIORITY

1. Does the information identify individual patients?

2. Is the information processed on behalf of
   a healthcare organisation?

3. Does the service create, receive, maintain,
   or transmit the information?
```

---

# 63. Screen 6 — Verification Questions

Only unresolved high-impact questions should appear.

```text
Question 1
-------------------------------
Does the health information stored in Atlassian
products identify individual patients?

[ Yes ] [ No ] [ Unknown ]
```

After the answer, the engine recalculates the assessment.

---

# 64. Screen 7 — Atlassian Readiness

```text
HIPAA

Applicability
✓ Likely

Relevant obligations
✓ Business Associate Agreement
✓ ePHI safeguards
✓ Security requirements

Atlassian support
✓ Supported
⚠ Configuration required
⚠ Contractual verification required

Migration risk
MEDIUM
```

---

# 65. Screen 8 — Exportable Report

The platform should produce:

```text
Regulatory Readiness Report
--------------------------------

Organisation

Assessment date

Knowledge base version

Applicable regulations

Potential regulations

Not currently indicated

Evidence

Missing information

Verification questions

Regulatory obligations

Atlassian capability mapping

Migration blockers

Recommended next actions
```

---

# 66. Example Final Report

```text
==================================================
REGULATORY READINESS ASSESSMENT
==================================================

Organisation:
Example Health Technology Company

Assessment:
2026-09-01

Knowledge Version:
2026.09.1


REGULATORY SUMMARY
--------------------------------------------------

HIPAA
Status: Likely Applicable
Confidence: 93%

GDPR
Status: Potentially Applicable
Confidence: 81%

DPDP
Status: Potentially Applicable
Confidence: 62%

DORA
Status: Not Currently Indicated
Confidence: 78%


HIPAA EVIDENCE
--------------------------------------------------

✓ Healthcare organisations are customers
✓ Patient-related information is stored
✓ Information is stored electronically
✓ Service is provided to healthcare organisation
✓ Processing may occur on behalf of customer


REQUIRES VERIFICATION
--------------------------------------------------

1. Is the information individually identifiable?

2. Does the information constitute PHI?

3. Does the service create, receive, maintain,
   or transmit PHI on behalf of the healthcare entity?


MIGRATION IMPACT
--------------------------------------------------

Potential HIPAA-related requirements identified.

Recommended actions:

1. Validate HIPAA applicability
2. Validate BAA requirement
3. Review Atlassian Cloud HIPAA capabilities
4. Validate relevant data residency/security requirements
5. Confirm contractual requirements
```

---

# 67. Knowledge Change Management

The Knowledge Base should behave like software.

Instead of:

```text
Edit regulation record
```

use:

```text
Pull source
    ↓
Detect change
    ↓
Analyse change
    ↓
Create proposal
    ↓
Review
    ↓
Test
    ↓
Publish
```

---

# 68. Example Change Workflow

```text
SOURCE VERSION 1
        │
        ▼
SOURCE VERSION 2
        │
        ▼
CONTENT DIFF
        │
        ▼
AI IMPACT ANALYSIS
        │
        ├── Legal concept changed
        │
        ├── Existing rule affected
        │
        └── New obligation detected
        │
        ▼
CHANGE PROPOSAL
        │
        ▼
HUMAN REVIEW
        │
        ▼
REGRESSION TESTS
        │
        ▼
KNOWLEDGE VERSION 2
        │
        ▼
PUBLISH
```

---

# 69. Impact Analysis

When a regulatory rule changes, the system should identify:

```text
Affected regulations
Affected legal concepts
Affected rules
Affected questions
Affected assessments
Affected customers
Affected Atlassian capabilities
```

Example:

```text
HIPAA rule changed

Affected:
HIPAA-BA-001
HIPAA-SEC-003

Potentially affected:
143 customer assessments

Action:
Re-evaluate affected assessments
```

---

# 70. Customer Assessment Re-evaluation

A regulatory knowledge update should not silently rewrite historical assessments.

Instead:

```text
Previous Assessment

Knowledge Version:
2026.08.1


New Knowledge

Knowledge Version:
2026.09.1
```

The platform can say:

```text
Your previous assessment may be affected
by a regulatory knowledge update.

Reason:
HIPAA rule HIPAA-BA-001 changed.

Recommended:
Re-run assessment.
```

---

# 71. Security and Privacy of the Platform

The platform itself will process sensitive customer information.

Therefore it must implement:

```text
Encryption at rest
Encryption in transit
Access control
Audit logging
Data minimisation
Tenant isolation
Retention controls
Role-based access control
Secrets management
```

Where possible, customer assessments should avoid storing unnecessary sensitive data.

---

# 72. Data Classification

The platform should classify its own information.

Example:

```text
Organisation profile
→ Internal

Regulatory assessment
→ Confidential

Customer data signals
→ Customer Confidential

Health information
→ Highly Sensitive
```

The platform should ideally store **signals** rather than raw customer data wherever possible.

Example:

Instead of storing:

```text
Patient John Smith
DOB ...
Medical record ...
```

store:

```text
health_information = true
identifiable_health_information = true
data_subject = patient
```

This follows a data-minimisation approach.

---

# 73. Suggested Technology Architecture

A possible implementation:

```text
Frontend
---------
React / Next.js

Backend
-------
Java / Spring Boot

Rule Engine
-----------
Custom deterministic rule engine

Knowledge Store
---------------
PostgreSQL

Knowledge / Graph
-----------------
PostgreSQL + graph model
or
Neo4j if graph complexity justifies it

Search
------
OpenSearch / Elasticsearch

Object Storage
--------------
S3-compatible storage

AI Layer
--------
LLM API

Workflow
--------
Event-driven processing

Messaging
---------
Kafka / SQS / equivalent

Authentication
--------------
SSO / OAuth / OIDC
```

The initial MVP can remain much simpler.

---

# 74. MVP Architecture

For the first version:

```text
React / HTML
      ↓
Spring Boot / Python API
      ↓
PostgreSQL
      ↓
JSON regulatory packs
      ↓
Deterministic Rule Engine
      ↓
Assessment API
```

No complex knowledge graph is required initially.

The important part is getting the **domain model correct**.

---

# 75. Recommended MVP Scope

## Phase 1

Implement:

```text
Signal model
Rule engine
Legal concept model
Evidence model
Question engine
Assessment model
HIPAA regulatory pack
Basic UI
```

---

# 76. MVP Regulatory Pack — HIPAA

Implement:

```text
HIPAA
│
├── Covered Entity
├── Business Associate
├── PHI
├── ePHI
├── Security Rule
├── Privacy Rule
└── Breach Notification
```

The initial implementation should use authoritative HHS material as the source of truth. HHS currently states that the HIPAA Security Rule applies to covered entities and business associates and protects ePHI.

---

# 77. Phase 2

Add:

```text
GDPR
CCPA / CPRA
India DPDP
Data Residency
```

This tests whether the signal ontology is sufficiently generic.

---

# 78. Phase 3

Add:

```text
DORA
FedRAMP
C5
TISAX
IRAP
PCI DSS
NIS2
```

At this stage the platform becomes a generic regulatory applicability engine rather than a HIPAA tool.

---

# 79. Phase 4 — Atlassian Integration

Integrate:

```text
Salesforce
CRM
Customer account data
Atlassian Cloud configuration
Migration assessment
Security questionnaire
Data residency configuration
Atlassian Trust / compliance information
```

---

# 80. Phase 5 — Automated Intelligence

Add:

```text
Public company research
Regulatory source monitoring
Automatic change detection
AI rule proposals
AI-generated questions
Assessment re-evaluation
Risk trend monitoring
```

---

# 81. Generic Regulatory Processing Pipeline

The final system should behave like:

```text
                    ORGANISATION
                         │
                         ▼
                 ┌───────────────┐
                 │ Raw Facts     │
                 └───────┬───────┘
                         │
                         ▼
                 ┌───────────────┐
                 │ Signals       │
                 └───────┬───────┘
                         │
                         ▼
                 ┌───────────────┐
                 │ Legal Concepts│
                 └───────┬───────┘
                         │
                         ▼
                 ┌───────────────┐
                 │ Rules         │
                 └───────┬───────┘
                         │
                         ▼
                 ┌───────────────┐
                 │ Assessment    │
                 └───────┬───────┘
                         │
                 ┌───────┴────────┐
                 │                │
                 ▼                ▼
          Missing Signals     Regulations
                 │                │
                 ▼                ▼
           Questions        Obligations
                 │                │
                 └───────┬────────┘
                         ▼
                 Final Assessment
                         │
                         ▼
                Product Capability
                         │
                         ▼
                Migration Readiness
```

---

# 82. Key Design Decision

The most important architectural decision is:

> **Do not build a "law checker". Build a regulatory knowledge and decision system.**

A law checker asks:

```text
Does GDPR apply?
```

A regulatory intelligence system understands:

```text
Organisation
   ↓
Activities
   ↓
Data
   ↓
People
   ↓
Relationships
   ↓
Jurisdictions
   ↓
Legal concepts
   ↓
Regulations
   ↓
Obligations
   ↓
Product requirements
```

This architecture scales much better.

---

# 83. Why This Model Scales

Consider:

```text
100 regulations
```

and:

```text
1000 canonical signals
```

If every regulation implements its own questionnaire:

```text
100 × many questions
```

the system becomes unmanageable.

Instead:

```text
100 regulations
        ↓
Shared signal ontology
        ↓
Shared legal concepts
        ↓
Reusable questions
```

A single signal can affect many regulations.

Example:

```text
health_information
```

may contribute to:

```text
HIPAA
GDPR
CCPA
DPDP
LGPD
```

---

# 84. Long-Term Knowledge Graph

Eventually the system can evolve into:

```text
                    ┌──────────────┐
                    │ Organisation │
                    └──────┬───────┘
                           │
                    has_signal
                           │
                           ▼
                    ┌──────────────┐
                    │    Signal    │
                    └──────┬───────┘
                           │
                    implies
                           │
                           ▼
                    ┌──────────────┐
                    │ Legal Concept│
                    └──────┬───────┘
                           │
                    governed_by
                           │
                           ▼
                    ┌──────────────┐
                    │ Regulation   │
                    └──────┬───────┘
                           │
                    requires
                           │
                           ▼
                    ┌──────────────┐
                    │ Obligation   │
                    └──────┬───────┘
                           │
                    satisfied_by
                           │
                           ▼
                    ┌──────────────┐
                    │ Capability   │
                    └──────────────┘
```

---

# 85. Success Criteria

The platform should be evaluated on:

## Accuracy

Does the system identify relevant regulations correctly?

## Explainability

Can every result be explained?

## Evidence

Can every important decision be traced to source evidence?

## Question Efficiency

How many questions are required to reach a high-confidence assessment?

## Maintainability

Can new regulations be added without changing the core engine?

## Regulatory Freshness

Can regulatory changes be detected and incorporated quickly?

## Reproducibility

Can an historical assessment be reconstructed?

## False Positive Rate

Does the system avoid saying a regulation applies simply because the organisation belongs to a broad industry?

## False Negative Rate

Does the system identify regulations even when the organisation's industry classification alone would not reveal them?

---

# 86. Key Metrics

Recommended product metrics:

```text
Average questions per assessment

Percentage of assessments resolved
without manual legal review

Regulatory classification accuracy

False-positive rate

False-negative rate

Percentage of results with evidence

Percentage of rules with authoritative provenance

Average time to update a regulatory change

Number of customers affected by a rule change

Percentage of assessments automatically re-evaluated
```

---

# 87. Core Product Principle

The platform should ultimately answer four questions:

### 1. What probably applies?

```text
HIPAA
GDPR
DPDP
DORA
...
```

### 2. Why?

```text
Signals
+
Legal concepts
+
Rules
+
Evidence
```

### 3. What do we still need to know?

```text
Missing signals
```

### 4. What does it mean for the product?

```text
Requirement
→ Capability
→ Gap
→ Migration impact
```

---

# 88. Final Architecture Principle

The final architecture can be summarised as:

```text
                ┌─────────────────────────┐
                │ AUTHORITATIVE SOURCES   │
                └────────────┬────────────┘
                             │
                             ▼
                ┌─────────────────────────┐
                │ REGULATORY KNOWLEDGE    │
                │                         │
                │ Laws                    │
                │ Definitions             │
                │ Legal Concepts          │
                │ Obligations             │
                │ Rules                   │
                │ Evidence                │
                └────────────┬────────────┘
                             │
                             ▼
CUSTOMER ───────► ┌─────────────────────────┐
SIGNALS            │ DECISION ENGINE        │
                   │                         │
                   │ Deterministic Rules    │
                   │ Evidence                │
                   │ Confidence              │
                   │ Unknown handling        │
                   └────────────┬────────────┘
                                │
                                ▼
                   ┌─────────────────────────┐
                   │ QUESTION OPTIMIZER      │
                   └────────────┬────────────┘
                                │
                                ▼
                   ┌─────────────────────────┐
                   │ REGULATORY ASSESSMENT   │
                   └────────────┬────────────┘
                                │
                                ▼
                   ┌─────────────────────────┐
                   │ PRODUCT CAPABILITY      │
                   │                         │
                   │ Supported               │
                   │ Configuration required  │
                   │ Contract required       │
                   │ Gap                     │
                   │ Roadmap                 │
                   └────────────┬────────────┘
                                │
                                ▼
                   ┌─────────────────────────┐
                   │ MIGRATION READINESS     │
                   └─────────────────────────┘
```

---

# 89. Guiding Philosophy

The system should follow five principles:

> **1. Facts over assumptions.**

> **2. Rules over black-box classification.**

> **3. Evidence over unexplained scores.**

> **4. Targeted questions over long questionnaires.**

> **5. Continuously maintained knowledge over static compliance tables.**

The ultimate goal is not to create another compliance questionnaire.

The goal is to create a **living regulatory intelligence system** that continuously understands:

```text
What changed?
       ↓
What regulation does it affect?
       ↓
What legal concept changed?
       ↓
What rule changed?
       ↓
Which customers are affected?
       ↓
What questions should we ask?
       ↓
What does this mean for Atlassian Cloud?
       ↓
Does it create a migration blocker?
```

---

# 90. One-Line Product Definition

> **A signal-driven regulatory intelligence platform that determines which security, privacy, compliance, and data-residency requirements are likely to apply to an organisation, explains why, identifies what remains unknown, and maps those requirements to product and migration readiness.**

---

# 91. Core Principle

```text
                AI INTERPRETS
                     │
                     ▼
              KNOWLEDGE MODELS
                     │
                     ▼
                RULES DECIDE
                     │
                     ▼
              EVIDENCE EXPLAINS
                     │
                     ▼
             HUMANS VALIDATE
                     │
                     ▼
            KNOWLEDGE EVOLVES
```

This creates a system that can start with **HIPAA**, but is architecturally capable of growing into a broader **enterprise regulatory intelligence platform**.
