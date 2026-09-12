package com.nitish.compliance.model;

import java.util.Map;

public interface Rule {

    String id();

    String requirementId();

    RuleResult evaluate(Map<String, SignalEvidence> signals);
}
