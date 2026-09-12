package com.nitish.compliance.engine;

/*
Json
{
  "signal": "data.health_data",
  "operator": "EQUALS",
  "value": "TRUE"
}

maps to

RuleCondition
 ├── signal   = data.health_data
 ├── operator = EQUALS
 └── value    = TRUE

 Why String value for now?

Our signals already have a SignalValue abstraction, but regulatory packs will eventually need different kinds of values:

TRUE / FALSE
"EU"
"HEALTHCARE"
1000
"MODERATE"

So we'll avoid coupling RuleCondition to SignalValue at this layer. We can introduce a stronger typed value model when we have a concrete requirement for it.

Important

Don't add AND / OR or nested conditions yet. We'll introduce composite conditions in a later step once the basic JSON-driven flow works.

* */

public record RuleCondition(
        String signal,
        RuleOperator operator,
        String value
) {
}