package com.nitish.compliance.pack;

import com.nitish.compliance.model.RuleResult;

import java.util.Map;

public record ApplicabilityResult(
        String requirementId,
        ApplicabilityStatus status,
        Map<String, RuleResult> ruleResults
) {
}
