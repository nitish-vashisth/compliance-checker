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
}