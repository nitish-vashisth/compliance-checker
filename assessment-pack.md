# Generate Assessment Execution Pack

## Purpose

Create a **minimal, machine-executable Assessment Execution Pack** from the supplied **VERIFIED Regulatory Requirement Pack**.

The Assessment Execution Pack will be consumed by a deterministic assessment engine to determine, from customer input, whether each regulatory object represented in the Regulatory Requirement Pack is:

* `APPLICABLE`
* `NOT_APPLICABLE`
* `CONDITIONALLY_APPLICABLE`
* `UNKNOWN`

This is a **compilation step**, not a regulatory knowledge extraction step.

The source Regulatory Requirement Pack has already been generated, reviewed, and verified by the appropriate domain experts.

Do not create or modify regulatory knowledge during this step.

---

# INPUTS

You will receive:

1. **VERIFIED REGULATORY REQUIREMENT PACK**
2. **CUSTOMER INPUT SCHEMA**

The Regulatory Requirement Pack is the authoritative source for:

* regulatory objects
* signals
* derived concepts
* applicability rules
* exceptions
* boundaries
* rule outcomes
* rule dependencies
* verification status

The Customer Input Schema defines how customer answers are represented.

---

# CORE OBJECTIVE

Compile the verified Regulatory Requirement Pack into a smaller **Assessment Execution Pack**.

The resulting pack must contain ONLY information required by an assessment engine to execute applicability decisions.

The assessment engine must be able to perform:

```text
Customer Input
      ↓
Signal Resolution
      ↓
Derived Concept Evaluation
      ↓
Applicability Rule Evaluation
      ↓
Exception / Boundary Evaluation
      ↓
Regulatory Applicability Result
```

without requiring access to the original Regulatory Requirement Pack.

---

# IMPORTANT RULES

## 1. Do not create new regulatory knowledge

Do not:

* create new rules
* modify existing rules
* create new signals
* modify signal meanings
* create new regulatory objects
* create new requirements
* create new exceptions
* infer new applicability logic

Everything in the Assessment Execution Pack must originate from the verified Regulatory Requirement Pack.

---

## 2. Preserve deterministic behavior

Every rule must be executable using explicit:

* signal values
* operators
* expected values
* logical operators
* derived concepts
* exceptions
* boundaries
* outcomes

Avoid natural-language rules where a machine-readable condition can be represented.

For example, prefer:

```json
{
  "signalId": "S001",
  "operator": "EQUALS",
  "value": true
}
```

over:

```text
The customer must be a federal agency.
```

---

## 3. Do not include unnecessary knowledge

Do NOT copy the entire Regulatory Requirement Pack.

Do not include information such as:

* lengthy regulatory descriptions
* source documents
* regulatory explanations
* discovery questions that are not needed during execution
* narrative guidance
* general definitions that are not required to evaluate a rule
* technical controls
* Atlassian capabilities
* recommendations

Only include information required for deterministic applicability evaluation.

---

# REQUIRED EXECUTION COMPONENTS

The Assessment Execution Pack should contain the following components.

## 1. Pack Metadata

Include:

* executionPackId
* regulatoryPackId
* regulatoryPackVersion
* frameworkId
* frameworkName
* executionPackVersion
* status
* generatedFrom
* verificationStatus

The pack must identify the exact Regulatory Requirement Pack version from which it was compiled.

---

## 2. Regulatory Objects

Include only regulatory objects that have applicability rules in the verified Regulatory Requirement Pack.

A regulatory object may represent:

* LAW
* ACT
* REGULATION
* FRAMEWORK
* RULE
* STANDARD
* OTHER

For each object include:

* regulatoryObjectId
* name
* type

Do not add regulatory objects that are not present in the Regulatory Requirement Pack.

---

## 3. Executable Signals

Include every signal required by at least one applicability rule or derived concept.

For every signal include:

* signalId
* name
* type
* allowedValues
* requiredForExecution
* customerInputField

The `customerInputField` must map the signal to the corresponding field in the Customer Input Schema.

If the signal is not required for any applicability decision, exclude it.

---

## 4. Derived Concepts

Include every derived concept required by an applicability rule.

For every derived concept include:

* conceptId
* inputSignals
* derivationLogic
* outputType
* unknownHandling

The derivation logic must be machine-executable.

Example:

```json
{
  "conceptId": "C001",
  "inputSignals": [
    "S001",
    "S002"
  ],
  "derivationLogic": {
    "operator": "AND",
    "conditions": [
      {
        "signalId": "S001",
        "operator": "EQUALS",
        "value": true
      },
      {
        "signalId": "S002",
        "operator": "EQUALS",
        "value": true
      }
    ]
  },
  "outputType": "BOOLEAN",
  "unknownHandling": "PROPAGATE_UNKNOWN"
}
```

Do not introduce derivation logic that does not exist in the Regulatory Requirement Pack.

---

# 5. Applicability Rules

This is the most important section.

For every applicability rule in the Regulatory Requirement Pack that determines regulatory applicability, create an executable rule.

Each rule must contain:

* ruleId
* regulatoryObjectId
* priority, if defined
* conditions
* logicalOperator
* outcome
* dependencies
* sourceRuleId
* verificationStatus

Example:

```json
{
  "ruleId": "FEDRAMP-R001",
  "regulatoryObjectId": "FEDRAMP",

  "conditions": [
    {
      "signalId": "S001",
      "operator": "EQUALS",
      "value": true
    },
    {
      "signalId": "S002",
      "operator": "EQUALS",
      "value": true
    }
  ],

  "logicalOperator": "AND",

  "outcome": {
    "status": "APPLICABLE"
  },

  "sourceRuleId": "FEDRAMP-R001",
  "verificationStatus": "DOMAIN_VERIFIED"
}
```

Every condition must identify exactly what value is being evaluated.

---

# 6. Supported Operators

Preserve the operators defined by the Regulatory Requirement Pack.

If the pack uses equivalent machine-readable operators, normalize them to a consistent representation where doing so does not change semantics.

Supported operators may include:

```text
EQUALS
NOT_EQUALS
IN
NOT_IN
CONTAINS
NOT_CONTAINS
EXISTS
NOT_EXISTS
GREATER_THAN
GREATER_THAN_OR_EQUAL
LESS_THAN
LESS_THAN_OR_EQUAL
IS_TRUE
IS_FALSE
```

For logical composition:

```text
AND
OR
NOT
```

Do not introduce an operator that changes or extends the regulatory logic.

If a rule cannot be represented deterministically using the available execution model, mark it:

```text
executionStatus = UNSUPPORTED
```

Do not approximate it.

---

# 7. Exceptions and Boundaries

Include only exceptions and boundaries that can change the applicability result.

For each include:

* exceptionId
* affectedRules
* conditions
* effect
* executionStatus

Example:

```json
{
  "exceptionId": "E001",
  "affectedRules": [
    "FEDRAMP-R001"
  ],
  "conditions": [
    {
      "signalId": "S005",
      "operator": "EQUALS",
      "value": true
    }
  ],
  "effect": {
    "status": "NOT_APPLICABLE"
  },
  "executionStatus": "EXECUTABLE"
}
```

Do not copy exceptions that have no effect on applicability execution.

---

# 8. Unknown Handling

This is mandatory.

The execution pack must explicitly define how missing information is handled.

Never convert:

```text
UNKNOWN → FALSE
```

If a rule requires a signal and the signal is unavailable, the condition should evaluate to:

```text
UNKNOWN
```

The rule result must follow the logical semantics defined by the pack.

For example:

```text
TRUE AND UNKNOWN = UNKNOWN
FALSE AND UNKNOWN = FALSE
TRUE OR UNKNOWN = TRUE
FALSE OR UNKNOWN = UNKNOWN
```

Unless the verified Regulatory Requirement Pack explicitly defines different semantics.

---

# 9. Rule Dependencies

Identify dependencies between:

```text
Customer Signal
      ↓
Derived Concept
      ↓
Rule
      ↓
Exception
      ↓
Regulatory Object
```

The execution pack must contain enough dependency information for the assessment engine to evaluate rules in the correct order.

---

# 10. Execution Order

Define an execution order where required:

```text
1. Validate customer input
2. Resolve customer signals
3. Evaluate derived concepts
4. Evaluate applicability rules
5. Evaluate exceptions
6. Resolve final regulatory applicability
```

If the Regulatory Requirement Pack defines explicit dependencies, preserve them.

Do not invent dependencies.

---

# 11. Final Regulatory Result

The execution pack must define how rule outcomes map to:

```text
APPLICABLE
NOT_APPLICABLE
CONDITIONALLY_APPLICABLE
UNKNOWN
```

For each regulatory object, identify:

* rules contributing to the final decision
* precedence between rules if explicitly defined
* conditions for conditional applicability
* conditions producing UNKNOWN

Do not invent precedence.

If precedence is not defined in the Regulatory Requirement Pack, mark it:

```text
resolutionStatus = REQUIRES_REVIEW
```

rather than inventing an ordering.

---

# 12. Traceability

Every executable object must remain traceable to the verified Regulatory Requirement Pack.

For every:

* signal
* derived concept
* rule
* exception
* regulatory object

include its originating ID.

Example:

```json
{
  "source": {
    "requirementPackId": "FEDRAMP-2026",
    "requirementPackVersion": "1.0",
    "sourceObjectId": "FEDRAMP-R001"
  }
}
```

This allows the assessment result to explain:

```text
Customer Answer
      ↓
Signal
      ↓
Rule
      ↓
Regulatory Object
```

---

# 13. Execution Validation

Before producing the final pack, validate:

### Signal validation

Every rule references an existing signal or derived concept.

### Rule validation

Every rule has:

* valid ruleId
* valid regulatoryObjectId
* valid conditions
* valid operators
* explicit outcome

### Derived concept validation

Every input signal exists.

### Exception validation

Every affected rule exists.

### Customer input validation

Every required executable signal maps to a Customer Input Schema field.

### No orphan objects

Do not include signals or concepts that cannot be reached by any applicability rule.

### No missing dependencies

Every rule dependency must exist.

### No unsupported logic

Do not silently convert non-deterministic logic into deterministic logic.

### Verification

Only rules marked as verified in the source Requirement Pack may be marked:

```text
EXECUTABLE
```

---

# OUTPUT

Return JSON only.

Use this structure:

```json
{
  "executionPack": {
    "executionPackId": "...",
    "executionPackVersion": "...",
    "regulatoryPackId": "...",
    "regulatoryPackVersion": "...",
    "frameworkId": "...",
    "frameworkName": "...",
    "status": "READY | PARTIAL | INVALID",
    "verificationStatus": "DOMAIN_VERIFIED"
  },

  "regulatoryObjects": [],

  "signals": [],

  "derivedConcepts": [],

  "rules": [],

  "exceptions": [],

  "executionOrder": [],

  "resultResolution": {
    "statuses": [
      "APPLICABLE",
      "NOT_APPLICABLE",
      "CONDITIONALLY_APPLICABLE",
      "UNKNOWN"
    ]
  },

  "validation": {
    "status": "VALID | INVALID",
    "errors": [],
    "warnings": []
  },

  "traceability": {
    "sourceRequirementPackId": "...",
    "sourceRequirementPackVersion": "..."
  }
}
```

---

# HARD CONSTRAINT

The Assessment Execution Pack is a **compiled execution artifact**.

It must NOT contain new regulatory reasoning.

The transformation must be:

```text
VERIFIED REGULATORY REQUIREMENT PACK
                +
CUSTOMER INPUT SCHEMA
                │
                ▼
       ASSESSMENT EXECUTION PACK
```

The transformation must NOT be:

```text
Regulatory Pack
      +
AI interpretation
      ↓
New rules
```

The Assessment Execution Pack must contain only the minimum verified information necessary for deterministic applicability evaluation.

---

# FINAL QUALITY CHECK

Before returning the output, verify:

* Every executable rule exists in the source Requirement Pack.
* Every signal exists in the source Requirement Pack.
* Every derived concept exists in the source Requirement Pack.
* Every exception exists in the source Requirement Pack.
* Every regulatory object exists in the source Requirement Pack.
* Every customer-facing signal maps to the Customer Input Schema.
* No regulatory rule was invented.
* No regulatory rule was modified.
* No customer information was assumed.
* UNKNOWN is preserved.
* No narrative interpretation is being used as an executable rule.
* All executable objects are traceable to the verified Requirement Pack.
* The resulting pack is sufficient for an assessment engine to determine regulatory applicability without access to the original Regulatory Requirement Pack.
