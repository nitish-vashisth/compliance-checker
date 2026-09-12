package com.nitish.compliance.pack;

import com.nitish.compliance.model.SignalEvidence;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RequirementPackAssessmentService {

    private final RequirementPackService requirementPackService;
    private final RequirementPackEvaluator evaluator;

    public RequirementPackAssessmentService(
            RequirementPackService requirementPackService,
            RequirementPackEvaluator evaluator
    ) {
        this.requirementPackService = requirementPackService;
        this.evaluator = evaluator;
    }

    public AssessmentResponse evaluate(
            String requirementPackId,
            Map<String, SignalEvidence> signals
    ) {
        RequirementPack pack =
                requirementPackService.findById(
                        requirementPackId
                );

        ApplicabilityResult result =
                evaluator.evaluate(
                        pack,
                        signals
                );

        return new AssessmentResponse(
                result,
                pack
        );
    }
}