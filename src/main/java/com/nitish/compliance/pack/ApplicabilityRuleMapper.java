package com.nitish.compliance.pack;

import com.nitish.compliance.engine.RuleCondition;
import com.nitish.compliance.engine.RuleDefinition;
import com.nitish.compliance.engine.RuleOperator;
import org.springframework.stereotype.Component;

@Component
public class ApplicabilityRuleMapper {

    public RuleDefinition map(
            ApplicabilityRule rule,
            String requirementId
    ) {
        String[] parts = rule.expression().split("==", 2);

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Unsupported applicability expression: "
                            + rule.expression()
            );
        }

        String signal = parts[0].trim();
        String value = parts[1].trim();

        return new RuleDefinition(
                rule.id(),
                requirementId,
                new RuleCondition(
                        signal,
                        RuleOperator.EQUALS,
                        value
                )
        );
    }
}