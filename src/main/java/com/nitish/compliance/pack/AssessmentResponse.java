package com.nitish.compliance.pack;

public record AssessmentResponse(
        ApplicabilityResult applicability,
        RequirementPack requirementPack
) {
}