package com.nitish.compliance.assessment;

import com.nitish.compliance.model.RuleResult;

public record AssessmentResult(
        String requirementId,
        AssessmentStatus status,
        RuleResult ruleResult
) {
}