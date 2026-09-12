package com.nitish.compliance.assessment;

import com.nitish.compliance.engine.RuleEngine;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;

import java.util.Map;

public class AssessmentService {

    private static final String HIPAA_RULE_ID = "HIPAA-001-R01";
    private static final String HIPAA_REQUIREMENT_ID = "HIPAA-001";

    private final RuleEngine ruleEngine;

    public AssessmentService(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public AssessmentResult assess(
            Map<String, SignalEvidence> signals) {

        RuleResult result =
                ruleEngine.evaluate(
                        HIPAA_RULE_ID,
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
                HIPAA_REQUIREMENT_ID,
                status,
                result
        );
    }
}