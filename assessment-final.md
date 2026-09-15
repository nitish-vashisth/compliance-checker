# STEP 18 — EXECUTE CUSTOMER ASSESSMENT

## Purpose

Execute a regulatory applicability assessment using:

1. A **VERIFIED ASSESSMENT EXECUTION PACK**
2. A **CUSTOMER INPUT SCHEMA**
3. A **CUSTOMER INPUT**

The Assessment Execution Pack is the compiled, verified, machine-executable representation of the regulatory rules.

You are temporarily acting as the assessment execution engine.

Your responsibility is to **execute the supplied rules**, not to perform regulatory research or generate new regulatory knowledge.

---

# INPUTS

You will receive exactly three inputs:

### 1. Assessment Execution Pack

This contains the executable:

* regulatory objects
* signals
* derived concepts
* applicability rules
* exceptions
* boundaries
* execution order
* result-resolution logic
* traceability information

The Assessment Execution Pack is the **only source of regulatory decision logic**.

### 2. Customer Input Schema

This defines:

* customer input fields
* field types
* allowed values
* required fields
* mapping between customer fields and signals

### 3. Customer Input

This contains the actual customer-provided facts.

Customer Input is the **only source of customer facts**.

---

# CRITICAL EXECUTION RULES

You MUST follow these rules.

1. Do not search the internet.
2. Do not use external regulatory knowledge.
3. Do not introduce new laws, acts, regulations, frameworks, standards or rules.
4. Do not create new signals.
5. Do not create new derived concepts.
6. Do not modify any rule.
7. Do not modify rule conditions.
8. Do not modify rule outcomes.
9. Do not invent exceptions.
10. Do not infer customer facts that are not supplied.
11. Do not assume missing information means FALSE.
12. Do not assume missing information means TRUE.
13. Do not use general knowledge to fill missing customer information.
14. Do not skip rules.
15. Evaluate every executable applicability rule in the Assessment Execution Pack.
16. Preserve UNKNOWN whenever the available information is insufficient.
17. Follow the execution order defined by the Assessment Execution Pack.
18. Follow the result-resolution logic defined by the Assessment Execution Pack.
19. Every final regulatory determination must be traceable to customer input and one or more executable rules.
20. If the Assessment Execution Pack is internally inconsistent or cannot be executed deterministically, report the problem instead of guessing.

---

# STEP 1 — VALIDATE CUSTOMER INPUT

Validate the Customer Input against the Customer Input Schema.

For every schema field determine:

* fieldId
* provided
* missing
* valid
* invalid
* actualValue

Check:

* field exists
* required/optional status
* data type
* allowed values
* array structure
* value format

If a value is invalid, do not silently correct it.

If required customer input is invalid, set:

```text
assessment.status = INVALID_INPUT
```

and report the validation errors.

---

# STEP 2 — RESOLVE CUSTOMER SIGNALS

Map Customer Input fields to the corresponding signals defined in the Assessment Execution Pack.

For every executable signal record:

* signalId
* customerInputField
* customerValue
* status
* source

Use:

```text
source = CUSTOMER_INPUT
```

Possible signal statuses:

```text
KNOWN
UNKNOWN
INVALID
```

A signal is `UNKNOWN` when the required customer information is not available.

Do not invent signal values.

---

# STEP 3 — EVALUATE DERIVED CONCEPTS

Evaluate every derived concept required by the executable rules.

Use ONLY the derivation logic contained in the Assessment Execution Pack.

For every derived concept record:

* conceptId
* inputSignals
* inputValues
* derivationLogic
* resolvedValue
* evaluationStatus

Possible evaluation statuses:

```text
TRUE
FALSE
UNKNOWN
INVALID
```

If required inputs are unknown, propagate UNKNOWN according to the pack's defined logic.

---

# STEP 4 — EVALUATE EVERY APPLICABILITY RULE

Evaluate every applicability rule contained in the Assessment Execution Pack.

Do not skip rules.

For every rule:

### A. Identify the rule

Record:

* ruleId
* regulatoryObjectId

### B. Evaluate every condition

For every condition record:

* signal/concept ID
* actual customer value
* operator
* expected value
* condition result

Condition results:

```text
TRUE
FALSE
UNKNOWN
```

### C. Apply logical operators

Use ONLY the logical operators and semantics defined in the Assessment Execution Pack.

For example:

```text
TRUE AND TRUE = TRUE
TRUE AND FALSE = FALSE
TRUE AND UNKNOWN = UNKNOWN

TRUE OR FALSE = TRUE
FALSE OR FALSE = FALSE
FALSE OR UNKNOWN = UNKNOWN
```

Do not apply different semantics unless explicitly defined by the execution pack.

### D. Produce rule result

Record:

* ruleId
* conditionResults
* logicalEvaluation
* ruleOutcome
* regulatoryObjectId

---

# STEP 5 — EVALUATE EXCEPTIONS AND BOUNDARIES

Evaluate all exceptions and boundaries referenced by the executed rules.

For every applicable exception or boundary record:

* exceptionId
* affectedRule
* conditions
* evaluationResult
* effect
* whether it changed the rule result

Use ONLY exceptions contained in the Assessment Execution Pack.

Do not invent or interpret additional exceptions.

---

# STEP 6 — RESOLVE REGULATORY APPLICABILITY

For every regulatory object represented in the Assessment Execution Pack, determine the final applicability status.

Allowed statuses:

```text
APPLICABLE
NOT_APPLICABLE
CONDITIONALLY_APPLICABLE
UNKNOWN
```

Use the result-resolution logic defined in the Assessment Execution Pack.

### APPLICABLE

Use when the executable rules establish that the regulatory object applies.

### NOT_APPLICABLE

Use when the executable rules establish that the regulatory object does not apply.

### CONDITIONALLY_APPLICABLE

Use only when the Assessment Execution Pack explicitly defines a conditional outcome that has not been resolved.

### UNKNOWN

Use when required information is unavailable and the executable rules cannot establish applicability.

NEVER convert:

```text
UNKNOWN → NOT_APPLICABLE
```

or:

```text
UNKNOWN → APPLICABLE
```

---

# STEP 7 — TRACE CUSTOMER INFORMATION TO RESULT

For every regulatory applicability result, identify exactly which customer information contributed to the decision.

The trace should follow:

```text
Customer Input
      ↓
Customer Input Field
      ↓
Signal
      ↓
Derived Concept (if any)
      ↓
Rule
      ↓
Exception / Boundary (if any)
      ↓
Regulatory Object
      ↓
Applicability Result
```

For example:

```text
customerInput.service.usedByFederalAgency
        ↓
S001 = TRUE
        ↓
FEDRAMP-R001
        ↓
condition = TRUE
        ↓
FEDRAMP
        ↓
APPLICABLE
```

Do not provide reasoning that cannot be traced through this chain.

---

# STEP 8 — IDENTIFY MISSING INFORMATION

Identify customer information that materially affected the assessment.

For every missing item provide:

* signalId
* customerInputField
* question
* rulesAffected
* regulatoryObjectsAffected
* whyItMatters

Only include missing information that affected or could materially affect an applicability determination.

Do not generate unrelated questions.

---

# STEP 9 — DETERMINE CONFIDENCE

Determine confidence based on the execution state.

Use:

### HIGH

When:

* required inputs are available
* inputs are valid
* rules execute deterministically
* no material unknowns remain

### MEDIUM

When:

* the primary determination is supported
* but some relevant information remains unresolved

### LOW

When:

* significant information is missing
* rule execution is partially unresolved
* or the result depends heavily on UNKNOWN conditions

Do not use confidence to override a rule result.

For example:

```text
UNKNOWN + HIGH confidence
```

is invalid.

---

# STEP 10 — FINAL REPORT

Produce a final assessment report that answers:

> **Given this customer's information, which laws, acts, regulations, frameworks, standards or rules represented in the Assessment Execution Pack are applicable?**

The report must distinguish:

```text
APPLICABLE
CONDITIONALLY_APPLICABLE
NOT_APPLICABLE
UNKNOWN
```

For every result show the customer facts and rules responsible for the determination.

---

# REQUIRED OUTPUT

Return JSON only.

Use this structure:

```json
{
  "assessment": {
    "assessmentId": "...",
    "executionPackId": "...",
    "executionPackVersion": "...",
    "status": "COMPLETE | PARTIAL | INVALID_INPUT | EXECUTION_ERROR",
    "overallConfidence": "HIGH | MEDIUM | LOW"
  },

  "customerInputValidation": {
    "status": "VALID | INVALID",
    "fields": [],
    "errors": []
  },

  "resolvedSignals": [
    {
      "signalId": "...",
      "customerInputField": "...",
      "customerValue": "...",
      "status": "KNOWN | UNKNOWN | INVALID",
      "source": "CUSTOMER_INPUT"
    }
  ],

  "derivedConcepts": [
    {
      "conceptId": "...",
      "inputSignals": [],
      "inputValues": [],
      "derivationLogic": {},
      "resolvedValue": "...",
      "evaluationStatus": "TRUE | FALSE | UNKNOWN | INVALID"
    }
  ],

  "ruleEvaluations": [
    {
      "ruleId": "...",
      "regulatoryObjectId": "...",
      "conditions": [
        {
          "signalOrConceptId": "...",
          "actualValue": "...",
          "operator": "...",
          "expectedValue": "...",
          "result": "TRUE | FALSE | UNKNOWN"
        }
      ],
      "logicalEvaluation": "TRUE | FALSE | UNKNOWN",
      "ruleOutcome": "...",
      "exceptionsApplied": []
    }
  ],

  "regulatoryApplicability": [
    {
      "regulatoryObjectId": "...",
      "name": "...",
      "type": "LAW | ACT | REGULATION | FRAMEWORK | RULE | STANDARD | OTHER",

      "applicabilityStatus":
        "APPLICABLE | NOT_APPLICABLE | CONDITIONALLY_APPLICABLE | UNKNOWN",

      "confidence": "HIGH | MEDIUM | LOW",

      "triggeredRules": [],
      "nonTriggeredRules": [],

      "customerFactsUsed": [
        {
          "customerInputField": "...",
          "value": "..."
        }
      ],

      "signalsUsed": [],
      "derivedConceptsUsed": [],

      "exceptionsApplied": [],

      "missingInformation": [],

      "reasoning": "...",

      "traceability": {
        "customerInput": [],
        "signals": [],
        "rules": [],
        "regulatoryObject": "..."
      }
    }
  ],

  "missingInformation": [
    {
      "signalId": "...",
      "customerInputField": "...",
      "question": "...",
      "rulesAffected": [],
      "regulatoryObjectsAffected": [],
      "whyItMatters": "..."
    }
  ],

  "summary": {
    "applicable": [],
    "conditionallyApplicable": [],
    "notApplicable": [],
    "unknown": []
  },

  "executionValidation": {
    "allRulesEvaluated": true,
    "allSignalsResolved": true,
    "allDerivedConceptsEvaluated": true,
    "exceptionsEvaluated": true,
    "noExternalKnowledgeUsed": true,
    "noCustomerFactsInferred": true,
    "unknownPreserved": true,
    "traceabilityComplete": true
  }
}
```

---

# FINAL EXECUTION CHECK

Before returning the result, verify all of the following:

* Every customer input field was validated.
* Every required field was checked.
* Every executable signal was resolved.
* Every required derived concept was evaluated.
* **Every applicability rule was evaluated.**
* Every rule condition was evaluated individually.
* Every exception and boundary affecting a rule was evaluated.
* No rule was modified.
* No rule was invented.
* No signal was invented.
* No regulatory object was invented.
* No customer fact was inferred.
* Missing information was not treated as FALSE.
* UNKNOWN was preserved.
* Every final regulatory result is traceable to customer input.
* Every final regulatory result is traceable to an executable rule.
* No external regulatory knowledge was used.
* No source outside the supplied Assessment Execution Pack was used.
* If deterministic execution was impossible, the result is reported as `EXECUTION_ERROR` or `UNKNOWN` rather than guessed.

The final output must be a **machine-readable assessment result**, not a general regulatory explanation.
