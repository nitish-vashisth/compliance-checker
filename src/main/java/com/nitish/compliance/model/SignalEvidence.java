package com.nitish.compliance.model;

import java.util.List;

/*
Signal
   │
   ├── value = TRUE
   │
   └── evidence
        ├── Salesforce
        ├── questionnaire
        └── public website
* */

public record SignalEvidence(
        Signal signal,
        List<Evidence> evidence
) {

    public EvidenceConfidence highestConfidence() {

        if (evidence == null || evidence.isEmpty()) {
            return EvidenceConfidence.UNKNOWN;
        }

        if (evidence.stream()
                .anyMatch(e -> e.confidence() == EvidenceConfidence.HIGH)) {
            return EvidenceConfidence.HIGH;
        }

        if (evidence.stream()
                .anyMatch(e -> e.confidence() == EvidenceConfidence.MEDIUM)) {
            return EvidenceConfidence.MEDIUM;
        }

        if (evidence.stream()
                .anyMatch(e -> e.confidence() == EvidenceConfidence.LOW)) {
            return EvidenceConfidence.LOW;
        }

        return EvidenceConfidence.UNKNOWN;
    }

}