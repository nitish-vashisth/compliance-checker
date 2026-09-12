package com.nitish.compliance.pack;

import com.nitish.compliance.engine.RuleDefinition;
import com.nitish.compliance.engine.RuleEngine;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RequirementPackEvaluator {

    private final RuleEngine ruleEngine;
    private final ApplicabilityRuleMapper ruleMapper;
    private final ApplicabilityDecisionService decisionService;

    public RequirementPackEvaluator(
            RuleEngine ruleEngine,
            ApplicabilityRuleMapper ruleMapper,
            ApplicabilityDecisionService decisionService
    ) {
        this.ruleEngine = ruleEngine;
        this.ruleMapper = ruleMapper;
        this.decisionService = decisionService;
    }

    public ApplicabilityResult evaluate(
            RequirementPack pack,
            Map<String, SignalEvidence> signals
    ) {
        Map<String, RuleResult> ruleResults =
                new LinkedHashMap<>();

        for (ApplicabilityRule rule : pack.applicabilityRules()) {

            RuleDefinition ruleDefinition =
                    ruleMapper.map(
                            rule,
                            pack.id()
                    );

            RuleResult result =
                    ruleEngine.evaluate(
                            ruleDefinition,
                            signals
                    );

            ruleResults.put(rule.id(), result);
        }

        ApplicabilityStatus status =
                decisionService.determineStatus(ruleResults);

        return new ApplicabilityResult(
                pack.id(),
                status,
                ruleResults
        );
    }
}