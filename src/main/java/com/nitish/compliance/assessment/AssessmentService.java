package com.nitish.compliance.assessment;

import com.nitish.compliance.model.SignalEvidence;
import com.nitish.compliance.pack.ApplicabilityResult;
import com.nitish.compliance.pack.RequirementPack;
import com.nitish.compliance.pack.RequirementPackRepository;
import com.nitish.compliance.pack.RequirementPackEvaluator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssessmentService {

    private final RequirementPackRepository repository;
    private final RequirementPackEvaluator evaluator;

    public AssessmentService(
            RequirementPackRepository repository,
            RequirementPackEvaluator evaluator) {

        this.repository = repository;
        this.evaluator = evaluator;
    }

    public AssessmentResult evaluate(
            Map<String, SignalEvidence> signals) {

        List<ApplicabilityResult> results =
                repository.findAll()
                        .stream()
                        .filter(pack ->
                                pack.status().name().equals("APPROVED"))
                        .map(pack ->
                                evaluator.evaluate(pack, signals))
                        .toList();

        return new AssessmentResult(results);
    }
}