1. First: Clean up the current theme list

There are duplicates:

Theme 9 and Theme 18 → Multi-Org / Tenant Consolidation Governance
Theme 10 and Theme 19 → NIS2 / Critical Infrastructure

So we currently have 23 unique themes, not 25.

I would also split some themes because they currently combine multiple unrelated regulatory systems.

For example:

FedRAMP / US Government Authorisation

actually contains:

FedRAMP
StateRAMP
CJIS
DoD Impact Levels
Federal contractor requirements

These should eventually become separate requirement packs because they have different applicability rules and sources.

Similarly:

India DPDP / APAC Data Protection

should not be one rule pack.

It should become:

India DPDP
China PIPL
Japan APPI
Singapore PDPA
South Korea PIPA
South Africa POPIA
Brazil LGPD
California CCPA/CPRA

The UI can still group them under:

Privacy & Data Protection

But the knowledge system should treat them independently.

2. The proposed generic model

I recommend changing the earlier architecture slightly.

Instead of:

Regulation
    ↓
Rules

we should have:

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

The important part is Requirement Type.

3. Requirement types

I recommend these top-level categories.

A. Laws & Regulations

These are based on statutory or regulatory applicability.

Examples:

HIPAA
GDPR
DORA
NIS2
DPDP
PIPL
APPI
PDPA
PIPA
POPIA
LGPD
CCPA/CPRA
EU AI Act

The engine asks:

Does this organisation fall within the scope of this law?

B. Government / Sovereign Authorisation

Examples:

FedRAMP
StateRAMP
CJIS
DoD Impact Levels
IRAP

The engine asks:

Does the organisation operate in a government or regulated environment where this authorisation may be required?

C. Industry / Security Standards

Examples:

PCI DSS
ISO 27001
SOC 2
C5
TISAX
BSI IT-Grundschutz
HITRUST

The engine asks:

Is this standard mandatory, contractually required, regulator-driven, or simply requested by the customer's internal policy?

This distinction is important.

For example:

PCI DSS

can become mandatory based on cardholder data and environment scope.

But:

SOC 2

may simply be a vendor assurance requirement.

So the result cannot always be:

SOC 2 applies

It may instead be:

SOC 2 evidence likely to be requested
D. Customer Security / Architecture Requirements

Examples:

Data Residency
Data Sovereignty
CMK
HSM
IP Allowlisting
Private Connectivity
SIEM Export
DLP
Sensitive Data Classification
Granular RBAC
SCIM
SSO

These are generally not laws by themselves.

They may be triggered by:

Law
+
Industry regulation
+
Government policy
+
Customer security policy
+
Architecture standard

Example:

Government customer
        ↓
Data sovereignty requirement
        ↓
Specific personnel access requirement
        ↓
Atlassian Cloud capability assessment
E. Internal Governance / Migration Process

Examples:

Legal Review
InfoSec Review
CAB Approval
Security Questionnaire
Tenant Consolidation Governance
Migration Approval Workflow

The engine asks:

Is this likely to become a migration process blocker?

This is not a legal applicability engine.

It is a migration risk detection engine.

4. The new complete taxonomy

I recommend reorganising your themes into the following groups.

A. Privacy & Data Protection
A01 — GDPR

Signals:

EU establishment
EU customers
EU data subjects
Personal data processing
Offering services to EU individuals
Monitoring EU individuals
A02 — GDPR Contractual & Data Transfer

Split from the GDPR theme:

DPA
SCC
International data transfers
Subprocessor transparency
Data Processing Impact Assessment
Data subject rights

This may be a requirement consequence of GDPR or customer privacy requirements rather than a separate law.

A03 — India DPDP

Signals:

India operations
India customers
Indian data principals
Digital personal data
Processing activities
Cross-border transfers
A04 — California CCPA / CPRA

Signals:

California consumers
Business thresholds
Revenue
Consumer personal information
Commercial activity
A05 — Brazil LGPD

Signals:

Brazilian data subjects
Brazil operations
Personal data processing
A06 — China PIPL

Signals:

China operations
Chinese individuals
Personal information processing
Cross-border transfer
Critical information infrastructure
A07 — Japan APPI
A08 — Singapore PDPA
A09 — South Korea PIPA
A10 — South Africa POPIA

These can all reuse the same privacy signal ontology.

That is where the scalability comes from.

We do not create:

10 separate questionnaires

Instead:

Shared Privacy Signals
          ↓
GDPR rules
DPDP rules
PIPL rules
LGPD rules
...
B. Healthcare & Life Sciences
B01 — HIPAA

Signals:

Healthcare provider
Health plan
Healthcare clearinghouse
Healthcare customer
Patient data
Identifiable health information
Electronic PHI
Acts on behalf of covered entity
B02 — HITRUST

Important distinction:

HIPAA = legal/regulatory framework

HITRUST = assurance/certification framework

The system should detect:

HITRUST likely to be requested

rather than claim:

HITRUST legally applies

unless there is a specific contractual or regulatory requirement.

C. Financial Services & Payments
C01 — DORA

Signals:

EU financial entity
Bank
Insurance
Investment firm
Financial market infrastructure
ICT service dependency
EU operations
C02 — PCI DSS

Signals:

Payment card data
PAN
Cardholder data environment
Payment processing
Payment service
Card data stored in Jira/Confluence
C03 — Financial Sector Regulatory Signals

Future packs may include:

Financial services outsourcing
Operational resilience
Third-party risk
Data retention
Financial authority requirements

This can become a reusable Financial Services Regulatory Profile.

D. Government & Public Sector

This should be broken into separate packs.

D01 — FedRAMP

Signals:

US federal agency customer
Federal contractor
Federal information
Federal system
Cloud service for government
FedRAMP mentioned in procurement
D02 — StateRAMP

Signals:

US state government customer
State procurement
StateRAMP requirement
D03 — CJIS

Signals:

Law enforcement customer
Criminal justice information
State/local law enforcement agency
CJIS data
D04 — DoD Impact Levels

Signals:

US Department of Defense customer
Defense contractor
Controlled Unclassified Information
DoD workload
Impact Level requirement
D05 — IRAP

Signals:

Australian government customer
Australian government data
PROTECTED classification
Defence-related workload
Australian government procurement
E. Critical Infrastructure
E01 — NIS2

Signals:

EU entity
Sector
Entity size
Energy
Transport
Healthcare
Digital infrastructure
Water
Wastewater
Financial market infrastructure
Public administration
Manufacturing
Critical digital services

NIS2 should not simply be:

EU company = NIS2

The rules need to evaluate:

Jurisdiction
+
Sector
+
Entity category
+
Size
+
National implementation

This is exactly the type of law where your signal-based engine becomes valuable.

F. Data Location & Sovereignty

This should be separated into multiple requirement packs.

F01 — Data Residency

Signals:

Required data location
Customer country
Data classification
Regulatory requirement
Government requirement
Contractual requirement

The output should distinguish:

Explicit legal requirement
Regulatory expectation
Customer contractual requirement
Customer preference
F02 — Data Sovereignty

Signals:

Government customer
National security workload
Personnel nationality requirement
Security clearance requirement
Foreign legal exposure concern
Export control
F03 — Export Controls

Potential future sub-packs:

ITAR
EAR
Korean strategic technology / NCT-related controls
Other national restrictions

These should not be merged with generic data residency.

G. AI Governance
G01 — EU AI Act

Signals:

EU deployment
AI usage
AI feature usage
Purpose of AI
Affected individuals
Employment use
Credit use
Healthcare use
Law enforcement use
Public sector use
High-risk use case

This should not ask:

Are you high risk under the EU AI Act?

Instead:

What is the AI being used for?
Who is affected?
Does it influence decisions?
Is it used for employment?
Is it used in critical infrastructure?

Then the engine derives the possible classification.

G02 — Enterprise AI Governance

Separate from EU AI Act.

Signals:

AI prohibited by policy
Customer requires AI feature controls
Data sharing restrictions
Model provider restrictions
BYO model requirement
Content exclusion requirement

This is primarily:

Customer policy / governance

not law applicability.

H. National & Sector Cloud Assurance
H01 — C5

Signals:

Germany
German government
BaFin-regulated organisation
Cloud procurement
C5 explicitly required
H02 — TISAX

Signals:

Automotive industry
Automotive OEM customer
Automotive supplier
TISAX requirement
H03 — BSI IT-Grundschutz

Signals:

German public sector
Critical infrastructure
Government procurement
H04 — Other National Cloud Schemes

Future packs:

Korean CSP Stability Assessment
EUCS
Other sovereign cloud schemes
I. Security Assurance & Evidence

These should be treated differently.

I01 — ISO 27001

Likely output:

Customer assurance requirement detected

Signals:

Industry
Procurement
Security questionnaire
ISO requirement
Government/enterprise policy
I02 — SOC 2

Signals:

Vendor security review
US enterprise
Procurement requirement
Customer security policy
I03 — CSA STAR

Same category:

Security assurance evidence requirement

These should probably share a parent capability:

Third-Party Security Assurance Requirement

J. Network & Connectivity
J01 — IP Allowlisting

Signals:

Corporate network restriction
Zero trust policy
Restricted inbound access
Customer IP policy
Security policy
J02 — Egress Control

Signals:

Outbound filtering
CASB
Proxy
Firewall policy
Domain allowlisting
J03 — Private Connectivity

Signals:

Private network requirement
No public internet policy
Private connectivity architecture

These are not regulatory packs.

They belong to:

Architecture Requirement Detection

K. Security Monitoring & Investigation
K01 — Audit Logging

Signals:

Security operations maturity
Regulated industry
Incident response requirement
Forensics requirement
Audit retention
K02 — SIEM Integration

Signals:

Splunk
Datadog
Sumo Logic
Security monitoring requirement
Central logging policy
L. Encryption & Key Management
L01 — Encryption Requirement

Signals:

Sensitive data
Regulated workload
Customer encryption policy
L02 — Customer Managed Keys

Signals:

Customer controls encryption keys
Government policy
Financial sector
Highly regulated environment
Explicit CMK requirement
L03 — HSM / External Key Management

Signals:

HSM requirement
External key control
XKS requirement
Sovereign key requirement
M. Identity & Access Management

These can reuse a common IAM signal model.

M01 — SSO / Identity Federation

Signals:

Corporate IdP
Okta
Entra
Ping
ADFS
Federated authentication
Multiple identity providers
M02 — SCIM / Identity Lifecycle

Signals:

Automated provisioning
Automated deprovisioning
Group synchronization
Lifecycle automation
M03 — Granular RBAC

Signals:

Complex roles
Separation of duties
Compliance admin
Security admin
Read-only security access
Business-unit separation
N. Data Protection Controls
N01 — Sensitive Data Classification

Signals:

Personal data
PHI
Financial data
Government data
Intellectual property
Data classification policy
N02 — DLP

Signals:

Sensitive content
Data leakage concern
Pre-migration scanning
Ongoing detection requirement
O. Security Testing & Review
O01 — Penetration Testing

Signals:

Customer security sign-off
Pen test requirement
Vendor assessment
Regulated industry
O02 — Vulnerability Scanning

Signals:

Customer-managed scanning policy
Vulnerability management requirement
Security approval process
O03 — Security Questionnaire

Signals:

SIG
CAIQ
Bespoke questionnaire
Third-party risk review
P. Governance & Organisational Structure
P01 — Legal / InfoSec / CAB Review

This is not a regulatory rule.

Instead, the engine detects:

Internal approval risk

Signals:

Legal review required
InfoSec approval
CAB process
Change freeze
Procurement dependency
Security questionnaire

Output:

Migration Process Risk:
HIGH

Likely blocker:
Customer-side security approval
P02 — Tenant / Organisation Governance

Signals:

Multiple Atlassian organisations
Multiple sites
Business-unit separation
M&A
Tenant consolidation
Central administration
Security administration

Output:

Migration Architecture Complexity

rather than:

Regulation applies
5. Final target structure

I would therefore create this master hierarchy:

REQUIREMENTS INTELLIGENCE PLATFORM

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
6. How all of this fits into the same engine

The core engine does not care whether the pack is HIPAA or CMK.

Every pack follows a common structure.

Requirement Pack
       │
       ├── Metadata
       │
       ├── Type
       │      LAW
       │      REGULATION
       │      STANDARD
       │      AUTHORISATION
       │      CUSTOMER_REQUIREMENT
       │      TECHNICAL_REQUIREMENT
       │      PROCESS_RISK
       │
       ├── Applicability signals
       │
       ├── Derived concepts
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
       ├── Requirements
       │
       ├── Capability mappings
       │
       └── Tests

For example:

HIPAA

has:

Type = LAW

while:

CMK

has:

Type = TECHNICAL_REQUIREMENT

and:

CAB Review

has:

Type = PROCESS_RISK

The engine still processes all of them using the same model.

7. What the system should explicitly say when it cannot determine something

This is extremely important.

The system must never force a Yes/No answer.

Every assessment should support:

LIKELY_APPLICABLE

POTENTIALLY_APPLICABLE

NOT_CURRENTLY_INDICATED

INSUFFICIENT_INFORMATION

MANUAL_REVIEW_REQUIRED

OUT_OF_SCOPE

For example:

DORA

Status:
MANUAL_REVIEW_REQUIRED

Reason:
The organisation appears to operate in financial services,
but the available information is insufficient to determine
whether it falls within the relevant entity category.

What is known:
✓ EU operations
✓ Financial services customer

What is missing:
? Legal entity classification
? Regulated activity
? Supervisory authority

Source:
[Authoritative regulatory reference]

Recommended action:
Validate with customer's compliance/legal team.

This is much safer than:

DORA = YES
8. A very important addition: "Detection" versus "Determination"

I strongly recommend that the product terminology uses two levels.

Level 1 — Detection

The system says:

We detected signals that this may be relevant.

Example:

Potential DORA relevance detected.
Level 2 — Determination

Only when sufficient evidence exists:

DORA applicability strongly indicated.

And for complex cases:

Legal / compliance verification required.

This prevents the product from pretending to provide definitive legal advice.

9. Recommended implementation plan

We should not start implementing 30+ packs immediately.

Instead, build the platform iteratively.

Phase 0 — Build the Generic Foundation

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

This is the actual foundation.

Phase 1 — HIPAA

HIPAA becomes the reference implementation.

We implement end-to-end:

Signals
        ↓
Derived concepts
        ↓
Rules
        ↓
Evidence
        ↓
Confidence
        ↓
Missing signals
        ↓
Targeted questions
        ↓
Final assessment
        ↓
Atlassian capability mapping

This proves the architecture.

Phase 2 — Add One Theme from Each Category

Instead of adding five privacy laws immediately, test the generic architecture across different requirement types.

I recommend:

Law
GDPR
Government authorisation
FedRAMP
Industry regulation
DORA
Technical requirement
Data Residency
Security standard
PCI DSS
Process blocker
Legal / InfoSec / CAB Review

After these six, we will know whether the architecture is genuinely generic.

Phase 3 — Build Shared Signal Domains

Once those are working, create reusable signal domains.

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

Then future packs become significantly easier.

Phase 4 — Expand by Shared Domain

Recommended order:

Privacy
GDPR
DPDP
CCPA/CPRA
LGPD
PIPL
APPI
PDPA
PIPA
POPIA
Government
FedRAMP
StateRAMP
CJIS
DoD IL
IRAP
Financial
DORA
PCI DSS
EU / Critical Infrastructure
NIS2
C5
TISAX
Architecture
Data Residency
Data Sovereignty
CMK
IP Allowlisting
Private Connectivity
Security Operations
Audit Logging
SIEM
Pen Test
Vulnerability Management
DLP
Data Classification
Identity
SSO
SCIM
RBAC
Governance
Legal Review
InfoSec Review
CAB
Multi-Org Governance
10. The iterative workflow for every theme

This is how I suggest we work one by one.

For every theme, we create the following.

Step 1 — Define the theme

Example:

Theme:
DORA

Type:
LAW / REGULATION

Jurisdiction:
European Union

Primary authority:
[Official authority]

Purpose:
Digital operational resilience for financial entities.
Step 2 — Break it into sub-concepts

For DORA:

financial_entity

eu_jurisdiction

ict_service_dependency

third_party_ict_provider

operational_resilience

incident_management

resilience_testing

third_party_risk
Step 3 — Identify authoritative sources

Every concept and rule must be linked to:

Primary source

Official guidance

Relevant article / section

Effective date

Last reviewed date
Step 4 — Identify signals

Example:

Headquarters country

Operating countries

Industry

Sub-industry

Financial licence

Financial regulator

Customer types

ICT dependency

Atlassian usage
Step 5 — Classify signals by collection stage
Phase 0
Automatically available

Phase 1
Low-friction customer signals

Phase 2
Usage / architecture signals

Phase 3
Targeted verification questions

Phase 4
Manual expert review
Step 6 — Define derived concepts

Example:

eu_financial_entity_candidate

regulated_financial_activity_candidate

dora_scope_candidate
Step 7 — Create applicability rules

Using:

TRUE
FALSE
UNKNOWN

and not simple yes/no classification.

Step 8 — Define confidence

Example:

High:
Customer-confirmed regulated entity

Medium:
Industry and geography strongly indicate relevance

Low:
Public information only
Step 9 — Define targeted questions

Only ask questions that can materially change the result.

Step 10 — Define obligations

Separate:

Applicability

from:

What becomes important if applicable?
Step 11 — Map to Atlassian capability

Each requirement should result in:

SUPPORTED

SUPPORTED_WITH_CONFIGURATION

SUPPORTED_WITH_CONTRACT

PARTIALLY_SUPPORTED

NOT_SUPPORTED

ROADMAP

UNKNOWN

MANUAL_VERIFICATION_REQUIRED
Step 12 — Create regression tests

Every pack must include:

Positive cases

Negative cases

Unknown cases

Edge cases

Conflicting evidence

Historical version tests
11. Proposed repository structure

I would structure the project like this:

requirements-intelligence/

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
│   └── registry/
│
├── capabilities/
│   └── atlassian/
│
└── tests/
12. My recommended first six iterations

To prove that this system works across all types, I suggest this order:

Iteration	Theme	Why
1	HIPAA	Complex applicability based on data + relationship
2	GDPR	Privacy law with jurisdiction + processing logic
3	FedRAMP	Government/customer-driven authorisation
4	DORA	Industry + jurisdiction + legal entity logic
5	Data Residency	Requirement can originate from law, contract, or policy
6	IP Allowlisting / Private Connectivity	Pure technical requirement detection

After these six, we should stop and evaluate the architecture.

If the same:

Signals
→ Concepts
→ Rules
→ Evidence
→ Questions
→ Assessment
→ Capability

model works for all six, then we have validated the generic platform.

After that, adding the remaining themes becomes mostly knowledge-pack development, not new platform development.

The key recommendation

I would not call this only a “Privacy Law Checker” anymore.

Your actual system is broader:

A signal-driven requirements intelligence platform that detects regulatory, compliance, security, architecture, sovereignty, and governance requirements that may affect a customer's migration to Atlassian Cloud.

And the most important rule for the entire product should be:

The system must never pretend to know something
that the available evidence does not support.

So every result must be able to say:

Likely applicable
Potentially applicable
Not currently indicated
Insufficient information
Manual review required
Cannot determine from available signals

That principle will make the system much more trustworthy than a generic AI chatbot or a static region × industry × segment matrix.

My recommendation for our next step: start with Iteration 0 — defining the generic core schemas and architecture, then implement HIPAA as the first real pack against that core. After HIPAA, we can take each theme from the roadmap and break it down systematically using the 12-step process above.
