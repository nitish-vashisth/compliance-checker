package com.nitish.compliance.assessment;

import com.nitish.compliance.pack.ApplicabilityResult;

import java.util.List;

public record AssessmentResult(
        List<ApplicabilityResult> results
) {
}