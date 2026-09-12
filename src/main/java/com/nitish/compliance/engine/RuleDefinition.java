package com.nitish.compliance.engine;


public record RuleDefinition(
        String id,
        String requirementId,
        RuleCondition condition
) {
}