package com.nitish.compliance.model;


/*
data.health_data = TRUE
organisation.us_government_customer = FALSE

The signal doesn't know where it came from.

Salesforce
Questionnaire
Public website
DC collector
Manual input
AI

That's evidence's responsibility.
* */

import com.nitish.compliance.engine.SignalData;

public record Signal(
        String id,
        SignalData value
) {
}