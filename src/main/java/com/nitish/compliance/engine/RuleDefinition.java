package com.nitish.compliance.engine;


import com.nitish.compliance.model.RuleResult;

/*
What this represents

A rule such as: HIPAA-001-R01

might conceptually be:

RuleDefinition
├── id
│   └── HIPAA-001-R01
│
├── requirementId
│   └── HIPAA-001
│
├── condition
│   ├── signal
│   │   └── data.health_data
│   ├── operator
│   │   └── EQUALS
│   └── value
│       └── TRUE
│
└── result
    └── TRUE

{
  "id": "HIPAA-001-R01",
  "requirementId": "HIPAA-001",
  "condition": {
    "signal": "data.health_data",
    "operator": "EQUALS",
    "value": "TRUE"
  },
  "result": "TRUE"
}

Why requirementId?

This is important for the architecture we're building.

A rule answers:

Does this particular applicability condition evaluate to true?

A requirement answers:

What regulatory/security requirement does this rule belong to?

So multiple rules can eventually belong to one requirement:

HIPAA-001
 ├── HIPAA-001-R01
 ├── HIPAA-001-R02
 ├── HIPAA-001-R03
 └── HIPAA-001-R04

We'll use that relationship later when we build the actual Requirement Pack.

One important decision

Notice we're using:

RuleResult result

rather than hardcoding TRUE.

That means the generic engine can eventually support rules such as:

condition → TRUE
condition → FALSE
condition → UNKNOWN

without changing the rule model.

* */
public record RuleDefinition(
        String id,
        String requirementId,
        RuleCondition condition,
        RuleResult result
) {
}