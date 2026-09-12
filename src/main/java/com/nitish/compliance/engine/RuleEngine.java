package com.nitish.compliance.engine;

import com.nitish.compliance.model.Rule;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;

import java.util.Map;

/*
This looks almost ridiculously simple.

That's intentional.

We're establishing the boundary first.

Later this class will handle:

rule loading
dependency resolution
derived concepts
three-valued logic
conflict detection
rule evaluation
explanation generation

But we don't need any of that yet.

* */

public class RuleEngine {

    public RuleResult evaluate(
            Rule rule,
            Map<String, SignalEvidence> signals) {

        return rule.evaluate(signals);
    }
}
