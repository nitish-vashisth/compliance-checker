package com.nitish.compliance.engine.rules;

import com.nitish.compliance.model.Rule;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;
import com.nitish.compliance.model.SignalValue;

import java.util.Map;

/*
Important

This is temporary.

I don't want us ending up with:

HealthcareDataRule.java
GDPRRule.java
DORARule.java
FedRAMPRule.java
...

That would defeat the architecture.

We're writing this first concrete rule only to prove the engine.

The next iteration will move the rule definition into JSON.

* */

public class HealthcareDataRule implements Rule {

    private static final String HEALTH_DATA = "data.health_data";

    @Override
    public String id() {
        return "healthcare-data-present";
    }

    @Override
    public String requirementId() {
        return "HIPAA";
    }

    @Override
    public RuleResult evaluate(
            Map<String, SignalEvidence> signals) {

        SignalEvidence signalEvidence = signals.get(HEALTH_DATA);

        if (signalEvidence == null) {
            return RuleResult.UNKNOWN;
        }

        if (signalEvidence.signal().value() == SignalValue.TRUE) {
            return RuleResult.TRUE;
        }

        if (signalEvidence.signal().value() == SignalValue.FALSE) {
            return RuleResult.FALSE;
        }

        return RuleResult.UNKNOWN;
    }
}