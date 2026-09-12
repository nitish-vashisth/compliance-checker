package com.nitish.compliance.pack;

import com.nitish.compliance.model.EvidenceConfidence;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RequirementPackEvaluator {

    private final RuleExpressionEvaluator expressionEvaluator;
    private final ApplicabilityDecisionService decisionService;
    Set<String> missingSignals = new LinkedHashSet<>();
    Map<String, EvidenceConfidence> signalConfidence =
            new LinkedHashMap<>();

    public RequirementPackEvaluator(
            RuleExpressionEvaluator expressionEvaluator,
            ApplicabilityDecisionService decisionService
    ) {
        this.expressionEvaluator = expressionEvaluator;
        this.decisionService = decisionService;
    }

    public ApplicabilityResult evaluate(
            RequirementPack pack,
            Map<String, SignalEvidence> signals
    ) {
        Map<String, RuleResult> ruleResults = new LinkedHashMap<>();

        for (ApplicabilityRule rule : pack.applicabilityRules()) {

            RuleResult result =
                    expressionEvaluator.evaluate(
                            rule.expression(),
                            signals
                    );

            ruleResults.put(rule.id(), result);

            if (result == RuleResult.UNKNOWN) {
                expressionEvaluator.collectMissingSignals(
                        rule.expression(),
                        signals,
                        missingSignals
                );
            }
        }

        ApplicabilityStatus status =
                decisionService.determineStatus(ruleResults);

        List<RequirementDefinition> applicableRequirements =
                status == ApplicabilityStatus.LIKELY_APPLICABLE
                        ? pack.requirements()
                        : List.of();

        List<CapabilityMapping> capabilityMappings =
                applicableRequirements.isEmpty()
                        ? List.of()
                        : pack.capabilityMappings()
                        .stream()
                        .filter(mapping ->
                                applicableRequirements.stream()
                                        .anyMatch(requirement ->
                                                requirement.id()
                                                        .equals(mapping.requirementId())))
                        .toList();

        List<DiscoveryQuestion> discoveryQuestions =
                pack.discoveryQuestions()
                        .stream()
                        .filter(question ->
                                missingSignals.contains(question.signal()))
                        .toList();

        for (Map.Entry<String, SignalEvidence> entry : signals.entrySet()) {
            signalConfidence.put(
                    entry.getKey(),
                    entry.getValue().highestConfidence()
            );
        }

        return new ApplicabilityResult(
                pack.id(),
                status,
                ruleResults,
                missingSignals,
                discoveryQuestions,
                signalConfidence,
                applicableRequirements,
                capabilityMappings
        );
    }
}