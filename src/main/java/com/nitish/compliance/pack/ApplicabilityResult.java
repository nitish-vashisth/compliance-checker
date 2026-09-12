package com.nitish.compliance.pack;

import com.nitish.compliance.model.EvidenceConfidence;
import com.nitish.compliance.model.RuleResult;

import java.util.Map;

import com.nitish.compliance.model.RuleResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record ApplicabilityResult(
        String requirementId,
        ApplicabilityStatus status,
        Map<String, RuleResult> ruleResults,
        Set<String> missingSignals,
        List<DiscoveryQuestion> discoveryQuestions,
        Map<String, EvidenceConfidence> signalConfidence,
        List<RequirementDefinition> applicableRequirements,
        List<CapabilityMapping> capabilityMappings
) {
}