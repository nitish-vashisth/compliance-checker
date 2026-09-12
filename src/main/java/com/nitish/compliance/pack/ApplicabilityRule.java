package com.nitish.compliance.pack;

public record ApplicabilityRule(
        String id,
        String description,
        RuleExpression expression
) {
}