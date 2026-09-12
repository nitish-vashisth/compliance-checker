package com.nitish.compliance.assessment;

import com.nitish.compliance.engine.RuleEngine;
import com.nitish.compliance.engine.rules.HealthcareDataRule;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;

import java.util.Map;

public class AssessmentService {

    private final RuleEngine ruleEngine;
    private final HealthcareDataRule healthcareDataRule;

    public AssessmentService(
            RuleEngine ruleEngine,
            HealthcareDataRule healthcareDataRule) {

        this.ruleEngine = ruleEngine;
        this.healthcareDataRule = healthcareDataRule;
    }

    public AssessmentResult assess(
            Map<String, SignalEvidence> signals) {

        RuleResult result =
                ruleEngine.evaluate(
                        healthcareDataRule,
                        signals);

        AssessmentStatus status = switch (result) {

            case TRUE ->
                    AssessmentStatus.POTENTIALLY_APPLICABLE;

            case FALSE ->
                    AssessmentStatus.NOT_APPLICABLE;

            case UNKNOWN ->
                    AssessmentStatus.UNKNOWN;
        };

        return new AssessmentResult(
                healthcareDataRule.requirementId(),
                status,
                result
        );
    }
}