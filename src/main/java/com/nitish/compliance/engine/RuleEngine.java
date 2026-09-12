package com.nitish.compliance.engine;

import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;

import java.util.Map;

/*


* */

import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RuleEngine {

    private final RuleRepository ruleRepository;
    private final RuleEvaluator ruleEvaluator;

    public RuleEngine(
            RuleRepository ruleRepository,
            RuleEvaluator ruleEvaluator) {

        this.ruleRepository = ruleRepository;
        this.ruleEvaluator = ruleEvaluator;
    }

    public RuleResult evaluate(
            String ruleId,
            Map<String, SignalEvidence> signals) {

        RuleDefinition rule = ruleRepository
                .findById(ruleId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Rule not found: " + ruleId
                        )
                );

        return ruleEvaluator.evaluate(rule, signals);
    }
}