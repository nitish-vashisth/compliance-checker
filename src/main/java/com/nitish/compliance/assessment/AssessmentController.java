package com.nitish.compliance.assessment;

import com.nitish.compliance.model.SignalEvidence;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(
            AssessmentService assessmentService) {

        this.assessmentService = assessmentService;
    }

    @PostMapping
    public AssessmentResult evaluate(
            @RequestBody Map<String, SignalEvidence> signals) {

        return assessmentService.evaluate(signals);
    }
}