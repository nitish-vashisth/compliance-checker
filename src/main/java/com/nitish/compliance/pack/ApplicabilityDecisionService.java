package com.nitish.compliance.pack;

import com.nitish.compliance.model.RuleResult;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ApplicabilityDecisionService {

    public ApplicabilityStatus determineStatus(
            Map<String, RuleResult> ruleResults) {

        if (ruleResults == null || ruleResults.isEmpty()) {
            return ApplicabilityStatus.INSUFFICIENT_INFORMATION;
        }

        boolean hasUnknown = false;

        for (RuleResult result : ruleResults.values()) {

            if (result == RuleResult.TRUE) {
                return ApplicabilityStatus.LIKELY_APPLICABLE;
            }

            if (result == RuleResult.UNKNOWN) {
                hasUnknown = true;
            }
        }

        if (hasUnknown) {
            return ApplicabilityStatus.INSUFFICIENT_INFORMATION;
        }

        return ApplicabilityStatus.NOT_CURRENTLY_INDICATED;
    }
}